package com.arise.steiner.repository;


import com.arise.steiner.entities.ItemTag;
import com.arise.steiner.entities.Word;
import com.arise.steiner.entities.model.InfoID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ItemTagRepository extends JpaRepository<ItemTag, InfoID> {
    ItemTag findByTagAndItemId(Word tag, Long fileId);
}
