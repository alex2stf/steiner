package com.arise.steiner.config;

import com.arise.steiner.services.AuthorizationDecoder;
import com.arise.steiner.utils.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {


  final AuthorizationDecoder decoder;
  final ApplicationProperties applicationProperties;

  public AuthInterceptor(AuthorizationDecoder decoder, ApplicationProperties applicationProperties) {
    this.decoder = decoder;
    this.applicationProperties = applicationProperties;
  }

  @Override
  @Conditional(ConditionIgnoreSecurity.class)
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    boolean forward = true;

    if ( handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler;
      if (hm.getMethodAnnotation(Authorized.class) != null){
        forward = (decoder.decode(request) != null);
      }
    }

    if (!forward){
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing. "
          + "Server is running in " + applicationProperties.getSecurityMode() + " mode");
      HttpUtil.dump(request);
    }

    return super.preHandle(request, response, handler);
  }


}
