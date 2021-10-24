package com.varosa.audit.api;

import com.varosa.audit.pojo.ApiResponse;
import com.varosa.audit.pojo.UserPojo;
import com.varosa.audit.service.UserService;
import com.varosa.audit.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user/")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Transactional
    @PostMapping(value = "signUp",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserPojo userPojo){
        try{
            UserPojo userPojo1= userService.insert(userPojo);
            return ResponseEntity.ok(successResponse("User Added",userPojo1));

        }
        catch (Exception e){
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

    @GetMapping(value = "showAll",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showAllUser(){
        try{
            List<Map<String,Object>> userServiceAll = userService.getAll();
            return ResponseEntity.ok(successResponse("User Shown",userServiceAll));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

    @DeleteMapping(value = "deleteId/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Long id){
        try{
            userService.delete(id);
            return ResponseEntity.ok(successResponse("User Deleted",true));
        }
        catch (Exception e)
        {

            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }


    @GetMapping(value = "showById/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showUserById(@PathVariable("id") Long id){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            Map<String,Object> display = userService.getById(id);
            return ResponseEntity.ok(successResponse("User Shown",display));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

}
