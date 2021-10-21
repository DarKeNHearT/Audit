package com.example.demo.service.impl;

import com.example.demo.model.Accounts;
import com.example.demo.pojo.AccountPojo;
import com.example.demo.repository.RepositoryAccounts;
import com.example.demo.repository.RepositoryTransaction;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    RepositoryAccounts repositoryAccounts;

    @Autowired
    RepositoryTransaction repositoryTransaction;

    @Transactional
    @Override
    public Accounts insert(AccountPojo accountPojo)throws Exception  {
        Accounts accounts;
        if(accountPojo.getId() != null) {
            accounts = checkit(accountPojo.getId());
        }
        else {
            accounts = new Accounts();
        }
        accounts.setStatus(accountPojo.isStatus());
        accounts.setName(accountPojo.getName());
            if (accountPojo.getParentId() != null) {
                Optional<Accounts> acc = repositoryAccounts.findById(accountPojo.getParentId());
                System.out.println(repositoryAccounts.getLevelByParentId(accountPojo.getParentId()));
                accounts.setLevel(repositoryAccounts.getLevelByParentId(accountPojo.getParentId()) + 1);
                accounts.setParentId(acc.get());
            } else {
                throw new Exception("Level 0 Account Can't Be Created");
            }
        return repositoryAccounts.save(accounts);
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return repositoryAccounts.getAll();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repositoryTransaction.deleteByAccount(id);
        repositoryAccounts.delete(new Accounts(id));
    }

    @Override
    public Map<String, Object> getById(Long id) {
        return repositoryAccounts.getById(id);
    }

    @Override
    public Accounts checkit(Long id) throws Exception {

        Optional<Accounts> accounts = repositoryAccounts.findById(id);
        if(!accounts.isPresent())
            throw new Exception("Account Not present");
        return accounts.get();
    }
}
