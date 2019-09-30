package com.arise.steiner.entities;

import com.arise.steiner.entities.model.InfoID;
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
@Table(name = "st_items_props")
public class ItemProperty {

    @EmbeddedId
    @AttributeOverrides(value = {
        @AttributeOverride(name = "entityId", column = @Column(name = "file_id")),
        @AttributeOverride(name = "infoId", column = @Column(name = "property_id"))
    })
    private InfoID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("entityId")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("infoId")
    private Property property;

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

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}

