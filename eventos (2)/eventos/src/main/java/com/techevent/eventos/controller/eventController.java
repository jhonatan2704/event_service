package com.techevent.eventos.controller;

import com.techevent.eventos.dto.EventoDto;
import com.techevent.eventos.model.Evento;
import com.techevent.eventos.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class eventController {
    @Autowired
    EventoService eventoService;

    @PostMapping("/eventos")
    public ResponseEntity<Evento> CadastrarEvento(@RequestBody @Valid EventoDto eventoDto){
        Evento novoEvento = eventoService.adicionarEvento(eventoDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(novoEvento);
    }

    @GetMapping("/eventos/response")
    public ResponseEntity<List<Evento>> listarEventos() {
        List<Evento> eventos = eventoService.ExibirEventos();
        return ResponseEntity.ok(eventos);
    }
}
