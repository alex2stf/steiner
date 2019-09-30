package com.arise.steiner.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "st_words")
public class Word {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "w_seq_gen", allocationSize = 1)
    @Column(name = "tag_id")
    protected Long id;


    @OneToMany(
        mappedBy = "tag",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<ItemTag> itemTags = new HashSet<>();


    @OneToMany(
        mappedBy = "tag",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<NodeTag> nodeTags = new HashSet<>();


    @NaturalId
    private String value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ItemTag> getItemTags() {
        return itemTags;
    }

    public void setItemTags(Set<ItemTag> itemTags) {
        this.itemTags = itemTags;
    }

    public Set<NodeTag> getNodeTags() {
        return nodeTags;
    }

    public void setNodeTags(Set<NodeTag> nodeTags) {
        this.nodeTags = nodeTags;
    }
}
