package com.richard.cursomc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Richard Rossetto on 9/15/18.
 */
@Data
public class CredenciaisDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String email;
    private String senha;

    public CredenciaisDTO(){}
}
