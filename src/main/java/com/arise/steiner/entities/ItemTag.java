package com.arise.steiner.entities;

import com.arise.steiner.entities.model.InfoID;
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
@Table(name = "st_item_tag")
public class ItemTag {

    @EmbeddedId
    @AttributeOverrides(value = {
        @AttributeOverride(name = "entityId", column = @Column(name = "file_id")),
        @AttributeOverride(name = "infoId", column = @Column(name = "tag_id"))
    })
    private InfoID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("entityId")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("infoId")
    private Word tag;


    @Column(name = "created_on")
    private Date createdOn = new Date();

    public ItemTag() {
    }

    public InfoID getId() {
        return id;
    }

    public void setId(InfoID id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
}
