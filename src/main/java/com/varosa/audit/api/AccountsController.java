package com.varosa.audit.api;


import com.varosa.audit.pojo.AccountPojo;
import com.varosa.audit.pojo.ApiResponse;
import com.varosa.audit.repository.RepositoryAccounts;
import com.varosa.audit.service.AccountService;
import com.varosa.audit.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/accounts/")
public class AccountsController extends BaseController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RepositoryAccounts repositoryAccounts;

    @PostMapping(value = "save",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> addAccount(@RequestBody AccountPojo accountPojo){
        try{
            AccountPojo accountSave = accountService.insert(accountPojo);
            return ResponseEntity.ok(successResponse("Account added",accountSave));
        }
        catch (Exception e){
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

    @GetMapping(value = "showAll",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showAllAccount(){
        try{
            List<Map<String,Object>> accounts = accountService.getAll();
            return ResponseEntity.ok(successResponse("Account Shown",accounts));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

    @DeleteMapping(value = "deleteId/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable("id") Long id){
        try{
            accountService.delete(id);
            return ResponseEntity.ok(successResponse("Account Deleted",true));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }


    @GetMapping(value = "showById/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showAccountById(@PathVariable("id") Long id) {
        try {
            Map<String, Object> display = accountService.getById(id);
            return ResponseEntity.ok(successResponse("Account Shown", display));
        } catch (Exception e) {
            return ResponseEntity.ok(errorResponse("Error..", e.getMessage()));
        }
    }
}
