package com.szs.app.controller;

import com.szs.app.auth.exception.AnnualIncomeDataScrapedException;
import com.szs.app.auth.exception.FailedScrapException;
import com.szs.app.auth.exception.UserNotFoundException;
import com.szs.app.auth.exception.handler.ErrorCode;
import com.szs.app.domain.response.AnnualIncomeResponse;
import com.szs.app.domain.entity.AnnualIncome;
import com.szs.app.domain.entity.User;
import com.szs.app.domain.input.ScrapInput;
import com.szs.app.domain.response.RefundResponse;
import com.szs.app.global.scrap.response.ApiResponse;
import com.szs.app.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class RefundController {

  private final YearEndTaxScrapHistoryService yearEndTaxScrapHistoryService;

  private final ScrapedDataService scrapedDataService;

  private final UserService userService;

  private final AnnualIncomeCompoundService annualIncomeCompoundService;

  private final PasswordEncoder passwordEncoder;

  @PostMapping("/scrap")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> scrap(@Validated @RequestBody ScrapInput input) {
    try {
      User currentUser = userService.getCurrentUser();
      ApiResponse scrap = yearEndTaxScrapHistoryService.scrap(input.getName(), input.getRegNo());
      if (isNull(scrap.getErrors()) || scrap.getErrors().isEmpty()) {
        AnnualIncome annualIncome = scrapedDataService.saveScrappedData(scrap, currentUser);
        return ResponseEntity.ok(AnnualIncomeResponse.from(annualIncome));
      } else {
        throw new FailedScrapException(ErrorCode.E0003, scrap.getErrors().get("message"));
      }

    } catch (UserNotFoundException userNotFoundException) {
      log.debug("user not found");
      throw userNotFoundException;
    } catch (BadCredentialsException badCredentialsException) {
      log.debug("bad credentials");
      throw badCredentialsException;
    } catch (AnnualIncomeDataScrapedException annualIncomeDataScrapedException) {
      log.debug("annualIncome data already exist");
      throw annualIncomeDataScrapedException;
    } catch (RuntimeException runtimeException) {
      log.warn(runtimeException.getMessage(), runtimeException.getCause());
      throw runtimeException;
    }
  }

  @PostMapping("/refund")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> refund() {
    try {
      User currentUser = userService.getCurrentUser();
      List<AnnualIncome> calculatedAnnualIncomes = annualIncomeCompoundService.getCalculatedAnnualIncomesByUserId(currentUser.getId());
      for (AnnualIncome calculatedAnnualIncome : calculatedAnnualIncomes) {
        System.out.println(".getIncomeYear() = " + calculatedAnnualIncome.getIncomeYear());
        System.out.println(".getRefund().getDeterminedTax() = " + calculatedAnnualIncome.getRefund().getDeterminedTax());
        System.out.println(".getRefund().getRetireTaxCredit() = " + calculatedAnnualIncome.getRefund().getRetireTaxCredit());
      }
      return ResponseEntity.ok(calculatedAnnualIncomes.stream().map(RefundResponse::from).collect(Collectors.toList()));

    } catch (RuntimeException runtimeException) {
      log.warn(runtimeException.getMessage(), runtimeException.getCause());
      throw runtimeException;
    }
  }
}
