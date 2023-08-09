package com.szs.app.controller;

import com.szs.app.domain.entity.User;
import com.szs.app.domain.type.RoleType;
import com.szs.app.global.scrap.response.ApiResponse;
import com.szs.app.repository.UserRepository;
import com.szs.app.service.ScrapedDataService;
import com.szs.app.service.YearEndTaxScrapHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class RefundController {

  private final YearEndTaxScrapHistoryService yearEndTaxScrapHistoryService;

  private final ScrapedDataService scrapedDataService;

  private final UserRepository userRepository;

  @PostMapping("/scrap")
  public ResponseEntity<?> scrap() {
    //FIXME 더미데이터
    // userRepository.save(user);
    // ApiResponse scrap = yearEndTaxScrapHistoryService.scrap(user.getName(), user.getRegNoFront(), user.getRegNoBack());
    // scrapedDataService.saveScrappedData(scrap, user);
    return ResponseEntity.ok(null);
  }
}