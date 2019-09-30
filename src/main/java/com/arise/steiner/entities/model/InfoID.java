package com.arise.steiner.entities.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

public class InfoID implements Serializable {

    @Column(name = "entityId")
    private String entityId;

    @Column(name = "info_id")
    private Long infoId;

    public InfoID() {

    }

    public InfoID(
        String entityId,
        Long infoId) {
        this.entityId = entityId;
        this.infoId = infoId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InfoID infoID = (InfoID) o;
        return Objects.equals(entityId, infoID.entityId) &&
            Objects.equals(infoId, infoID.infoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, infoId);
    }
}
