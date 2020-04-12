package br.com.repository;

import br.com.domain.Perfil;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, String> {
}
