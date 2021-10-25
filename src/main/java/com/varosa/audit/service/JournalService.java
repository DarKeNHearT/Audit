package com.varosa.audit.service;

import com.varosa.audit.pojo.JournalPojo;
import com.varosa.audit.pojo.TrialPojo;

import java.util.List;
import java.util.Map;

public interface JournalService {
    List<Map<String,Object>> ledgerAccount(JournalPojo journalPojo);
    List<Map<String,Object>> trialBalance(TrialPojo trialPojo);
    List<Map<String,Object>> profitAndLoss(TrialPojo trialPojo);
    boolean checkIt(Long id,TrialPojo trialPojo);
    List<List<Map<String,Object>>> balanceSheet(TrialPojo trialPojo);
}
