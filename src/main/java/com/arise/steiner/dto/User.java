package com.arise.steiner.dto;

import com.arise.steiner.entities.Owner;

public class User {

  private final String name;
  private final String domain;

  public User(){
    name = null;
    domain = null;
  }

  public User (Owner owner){
    this.name = owner.getName();
    this.domain = owner.getDomain();
  }



  public String getName() {
    return name;
  }

  public String getDomain() {
    return domain;
  }
}
