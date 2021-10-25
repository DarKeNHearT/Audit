package com.varosa.audit.api;

import com.varosa.audit.pojo.ApiResponse;
import com.varosa.audit.pojo.TransactionPojo;
import com.varosa.audit.service.TransactionService;
import com.varosa.audit.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/transaction/")
public class TransactionContoller extends BaseController {

    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "save",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> addTransacation(@RequestBody TransactionPojo transactionPojo) {
        try {
            TransactionPojo transactionSave = transactionService.insert(transactionPojo);
            return ResponseEntity.ok(successResponse("Transaction Added", transactionSave));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(errorResponse("Error..", e.getMessage()));
        }
    }

    @GetMapping(value = "showAll",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showAllTransaction() {
        try {
            List<Map<String, Object>> transactions = transactionService.getAll();
            return ResponseEntity.ok(successResponse("Transaction Shown", transactions));
        } catch (Exception e) {
            return ResponseEntity.ok(errorResponse("Error..", e.getMessage()));
        }
    }

    @DeleteMapping(value = "deleteId/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> deleteTransaction(@PathVariable("id") Long id) {
        try {
            transactionService.delete(id);
            return ResponseEntity.ok(successResponse("Transaction Deleted", true));
        } catch (Exception e) {
            return ResponseEntity.ok(errorResponse("Error..", e.getMessage()));
        }
    }


    @GetMapping(value = "showById/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showTransactionById(@PathVariable("id") Long id){
        try{
            Map<String,Object> display = transactionService.getById(id);
            return ResponseEntity.ok(successResponse("Transaction Shown",display));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

}
