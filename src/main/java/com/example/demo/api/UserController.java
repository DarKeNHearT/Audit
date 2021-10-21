package com.example.demo.api;

import com.example.demo.model.Accounts;
import com.example.demo.model.Branch;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.pojo.AccountPojo;
import com.example.demo.pojo.ApiResponse;
import com.example.demo.pojo.UserPojo;
import com.example.demo.repository.RepositoryBranch;
import com.example.demo.repository.RepositoryUser;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user/")
public class UserController {

    @Autowired
    UserService userService;

    @Transactional
    @PostMapping(value = "signUp",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserPojo userPojo){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            User userSave = userService.insert(userPojo);
            response.setData(userSave);
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
    public ResponseEntity<ApiResponse> showAllUser(){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            List<Map<String,Object>> transactions = userService.getAll();
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
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Long id){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            userService.delete(id);
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
    public ResponseEntity<ApiResponse> showUserById(@PathVariable("id") Long id){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            Map<String,Object> display = userService.getById(id);
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
