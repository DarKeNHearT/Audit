package com.example.demo.repository;


import com.example.demo.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RepositoryAccounts extends JpaRepository<Accounts, Long> {

    @Query(value = "Select a.* from accounts a",nativeQuery = true)
    List<Map<String,Object>> getAll();

    @Query(value = "Select a.* from accounts a where a.id=?1",nativeQuery = true)
    Map<String,Object> getById(Long id);

    @Query(value = "Select a.id from accounts a where a.name=?1",nativeQuery = true)
    public Long getAccountIdByName(String name);

    @Query(value="Select a.level from accounts a where a.id = ?1 ",nativeQuery = true)
    public long getLevelByParentId(Long id);
}
