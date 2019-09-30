package com.arise.steiner.repository;


import com.arise.steiner.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface OwnerRepository extends CrudRepository<Owner, String> {

}
