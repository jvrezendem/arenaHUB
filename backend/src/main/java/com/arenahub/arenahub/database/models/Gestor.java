package com.arenahub.arenahub.database.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.arenahub.arenahub.database.models.enums.UserRoles;

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
@Table(name = "gestor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Gestor implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_gestor")
    private long idGestor;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "senha_hash")
    private String senhaHash;

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "idGestor")
    private Set<Reserva> reservas;

    @OneToMany(mappedBy = "idGestor")
    private Set<MovimentacaoCredito> movimentacoes;

    @Column(name = "role", nullable = false)
    private UserRoles role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role.equals(UserRoles.ADMIN)) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public @Nullable String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
