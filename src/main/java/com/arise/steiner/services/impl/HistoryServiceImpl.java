package com.arise.steiner.services.impl;

import com.arise.steiner.entities.Node;
import com.arise.steiner.entities.model.Action;
import com.arise.steiner.entities.Snapshot;
import com.arise.steiner.repository.SnapshotRepository;
import com.arise.steiner.services.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final SnapshotRepository snapshotRepository;

    public HistoryServiceImpl(SnapshotRepository snapshotRepository) {
        this.snapshotRepository = snapshotRepository;
    }


    private Snapshot createHistoryItem(Node node){
        Snapshot snapshot = new Snapshot();
        snapshot.setNode(node);


//        if (node.getItems() != null && !node.getItems().isEmpty()){
//            Set<Long> files = new HashSet<>();
//            for (Item f: node.getItems()){
//                files.add(f.getId());
//            }
//
//            snapshot.setFileIds(files);
//        }
//
//        snapshot.setCurrentFileId(node.getCurrentItem() != null ? node.getCurrentItem().getId() : null);
//        snapshot.setCreationDate(new Date());

        return snapshot;
    }

    public void logAction(Node node, Action action) {
        Snapshot snapshot = createHistoryItem(node);
        snapshot.setHistoryAction(action.name());
        snapshotRepository.save(snapshot);
    }


    @Override
    public void logUpload(Node node) {
        logAction(node, Action.UPLOADED);
    }

    @Override
    public void logCreated(Node node) {
        logAction(node, Action.NODE_CREATED);
    }

    @Override
    public void logBeforeUpdate(Node node) {
        logAction(node, Action.NODE_UPDATE);
    }


}
