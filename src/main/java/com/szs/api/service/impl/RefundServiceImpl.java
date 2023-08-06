package com.szs.api.service.impl;

import com.szs.api.repository.RefundRepository;
import com.szs.api.service.RefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefundServiceImpl implements RefundService {

  private final RefundRepository refundRepository;

  @Autowired
  public RefundServiceImpl(RefundRepository refundRepository) {
    this.refundRepository = refundRepository;
  }

}
