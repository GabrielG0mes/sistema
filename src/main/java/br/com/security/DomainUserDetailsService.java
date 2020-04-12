package br.com.security;

import br.com.domain.Operador;
import br.com.repository.OperadorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final OperadorRepository operadorRepository;

    public DomainUserDetailsService(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return operadorRepository.findOneWithPerfisByLogin(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, Operador operador) {
        List<GrantedAuthority> grantedAuthorities = operador.getPerfis().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getNome()))
            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(operador.getLogin(),
            operador.getSenha(),
            grantedAuthorities);
    }
}
