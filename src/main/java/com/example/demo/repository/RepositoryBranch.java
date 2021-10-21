package com.example.demo.repository;

import com.example.demo.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RepositoryBranch extends JpaRepository<Branch,Long> {
    @Query(value = "Select a.* from branch a",nativeQuery = true)
    List<Map<String,Object>> getAll();

    @Query(value = "Select a.* from branch a where a.id = ?1",nativeQuery = true)
    Map<String,Object>getById(Long id);

    @Query(value = "Select a.id from branch a where a.name = ?1",nativeQuery = true)
    Long getIdByName(String name);
}
