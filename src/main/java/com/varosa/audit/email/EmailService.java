package com.varosa.audit.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTextEmail(EmailTemplate emailTemplate){
        SimpleMailMessage msg = new SimpleMailMessage();
        try{
            if(emailTemplate.getSendto().contains(",")) {
                String[] emails = emailTemplate.getSendto().split(",");
                int size = emails.length;
                for (int i = 0; i < size; i++) {
                    msg.setTo(emails[i]);
                    msg.setSubject(emailTemplate.getSubject());
                    msg.setText(emailTemplate.getBody());
                    javaMailSender.send(msg);
                }
            }
                else
                {
                    msg.setTo(emailTemplate.getSendto());
                    msg.setSubject(emailTemplate.getSubject());
                    msg.setText(emailTemplate.getBody());
                    System.out.println("hello");
                    javaMailSender.send(msg);
                }
        }
        catch (Exception e){
            System.out.println("Mail Error");
            e.printStackTrace();
        }
    }

    public void sendEmailWithAttachment(MultipartFile multipartFile, EmailTemplate emailTemplate) throws Exception{
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        InputStreamSource attachment = new ByteArrayResource(multipartFile.getBytes());
        helper.addAttachment(multipartFile.getOriginalFilename(),attachment);

        if(emailTemplate.getSendto().contains(",")) {
            String[] emails = emailTemplate.getSendto().split(",");
            int size = emails.length;
            for (int i = 0; i < size; i++) {
                helper.setTo(emails[i]);
                helper.setSubject(emailTemplate.getSubject());
                helper.setText(emailTemplate.getBody());
                javaMailSender.send(msg);
            }
        }
        else
        {
            helper.setTo(emailTemplate.getSendto());
            helper.setSubject(emailTemplate.getSubject());
            helper.setText(emailTemplate.getBody());
            javaMailSender.send(msg);
        }
    }
}
