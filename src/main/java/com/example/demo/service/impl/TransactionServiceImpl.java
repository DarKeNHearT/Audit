package com.example.demo.service.impl;

import com.example.demo.model.Accounts;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.pojo.TransactionPojo;
import com.example.demo.repository.RepositoryAccounts;
import com.example.demo.repository.RepositoryTransaction;
import com.example.demo.repository.RepositoryUser;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    RepositoryTransaction repositoryTransaction;
    @Autowired
    RepositoryAccounts repositoryAccounts;
    @Autowired
    RepositoryUser repositoryUser;

    @Transactional
    @Override
    public Transaction insert(TransactionPojo transactionPojo) throws Exception {
        Transaction transaction;
        if(transactionPojo.getId() != null){
            transaction = checkit(transactionPojo.getId());
        }
        else {

            transaction = new Transaction();
        }
        Long debitId= repositoryAccounts.getAccountIdByName(transactionPojo.getDebitAccountName());
        Long creditId = repositoryAccounts.getAccountIdByName(transactionPojo.getCreditAccountName());
        Long userId = repositoryUser.getUserIdByEmail(transactionPojo.getEmail());

        if(debitId ==null || creditId == null) {
            throw new Exception("Credit/Debit Accounts Not Found");
        }
        if(userId == null)
            throw new Exception("User Not Found");
        Optional<Accounts> debitAccount = repositoryAccounts.findById(debitId);
        Optional<Accounts> creditAccount = repositoryAccounts.findById(creditId);
        Optional<User> username = repositoryUser.findById(userId);

        transaction.setCreditAccount(creditAccount.get());
        transaction.setDebitAccount(debitAccount.get());
        transaction.setStatus(transactionPojo.isStatus());
        transaction.setAmount(transactionPojo.getAmount());
        transaction.setCreatedDate(transactionPojo.getCreatedDate());
        transaction.setDescription(transactionPojo.getDescription());
        transaction.setUserId(username.get());
        return repositoryTransaction.save(transaction);
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return repositoryTransaction.getAll();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repositoryTransaction.delete(new Transaction(id));
    }

    @Override
    public Map<String, Object> getById(Long id) {
        return repositoryTransaction.getById(id);
    }

    @Override
    public Transaction checkit(Long id) throws Exception {
        Optional<Transaction> transaction = repositoryTransaction.findById(id);
        if (!transaction.isPresent()){
            throw new Exception("Id not found");
    }
        return transaction.get();
    }
}
