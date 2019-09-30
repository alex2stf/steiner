package com.arise.steiner.repository;

import com.arise.steiner.entities.Item;
import com.arise.steiner.entities.Node;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Item entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Set<Item> getByNode(Node node);

    @Query(value = "SELECT * FROM dms_files WHERE id = :id", nativeQuery = true)
    Item getNativeById(@Param("id") Long id);

}
