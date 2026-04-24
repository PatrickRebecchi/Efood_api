package com.patrick.efoodapi.dto.request;

import java.util.List;

public record PedidoRequestDTO(
        Long usuarioId,
        List<ItemPedidoRequestDTO> itens
) {}