package com.example.demo.service;

import com.example.demo.model.Accounts;
import com.example.demo.model.User;
import com.example.demo.pojo.AccountPojo;
import com.example.demo.pojo.UserPojo;

import java.util.List;
import java.util.Map;

public interface UserService {
    User insert(UserPojo userPojo) throws Exception;
    User checkit(Long id) throws Exception;
    List<Map<String,Object>> getAll();
    void delete(Long id);
    Map<String,Object> getById(Long id);
}
