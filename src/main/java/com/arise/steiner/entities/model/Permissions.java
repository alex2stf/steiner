package com.arise.steiner.entities.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;

@Embeddable
public class Permissions implements Serializable {

  @Type(type= "org.hibernate.type.NumericBooleanType")
  private Boolean create;

  @Type(type= "org.hibernate.type.NumericBooleanType")
  private Boolean read;

  @Type(type= "org.hibernate.type.NumericBooleanType")
  private Boolean update;

  @Type(type= "org.hibernate.type.NumericBooleanType")
  private Boolean drop;

  public Boolean getCreate() {
    return create;
  }

  public void setCreate(Boolean create) {
    this.create = create;
  }

  public Boolean getRead() {
    return read;
  }

  public void setRead(Boolean read) {
    this.read = read;
  }

  public Boolean getUpdate() {
    return update;
  }

  public void setUpdate(Boolean update) {
    this.update = update;
  }

  public Boolean getDrop() {
    return drop;
  }

  public void setDrop(Boolean drop) {
    this.drop = drop;
  }
}
