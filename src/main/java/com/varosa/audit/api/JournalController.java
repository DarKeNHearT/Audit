package com.varosa.audit.api;

import com.varosa.audit.pojo.ApiResponse;
import com.varosa.audit.pojo.JournalPojo;
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
            List<Map<String,Object>> journalPojos = journalService.ledgerAccount(journalPojo);
            return ResponseEntity.ok(successResponse("LedgerAccount Shown",journalPojos));
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(errorResponse("Error..",e.getMessage()));
        }
    }
}
