package com.patrick.efoodapi.validacao;


import com.patrick.efoodapi.dto.request.UsuarioRquestDTO;

public interface ValidacaoCriarUsuario {
    void validar(UsuarioRquestDTO dto);
}