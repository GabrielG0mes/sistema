package br.com.repository;

import br.com.domain.Telefone;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
