package com.techevent.vendas.service;

import com.techevent.vendas.dto.AutorizarVendaDto;
import com.techevent.vendas.repository.VendaRepository;
import com.techevent.vendas.model.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    public Venda salvandoValores(BigDecimal valor) {
        Venda venda = new Venda();
        venda.setValorBruto(valor);
        venda.calcularTotal();

        if (venda.getValorBruto().compareTo(BigDecimal.ZERO) == 0) {
            return venda;
        }
        return vendaRepository.save(venda);
    }

    public AutorizarVendaDto calcularPagamento(BigDecimal precoBase) {
        BigDecimal valorBruto = obterValorBrutoPago();
        BigDecimal desconto = obterDesconto();

        BigDecimal precoComDesconto = precoBase.subtract(desconto)
                .max(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);

        int comparacao = valorBruto.compareTo(precoComDesconto);

        BigDecimal valorTotal;
        if (comparacao == 0) {
            valorTotal = valorBruto;
        } else if (comparacao > 0) {
            BigDecimal diferenca = valorBruto.subtract(precoComDesconto);
            valorTotal = valorBruto.subtract(diferenca);
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                "Valor pago (" + valorBruto + ") menor que o preço com desconto (" + precoComDesconto + ")");
        }

        Venda venda = new Venda();
        venda.setValorBruto(valorBruto);
        venda.setValorDesconto(desconto);
        venda.setValorTotal(valorTotal);
        vendaRepository.save(venda);

        return new AutorizarVendaDto(valorBruto, valorTotal, desconto);
    }

    private BigDecimal obterValorBrutoPago() { return new BigDecimal("100.00"); }
    private BigDecimal obterDesconto()       { return new BigDecimal("10.00"); }

}
