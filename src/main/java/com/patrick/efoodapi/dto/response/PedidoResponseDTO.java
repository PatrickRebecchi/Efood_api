package com.patrick.efoodapi.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        String nomeUsuario,
        LocalDateTime dataCriacao,
        Double valorTotal,
        String status,
        List<ItemPedidoResponseDTO> itens
) {}