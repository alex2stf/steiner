package com.arise.steiner.config;

import com.arise.steiner.dto.User;
import com.arise.steiner.entities.Owner;
import com.arise.steiner.names.Props;
import com.arise.steiner.services.AuthorizationDecoder;
import com.arise.steiner.services.OwnerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import sun.plugin.liveconnect.SecurityContextHelper;

@ConditionalOnProperty(value = Props.SECURITY_MODE, havingValue = "JWT_LOGIN")
@Configuration
@ComponentScan(basePackages = "com.arise.steiner")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityJWTLogin  extends WebSecurityConfigurerAdapter {



  final ApplicationProperties applicationProperties;
  final JWTConfigurer configurer;

  private static final Logger log = LoggerFactory.getLogger(SecurityJWTLogin.class);



  public SecurityJWTLogin(
      final ApplicationProperties applicationProperties,
      final AuthorizationDecoder decoder,
      final OwnerService ownerService) {
    this.applicationProperties = applicationProperties;
    this.configurer = new JWTConfigurer(applicationProperties, decoder, ownerService);


    try {
      Owner owner = new Owner();
      owner.setName("x");
      owner.setPassword("x");

      configurer.getTokenProvider().createToken(owner);
    }
    catch (Exception ex){
      throw new RuntimeException("Invalid application.secret provided for jwt token");
    }

  }






  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .apply(configurer);

  }

  public JWTConfigurer getConfigurer() {
    return configurer;
  }

  public static class JWTConfigurer  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    final TokenProvider tokenProvider;

    final ApplicationProperties applicationProperties;
    final AuthorizationDecoder decoder;


    public TokenProvider getTokenProvider() {
      return tokenProvider;
    }

    private JWTConfigurer(final ApplicationProperties applicationProperties, final AuthorizationDecoder decoder, final OwnerService ownerService) {
      this.applicationProperties = applicationProperties;
      tokenProvider = new TokenProvider(applicationProperties, ownerService);
      this.decoder = decoder;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
      JWTFilter customFilter = new JWTFilter(tokenProvider, decoder);
      http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
  }

  public static class TokenProvider {
    final ApplicationProperties applicationProperties;
    final OwnerService ownerService;

    TokenProvider(ApplicationProperties applicationProperties, OwnerService ownerService) {
      this.applicationProperties = applicationProperties;
      this.ownerService = ownerService;
    }

    public String createToken(Owner owner) {
      Claims claims = Jwts.claims();
      claims.put("name", owner.getName());
      Date now = new Date();
      Date validity = new Date(now.getTime() + 3600000);

      return Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(now)
          .setExpiration(validity)
          .signWith(getSecretKey())
          .compact();
    }

    SecretKey getSecretKey(){
      return io.jsonwebtoken.security.Keys.hmacShaKeyFor(applicationProperties.getSecret().getBytes());
    }

    public User validateToken(String bearerToken) {
      io.jsonwebtoken.Jwt jwt = Jwts.parser().setSigningKey(getSecretKey()).parse(bearerToken);
      if (jwt.getBody() instanceof Map){
        try {
          String name = ((Map)jwt.getBody()).get("name").toString();
          return ownerService.findUserByName(name);
        } catch (Exception e) {
          log.error("failed to decode", e);
          return null;
        }
      }
      return null;
    }
  }

  static class JWTFilter extends GenericFilterBean {

    final TokenProvider tokenProvider;
    final AuthorizationDecoder decoder;

    public JWTFilter(TokenProvider tokenProvider, AuthorizationDecoder decoder) {
      this.tokenProvider = tokenProvider;
      this.decoder = decoder;
    }


    public Authentication getAuthentication(User user) {
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("OWNER")); // description is a string
      return new UsernamePasswordAuthenticationToken(user.getName(), "", authorities);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      String authHeader = decoder.getAuthorizationHeader(httpServletRequest);
      String bearerToken = decoder.resolveBearer(authHeader);
      if (bearerToken != null){
        try {
         User user = tokenProvider.validateToken(bearerToken);
         if (user != null){
           SecurityContextHolder.getContext().setAuthentication(getAuthentication(user));
           decoder.register(user, httpServletRequest);
         }
        } catch (Exception e){
          System.out.println(e.getMessage());
        }
      }
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }
}
