package com.arise.steiner.config;

import com.arise.steiner.client.SecurityMode;
import com.arise.steiner.names.Props;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConditionLoginEnable implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
    String securityMode = (context.getEnvironment().getProperty(Props.SECURITY_MODE));
    return SecurityMode.JWT_LOGIN.name().equalsIgnoreCase(securityMode);
  }
}
