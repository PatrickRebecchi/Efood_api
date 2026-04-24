package com.patrick.efoodapi.dto.response;

import com.patrick.efoodapi.model.enums.Role;

public record UsuarioResponseDTO (
        Long id,
        String nome,
        String email,
        Role role
){
}
