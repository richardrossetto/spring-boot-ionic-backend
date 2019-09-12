/**
 * 
 */
package com.richard.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.richard.cursomc.domain.Cliente;
import com.richard.cursomc.repositories.ClienteRepository;
import com.richard.cursomc.services.exceptions.ObjectNotFoundException;

/**
 * Created by Richard Rossetto on 16 de set de 2018
 */
@Service
public class AuthService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email nao encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	/**
	 * @return
	 */
	private String newPassword() {
		char[] vet = new char[10];
		for(int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	/**
	 * @return
	 */
	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		} else if(opt == 1) {
			return (char)(rand.nextInt(26) + 65);
		}else {
			return (char)(rand.nextInt(26) + 97);
		}
	}

}
