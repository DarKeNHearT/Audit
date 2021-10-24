package com.varosa.audit.api;

import com.varosa.audit.model.Branch;
import com.varosa.audit.pojo.ApiResponse;
import com.varosa.audit.pojo.BranchPojo;
import com.varosa.audit.service.BranchService;
import com.varosa.audit.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/api/branch/")
public class BranchController extends BaseController {
    @Autowired
    private BranchService branchService;

    @PostMapping(value = "save",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addBranch(@RequestBody BranchPojo branchPojo){
        try{
            Branch branchSave = branchService.saveit(branchPojo);
            return ResponseEntity.ok(successResponse("Branch added",branchSave));

        }
        catch (Exception e){
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

    @GetMapping(value = "showAll",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showAllBranch(){
        try{
            List<Map<String,Object>> branch = branchService.getAll();
            return ResponseEntity.ok(successResponse("Branch Shown",branch));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }

    @DeleteMapping(value = "deleteId/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> deleteBranch(@PathVariable("id") Long id){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            branchService.delete(id);
            return ResponseEntity.ok(successResponse("Branch deleted",true));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }


    @GetMapping(value = "showById/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> showAccountById(@PathVariable("id") Long id){
        try{
            Map<String,Object> display = branchService.getById(id);
            return ResponseEntity.ok(successResponse("Branch Shown",display));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }
}
