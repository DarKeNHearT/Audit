package com.varosa.audit.repository;

import com.varosa.audit.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RepositoryLedgerAccount extends JpaRepository<Accounts,Long>{

    @Query(value = "select * from ledger_accounts(?1,?2,?3)",nativeQuery = true)
    public List<Map<String,Object>> getLedgerAccount(Long id, Date fromDate,Date toDate);

    @Query(value = "select * from total_of_ledger_accounts(?1,?2,?3)",nativeQuery = true)
    public Map<String,Object> getTotalOfLedgerAccount(Long id,Date fromDate,Date toDate);

    @Query(value = "select * from no_of_ledger_accounts(?1,?2)",nativeQuery = true)
    public List<Long> getAllAccountsByDate(Date fromDate,Date toDate);

    @Query(value = "select * from get_all_child_from_accounts(?1,?2)",nativeQuery = true) //7 for income and 8 for expense
    public List<Map<String,Object>> getChildAccountByParentAndLevel(Long id,Long level);

    @Query(value = "Select a.all_parent from accounts a where a.id = ?1",nativeQuery = true)
    public String getAllParentOfAccount(Long id);
}
