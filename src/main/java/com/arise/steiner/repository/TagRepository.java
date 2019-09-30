package com.arise.steiner.repository;

import com.arise.steiner.entities.Word;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Word, String> {


    Word findByValue(String string);

    Set<Word> findByValueIn(List<String> args);
//    Word findByValueAndDocumentAndFile(String id, Item file, Node document);
//    Set<Word> findByValueIn(Set<String> values);
}
