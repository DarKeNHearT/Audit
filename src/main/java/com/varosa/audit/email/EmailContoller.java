package com.varosa.audit.email;

import com.varosa.audit.pojo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("send/mail/")
public class EmailContoller {
    @Autowired
    private EmailService emailService;

    @PostMapping(value="textMail",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> sendTextMail(@RequestBody EmailTemplate emailTemplate )throws Exception{
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try {
            emailService.sendTextEmail(emailTemplate);
            response.setData(true);
            response.setMessage("Added");
            response.setStatus(1);
            headers.add("content-range",String.valueOf(1));
        }
        catch (Exception e) {
            response.setData(false);
            response.setMessage("Failed");
            response.setStatus(0);
            e.printStackTrace();
            headers.add("content-range",String.valueOf(1));
        }
        headers.add("content-type","application/json; charset=UTF-8");
        return new ResponseEntity<ApiResponse>(response,headers, HttpStatus.OK);
    }

    @PostMapping(value = "/fileMail",consumes = "multipart/form-data",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> sendFileMail(@ModelAttribute("emails") EmailTemplate emailTemplate, @RequestParam("file")MultipartFile file){
        ApiResponse response = new ApiResponse();
        HttpHeaders headers = new HttpHeaders();
        try{
            emailService.sendEmailWithAttachment(file,emailTemplate);
            response.setData(true);
            response.setMessage("Added");
            response.setStatus(1);
            headers.add("content-range",String.valueOf(1));
        }
        catch (Exception e){
            response.setData(false);
            response.setMessage("Failed");
            response.setStatus(0);
            e.printStackTrace();
            headers.add("content-range",String.valueOf(1));
        }
        headers.add("content-type","application/json; charset=UTF-8");
        return new ResponseEntity<ApiResponse>(response,headers, HttpStatus.OK);
    }
}
