package com.varosa.audit.service.impl;

import com.varosa.audit.pojo.JournalPojo;
import com.varosa.audit.repository.RepositoryLedgerAccount;
import com.varosa.audit.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    RepositoryLedgerAccount repositoryLedgerAccount;
    @Override
    public List<Map<String,Object>> ledgerAccount(JournalPojo journalPojo) {
        List<Map<String,Object>> listMappings = repositoryLedgerAccount.getLedgerAccount(journalPojo.getId(),journalPojo.getFromDate(),journalPojo.getToDate());
        int totalDebit = 0;
        int totalCredit = 0;
        int totalBalance = 0;
        for(int i =0;i< listMappings.size();i++){
            int debit= listMappings.get(i).get("debit").hashCode();
            int credit = listMappings.get(i).get("credit").hashCode();
            totalCredit += credit;
            totalDebit += debit;
        }
        totalBalance = totalDebit - totalCredit;
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("total_credit",totalCredit);
        map.put("total_debit",totalDebit);
        map.put("total_balance",totalBalance);
        map.put("data_list",listMappings);
        list.add(map);
        return list;

    }
}
