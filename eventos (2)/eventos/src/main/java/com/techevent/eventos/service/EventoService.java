package com.techevent.eventos.service;

import com.techevent.eventos.dto.AutorizarVendaDto;
import com.techevent.eventos.dto.CalcularVendaDto;
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

    private BigDecimal obterPrecoTotal(BigDecimal precoBase) {
        AutorizarVendaDto autorizacao = client.calcularPagamento(new CalcularVendaDto(precoBase));
        return autorizacao.valorTotal();
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
        evento.setPrecoTotal(obterPrecoTotal(evento.getPrecoBase()));
        return eventoRepository.save(evento);
    }


    public List<Evento> ExibirEventos() {
        return eventoRepository.findAll();
    }


}
