package com.patrick.efoodapi.dto.response;

public record LoginResponseDTO(
        String token,
        long id,
        String nome,
        String email,
        String role
) {
}