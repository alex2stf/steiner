package com.arise.steiner.config;

import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan
public class ApplicationConfig implements WebMvcConfigurer {
  final AuthInterceptor authInterceptor;
  final UserArgumentResolver userArgumentResolver;

  public ApplicationConfig(AuthInterceptor authInterceptor, UserArgumentResolver userArgumentResolver) {
    this.authInterceptor = authInterceptor;
    this.userArgumentResolver = userArgumentResolver;
  }

  /**
   * manually add converters
   * @param converters
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//    converters.add(new ProtobufHttpMessageConverter());
    converters.add(new MappingJackson2HttpMessageConverter());
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(userArgumentResolver);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor);
  }
}
