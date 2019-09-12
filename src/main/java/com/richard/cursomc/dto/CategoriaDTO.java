package com.richard.cursomc.dto;

import com.richard.cursomc.domain.Categoria;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
public class CategoriaDTO  implements Serializable {

    private static final long serialVersionUID = 1l;

    private Integer id;

    @NotEmpty(message = "Preenchimento Obirgatório")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }


}
