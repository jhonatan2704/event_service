package com.techevent.vendas.model;

import java.math.BigDecimal;

public record AutorizarVendaDto(BigDecimal valorBruto,
        BigDecimal valorTotal,
        BigDecimal valorDesconto) {
}
