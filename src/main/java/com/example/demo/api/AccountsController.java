package com.example.demo.api;


import com.example.demo.model.Accounts;
import com.example.demo.pojo.AccountPojo;
import com.example.demo.pojo.ApiResponse;
import com.example.demo.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/accounts/")
public class AccountsController{

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "save",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> addAccount(@RequestBody AccountPojo accountPojo){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            Accounts accountSave = accountService.insert(accountPojo);
            response.setData(accountSave);
            response.setMessage("Added");
            response.setStatus(1);
            headers.add("content-range",String.valueOf(1));

        }
        catch (Exception e){
            response.setStatus(0);
            response.setData(null);
            response.setMessage("Failed");
            e.printStackTrace();
            headers.add("content-range",String.valueOf(0));
        }
        headers.add("content-type","application/json; charset=UTF-8");
        return new ResponseEntity<ApiResponse>(response,headers, HttpStatus.OK);
    }

    @GetMapping(value = "showAll",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showAllAccount(){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            List<Map<String,Object>> accounts = accountService.getAll();
            response.setMessage("Successfull");
            response.setStatus(1);
            response.setData(accounts);
            headers.add("content-range",String.valueOf(1));
        }
        catch (Exception e)
        {
            response.setData(null);
            response.setMessage("Failure");
            response.setStatus(0);
            e.printStackTrace();
            headers.add("content-range",String.valueOf(0));
        }
        headers.add("content-type","application/json; charset=UTF-8");
        return new ResponseEntity<ApiResponse>(response,headers,HttpStatus.OK );
    }

    @DeleteMapping(value = "deleteId/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable("id") Long id){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            accountService.delete(id);
            response.setMessage("Successfull");
            response.setStatus(1);
            response.setData(true);
            headers.add("content-range",String.valueOf(1));
        }
        catch (Exception e)
        {
            response.setData(null);
            response.setMessage("Failure");
            response.setStatus(0);
            e.printStackTrace();
            headers.add("content-range",String.valueOf(0));
        }
        headers.add("content-type","application/json; charset=UTF-8");
        return new ResponseEntity<ApiResponse>(response,headers,HttpStatus.OK );
    }


    @GetMapping(value = "showById/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showAccountById(@PathVariable("id") Long id){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            Map<String,Object> display = accountService.getById(id);
            response.setMessage("Successfull");
            response.setStatus(1);
            response.setData(display);
            headers.add("content-range",String.valueOf(1));
        }
        catch (Exception e)
        {
            response.setData(null);
            response.setMessage("Failure");
            response.setStatus(0);
            e.printStackTrace();
            headers.add("content-range",String.valueOf(0));
        }
        headers.add("content-type","application/json; charset=UTF-8");
        return new ResponseEntity<ApiResponse>(response,headers,HttpStatus.OK );
    }
}
