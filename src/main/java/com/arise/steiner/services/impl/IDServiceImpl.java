package com.arise.steiner.services.impl;

import com.arise.steiner.services.IDService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class IDServiceImpl implements IDService {

  @Override
  public String next() {
    return UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
  }
}
