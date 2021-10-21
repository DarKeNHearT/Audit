package com.example.demo.service;

import com.example.demo.model.Transaction;
import com.example.demo.pojo.TransactionPojo;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Transaction insert(TransactionPojo transactionPojo) throws Exception;
    List<Map<String,Object>> getAll();
    void delete(Long id);
    Map<String,Object> getById(Long id);
    Transaction checkit(Long id) throws Exception;
}
