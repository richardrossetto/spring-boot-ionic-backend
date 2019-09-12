package com.richard.cursomc.services;

import com.richard.cursomc.domain.Cliente;
import com.richard.cursomc.repositories.ClienteRepository;
import com.richard.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Richard Rossetto on 9/15/18.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente == null){
            throw new UsernameNotFoundException(email);
        }
        return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
    }
}
