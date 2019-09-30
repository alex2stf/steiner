package com.arise.steiner.entities;

import com.arise.steiner.entities.model.InfoID;
import com.arise.steiner.names.Schemas;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "st_node_tags")
public class NodeTag {

    @EmbeddedId
    @AttributeOverrides(value = {
        @AttributeOverride(name = "entityId", column = @Column(name = Schemas.NODE_ID)),
        @AttributeOverride(name = "infoId", column = @Column(name = "tag_id"))
    })
    private InfoID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("entityId")
    private Node node;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("infoId")
    private Word tag;


    @Column(name = "created_on")
    private Date createdOn = new Date();

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Word getTag() {
        return tag;
    }

    public void setTag(Word tag) {
        this.tag = tag;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public InfoID getId() {
        return id;
    }

    public void setId(InfoID id) {
        this.id = id;
    }
}
