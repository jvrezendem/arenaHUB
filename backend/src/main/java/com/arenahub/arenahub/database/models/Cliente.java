package com.arenahub.arenahub.database.models;

import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import com.arenahub.arenahub.database.models.enums.TipoCliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idCliente;

    private String nome;

    private TipoCliente tipo;

    @Column(name = "saldo_creditos")
    private short saldoCreditos;

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalTime criadoEm;

}
