package com.techevent.eventos.service;

import com.techevent.eventos.dto.EventoDto;
import com.techevent.eventos.model.Evento;
import com.techevent.eventos.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Evento adicionarEvento(EventoDto eventoDto){
        Evento evento = new Evento();
        evento.setNome(eventoDto.getNome());
        evento.setDescricao(eventoDto.getDescricao());
        evento.setData(eventoDto.getData());
        evento.setLocal(eventoDto.getLocal());
        evento.setCapacidadeMaxima(eventoDto.getCapacidadeMaxima());
        evento.setPrecoBase(eventoDto.getPrecoBase());

        return eventoRepository.save(evento);
    }


    public List<Evento> ExibirEventos() {
        return eventoRepository.findAll();
    }
}
