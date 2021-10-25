package com.varosa.audit.repository;

import com.varosa.audit.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RepositoryTransaction extends JpaRepository<Transaction,Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from transaction where debit_account= ?1 or credit_account = ?1",nativeQuery = true)
    void deleteByAccount(Long id);

    @Query(value = "Select a.* from transaction a",nativeQuery = true)
    List<Map<String,Object>> getAll();

   @Query(value = "Select a.* from transaction a where a.id = ?1", nativeQuery = true)
    Map<String,Object>getById(Long id);

   @Query(value = "Select a.amount from transaction a where (a.debit_account = ?1 or a.credit_account = ?1) and (a.created_date>?2 and a.created_date<?3)",nativeQuery = true)
    List<Long> getTransactionByAccountId(Long id,Date fromDate,Date toDate);

   @Query(value = "Select a.id from transaction a where created_date>?1 and created_date<?2",nativeQuery = true)
    List<Long> getTransactionIdWithinDate(Date fromDate,Date toDate);

    @Query(value = "Select a.id from transaction a where (a.debit_account = ?1 or a.credit_account = ?1)",nativeQuery = true)
    List<Long> getTransactionIdByAccountIdOnly(Long id);

}
