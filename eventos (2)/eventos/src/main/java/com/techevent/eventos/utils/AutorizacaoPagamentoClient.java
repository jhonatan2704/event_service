package com.techevent.eventos.utils;

import com.techevent.eventos.dto.AutorizarVendaDto;
import com.techevent.eventos.dto.CalcularVendaDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("vendas")
public interface AutorizacaoPagamentoClient {
    @GetMapping("/vendas/pagamento/vendas/{nome}")
        AutorizarVendaDto obterAutorizacao(@PathVariable("nome") String nome);

    @PostMapping("/vendas/pagamento")
    AutorizarVendaDto calcularPagamento(@RequestBody CalcularVendaDto dto);
}
