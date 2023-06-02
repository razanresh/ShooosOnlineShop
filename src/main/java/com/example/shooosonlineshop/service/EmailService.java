package com.example.shooosonlineshop.service;

import com.example.shooosonlineshop.model.EmailDetails;

public interface EmailService {
String sendSimpleMail(EmailDetails details);
String sendMailWithAttachment(EmailDetails details);
}
