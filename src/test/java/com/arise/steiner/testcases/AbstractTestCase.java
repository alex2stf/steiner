package com.arise.steiner.testcases;

import com.arise.steiner.IntegrationTest;
import com.arise.steiner.config.ApplicationProperties;
import java.util.function.Supplier;

public abstract class AbstractTestCase implements Supplier<Boolean> {

  protected final IntegrationTest test;

  public AbstractTestCase(IntegrationTest integrationTest){
    this.test = integrationTest;
  }

  @Override
  public final Boolean get() {
    return executeTest();
  }

  protected abstract Boolean executeTest();
}
