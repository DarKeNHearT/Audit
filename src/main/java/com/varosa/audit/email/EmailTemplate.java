package com.varosa.audit.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;

public class EmailTemplate {
    @Email
    private String sendto;
    @Max(value = 100)
    private String subject;
    @Max(value = 500)
    private String body;

    public String getSendto() {
        return sendto;
    }

    public void setSendto(String sendto) {
        this.sendto = sendto;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
