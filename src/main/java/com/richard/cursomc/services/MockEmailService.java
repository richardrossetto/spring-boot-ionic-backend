package com.richard.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

/**
 * Created by Richard Rossetto on 9/12/18.
 */
public class MockEmailService extends AbstractEmailService{

    private static final Logger logger = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        logger.info("Simulando envio de email.....");
        logger.info(msg.toString());
        logger.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        logger.info("Simulando envio de email  html.....");
        logger.info(msg.toString());
        logger.info("Email enviado");
    }
}
