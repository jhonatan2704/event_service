package com.techevent.vendas.controller;

import com.techevent.vendas.model.AutorizarVendaDto;
import com.techevent.vendas.model.Venda;
import com.techevent.vendas.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<Venda> VendaIngresso(@RequestBody @Valid Venda venda) {
        Venda novaVenda = vendaService.salvandoValores(venda.getValorBruto());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }

    @GetMapping("/pagamento/vendas/{nome}")
    public AutorizarVendaDto obterAutorizacao(@PathVariable("nome") String nome) {
        // Exemplo fixo, só para testar a ligação.
        // Depois você troca por uma busca real (service/repository).
        BigDecimal valorBruto = new BigDecimal("100.00");
        BigDecimal valorDesconto = new BigDecimal("10.00");
        BigDecimal valorTotal = valorBruto.subtract(valorDesconto);

        return new AutorizarVendaDto(valorBruto, valorTotal, valorDesconto);
    }
}
