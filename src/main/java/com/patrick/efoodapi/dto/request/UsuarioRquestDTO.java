package com.patrick.efoodapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRquestDTO (
        @NotBlank(message = "Nome obrigatorio")
        String nome,
        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "Senha obrigatória")
        String senha
){
}
