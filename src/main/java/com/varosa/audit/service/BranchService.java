package com.varosa.audit.service;

import com.varosa.audit.model.Branch;
import com.varosa.audit.pojo.BranchPojo;

import java.util.List;
import java.util.Map;

public interface BranchService {
    Branch saveit(BranchPojo branchPojo) throws Exception;
    List<Map<String,Object>> getAll();
    void delete(Long id) throws Exception;
    Map<String,Object> getById(Long id) throws Exception;
    Branch checkit(Long id) throws Exception;
}
