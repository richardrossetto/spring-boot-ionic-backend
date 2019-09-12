package com.richard.cursomc.services;

import com.richard.cursomc.domain.Cidade;
import com.richard.cursomc.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Richard Rossetto on 9/21/18.
 */
@Service
public class CidadeService {

    @Autowired private CidadeRepository cidadeRepository;

    public List<Cidade> findByCidade(Integer estadoId){
        return cidadeRepository.findCidades(estadoId);
    }
}
