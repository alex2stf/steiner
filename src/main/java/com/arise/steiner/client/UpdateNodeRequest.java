package com.arise.steiner.client;

public class UpdateNodeRequest extends CreateNodeRequest {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
