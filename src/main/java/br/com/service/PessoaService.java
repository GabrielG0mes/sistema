package br.com.service;

import br.com.service.dto.PessoaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PessoaService {

    PessoaDTO save(PessoaDTO pessoaDTO);

    Page<PessoaDTO> findAll(Pageable pageable);

    Optional<PessoaDTO> findOne(Long id);

    void delete(Long id);
}
