package com.arenahub.arenahub.database.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import com.arenahub.arenahub.database.models.enums.StatusReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_reserva")
    private long idReserva;

    @ManyToOne
    @JoinColumn(name = "id_quadra", nullable = false)
    private Quadra idQuadra;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente idCliente;

    @ManyToOne
    @JoinColumn(name = "id_gestor", nullable = false)
    private Gestor idGestor;

    private LocalDate data;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fim")
    private LocalTime horaFim;

    @Column(nullable = false)
    private StatusReserva situacao;

    @Column(name = "criada_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadaEm;

    @Column(name = "alterada_em", nullable = true)
    private LocalDateTime alteradaEm;

    @Column(name = "cancelada_em", nullable = true)
    private LocalDateTime canceladaEm;
}
