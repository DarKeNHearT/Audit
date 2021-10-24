package com.varosa.audit.service;

import com.varosa.audit.model.Accounts;
import com.varosa.audit.pojo.AccountPojo;

import java.util.List;
import java.util.Map;

public interface AccountService {
    AccountPojo insert(AccountPojo accountPojo) throws Exception;
    List<Map<String, Object>> getAll();
    void delete(Long id) throws Exception;
    Map<String,Object> getById(Long id) throws Exception;
    Accounts checkit(Long id) throws Exception;
}
