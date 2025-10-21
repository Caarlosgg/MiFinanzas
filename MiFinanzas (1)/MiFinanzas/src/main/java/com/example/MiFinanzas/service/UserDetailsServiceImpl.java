package com.example.MiFinanzas.service;

import com.example.MiFinanzas.model.Usuario;
import com.example.MiFinanzas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("[LOGIN] Buscando usuario por email: " + email);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("[LOGIN] Usuario NO encontrado");
                    return new UsernameNotFoundException("Usuario no encontrado con email: " + email);
                });

        System.out.println("[LOGIN] Usuario encontrado, retornando UserDetails");
        return new User(
                usuario.getEmail(),
                usuario.getContraseña(),
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );
    }
}
