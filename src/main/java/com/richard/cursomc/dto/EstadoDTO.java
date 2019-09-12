package com.richard.cursomc.dto;

import com.richard.cursomc.domain.Estado;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Richard Rossetto on 9/21/18.
 */
@Data
public class EstadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public EstadoDTO() {
    }

    public EstadoDTO(Estado obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
    }
}
