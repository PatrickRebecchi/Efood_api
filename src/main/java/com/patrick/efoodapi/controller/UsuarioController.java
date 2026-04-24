package com.patrick.efoodapi.controller;

import com.patrick.efoodapi.dto.request.UsuarioRquestDTO;
import com.patrick.efoodapi.dto.response.UsuarioResponseDTO;
import com.patrick.efoodapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioResponseDTO> buscarTodosUsuarios(){
        return service.buscarTodosUsuarios();
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRquestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastrar(dto));
    }

    @PutMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> tornarAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(service.tornarAdmin(id));
    }


}
