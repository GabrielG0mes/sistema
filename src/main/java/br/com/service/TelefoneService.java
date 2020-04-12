package br.com.service;

import br.com.service.dto.TelefoneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TelefoneService {

    TelefoneDTO save(TelefoneDTO telefoneDTO);

    Page<TelefoneDTO> findAll(Pageable pageable);

    Optional<TelefoneDTO> findOne(Long id);

    void delete(Long id);
}
