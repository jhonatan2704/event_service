package com.techevent.eventos.utils;

import com.techevent.eventos.dto.AutorizarVendaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("vendas")
public interface AutorizacaoPagamentoClient {
    @GetMapping("/vendas/pagamento/vendas/{nome}")
        AutorizarVendaDto obterAutorizacao(@PathVariable("nome") String nome);

}
