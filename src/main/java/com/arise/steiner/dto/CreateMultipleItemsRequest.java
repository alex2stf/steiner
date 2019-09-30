package com.arise.steiner.dto;

import java.util.List;

public class CreateMultipleItemsRequest {

    private Long documentId;
    private List<CreateItemRequest> files;

    public List<CreateItemRequest> getFiles() {
        return files;
    }

    public void setFiles(List<CreateItemRequest> files) {
        this.files = files;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }


}
