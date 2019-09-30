package com.arise.steiner.config;

import com.arise.steiner.client.SecurityMode;
import com.arise.steiner.names.Props;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * implements configuration {@link SecurityNoneConfig}
 * if {@link Props#SECURITY_MODE} is not defined or has value 'NONE' or 'LOOSE'
 */
public class ConditionIgnoreSecurity implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
    String testValue = (context.getEnvironment().getProperty(Props.SECURITY_MODE));
    return (!StringUtils.hasText(testValue)) || SecurityMode.NONE.name().equalsIgnoreCase(testValue) || SecurityMode.LOOSE.name().equalsIgnoreCase(testValue);
  }
}
