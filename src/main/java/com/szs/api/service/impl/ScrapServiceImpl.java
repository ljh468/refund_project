package com.szs.api.service.impl;

import com.szs.api.repository.ScrapRepository;
import com.szs.api.service.ScrapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScrapServiceImpl implements ScrapService {

  private final ScrapRepository scrapRepository;

  @Autowired
  public ScrapServiceImpl(ScrapRepository scrapRepository) {
    this.scrapRepository = scrapRepository;
  }

}
