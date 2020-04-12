package br.com.service;

import br.com.config.Constants;
import br.com.domain.Perfil;
import br.com.domain.Operador;
import br.com.repository.PerfilRepository;
import br.com.repository.OperadorRepository;
import br.com.security.PerfisConstants;
import br.com.security.SecurityUtils;
import br.com.service.dto.OperadorDTO;

import br.com.service.mapper.OperadorMapper;
import io.github.jhipster.security.RandomUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OperadorService {

    private final OperadorRepository operadorRepository;

    private final OperadorMapper operadorMapper;

    private final PasswordEncoder passwordEncoder;

    private final PerfilRepository perfilRepository;

    public OperadorService(OperadorRepository operadorRepository, OperadorMapper operadorMapper, PasswordEncoder passwordEncoder, PerfilRepository perfilRepository) {
        this.operadorRepository = operadorRepository;
        this.operadorMapper = operadorMapper;
        this.passwordEncoder = passwordEncoder;
        this.perfilRepository = perfilRepository;
    }

    public Operador registerUser(OperadorDTO operadorDTO, String password) {
        operadorRepository.findOneByLogin(operadorDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new UsernameAlreadyUsedException();
            }
        });
        Operador newOperador = new Operador();
        String encryptedPassword = passwordEncoder.encode(password);
        newOperador.setLogin(operadorDTO.getLogin().toLowerCase());
        newOperador.setSenha(encryptedPassword);
        newOperador.setNome(operadorDTO.getNome());
        Set<Perfil> authorities = new HashSet<>();
        perfilRepository.findById(PerfisConstants.USER).ifPresent(authorities::add);
        newOperador.setPerfis(authorities);
        operadorRepository.save(newOperador);
        return newOperador;
    }

    private boolean removeNonActivatedUser(Operador existingOperador) {
        operadorRepository.delete(existingOperador);
        operadorRepository.flush();
        return true;
    }

    public OperadorDTO createUser(OperadorDTO operadorDTO) {
        Operador operador = new Operador();
        operador.setLogin(operadorDTO.getLogin().toLowerCase());
        operador.setNome(operadorDTO.getNome());
        operador.setDataCadastro(Instant.now());
        String senha = RandomUtil.generatePassword();
        String encryptedPassword = passwordEncoder.encode(senha);
        operador.setSenha(encryptedPassword);
        if (operadorDTO.getPerfis() != null) {
            Set<Perfil> perfis = operadorDTO.getPerfis().stream()
                .map(perfilRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            operador.setPerfis(perfis);
        }
        OperadorDTO operadorDto = operadorMapper.operadorToOperadorDTO(operadorRepository.save(operador));
        operadorDto.setSenha(senha);
        return operadorDto;
    }

    public void updateUser(String firstName) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(operadorRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setNome(firstName);
            });
    }

    public Optional<OperadorDTO> updateUser(OperadorDTO operadorDTO) {
        return Optional.of(operadorRepository
            .findById(operadorDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                user.setLogin(operadorDTO.getLogin().toLowerCase());
                user.setNome(operadorDTO.getNome());
                String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
                user.setSenha(encryptedPassword);
                Set<Perfil> managedAuthorities = user.getPerfis();
                managedAuthorities.clear();
                operadorDTO.getPerfis().stream()
                    .map(perfilRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                return user;
            })
            .map(OperadorDTO::new);
    }

    public void deleteUser(String login) {
        operadorRepository.findOneByLogin(login).ifPresent(user -> {
            operadorRepository.delete(user);
        });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(operadorRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getSenha();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setSenha(encryptedPassword);
            });
    }

    @Transactional(readOnly = true)
    public Page<OperadorDTO> getAllManagedUsers(Pageable pageable) {
        return operadorRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(OperadorDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<Operador> getUserWithAuthoritiesByLogin(String login) {
        return operadorRepository.findOneWithPerfisByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<Operador> getUserWithAuthorities(Long id) {
        return operadorRepository.findOneWithPerfisById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Operador> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(operadorRepository::findOneWithPerfisByLogin);
    }

    public List<String> getAuthorities() {
        return perfilRepository.findAll().stream().map(Perfil::getNome).collect(Collectors.toList());
    }

}
