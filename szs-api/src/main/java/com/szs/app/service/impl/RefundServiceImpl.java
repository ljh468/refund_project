package com.szs.app.service.impl;

import com.szs.app.domain.entity.Refund;
import com.szs.app.repository.RefundRepository;
import com.szs.app.service.RefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

  private final RefundRepository refundRepository;

  @Override
  @Transactional
  public void save(Refund refund) {
    refundRepository.save(refund);
  }
}
