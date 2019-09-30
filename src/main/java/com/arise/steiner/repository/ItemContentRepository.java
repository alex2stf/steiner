package com.arise.steiner.repository;

import com.arise.steiner.entities.ItemBlobContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ItemContentRepository extends JpaRepository<ItemBlobContent, Long> {

}
