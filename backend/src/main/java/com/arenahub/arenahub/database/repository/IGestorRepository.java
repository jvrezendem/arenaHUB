package com.arenahub.arenahub.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arenahub.arenahub.database.models.Gestor;

public interface IGestorRepository extends JpaRepository<Gestor, Long> {

    Gestor findByEmail(String email);
}
