package com.varosa.audit.service.impl;

import com.varosa.audit.model.Accounts;
import com.varosa.audit.model.Transaction;
import com.varosa.audit.model.User;
import com.varosa.audit.pojo.TransactionPojo;
import com.varosa.audit.repository.RepositoryAccounts;
import com.varosa.audit.repository.RepositoryTransaction;
import com.varosa.audit.repository.RepositoryUser;
import com.varosa.audit.service.AccountService;
import com.varosa.audit.service.TransactionService;
import com.varosa.audit.service.UserService;
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
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;

    @Transactional
    @Override
    public TransactionPojo insert(TransactionPojo transactionPojo) throws Exception {
        Transaction transaction;
        if(transactionPojo.getId() != null){
            transaction = checkit(transactionPojo.getId());
        }
        else {
            transaction = new Transaction();
        }
        Accounts creditAccount = accountService.checkit(transactionPojo.getCreditAccountId());
        Accounts debitAccount = accountService.checkit(transactionPojo.getDebitAccountId());
        User user = userService.checkit(transactionPojo.getUserId());
        transaction.setCreditAccount(creditAccount);
        transaction.setDebitAccount(debitAccount);
        transaction.setStatus(transactionPojo.isStatus());
        transaction.setAmount(transactionPojo.getAmount());
        transaction.setCreatedDate(transactionPojo.getCreatedDate());
        transaction.setDescription(transactionPojo.getDescription());
        transaction.setUserId(user);
        Transaction transaction1 =  repositoryTransaction.save(transaction);
        transactionPojo.setId(transaction1.getId());
        return transactionPojo;
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return repositoryTransaction.getAll();
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        checkit(id);
        repositoryTransaction.delete(new Transaction(id));
    }

    @Override
    public Map<String, Object> getById(Long id) throws Exception {
        checkit(id);
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
