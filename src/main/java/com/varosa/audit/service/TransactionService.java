package com.varosa.audit.service;

import com.varosa.audit.model.Transaction;
import com.varosa.audit.pojo.TransactionPojo;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    TransactionPojo insert(TransactionPojo transactionPojo) throws Exception;
    List<Map<String,Object>> getAll();
    void delete(Long id) throws Exception;
    Map<String,Object> getById(Long id) throws Exception;
    Transaction checkit(Long id) throws Exception;
}
