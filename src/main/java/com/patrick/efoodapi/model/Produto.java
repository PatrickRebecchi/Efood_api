package com.patrick.efoodapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message= "Nome do produto obrigatorio.")
    private String nome;
    @NotNull
    @Positive
    private Double valor;
    private LocalDateTime dataCriacaoProduto;

    @OneToMany(mappedBy = "produto_id")
    private List<ItemPedido> itens;


}
