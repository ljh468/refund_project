package com.szs.app.service.impl;

import com.szs.app.auth.exception.AnnualIncomeDataAlreadyExistsException;
import com.szs.app.domain.entity.*;
import com.szs.app.domain.type.PaymentType;
import com.szs.app.global.scrap.response.ApiResponse;
import com.szs.app.global.scrap.response.DataResponse;
import com.szs.app.repository.AnnualIncomeRepository;
import com.szs.app.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapedDataServiceServiceImpl implements ScrapedDataService {

  private final YearEndTaxScrapHistoryService yearEndTaxScrapHistoryService;

  private final AnnualIncomeService annualIncomeService;

  private final IncomeSalaryService incomeSalaryService;

  private final DeductionService deductionService;

  private final AnnualIncomeRepository annualIncomeRepository;

  @Override
  @Transactional
  public AnnualIncome saveScrappedData(ApiResponse scrap, User user) {
    DataResponse data = scrap.getData();
    if (nonNull(data)) {
      
      // 동일년도의 연말정산 소득데이터가 존재하는지 확인
      String latestYear = String.valueOf(data.getJsonList().getIncomeSalaries().get(0).getPaymentDate().getYear());
      checkExistsIncomeDataForScrapedYear(user.getId(), latestYear);

      // 연말정산 스크랩 히스토리 저장
      YearEndTaxScrapHistory scrapHistory = saveYearEndYaxScrapHistory(data, user);

      // 연간소득 데이터 초기저장 -> id값 생성
      AnnualIncome newAnnualIncome = annualIncomeRepository.save(generateAnnualIncome(user, scrapHistory, data));

      // 급여 소득 데이터 저장 -> 총 소득을 계산해서 연간소득 데이터에 반영
      AtomicReference<Long> totalIncome = saveIncomeSalary(data, newAnnualIncome);
      newAnnualIncome.updateIncomeTotal(totalIncome.get());

      // 소득공제 데이터 저장
      saveDeduction(data, newAnnualIncome);

      // 연간소득 데이터 최종저장 -> 총 소득 반영
      return saveAnnualIncome(newAnnualIncome);
    }
    return null;
  }

  private void checkExistsIncomeDataForScrapedYear(String userId, String latestYear) {
    AnnualIncome annualIncome = annualIncomeService.findByUserIdAndIncomeYearNotDeleted(userId, latestYear);
    if(nonNull(annualIncome) && nonNull(annualIncome.getIncomeTotal())) {
      throw new AnnualIncomeDataAlreadyExistsException(userId, latestYear);
    }
  }

  private AnnualIncome generateAnnualIncome(User user, YearEndTaxScrapHistory scrapHistory, DataResponse data) {
    AnnualIncome annualIncome = AnnualIncome.builder()
                                            .incomeYear(String.valueOf(data.getJsonList().getIncomeSalaries().get(0).getPaymentDate().getYear()))
                                            .calculatedTax(data.getJsonList().getCalculatedTax())
                                            .isDeleted(false)
                                            .build();
    annualIncome.addUser(user);
    annualIncome.addYearEndTaxScrapHistory(scrapHistory);
    return annualIncome;
  }

  @Transactional
  public YearEndTaxScrapHistory saveYearEndYaxScrapHistory(DataResponse data, User user) {
    YearEndTaxScrapHistory scrapHistory = YearEndTaxScrapHistory.builder()
                                                                .appVer(data.getAppVer())
                                                                .errMsg(data.getErrMsg().isBlank() ? null : data.getErrMsg())
                                                                .svcCd(data.getSvcCd())
                                                                .company(data.getCompany())
                                                                .hostNm(data.getHostNm())
                                                                .workerResDt(data.getWorkerResDt())
                                                                .workerReqDt(data.getWorkerReqDt())
                                                                .createdAt(LocalDateTime.now())
                                                                .build();
    scrapHistory.addUser(user);
    return yearEndTaxScrapHistoryService.save(scrapHistory);
  }

  @Transactional
  public AnnualIncome saveAnnualIncome(AnnualIncome annualIncome) {
    return annualIncomeService.save(annualIncome);
  }

  @Transactional
  public AtomicReference<Long> saveIncomeSalary(DataResponse data, AnnualIncome annualIncome) {
    AtomicReference<Long> totalIncome = new AtomicReference<>(0L);
    List<IncomeSalary> incomeSalaries = new ArrayList<>();
    data.getJsonList().getIncomeSalaries().forEach(
        response -> {
          if (nonNull(response.getPaymentTotal())) {
            totalIncome.updateAndGet(total -> total + response.getPaymentTotal());
          }
          IncomeSalary incomeSalary = IncomeSalary.builder()
                                                  .incomeDetail(response.getIncomeDetail())
                                                  .incomeType(response.getIncomeType())
                                                  .companyName(response.getCompanyName())
                                                  .companyRegNo(response.getCompanyRegNo())
                                                  .paymentTotal(response.getPaymentTotal())
                                                  .paymentDate(response.getPaymentDate())
                                                  .workStartDate(response.getWorkStartDate())
                                                  .workEndDate(response.getWorkEndDate())
                                                  .build();
          incomeSalary.addAnnualIncome(annualIncome);
          incomeSalaries.add(incomeSalary);
        }
    );
    incomeSalaryService.saveAll(incomeSalaries);
    return totalIncome;
  }

  @Transactional
  public void saveDeduction(DataResponse data, AnnualIncome annualIncome) {
    List<Deduction> deductions = new ArrayList<>();
    data.getJsonList().getDeductions().forEach(
        response -> {
          Deduction deduction = Deduction.builder()
                                         .paymentType(PaymentType.fromName(response.getPaymentType()))
                                         .paymentAmount(response.getPaymentAmount())
                                         .build();
          deduction.addAnnualIncome(annualIncome);
          deductions.add(deduction);
        }
    );
    deductionService.saveAll(deductions);
  }
}
