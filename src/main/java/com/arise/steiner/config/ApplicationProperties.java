package com.arise.steiner.config;

import com.arise.steiner.client.SecurityMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
@ComponentScan
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true, ignoreInvalidFields = true)
public class ApplicationProperties {

  public static final String APP_ID = UUID.randomUUID().toString();

  String user;
  String password;
  String secret;
  Enablers enable;
  SecurityMode securityMode;

  public boolean isSecurityNone(){
    return securityMode == null || SecurityMode.NONE.equals(securityMode);
  }

  public boolean isSecurityLoose(){
    return SecurityMode.LOOSE.equals(securityMode);
  }

  public SecurityMode getSecurityMode() {
    return securityMode;
  }

  public void setSecurityMode(SecurityMode securityMode) {
    this.securityMode = securityMode;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public Enablers getEnable() {
    return enable;
  }

  public void setEnable(Enablers enable) {
    this.enable = enable;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isDebugEnabled(){
    return enable != null && Boolean.TRUE.equals(enable.debug);
  }

  private class Enablers {



    Boolean debug;

    public Boolean getDebug() {
      return debug;
    }

    public void setDebug(Boolean debug) {
      this.debug = debug;
    }

  }


}
