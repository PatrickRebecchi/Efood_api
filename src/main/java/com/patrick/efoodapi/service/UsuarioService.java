package com.patrick.efoodapi.service;

import com.patrick.efoodapi.dto.request.UsuarioRquestDTO;
import com.patrick.efoodapi.dto.response.UsuarioResponseDTO;
import com.patrick.efoodapi.model.Usuario;
import com.patrick.efoodapi.model.enums.Role;
import com.patrick.efoodapi.repository.UsuarioRepository;
import com.patrick.efoodapi.validacao.ValidacaoUsuarioCriar;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private List<ValidacaoUsuarioCriar> validacao;

    @Transactional
    public List<UsuarioResponseDTO> buscarTodosUsuarios() {
        return converteDados(repository.findAll());
    }

    public List<UsuarioResponseDTO> converteDados(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(u -> new UsuarioResponseDTO(
                        u.getId(),
                        u.getNome(),
                        u.getEmail(),
                        u.getRole()))
                .collect(Collectors.toList());
    }


    public UsuarioResponseDTO cadastrar(UsuarioRquestDTO dto) {
        validacao.forEach(u -> u.validar(dto));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setRole(Role.CLIENTE);
        usuario.setSenha(new BCryptPasswordEncoder().encode(dto.senha()));
        usuario.setDataCriacao(java.time.LocalDateTime.now());

        Usuario usuarioSalvo = repository.save(usuario);

        return new UsuarioResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getRole()
        );
    }

    @Transactional
    public UsuarioResponseDTO tornarAdmin(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setRole(Role.ADMIN);

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
}
