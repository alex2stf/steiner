package com.arise.steiner.entities;


import java.sql.Clob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "st_blob_content")
public class ItemBlobContent {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "st_content")
    private Clob content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clob getContent() {
        return content;
    }

    public void setContent(Clob content) {
        this.content = content;
    }
}
