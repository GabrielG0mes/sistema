package br.com.repository;

import br.com.domain.Operador;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long> {

    Optional<Operador> findOneByLogin(String login);

    @EntityGraph(attributePaths = "perfis")
    Optional<Operador> findOneWithPerfisById(Long id);

    @EntityGraph(attributePaths = "perfis")
    Optional<Operador> findOneWithPerfisByLogin(String login);

    Page<Operador> findAllByLoginNot(Pageable pageable, String login);
}
