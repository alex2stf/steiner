package com.arise.steiner.services;

import com.arise.steiner.config.ApplicationProperties;
import com.arise.steiner.dto.User;
import com.arise.steiner.errors.SteinerException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

@Component
public class AuthorizationDecoder {

  static final Gson gson = new Gson();


  final OwnerService ownerService;

  final ApplicationProperties applicationProperties;

  final String userKey;


  public AuthorizationDecoder(
      OwnerService ownerService, ApplicationProperties applicationProperties) {
    this.ownerService = ownerService;

    this.applicationProperties = applicationProperties;
    this.userKey = applicationProperties.getSecret() + "user";
  }


  public void register(User user, HttpServletRequest request) {
    request.setAttribute(userKey, user);
  }

  public User decode(HttpServletRequest request) throws SteinerException {
    if (request.getAttribute(userKey) != null) {
      return (User) request.getAttribute(userKey);
    }

    String payload = getAuthorizationHeader(request);

    User user = decode(payload);
    register(user, request);
    return user;
  }

  public String getAuthorizationHeader(HttpServletRequest request) {
    String payload = request.getHeader("Authorization");
    if (!StringUtils.hasText(payload)) {
      payload = request.getHeader("authorization");
    }
    return payload;
  }

  public String resolveBearer(String payload) {
    if (StringUtils.hasText(payload) && payload.startsWith("Bearer ")) {
      return payload.substring(7, payload.length());
    }
    return null;
  }

  private User decode(String payload) throws SteinerException {
    User user = null;
    if (applicationProperties.isSecurityNone()) {
      return ownerService.findUserByName(payload);
    }
    String data = resolveBearer(payload);

    if (data != null) {
      user = decodeBearer(data);
    } else if (payload.startsWith("Basic ")) {
      user = decodeBasicAuth(payload);
    }
    return user;
  }

  private User decodeBasicAuth(String payload) throws SteinerException {
    String auth = payload.split(" ")[1];
    byte[] bytes = Base64Utils.decodeFromString(auth);
    String decoded = new String(bytes, Charset.forName("UTF-8"));
    String parts[] = decoded.split(":");
    if (applicationProperties.isSecurityLoose()) {
      return ownerService.findUserByName(parts[0]);
    }
    return ownerService.loginUser(parts[0], parts[1]);
  }

  private User decodeBearer(String payload) throws SteinerException {
    JsonObject jsonObject = gson.fromJson(
        JwtHelper.decode(payload).getClaims(),
        JsonObject.class
    );
    return ownerService.findUserByName(jsonObject.get("email").getAsString());
  }


}
