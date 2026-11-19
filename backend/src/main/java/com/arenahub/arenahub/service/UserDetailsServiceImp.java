package com.arenahub.arenahub.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arenahub.arenahub.database.models.Gestor;
import com.arenahub.arenahub.database.repository.IGestorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final IGestorRepository gestorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Gestor gestor = gestorRepository.findByEmail(username);

       if(gestor == null){
        throw new UsernameNotFoundException("Usuário não encontrado");
       }

       return gestor;
    }

}