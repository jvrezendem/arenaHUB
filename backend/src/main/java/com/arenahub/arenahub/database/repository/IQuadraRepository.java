package com.arenahub.arenahub.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arenahub.arenahub.database.models.Quadra;

public interface IQuadraRepository extends JpaRepository<Quadra, Long> {

    Optional<Quadra> findByNome(String nome);
}
