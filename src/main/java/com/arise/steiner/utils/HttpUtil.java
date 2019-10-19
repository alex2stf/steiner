package com.arise.steiner.utils;

import com.arise.steiner.config.AuthInterceptor;
import java.util.Enumeration;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class HttpUtil {




  private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

  public static void dump(HttpServletRequest request) {
    Enumeration<String> headerNames = request.getHeaderNames();
    if (headerNames == null) {
      return;
    }
    while (headerNames.hasMoreElements()) {
      String key = headerNames.nextElement();
      log.info("Header: " + key + " = " + request.getHeader(key));
    }

    Enumeration en = request.getAttributeNames();
    if (en != null){
      while (en.hasMoreElements()) {
        Object currentElem = en.nextElement();
        System.out.println("currentElem.getClass(): " + currentElem.getClass());
        System.out.println("currentElem.toString(): " + currentElem);
      }
    }
  }






}
