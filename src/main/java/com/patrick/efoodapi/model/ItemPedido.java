package com.patrick.efoodapi.model;

import jakarta.persistence.*;

@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pedido pedido_id;

    @ManyToOne
    private Produto produto_id;

    private Integer quantidade;

    private Double preco;
}