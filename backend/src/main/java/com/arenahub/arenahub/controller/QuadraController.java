package com.arenahub.arenahub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arenahub.arenahub.dto.QuadraDto;
import com.arenahub.arenahub.dto.QuadraUpdateDto;
import com.arenahub.arenahub.service.QuadraService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/quadra")
@RequiredArgsConstructor
public class QuadraController {

    private final QuadraService quadraService;

    @GetMapping("/getAll")
    public ResponseEntity<List<QuadraDto>> getAll() {
        return new ResponseEntity<>(quadraService.getAllQuadra(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<QuadraDto> getById(@PathVariable long id) {
        return new ResponseEntity<>(quadraService.getQuadraById(id), HttpStatus.OK);
    }

    @GetMapping("/getByNome/{nome}")
    public ResponseEntity<QuadraDto> getByNome(@PathVariable String nome) {
        return new ResponseEntity<>(quadraService.getQuadraByNome(nome), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createQuadra(@RequestBody QuadraDto quadraDto) {
        quadraService.createQuadra(quadraDto);
        return new ResponseEntity<>("Quadra criada com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQuadra(@PathVariable long id, @RequestBody QuadraUpdateDto quadraDto) {
        quadraService.updateQuadra(quadraDto, id);
        return new ResponseEntity<>("Quadra atualizada com sucesso!", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuadra(@PathVariable long id) {
        quadraService.deleteQuadra(id);
        return new ResponseEntity<>("Quadra deletada com sucesso!", HttpStatus.OK);
    }
}