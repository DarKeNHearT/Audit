package com.varosa.audit.service;

import com.varosa.audit.pojo.JournalPojo;

import java.util.List;
import java.util.Map;

public interface JournalService {
    List<Map<String,Object>> ledgerAccount(JournalPojo journalPojo);
}
