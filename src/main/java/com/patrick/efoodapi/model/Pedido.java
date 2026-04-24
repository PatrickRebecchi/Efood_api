package com.patrick.efoodapi.model;

import com.patrick.efoodapi.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime dataCriacaoDoPedido;
    @ManyToOne
    private Usuario usuario_id;

    @OneToMany(mappedBy = "pedido_id", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    private Double valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;


}
