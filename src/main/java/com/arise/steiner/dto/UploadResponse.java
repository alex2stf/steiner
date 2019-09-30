package com.arise.steiner.dto;

public class UploadResponse {

    private boolean success;
    private String documentId;
    private String directURL;
    private String errors;
    private String warnings;
    private String dbFilePath;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDirectURL() {
        return directURL;
    }

    public void setDirectURL(String directURL) {
        this.directURL = directURL;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getWarnings() {
        return warnings;
    }

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    public String getDbFilePath() {
        return dbFilePath;
    }

    public void setDbFilePath(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "UploadResponse{" +
            "success=" + success +
            ", documentId='" + documentId + '\'' +
            ", directURL='" + directURL + '\'' +
            ", errors='" + errors + '\'' +
            ", warnings='" + warnings + '\'' +
            ", dbFilePath='" + dbFilePath + '\'' +
            '}';
    }
}
