package com.arise.steiner.entities;


import com.arise.steiner.controllers.NodesController;
import com.arise.steiner.entities.model.BasicEntity;
import com.arise.steiner.entities.model.StringIDSConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;

/**
 * A Item.
 */
@Entity
@Table(name = "st_item")
//@Where(clause = "is_hidden = 0")
public class Item extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;


    @Column(name = "name")
    private String name;

    @Column(name = "file_size")
    private Long size;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "path")
    private String path;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "it_notes")
    private String notes;

    @Column(name = "it_status")
    private String status;


    @Column(name = "it_info")
    private String info;


    @JsonIgnore
    @ManyToOne
    private Node node;

    /**
     * Very important propery used to store the source file used to create the current file
     *
     * @see NodesController#createLink(Long, Long)
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * Very important property used to store the ids of the files generated using current file as source
     *
     * @see NodesController#createLink(Long, Long)
     */
    @Column(name = "child_ids")
    @Convert(converter = StringIDSConverter.class)
    private Set<String> childIds;


    @OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<ItemTag> tags;


    @JsonIgnore
    @OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ItemProperty> properties;


    public Item() {

    }




    public Item(Item parent) {
        this.name = parent.name;
        this.mimeType = parent.mimeType;
        this.path = parent.path;
        this.notes = parent.notes;
        this.parentId = parent.getId();
        this.info = parent.getInfo();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<ItemTag> getTags() {
        return tags;
    }

    public void setTags(Set<ItemTag> tags) {
        this.tags = tags;
    }

    public Set<ItemProperty> getProperties() {
        return properties;
    }

    public void setProperties(Set<ItemProperty> properties) {
        this.properties = properties;
    }

    @Transient
    @JsonProperty(value = "tags", access = JsonProperty.Access.READ_ONLY)
    public Set<String> getTagStrings() {

        Set<String> r = new HashSet<>();
        if (this.getTags() == null || this.getTags().isEmpty()) {
            return r;
        }
        for (ItemTag t : this.getTags()) {
            r.add(t.getTag().getValue());
        }

        return r;
    }

    @Transient
    @JsonProperty(value = "props", access = JsonProperty.Access.READ_ONLY)
    public Map<String, String> getProps() {

        Map<String, String> r = new HashMap<>();

        if (this.getProperties() == null || this.getProperties().isEmpty()) {
            return r;
        }
        for (ItemProperty fp : this.getProperties()) {
            r.put(fp.getProperty().getKey(), fp.getProperty().getValue());
        }

        return r;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * 'cloned' property based on the fact that a file has multiple {@link Item#childIds}
     *
     * @return true if there are more than one childs created using current entity
     */
    @Transient
    @JsonProperty(value = "cloned", access = JsonProperty.Access.READ_ONLY)
    public boolean hasChilds() {
        return childIds != null && !childIds.isEmpty();
    }

    @Transient
    @JsonProperty(value = "nodeId", access = JsonProperty.Access.READ_ONLY)
    public String nodeId() {
        return node != null ? node.getId() : null;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Set<String> getChildIds() {
        return childIds;
    }

    public void setChildIds(Set<String> childIds) {
        this.childIds = childIds;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "Item{" +
            "uploadUser=" + getUserId() +
            ", name='" + name + '\'' +
            ", size=" + size +
            ", mimeType='" + mimeType + '\'' +
            ", path='" + path + '\'' +
            ", serviceId='" + serviceId + '\'' +
            ", notes='" + notes + '\'' +
            ", status='" + status + '\'' +
            ", node=" + node +
            ", parentId=" + parentId +
            ", childIds=" + childIds +
            ", tags=" + tags +
            ", properties=" + properties +
            ", id=" + id +
            '}';
    }
}
