package com.arise.steiner.services;

import com.arise.steiner.entities.Node;

public interface HistoryService {
    void logUpload(Node node);
    void logCreated(Node node);
    void logBeforeUpdate(Node node);
}
