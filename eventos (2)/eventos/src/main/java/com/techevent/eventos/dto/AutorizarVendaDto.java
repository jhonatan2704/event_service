package com.techevent.eventos.dto;

import java.math.BigDecimal;

public record AutorizarVendaDto(BigDecimal valorBruto, BigDecimal valorTotal, BigDecimal valorDesconto) {
}
