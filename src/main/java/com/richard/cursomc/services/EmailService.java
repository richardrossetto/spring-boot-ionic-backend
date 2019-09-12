package com.richard.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.richard.cursomc.domain.Cliente;
import com.richard.cursomc.domain.Pedido;

/**
 * Created by Richard Rossetto on 9/12/18.
 */
public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage msg);
    
    void sendNewPasswordEmail(Cliente cliente, String newPass);
}
