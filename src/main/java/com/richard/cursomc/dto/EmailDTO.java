/**
 * 
 */
package com.richard.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * Created by Richard Rossetto on 16 de set de 2018
 */
@Data
public class EmailDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "E-mail inválido")
	private String email;

}
