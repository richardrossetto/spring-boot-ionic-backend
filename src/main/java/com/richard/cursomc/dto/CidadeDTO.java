package com.richard.cursomc.dto;

import com.richard.cursomc.domain.Cidade;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Richard Rossetto on 9/21/18.
 */
@Data
public class CidadeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public CidadeDTO(){}

    public CidadeDTO(Cidade obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
    }
}
