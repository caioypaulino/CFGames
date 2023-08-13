package com.project.cfgames.services;

import com.project.cfgames.entities.Cliente;
import com.project.cfgames.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements UserDetailsService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // retorna senha criptografada com BCryptPasswordEncoder setado em @Configuration
    public String bCryptSenha(Cliente cliente) {
        return passwordEncoder.encode(cliente.getSenha());
    }

    // userdetailsservice method
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(username);

        if (cliente == null) {
            throw new UsernameNotFoundException("Email não encontrado");
        }

        return cliente;
    }
}
