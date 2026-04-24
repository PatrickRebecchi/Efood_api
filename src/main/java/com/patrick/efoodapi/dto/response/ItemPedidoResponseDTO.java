package com.patrick.efoodapi.dto.response;


public record ItemPedidoResponseDTO(
        Long produtoId,
        String nomeProduto,
        Double preco,
        Integer quantidade
) {}