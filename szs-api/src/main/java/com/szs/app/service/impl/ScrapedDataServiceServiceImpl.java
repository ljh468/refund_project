package com.szs.app.service.impl;

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
  public void saveScrappedData(ApiResponse scrap, User user) {
    DataResponse data = scrap.getData();
    if (nonNull(data)) {
      //FIXME AnnualIncome(연간소득)데이터가 존재하는지 확인하는 로직 필요
      YearEndTaxScrapHistory scrapHistory = saveYearEndYaxScrapHistory(data, user);
      AnnualIncome annualIncome = generateAnnualIncome(user, scrapHistory, data);
      AnnualIncome newAnnualIncome = annualIncomeRepository.save(annualIncome);
      AtomicReference<Long> totalIncome = saveIncomeSalary(data, newAnnualIncome);
      newAnnualIncome.updateIncomeTotal(totalIncome.get());
      saveDeduction(data, newAnnualIncome);
      saveAnnualIncome(newAnnualIncome);
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
                                                                .build();
    scrapHistory.addUser(user);
    return yearEndTaxScrapHistoryService.save(scrapHistory);
  }

  @Transactional
  public void saveAnnualIncome(AnnualIncome annualIncome) {
    annualIncomeService.save(annualIncome);
  }

  @Transactional
  public AtomicReference<Long> saveIncomeSalary(DataResponse data, AnnualIncome annualIncome) {
    AtomicReference<Long> totalIncome = new AtomicReference<>(0L);
    List<IncomeSalary> incomeSalaries = new ArrayList<>();
    data.getJsonList().getIncomeSalaries().forEach(
        response -> {
          totalIncome.updateAndGet(total -> total + response.getPaymentTotal());
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
        });
    deductionService.saveAll(deductions);
  }
}
