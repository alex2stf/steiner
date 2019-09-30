package com.arise.steiner.entities;

import com.arise.steiner.entities.model.BasicEntity;
import com.arise.steiner.names.Schemas;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A Node.
 */
@Entity
@Table(name = Schemas.NODES_TABLE)
public class Node extends BasicEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  protected String id;


  @OneToOne
  @JoinColumn(unique = true)
  private Item currentItem;

  @OneToMany(mappedBy = "node", fetch = FetchType.EAGER)
  @OrderBy
  private Set<Item> items;

  @Column(name = "nd_status")
  private String status;

  @Column(name = "nd_type")
  private String type = "unknown";

  @Column(name = "nd_description")
  private String description;

  @Column(name = "product_name")
  private String product;

  @Column(name = "nd_code")
  private String code;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "phase_name")
  private String phase;

  @Column(name = "nd_reason")
  private String reason;

  @Column(name = "nd_source")
  private String source;


  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = Schemas.NODE_ID, foreignKey = @ForeignKey(name = Schemas.FK_NODE_TAG))
  @JsonIgnore
  private Set<NodeTag> tags;


  @OneToMany(mappedBy = "node", orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonIgnore
  private Set<NodeProperty> properties;


  @SuppressWarnings("unused")
  @Transient
  @JsonProperty(value = "tags", access = JsonProperty.Access.READ_ONLY)
  public Set<String> getTagStrings() {

    Set<String> r = new HashSet<>();
    if (this.getTags() == null || this.getTags().isEmpty()) {
      return r;
    }
    for (NodeTag t : this.getTags()) {
      r.add(t.getTag().getValue());
    }

    return r;
  }


  @SuppressWarnings("unused")
  @Transient
  @JsonProperty(value = "props", access = JsonProperty.Access.READ_ONLY)
  public Map<String, String> getProps() {

    Map<String, String> r = new HashMap<>();

    if (this.getProperties() == null || this.getProperties().isEmpty()) {
      return r;
    }
    for (NodeProperty fp : this.getProperties()) {
      r.put(fp.getProperty().getKey(), fp.getProperty().getValue());
    }

    return r;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Set<NodeTag> getTags() {
    return tags;
  }

  public void setTags(Set<NodeTag> tags) {
    this.tags = tags;
  }

  public Set<NodeProperty> getProperties() {
    return properties;
  }

  public void setProperties(Set<NodeProperty> properties) {
    this.properties = properties;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public Item getCurrentItem() {
    return currentItem;
  }

  public void setCurrentItem(Item currentItem) {
    this.currentItem = currentItem;
  }

  public Set<Item> getItems() {
    return items;
  }

  public void setItems(Set<Item> items) {
    this.items = items;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getPhase() {
    return phase;
  }

  public void setPhase(String phase) {
    this.phase = phase;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Node{" +
        ", status='" + status + '\'' +
        ", type='" + type + '\'' +
        ", description='" + description + '\'' +
        ", productId=" + productId +
        ", phase='" + phase + '\'' +
        ", reason='" + reason + '\'' +
        ", source='" + source + '\'' +
        ", owner=" + getUserId() +
        ", id=" + id +
        '}';
  }
}
