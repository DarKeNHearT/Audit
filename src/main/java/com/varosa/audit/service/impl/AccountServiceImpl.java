package com.varosa.audit.service.impl;

import com.varosa.audit.model.Accounts;
import com.varosa.audit.pojo.AccountPojo;
import com.varosa.audit.repository.RepositoryAccounts;
import com.varosa.audit.repository.RepositoryTransaction;
import com.varosa.audit.service.AccountService;
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
    public AccountPojo insert(AccountPojo accountPojo)throws Exception  {
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
                accounts.setLevel(repositoryAccounts.getLevelByParentId(accountPojo.getParentId()) + 1);
                accounts.setParentId(acc.get());
            }
            else {
                throw new Exception("Level 0 Account Can't Be Created");
            }
            Accounts accounts1 = repositoryAccounts.save(accounts);
            String all_parent= repositoryAccounts.getParentColumn(accounts1.getId());
            accounts1.setAll_parent(all_parent);
            repositoryAccounts.save(accounts1);
            accountPojo.setId(accounts1.getId());
            return accountPojo;

    }

    @Override
    public List<Map<String, Object>> getAll() {
        return repositoryAccounts.getAll();
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        checkit(id);
        repositoryTransaction.deleteByAccount(id);
        repositoryAccounts.delete(new Accounts(id));
    }

    @Override
    public Map<String, Object> getById(Long id) throws Exception {
        checkit(id);
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
