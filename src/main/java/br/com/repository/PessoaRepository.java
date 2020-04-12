package br.com.repository;

import br.com.domain.Pessoa;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
