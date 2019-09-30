package com.arise.steiner.client;

import com.arise.steiner.config.Author;

/**
 * Types for detecting action {@link com.arise.steiner.entities.Owner}
 */
public enum  SecurityMode {

  /**
   * any 'Authority' header will be considered {@link com.arise.steiner.entities.Owner#name}
   * all endpoints will be ignored by security
   * owners CRUD operations are disabled. Login is disabled.
   * Useful for debug purposes and easy access inside a secure environment
   */
  NONE,
  /**
   * 'Authority' header will be scanned using interceptor.
   * Any 'Bearer' token will be decoded, and it's 'email' property will be considered {@link com.arise.steiner.entities.Owner#name}
   * Any 'Basic auth' name will be considered {@link com.arise.steiner.entities.Owner#name}
   * owners CRUD operations are disabled. Login is disabled.
   * Useful when reusing existing parent microservice bearer token inside a secure environment
   */
  LOOSE,

  /**
   * Basic auth security
   * owners CRUD operations are enabled. Login is disabled.
   * For controllers with parameters annotated with {@link Author} security check will be done for each request.
   * Useful when current microservice is used by multiple other services inside a not quite secure environment.
   */
  BASIC_AUTH,

  /**
   * Fully jwt token support.
   * owners CRUD operations are enabled. Login is enabled.
   */
  JWT_LOGIN
}
