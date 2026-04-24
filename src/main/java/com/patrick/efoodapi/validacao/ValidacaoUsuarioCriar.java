package com.patrick.efoodapi.validacao;

import com.patrick.efoodapi.dto.request.UsuarioRquestDTO;
import com.patrick.efoodapi.exception.EfoodException;
import com.patrick.efoodapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoUsuarioCriar implements ValidacaoCriarUsuario{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validar(UsuarioRquestDTO dto){
        if  (repository.existsByEmail(dto.email())){
            throw new EfoodException("Email já cadastrado! (ValidacaoEmail)");
        }
    }
}
