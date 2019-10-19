package com.arise.steiner.entities;

import com.arise.steiner.entities.model.BasicEntity;
import com.arise.steiner.entities.model.CSVDataLong;
import com.arise.steiner.entities.model.CSVDataStr;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "st_snapshots")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "hist_sequence_generator", allocationSize = 1)
public class Snapshot extends BasicEntity implements Serializable  {

    @Id
    protected String id;

    @Column(name = "action_name")
    private String historyAction;

    @ManyToOne
    @JoinColumn(name = "node_id")
    @JsonIgnore
    private Node node;

    @Column(name = "current_file_id")
    private Long currentFileId;

    @Column(name = "files_ids")
    @Convert(converter = CSVDataLong.class)
    private Set<Long> fileIds;

    @Column(name = "node_status")
    private String status;

    @Column(name = "node_type")
    private String nodeType = "unknown";

    @Column(name = "node_description")
    private String nodeDescription;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "phase_name")
    private String phase;

    @Column(name = "doc_reason")
    private String reason;

    @Column(name = "doc_source")
    private String source;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "mark_deleted")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean deleted;

    @Convert(converter = CSVDataStr.class)
    @Column(name = "tags_snapshot")
    private Set<String> tags;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
        this.setNodeType(node.getType());
        this.setNodeType(node.getType());
        this.setSource(node.getSource());
        this.setReason(node.getReason());
        this.setPhase(node.getPhase());
        this.setProductId(node.getProductId());
        this.setCreatedBy(node.getCreatedBy());
    }

    public String getHistoryAction() {
        return historyAction;
    }

    public void setHistoryAction(String historyAction) {
        this.historyAction = historyAction;
    }

    public Long getCurrentFileId() {
        return currentFileId;
    }

    public void setCurrentFileId(Long currentFileId) {
        this.currentFileId = currentFileId;
    }

    public Set<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(Set<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }




    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
