package com.richard.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

/**
 * Created by Richard Rossetto on 9/12/18.
 */
public class SmtpEmailService extends AbstractEmailService {

    private static final Logger logger = LoggerFactory.getLogger(SmtpEmailService.class);

    @Autowired
    MailSender mailSender;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        logger.info("Enviando email.....");
        mailSender.send(msg);
        logger.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        logger.info("Enviando email.....");
        javaMailSender.send(msg);
        logger.info("Email enviado");
    }
}
