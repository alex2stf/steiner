package com.arise.steiner.controllers;

import com.arise.steiner.client.LoginRequest;
import com.arise.steiner.config.ConditionLoginEnable;
import com.arise.steiner.config.SecurityJWTLogin;
import com.arise.steiner.dto.User;
import com.arise.steiner.entities.Owner;
import com.arise.steiner.errors.SteinerException;
import com.arise.steiner.names.Routes;
import com.arise.steiner.services.OwnerService;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * following constroller exists only if {@link ConditionLoginEnable} matches
 */
@RestController
@RequestMapping("/api")
@Conditional(ConditionLoginEnable.class)
public class LoginController {

  final OwnerService ownerService;
  final SecurityJWTLogin securityJWTLogin;

  public LoginController(OwnerService ownerService, SecurityJWTLogin securityJWTLogin) {
    this.ownerService = ownerService;
    this.securityJWTLogin = securityJWTLogin;
  }

  @PostMapping(Routes.LOGIN)
  public String login(@RequestBody LoginRequest request) throws SteinerException {

    Owner user = ownerService.login(request.getName(), request.getPassword());

    return securityJWTLogin.getConfigurer().getTokenProvider().createToken(user);

  }
}
