package com.arenahub.arenahub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.arenahub.arenahub.database.models.Quadra;
import com.arenahub.arenahub.database.repository.IQuadraRepository;
import com.arenahub.arenahub.dto.QuadraDto;
import com.arenahub.arenahub.dto.QuadraUpdateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuadraService {

    private final IQuadraRepository quadraRepository;

    public List<QuadraDto> getAllQuadra() {
        return quadraRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public QuadraDto getQuadraById(long id) {
        return quadraRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Quadra não encontrada!" + id));

    }

    public QuadraDto getQuadraByNome(String nome) {
        return quadraRepository.findByNome(nome).map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Quadra não encontrada!" + nome));
    }

    public void createQuadra(QuadraDto quadraDto) {

        Quadra quadra = quadraRepository.findByNome(quadraDto.nome()).orElse(null);

        if (quadra != null) {
            throw new RuntimeException("Quadra com esse nome já existe!");
        }

        quadraRepository.save(
                Quadra.builder()
                        .nome(quadraDto.nome())
                        .horaAbertura(quadraDto.horaAbertura())
                        .horaFechamento(quadraDto.horaFechamento())
                        .ativa(quadraDto.ativa())
                        .criadaEm(LocalDateTime.now())
                        .build());
    }

    public void updateQuadra(QuadraUpdateDto quadraDto, long id) {
        Quadra quadra = quadraRepository.findById(id).orElseThrow();

        if (quadraDto.nome() != null)
            quadra.setNome(quadraDto.nome());
        if (quadraDto.horaAbertura() != null)
            quadra.setHoraAbertura(quadraDto.horaAbertura());
        if (quadraDto.horaFechamento() != null)
            quadra.setHoraFechamento(quadraDto.horaFechamento());
        if (quadraDto.ativa() != null)
            quadra.setAtiva(quadraDto.ativa());

        quadraRepository.save(quadra);
    }

    public void deleteQuadra(long id) {
        Quadra quadra = quadraRepository.findById(id).orElseThrow(() -> new RuntimeException());
        quadraRepository.deleteById(quadra.getIdQuadra());
    }

    private QuadraDto toDto(Quadra quadra) {
        return new QuadraDto(
                quadra.getNome(),
                quadra.getHoraAbertura(),
                quadra.getHoraFechamento(),
                quadra.isAtiva());
    }

}
