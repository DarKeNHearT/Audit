package com.example.demo.api;

import com.example.demo.model.Transaction;
import com.example.demo.pojo.ApiResponse;
import com.example.demo.pojo.TransactionPojo;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/transaction/")
public class TransactionContoller {

    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "save",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> addTransacation(@RequestBody TransactionPojo transactionPojo){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            Transaction transactionSave = transactionService.insert(transactionPojo);
            response.setData(transactionSave);
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
    public ResponseEntity<ApiResponse> showAllTransaction(){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            List<Map<String,Object>> transactions = transactionService.getAll();
            response.setMessage("Successfull");
            response.setStatus(1);
            response.setData(transactions);
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
    public ResponseEntity<ApiResponse> deleteTransaction(@PathVariable("id") Long id){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            transactionService.delete(id);
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
    public ResponseEntity<ApiResponse> showTransactionById(@PathVariable("id") Long id){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            Map<String,Object> display = transactionService.getById(id);
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
