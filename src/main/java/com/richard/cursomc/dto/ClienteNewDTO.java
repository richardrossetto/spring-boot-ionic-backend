package com.richard.cursomc.dto;

import com.richard.cursomc.services.validation.ClienteInsert;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ClienteInsert
public class ClienteNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String cpfOuCnpj;
    private Integer tipo;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String senha;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String logradouro;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String numero;
    private String bairro;
    private String complemento;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String cep;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String telefone1;
    private String telefone2;
    private String telefone3;

    private Integer cidadeId;

    public ClienteNewDTO(){}

}
