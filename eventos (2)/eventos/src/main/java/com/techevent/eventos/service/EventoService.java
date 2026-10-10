package com.techevent.eventos.service;

import com.techevent.eventos.dto.AutorizarVendaDto;
import com.techevent.eventos.dto.EventoDto;
import com.techevent.eventos.model.AutorizarVenda;
import com.techevent.eventos.model.Evento;
import com.techevent.eventos.repository.EventoRepository;
import com.techevent.eventos.utils.AutorizacaoPagamentoClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;
    private final AutorizacaoPagamentoClient client;
    public EventoService(EventoRepository eventoRepository, AutorizacaoPagamentoClient client) {
        this.eventoRepository = eventoRepository;
        this.client = client;
    }

    private BigDecimal obterStatusPagamento(String nome, BigDecimal precoBase) {
        AutorizarVendaDto autorizacao = client.obterAutorizacao(nome);

        if (autorizacao.valorBruto() == null || autorizacao.valorBruto().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        int comparacao = autorizacao.valorBruto().compareTo(precoBase);

        if (comparacao == 0) {
            return autorizacao.valorTotal();
        } else if (comparacao > 0) {
            BigDecimal diferenca = autorizacao.valorTotal().subtract(precoBase);
            return autorizacao.valorTotal().subtract(diferenca);
        } else {
            throw new IllegalArgumentException("O valor bruto (" + autorizacao.valorBruto() + ") é menor que o preço base (" + precoBase + ").");
        }
    }

    public Evento adicionarEvento(EventoDto eventoDto){
        Evento evento = new Evento();
        evento.setNome(eventoDto.getNome());
        evento.setDescricao(eventoDto.getDescricao());
        evento.setData(eventoDto.getData());
        evento.setLocal(eventoDto.getLocal());
        evento.setCapacidadeMaxima(eventoDto.getCapacidadeMaxima());
        evento.setPrecoBase(eventoDto.getPrecoBase());
        eventoRepository.save(evento);
        evento.setPrecoTotal(obterStatusPagamento(evento.getNome(), evento.getPrecoBase()));
        return eventoRepository.save(evento);
    }


    public List<Evento> ExibirEventos() {
        return eventoRepository.findAll();
    }


}
