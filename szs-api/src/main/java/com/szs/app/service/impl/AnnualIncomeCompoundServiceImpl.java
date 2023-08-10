package com.szs.app.service.impl;

import com.szs.app.domain.entity.AnnualIncome;
import com.szs.app.domain.entity.Refund;
import com.szs.app.repository.RefundRepository;
import com.szs.app.service.AnnualIncomeService;
import com.szs.app.service.AnnualIncomeCompoundService;
import com.szs.app.service.RefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnualIncomeCompoundServiceImpl implements AnnualIncomeCompoundService {

  private final AnnualIncomeService annualIncomeService;

  private final RefundService refundService;

  /**
   * 1. [총소득 1 : 환급액 1] 모든 연간 총소득 데이터 조회
   * 2. 연간총소득(AnnualIncome)에 해당하는 환급액(Refund)을 조회
   * 3. AnnualIncome에 해당하는 계산된 Refund가 없으면 계산하고 저장
   * @param userId
   * @return List<AnnualIncome>
   */
  @Override
  @Transactional
  public List<AnnualIncome> getCalculatedAnnualIncomesByUserId(String userId) {
    List<AnnualIncome> annualIncomes = annualIncomeService.findAllByUserId(userId);
    annualIncomes.forEach(annualIncome -> {
      if (isNull(annualIncome.getRefund())) {
        // NOTE: 환급액 계산
        annualIncome.calculateRefund();
        annualIncome.setRefundCalculated(true);
        annualIncomeService.save(annualIncome);

        // NOTE: 환급액 저장
        annualIncome.getRefund().addUser(annualIncome.getUser());
        refundService.save(annualIncome.getRefund());
      }
    });
    return annualIncomes;
  }
}
