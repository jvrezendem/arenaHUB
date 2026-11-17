package com.arenahub.arenahub.database.models;

import java.time.LocalDateTime;

import com.arenahub.arenahub.database.models.enums.TipoMovimentacaoCredito;

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
@Table(name = "movimentacao_credito")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimentacaoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMovimentacao;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente idCliente;

    @ManyToOne
    @JoinColumn(name = "id_gestor", nullable = false)
    private Gestor idGestor;

    @Column(nullable = false)
    private TipoMovimentacaoCredito tipo;

    private String motivo;

    private String observacao;

    @Column(name = "saldo_antes", nullable = false)
    private short saldoAntes;

    @Column(name = "saldo_depois", nullable = false)
    private short saldoDepois;

    @Column(name = "registrada_em", updatable = false)
    private LocalDateTime registradaEm;

}
