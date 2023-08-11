package com.szs.app.controller;

import com.szs.app.auth.exception.AnnualIncomeDataScrapedException;
import com.szs.app.auth.exception.FailedScrapException;
import com.szs.app.auth.exception.UserNotFoundException;
import com.szs.app.auth.exception.handler.ErrorCode;
import com.szs.app.global.encoder.CustomPasswordEncoder;
import com.szs.app.domain.response.AnnualIncomeResponse;
import com.szs.app.domain.entity.AnnualIncome;
import com.szs.app.domain.entity.User;
import com.szs.app.domain.response.RefundResponse;
import com.szs.app.global.scrap.response.ApiResponseData;
import com.szs.app.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
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

  private final CustomPasswordEncoder passwordEncoder;

  @PostMapping("/scrap")
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "연말정산 스크랩", description = "인증 토큰을 이용하여 자기 정보만 스크랩")
  public ResponseEntity<?> scrap() {
    try {
      User currentUser = userService.getCurrentUser();
      String regNumber = currentUser.getRegNoFront() + "-" + passwordEncoder.decode(currentUser.getRegNoBack());
      ApiResponseData scrap = yearEndTaxScrapHistoryService.scrap(currentUser.getName(), regNumber);
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
    } catch (Exception exception) {
      log.warn(exception.getMessage(), exception.getCause());
      throw new RuntimeException(exception.getMessage(), exception.getCause());
    }
  }

  @PostMapping("/refund")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> refund() {
    try {
      User currentUser = userService.getCurrentUser();
      List<AnnualIncome> calculatedAnnualIncomes = annualIncomeCompoundService.getCalculatedAnnualIncomesByUserId(currentUser.getId());
      return ResponseEntity.ok(calculatedAnnualIncomes.stream().map(RefundResponse::from).collect(Collectors.toList()));

    } catch (Exception exception) {
      log.warn(exception.getMessage(), exception.getCause());
      throw new RuntimeException(exception.getMessage(), exception.getCause());
    }
  }
}
