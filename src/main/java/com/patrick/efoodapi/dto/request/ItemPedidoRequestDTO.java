package com.patrick.efoodapi.dto.request;

public record ItemPedidoRequestDTO(
        Long produtoId,
        Integer quantidade
) {}