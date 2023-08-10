package com.szs.app.controller;

import com.szs.app.domain.response.AnnualIncomeResponse;
import com.szs.app.domain.entity.AnnualIncome;
import com.szs.app.domain.entity.User;
import com.szs.app.domain.input.ScrapInput;
import com.szs.app.global.scrap.response.ApiResponse;
import com.szs.app.service.ScrapedDataService;
import com.szs.app.service.UserService;
import com.szs.app.service.YearEndTaxScrapHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class RefundController {

  private final YearEndTaxScrapHistoryService yearEndTaxScrapHistoryService;

  private final ScrapedDataService scrapedDataService;

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  @PostMapping("/scrap")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> scrap(@Validated @RequestBody ScrapInput input) {


    User currentUser = userService.getCurrentUser();
    String[] regArr = input.getRegNo().split("-");
    if (passwordEncoder.matches(regArr[1], currentUser.getRegNoBack())) {
      ApiResponse scrap = yearEndTaxScrapHistoryService.scrap(input.getName(), input.getRegNo());
      AnnualIncome annualIncome = scrapedDataService.saveScrappedData(scrap, currentUser);
      return ResponseEntity.ok(AnnualIncomeResponse.from(annualIncome));
    } else {
      // 예외 처리 필요
      // return ResponseEntity.badRequest().body("가입한 유저와 주민등록번호 뒷자리가 일치하지 않습니다.");
      return null;
    }
  }
}
