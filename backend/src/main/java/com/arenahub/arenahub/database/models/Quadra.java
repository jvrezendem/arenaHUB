package com.arenahub.arenahub.database.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quadra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quadra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_quadra")
    private long idQuadra;

    private String nome;

    @Column(name = "hora_abertura")
    private LocalTime horaAbertura;

    @Column(name = "hora_fechamento")
    private LocalTime horaFechamento;

    private boolean ativa;

    @Column(name = "criada_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadaEm;

    @OneToMany(mappedBy = "reserva")
    private Set<Reserva> reservas;
}
