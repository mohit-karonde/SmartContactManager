package com.scm.Services;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    

    void sendEmail(String to, String subject, String body);

    //
    void sendEmailWithHtml();

    //
    void sendEmailWithAttachment();


}
