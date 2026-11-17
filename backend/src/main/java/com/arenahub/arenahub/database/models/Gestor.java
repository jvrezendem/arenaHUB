package com.arenahub.arenahub.database.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
public class Gestor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_gestor")
    private long idGestor;

    private String nome;

    @Column(unique = true)
    private String login;

    @Column(name = "senha_hash")
    private String senhaHash;

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;
}
