package com.example.demo.service;

import com.example.demo.model.Accounts;
import com.example.demo.pojo.AccountPojo;

import java.util.List;
import java.util.Map;

public interface AccountService {
    Accounts insert(AccountPojo accountPojo) throws Exception;
    List<Map<String, Object>> getAll();
    void delete(Long id);
    Map<String,Object> getById(Long id);
    Accounts checkit(Long id) throws Exception;
}
