package com.varosa.audit.service.impl;

import com.varosa.audit.model.Transaction;
import com.varosa.audit.pojo.JournalPojo;
import com.varosa.audit.pojo.TrialPojo;
import com.varosa.audit.repository.RepositoryAccounts;
import com.varosa.audit.repository.RepositoryLedgerAccount;
import com.varosa.audit.repository.RepositoryTransaction;
import com.varosa.audit.service.JournalService;
import org.hibernate.sql.OracleJoinFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    RepositoryLedgerAccount repositoryLedgerAccount;

    @Autowired
    RepositoryTransaction repositoryTransaction;

    @Autowired
    RepositoryAccounts repositoryAccounts;

    @Override
    public List<Map<String, Object>> ledgerAccount(JournalPojo journalPojo) {
        List<Map<String,Object>> empty = new ArrayList<>();
        if(checkItByDate(journalPojo)==true) {
            return empty; //check if for previous transaction dates are present or not
        }
        List<Map<String, Object>> listMappings = repositoryLedgerAccount.getLedgerAccount(journalPojo.getId(), journalPojo.getFromDate(), journalPojo.getToDate());
        if(listMappings.size()==0)
                return empty; //check if for previous transaction having dates but no created required ledger Account
        Map<String, Object> totalMapping = total(journalPojo.getId(),journalPojo.getFromDate(),journalPojo.getToDate());
        Long totalDebit = Long.valueOf(totalMapping.get("debit_amount").toString());
        Long totalCredit = Long.valueOf(totalMapping.get("credit_amount").toString());
        Long totalBalance = totalDebit - totalCredit;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total_credit", totalCredit);
        map.put("total_debit", totalDebit);
        if(totalBalance<0){
            map.put("debit_added_balance",Math.abs(totalBalance));
        }
        else if(totalBalance>0){
            map.put("credit_added_balance",totalBalance);
        }
        map.put("data_list", balanceCalculate(listMappings));
        list.add(map);
        return list;
    }


    public List<Map<String, Object>> trialBalance(TrialPojo trialPojo){
        List<Long> accounts = repositoryLedgerAccount.getAllAccountsByDate(trialPojo.getFromDate(), trialPojo.getToDate());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int debitBalance = 0;
        int creditBalance = 0;
        Long totalOpeningBalance = Long.valueOf(0);
        Long totalClosingBalance = Long.valueOf(0);
        List<Map<String,Object>> previousAccountOpeningBalance=openingBalanceOfPreviousMonth(accounts,trialPojo);
        //Previous ClosingBalances which are Not Included  Accounts
        for(int i = 0 ; i< accounts.size();i++){
            if(accounts.get(i)==null)
                continue;
            Long openingBalance = openingClosingBalance(accounts.get(i),trialPojo,1);//gets Closing Balance of Previous Month
            if(openingBalance == null)
                openingBalance=Long.valueOf(0);

            Map<String, Object> totalAccounts = total(accounts.get(i),trialPojo.getFromDate(),trialPojo.getToDate());
            Map<String, Object> map = new HashMap<String, Object>();
            Long totalDebit = Long.valueOf(totalAccounts.get("debit_amount").toString());
            Long totalCredit = Long.valueOf(totalAccounts.get("credit_amount").toString());
            Long balance = totalDebit - totalCredit;
            Long totalOfClosingBalance = Long.valueOf(0);
            String allParent = repositoryLedgerAccount.getAllParentOfAccount(accounts.get(i));
            long[] numbers = Arrays.stream(allParent.split(",")).mapToLong(Long::parseLong).toArray();

            List<List<Map<String,Object>>> parentList = new ArrayList<>();
            List<Map<String,Object>> parentSubList = new ArrayList<>();
            for(int j =1 ; j< numbers.length;j++){
                Map<String,Object> parent = new HashMap<>();
                parent.put("level",j);
                parent.put("name",repositoryAccounts.getNameFromId(numbers[j]));
                parent.put("id",numbers[j]);
                parentSubList.add(parent);
            }
            if(balance > 0){
                map.put("opening_balance",openingBalance);
                map.put("id",accounts.get(i));
                map.put("name",repositoryAccounts.getNameFromId(accounts.get(i)));
                map.put("debit_amount",balance);
                map.put("credit_amount",null);
                totalOfClosingBalance = openingBalance+balance;
                map.put("closing_balance",totalOfClosingBalance);
                map.put("parent_accounts",parentSubList);

                list.add(map);
                totalClosingBalance += totalOfClosingBalance;
                totalOpeningBalance +=openingBalance;
                debitBalance+=balance;
            }
            else if (balance <0){
                map.put("opening_balance",openingBalance);
                map.put("id",accounts.get(i));
                map.put("name",repositoryAccounts.getNameFromId(accounts.get(i)));
                map.put("debit_amount",null);
                map.put("credit_amount",Math.abs(balance));
                totalOfClosingBalance=openingBalance-Math.abs(balance);
                map.put("closing_balance",totalOfClosingBalance);
                map.put("parent_accounts",parentSubList);
                list.add(map);
                totalClosingBalance += totalOfClosingBalance;
                totalOpeningBalance +=openingBalance;
                creditBalance+=Math.abs(balance);
            }
            else {
                map.put("opening_balance", openingBalance);
                map.put("id", accounts.get(i));
                map.put("debit_amount", null);
                map.put("credit_amount", null);
                map.put("name",repositoryAccounts.getNameFromId(accounts.get(i)));
                map.put("parent_accounts",parentSubList);

                totalClosingBalance += totalOfClosingBalance;
                totalOpeningBalance +=openingBalance;
                map.put("closing_balance", openingBalance);
                list.add(map);
            }
        }
        Map<String, Object> totalMap = new HashMap<String, Object>();
        totalMap.put("debit_balance",debitBalance);
        totalMap.put("credit_balance",creditBalance);

        Long totalPreviousBalance = Long.valueOf(previousAccountOpeningBalance.get(previousAccountOpeningBalance.size()-1).get("totalBalance").toString());//previous opening balance
        totalOpeningBalance+=totalPreviousBalance;
        totalClosingBalance+=totalPreviousBalance;
        totalMap.put("total_opening_balance",totalOpeningBalance);
        totalMap.put("total_closing_balance",totalClosingBalance);
        list.add(totalMap);
        previousAccountOpeningBalance.remove(previousAccountOpeningBalance.size()-1);
        list.addAll(previousAccountOpeningBalance);
        return list;
    }

    //Gives Balance of Previous Month of Selected Accounts
    public Long openingClosingBalance(Long id,TrialPojo trialPojo,int a){
            JournalPojo journalPojo = monthDecrease(trialPojo,a);
            journalPojo.setId(id);
            Long openingBalance = balance(journalPojo);
            return openingBalance;
    }

    //Balances present in Accounts in included in present month
    public List<Map<String,Object>> openingBalanceOfPreviousMonth(List<Long> accounts,TrialPojo trialPojo){
        JournalPojo journalPojo = monthDecrease(trialPojo,1);
        //creating a hardcoded date
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.JANUARY, 1); //Year, month and day of month
        Date date = cal.getTime();
        //getting All accounts which are not included in present month for opening balance
        List<Long> previousAccounts = repositoryLedgerAccount.getAllAccountsByDate(date,journalPojo.getToDate());//
        List<Map<String,Object>> list = new ArrayList<>();
        Long totalOpeningAndClosingBalance = 0L;
        journalPojo.setFromDate(date);

        for(int i=0;i<previousAccounts.size();i++){
            if(accounts.contains(previousAccounts.get(i))){
                continue;
            }
            if(previousAccounts.get(i)==null)
                continue;
            String allParent = repositoryLedgerAccount.getAllParentOfAccount(previousAccounts.get(i));
            int[] numbers = Arrays.stream(allParent.split(",")).mapToInt(Integer::parseInt).toArray();
            Map<String,Object> map = new HashMap<>();
            journalPojo.setId(previousAccounts.get(i));
            Long balance = balance(journalPojo);
            totalOpeningAndClosingBalance+=balance;
            map.put("id",journalPojo.getId());
            map.put("name",repositoryAccounts.getNameFromId(journalPojo.getId()));
            map.put("debit_amount",null);
            map.put("closing_balance",balance);
            map.put("credit_amount",null);
            map.put("opening_balance",null);
            map.put("parent_id",numbers);
            list.add(map);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("totalBalance",totalOpeningAndClosingBalance);
        list.add(map);
        return list;
    }
    //Decreases the Month By given Integer a
    public JournalPojo monthDecrease(TrialPojo trialPojo,int a){
        JournalPojo journalPojo = new JournalPojo();
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(trialPojo.getFromDate());
        calFrom.add(Calendar.MONTH, -a);
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(trialPojo.getToDate());
        calTo.add(Calendar.MONTH, -a);
        Date fromDate = calFrom.getTime();
        Date toDate = calTo.getTime();
        journalPojo.setFromDate(fromDate);
        journalPojo.setToDate(toDate);
        return journalPojo;
    }
    //Get tillDateClosingBalance
    public Long balance(JournalPojo journalPojo){
        List<Map<String, Object>> listForOpeningBalance = ledgerAccount(journalPojo);
        if (listForOpeningBalance.size() == 0)
            return null;
        Long totalCredit = Long.valueOf(listForOpeningBalance.get(0).get("total_credit").toString());
        Long totalDebit = Long.valueOf(listForOpeningBalance.get(0).get("total_debit").toString());
        return totalDebit - totalCredit;
    }

    public Map<String, Object> total(Long id, Date fromDate, Date toDate){
        return repositoryLedgerAccount.getTotalOfLedgerAccount(id,fromDate, toDate);
    }

    //  7 for Income accounts
    //  6 for Expense Accounts
    //   Static Level 1 (Income and Expense) Should be present
    //  profitAndLoss Base Calculator
    public List<Map<String, Object>> profitAndLossInDepth(TrialPojo trialPojo,int token) {
            List<Map<String,Object>> childAccountOfIncome = repositoryLedgerAccount.getChildAccountByParentAndLevel(Long.valueOf(7),Long.valueOf(4));
            List<Map<String,Object>> childAccountOfExpense = repositoryLedgerAccount.getChildAccountByParentAndLevel(Long.valueOf(6),Long.valueOf(4));


            List<Map<String,Object>> listOfIncomeAccount = new ArrayList<>();
            List<Map<String,Object>> listOfExpenseAccount = new ArrayList<>();

            int TotalIncome = 0;
            int TotalExpense = 0;

            //Income Section
        for(int i = 0; i< childAccountOfIncome.size();i++) {
            Long childAccount = Long.valueOf(childAccountOfIncome.get(i).get("id").toString());
            if(checkIt(childAccount,trialPojo)==false)
                continue;
            List<Long> Amount1 = new ArrayList<>();
            Map<String,Object> modifiedIncomeMap = new HashMap<>();
            List<Long> incomeAmount = repositoryTransaction.getTransactionByAccountId(childAccount,trialPojo.getFromDate(),trialPojo.getToDate());
            int total_income_amount_of_account = 0;
            modifiedIncomeMap.putAll(childAccountOfIncome.get(i));
            for(int j = 0;j<incomeAmount.size();j++){
                Amount1.add(incomeAmount.get(j));
                total_income_amount_of_account+=incomeAmount.get(j).hashCode();
            }
            modifiedIncomeMap.put("amount",Amount1);
            TotalIncome+=total_income_amount_of_account;
            modifiedIncomeMap.put("total_amount",total_income_amount_of_account);
            listOfIncomeAccount.add(modifiedIncomeMap);
        }
        //Expense Section
        for(int i = 0; i< childAccountOfExpense.size();i++) {
            Long childAccount = Long.valueOf(childAccountOfExpense.get(i).get("id").toString());
            if(checkIt(childAccount,trialPojo)==false)
                continue;
            List<Long> Amount2 = new ArrayList<>();
            Map<String,Object> modifiedExpenseMap = new HashMap<>();
            List<Long> expenseAmount = repositoryTransaction.getTransactionByAccountId(childAccount,trialPojo.getFromDate(),trialPojo.getToDate());
            int total_expense_amount_of_account = 0;
            modifiedExpenseMap.putAll(childAccountOfExpense.get(i));
            for(int j = 0;j<expenseAmount.size();j++){
                Amount2.add(expenseAmount.get(j));
                total_expense_amount_of_account+=expenseAmount.get(j).hashCode();
            }
            modifiedExpenseMap.put("amount",Amount2);
            TotalExpense += total_expense_amount_of_account;
            modifiedExpenseMap.put("total_amount",total_expense_amount_of_account);
            listOfExpenseAccount.add(modifiedExpenseMap);
        }


        //declaration for Output response
        List<Map<String,Object>> finalOutput= new ArrayList<>();

            Map<String, Object> income = new HashMap<>();
            Map<String, Object> expense = new HashMap<>();
            Map<String, Object> profitLoss = new HashMap<>();
        if(token==1) {
            income.put("id", 7);
            income.put("sub_level", listOfIncomeAccount);
            income.put("total_income", TotalIncome);
            expense.put("id", 6);
            expense.put("sub_level", listOfExpenseAccount);
            expense.put("total_expense", TotalExpense);
            finalOutput.add(income);
            finalOutput.add(expense);
        }
        profitLoss.put("profit_loss",TotalIncome-TotalExpense);
        finalOutput.add(profitLoss);
        return finalOutput;
    }
    //sub-Division of Profit And Loss
    public Long previousProfitAndLossBalance(TrialPojo trialPojo1){
        JournalPojo  journalPojo = monthDecrease(trialPojo1,1);

        //creating date
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.JANUARY, 1); //Year, month and day of month
        Date date = cal.getTime();

        trialPojo1.setFromDate(date);
        trialPojo1.setToDate(journalPojo.getToDate());
        List<Map<String,Object>> previousProfitAndLoss = profitAndLossInDepth(trialPojo1,2);
        Map<String,Object> map = new HashMap<>();
        return Long.valueOf(previousProfitAndLoss.get(0).get("profit_loss").toString());
    }
    //Main Profit And Loss
    @Override
    public List<Map<String,Object>> profitAndLoss(TrialPojo trialPojo){
        List<Map<String,Object>> present = profitAndLossInDepth(trialPojo,1);
        Long previousProfitAndLoss = previousProfitAndLossBalance(trialPojo);
        Map<String,Object> close = new HashMap<>();
        Long netProfitLoss =Long.valueOf(present.get(2).get("profit_loss").toString());
        close.put("opening_balance",previousProfitAndLoss);
        close.put("profit_loss",netProfitLoss);
        close.put("closing_balance",previousProfitAndLoss+netProfitLoss);
        present.get(2).remove("profit_loss").toString();
        present.set(2,close);
        return present;
    }

    //ledger Account closing Balance Calculator
    public List<Map<String,Object>> balanceCalculate(List<Map<String,Object>> listMappings){
        List<Map<String,Object>>  modifiedList = new ArrayList<>();
        Long balance = Long.valueOf(0);
        for(int i = 0 ; i<listMappings.size();i++){
            Map<String,Object> modifiedMapping = new HashMap<>();
            modifiedMapping.putAll(listMappings.get(i));
            Long debitAmount = Long.valueOf(listMappings.get(i).get("debit").toString());
            Long creditAmount = Long.valueOf(listMappings.get(i).get("credit").toString());
            if(debitAmount == 0 && creditAmount != 0) {
                balance -= creditAmount;
            }
            else if(creditAmount == 0 && debitAmount !=0) {
                balance += debitAmount;
            }
            modifiedMapping.put("balance",balance);
            modifiedList.add(modifiedMapping);
        }
        return modifiedList;
    }

    @Override
    public boolean checkIt(Long id,TrialPojo trialPojo){
        int value = repositoryTransaction.getTransactionByAccountId(id,trialPojo.getFromDate(),trialPojo.getToDate()).size();
        if(value == 0)
            return false;
        else
            return true;
    }

    public boolean checkItByDate(JournalPojo journalPojo){

        List<Long> id = repositoryTransaction.getTransactionIdWithinDate(journalPojo.getFromDate(),journalPojo.getToDate());
        if(id.size() == 0)
            return true;
        else
            return false;
    }

    public boolean checkItById(Long id){
        List<Long> ids = repositoryTransaction.getTransactionIdByAccountIdOnly(id);
        if(ids.size() == 0)
            return true;
        else
            return false;
    }


    public List<Map<String, Object>> balanceSheetCalculator(TrialPojo trialPojo,Long parentId,Long level) {
        List<Map<String,Object>> level1 = repositoryLedgerAccount.getChildAccountByParentAndLevel(parentId,level); //Assets id = 3 And Need Level 4 accounts
        Map<String,Object> levelType = new HashMap<>();
        List<Map<String,Object>> detailsUnderLevel = new ArrayList<>();
        Long totalBalance = 0L;
        for(int i = 0 ; i<level1.size();i++) {
            JournalPojo journalPojo = new JournalPojo();
            Long level4Id = Long.valueOf(level1.get(i).get("id").toString());
            journalPojo.setId(level4Id);
            journalPojo.setFromDate(trialPojo.getFromDate());
            journalPojo.setToDate(trialPojo.getToDate());
            Map<String,Object> map = new HashMap<>();
            //check if present or not

            if(checkItById(level4Id))
                continue;
            //*-*
            Calendar cal = Calendar.getInstance();
            cal.set(2000, Calendar.JANUARY, 1); //Year, month and day of month
            Date date = cal.getTime();
            journalPojo.setFromDate(date);
            Long openingBalance = Math.abs(balance(journalPojo));
            if (openingBalance == null)
                openingBalance = 0L;
            totalBalance+=openingBalance;
            map.put("id",level4Id);
            map.put("balance",openingBalance);
            detailsUnderLevel.add(map);
        }
        levelType.put("account_list",detailsUnderLevel);
        levelType.put("level_1_id",parentId);
        levelType.put("total_balance",totalBalance);
        List<Map<String,Object>> detailsLevel = new ArrayList<>();
        detailsLevel.add(levelType);
        return detailsLevel;
    }

    @Override
    public List<List<Map<String, Object>>> balanceSheet(TrialPojo trialPojo) {
        List<Map<String,Object>> totalOutput = new ArrayList<>();
        List<List<Map<String,Object>>> finalOutput = new ArrayList<>();
        List<Map<String,Object>> assets = balanceSheetCalculator(trialPojo,3L,4L);

        List<Map<String,Object>> liabilities = balanceSheetCalculator(trialPojo,4L,4L);

        Long profitLoss = Long.valueOf(profitAndLoss(trialPojo).get(2).get("closing_balance").toString());

        List<Map<String,Object>> capital = balanceSheetCalculator(trialPojo,5L,4L);
        Map<String,Object> map = new HashMap<>();
        Long assetsTotal = Long.valueOf(assets.get(assets.size()-1).get("total_balance").toString());
        Long liabilitiesTotal = Long.valueOf(liabilities.get(liabilities.size()-1).get("total_balance").toString());
        Long capitalTotal = Long.valueOf(capital.get(capital.size()-1).get("total_balance").toString());
        Long total = liabilitiesTotal+capitalTotal+profitLoss;

        map.put("total_assets",assetsTotal);
        map.put("total_liabilities",total);
        map.put("profit_loss",profitLoss);
        totalOutput.add(map);
        finalOutput.add(totalOutput);
        finalOutput.add(assets);
        finalOutput.add(liabilities);
        finalOutput.add(capital);
        return finalOutput;
    }
}
