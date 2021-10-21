package com.example.demo.service;

import com.example.demo.model.Accounts;
import com.example.demo.model.Branch;
import com.example.demo.pojo.BranchPojo;

import java.util.List;
import java.util.Map;

public interface BranchService {
    Branch saveit(BranchPojo branchPojo) throws Exception;
    List<Map<String,Object>> getAll();
    void delete(Long id);
    Map<String,Object> getById(Long id);
    Branch checkit(Long id) throws Exception;
}
