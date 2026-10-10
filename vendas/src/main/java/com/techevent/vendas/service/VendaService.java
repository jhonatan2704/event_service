package com.techevent.vendas.service;

import com.techevent.vendas.repository.VendaRepository;
import com.techevent.vendas.model.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

}
