package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RepositoryUser extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Optional<User> findUserByEmail(String email);

    @Query(value ="Select a.id from users a where a.email = ?1",nativeQuery = true)
    public Long getUserIdByEmail(String email);

    @Query(value="Select a.* from users a",nativeQuery = true)
    public List<Map<String,Object>> getAll();

    @Query(value="Select a.* from users a where a.id = ?1",nativeQuery = true)
    public Map<String,Object> getById(Long id);



}
