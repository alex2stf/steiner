package com.arise.steiner.entities;

import static com.arise.steiner.names.Schemas.CAN_CREATE_OWNERS;
import static com.arise.steiner.names.Schemas.CAN_DROP_OWNERS;
import static com.arise.steiner.names.Schemas.CAN_READ_OWNERS;
import static com.arise.steiner.names.Schemas.CAN_UPDATE_OWNERS;
import static com.arise.steiner.names.Schemas.ENC_TYPE;
import static com.arise.steiner.names.Schemas.NAME;
import static com.arise.steiner.names.Schemas.OWNERS_TABLE;
import static com.arise.steiner.names.Schemas.PASSWORD;

import com.arise.steiner.entities.model.PassEncodingType;
import com.arise.steiner.entities.model.Permissions;
import com.arise.steiner.names.Schemas;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = OWNERS_TABLE)
public class Owner {

  @Id
  @Column(name = NAME)
  private String name;

  @Column(name = PASSWORD)
  @JsonIgnore
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = ENC_TYPE)
  private PassEncodingType enctype;


  @Embedded
  @AttributeOverrides(value = {
      @AttributeOverride(name = "create", column = @Column(name = CAN_CREATE_OWNERS)),
      @AttributeOverride(name = "read", column = @Column(name = CAN_READ_OWNERS)),
      @AttributeOverride(name = "update", column = @Column(name = CAN_UPDATE_OWNERS)),
      @AttributeOverride(name = "drop", column = @Column(name = CAN_DROP_OWNERS))
  })
  private Permissions ownersPermissions;


  public Permissions getOwnersPermissions() {
    return ownersPermissions;
  }

  public void setOwnersPermissions(Permissions ownersPermissions) {
    this.ownersPermissions = ownersPermissions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public PassEncodingType getEnctype() {
    return enctype;
  }

  public void setEnctype(PassEncodingType enctype) {
    this.enctype = enctype;
  }

  public String getDomain() {
    return null;
  }
}
