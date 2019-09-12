package com.richard.cursomc.services;

import com.richard.cursomc.domain.Estado;
import com.richard.cursomc.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Richard Rossetto on 9/21/18.
 */
@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAllByOrderByNome();
    }
}
