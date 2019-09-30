package com.arise.steiner;

import com.arise.steiner.client.SecurityMode;

public class SecurityBasicTest  extends IntegrationTest {

  @Override
  public SecurityMode getSecurityMode() {
    return SecurityMode.BASIC_AUTH;
  }
}
