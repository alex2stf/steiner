package com.arise.steiner.repository;

import com.arise.steiner.entities.Transition;
import com.arise.steiner.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitionRepository  extends JpaRepository<Transition, String> {
}
