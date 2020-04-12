package br.com.service.impl;

import br.com.service.TelefoneService;
import br.com.domain.Telefone;
import br.com.repository.TelefoneRepository;
import br.com.service.dto.TelefoneDTO;
import br.com.service.mapper.TelefoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TelefoneServiceImpl implements TelefoneService {

    private final Logger log = LoggerFactory.getLogger(TelefoneServiceImpl.class);

    private final TelefoneRepository telefoneRepository;

    private final TelefoneMapper telefoneMapper;

    public TelefoneServiceImpl(TelefoneRepository telefoneRepository, TelefoneMapper telefoneMapper) {
        this.telefoneRepository = telefoneRepository;
        this.telefoneMapper = telefoneMapper;
    }

    @Override
    public TelefoneDTO save(TelefoneDTO telefoneDTO) {
        log.debug("Request to save Telefone : {}", telefoneDTO);
        Telefone telefone = telefoneMapper.toEntity(telefoneDTO);
        telefone = telefoneRepository.save(telefone);
        return telefoneMapper.toDto(telefone);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TelefoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Telefones");
        return telefoneRepository.findAll(pageable)
            .map(telefoneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelefoneDTO> findOne(Long id) {
        log.debug("Request to get Telefone : {}", id);
        return telefoneRepository.findById(id)
            .map(telefoneMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Telefone : {}", id);
        telefoneRepository.deleteById(id);
    }
}
