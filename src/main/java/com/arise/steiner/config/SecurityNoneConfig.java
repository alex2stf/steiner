package com.arise.steiner.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Conditional(value=ConditionIgnoreSecurity.class)
@Configuration
@ComponentScan
@EnableWebSecurity
public class SecurityNoneConfig extends WebSecurityConfigurerAdapter {

    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers(HttpMethod.POST, "/**");
      web.ignoring().antMatchers(HttpMethod.GET, "/**");
      web.ignoring().antMatchers(HttpMethod.PUT, "/**");
      web.ignoring().antMatchers(HttpMethod.DELETE, "/**");
    }

}
