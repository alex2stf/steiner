package com.arise.steiner.repository;

import com.arise.steiner.entities.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface SnapshotRepository extends JpaRepository<Snapshot, Long> {

}
