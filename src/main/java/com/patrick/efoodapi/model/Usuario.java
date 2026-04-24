package com.patrick.efoodapi.model;

import com.patrick.efoodapi.model.enums.Role;
import com.patrick.efoodapi.model.enums.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank (message= "Nome é obrigatorio.")
    @Column(unique = true)
    private String nome;
    @Email
    @NotBlank (message= "Email é obrigatorio.")
    @Column(unique = true)
    private String email;
    private LocalDateTime dataCriacao;
    @NotBlank
    private String senha;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "usuario_id")
    private List<Pedido> pedidos;


}
