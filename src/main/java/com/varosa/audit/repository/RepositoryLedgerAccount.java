package com.varosa.audit.repository;

import com.varosa.audit.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RepositoryLedgerAccount extends JpaRepository<Accounts,Long>{

    @Query(value = "select * from ledger_account(?1,?2,?3)",nativeQuery = true)
    public List<Map<String,Object>> getLedgerAccount(Long id, Date fromDate,Date toDate);
}
