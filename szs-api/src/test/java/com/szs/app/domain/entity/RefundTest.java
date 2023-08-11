package com.szs.app.domain.entity;

import com.szs.app.domain.type.PaymentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RefundTest {

  private final Long calculatedTax = 3000000L;

  private Deduction insurance = new Deduction(PaymentType.INSURANCE, 100000L);

  private Deduction education = new Deduction(PaymentType.EDUCATION, 200000L);

  private Deduction donation = new Deduction(PaymentType.DONATION, 150000L);

  private Deduction medical = new Deduction(PaymentType.MEDICAL, 4400000L);

  private List<Deduction> deductions = List.of(insurance, education, donation, medical);

  @Test
  void 근로소득공제금액이_정상적으로_계산되는지_확인한다() {
    assertEquals(1650000L, Refund.calculatedWorkIncomeCredit(calculatedTax)); // 300만원 -> 165만원
    assertEquals(0L, Refund.calculatedWorkIncomeCredit(0)); // 0원 -> 0원
  }

  @Test
  void 보험료_교육비_기부금_의료비에_해당하는_특별세액공제금액이_정상적으로_계산되는지_확인한다() {
    Deduction insurance = new Deduction(PaymentType.INSURANCE, 100000L);
    Deduction education = new Deduction(PaymentType.EDUCATION, 200000L);
    Deduction donation = new Deduction(PaymentType.DONATION, 150000L);
    Deduction medical = new Deduction(PaymentType.MEDICAL, 4400000L);

    List<Deduction> deductions = List.of(insurance, education, donation, medical);

    assertEquals(454500L, Refund.calculatedSpecialCredit(deductions, 60000000L));
    assertEquals(319500L, Refund.calculatedSpecialCredit(deductions, 90000000L));
    assertEquals(724500L, Refund.calculatedSpecialCredit(deductions, 0L));
  }

  @Test
  void 특별세액공제금액에_따른_표준세액공제금액이_정상적으로_계산되는지_확인한다() {
    assertEquals(0L, Refund.calculatedStandardCredit(454500L));
    assertEquals(130000L, Refund.calculatedStandardCredit(12999L));
    assertEquals(130000L, Refund.calculatedStandardCredit(0L));
  }

  @Test
  void 퇴직급여납입금액이_주어졌을때_퇴직급여공제금액_정상적으로_계산되는지_확인한다() {
    Deduction retirement = new Deduction(PaymentType.RETIREMENT, 6000000L);
    List<Deduction> deductions = List.of(retirement);
    assertEquals(900000L, Refund.calculatedRetirementCredit(deductions));
  }
}