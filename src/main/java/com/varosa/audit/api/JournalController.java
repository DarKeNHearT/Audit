package com.varosa.audit.api;

import com.varosa.audit.pojo.ApiResponse;
import com.varosa.audit.pojo.JournalPojo;
import com.varosa.audit.pojo.TrialPojo;
import com.varosa.audit.service.JournalService;
import com.varosa.audit.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/api/journal/")
public class JournalController extends BaseController {
    @Autowired
    JournalService journalService;

    @GetMapping(value = "ledgerAccount",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> displayLedgerAccount(@RequestBody JournalPojo journalPojo){
        try{
            List<Map<String,Object>> ledgerAccount = journalService.ledgerAccount(journalPojo);
            return ResponseEntity.ok(successResponse("LedgerAccount Shown",ledgerAccount));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

    @GetMapping(value = "trialBalance",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> displayTrialBalance(@RequestBody TrialPojo trialPojo){
        try{
            List<Map<String,Object>> trialBalance = journalService.trialBalance(trialPojo);
            return ResponseEntity.ok(successResponse("TrialBalance Shown",trialBalance));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

    @GetMapping(value = "profitAndLoss",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> displayProfitAndLoss(@RequestBody TrialPojo trialPojo){
        try{
            List<Map<String,Object>> profitAndLoss = journalService.profitAndLoss(trialPojo);
            return ResponseEntity.ok(successResponse("ProfitAndLoss Shown",profitAndLoss));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

    @GetMapping(value = "balanceSheet",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> displayBalanceSheet(@RequestBody TrialPojo trialPojo){
        try{
            List<List<Map<String,Object>>> balanceSheet = journalService.balanceSheet(trialPojo);
            return ResponseEntity.ok(successResponse("BalanceSheet Shown",balanceSheet));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }
}
