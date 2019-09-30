package com.arise.steiner.config;


import com.arise.steiner.entities.Owner;
import com.arise.steiner.errors.EntityNotFoundException;
import com.arise.steiner.errors.SteinerException;
import com.arise.steiner.names.Props;
import com.arise.steiner.services.OwnerService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ConditionalOnProperty(value = Props.SECURITY_MODE, havingValue = "BASIC_AUTH")
@Configuration
@ComponentScan(basePackages = "com.arise.steiner")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter {

  final AuthProvider provider;

  final OwnerService ownerService;

  static final String ROLE = "OWNER";

  public SecurityBasicConfig(OwnerService ownerService) {
    this.ownerService = ownerService;

    this.provider = new AuthProvider(ownerService);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(provider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
        //create stateless basic auth login with this line, otherwise only for first request a check will be made
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests().antMatchers("/**").hasAuthority(ROLE)
        .and().csrf().disable().headers().frameOptions().disable();
  }

  private static class AuthProvider implements AuthenticationProvider {

    private final OwnerService ownerService;

    public AuthProvider(OwnerService ownerService) {
      this.ownerService = ownerService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      Owner owner;
      try {
        owner = ownerService.login(authentication.getName(), authentication.getCredentials().toString());
      } catch (SteinerException e) {
        throw new BadCredentialsException("Basic auth failed", e);
      }
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(ROLE)); // description is a string
      return new UsernamePasswordAuthenticationToken(owner.getName(), owner.getPassword(), authorities);

    }

    @Override
    public boolean supports(Class<?> aClass) {
      System.out.println(aClass);
      return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
  }
}
