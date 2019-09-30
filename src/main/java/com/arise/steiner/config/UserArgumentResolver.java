package com.arise.steiner.config;

import com.arise.steiner.services.AuthorizationDecoder;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class UserArgumentResolver implements WebArgumentResolver, HandlerMethodArgumentResolver {

  final AuthorizationDecoder decoder;

  public UserArgumentResolver(AuthorizationDecoder decoder) {
    this.decoder = decoder;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
    if (parameter.getParameterAnnotation(Author.class) != null){
      return decoder.decode((HttpServletRequest) webRequest.getNativeRequest());
    }
    return null;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(Author.class) != null;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
      throws Exception {
    if (parameter.getParameterAnnotation(Author.class) != null){
      return decoder.decode((HttpServletRequest) webRequest.getNativeRequest());
    }
    return null;
  }
}
