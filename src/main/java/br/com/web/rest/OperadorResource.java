package br.com.web.rest;

import br.com.config.Constants;
import br.com.domain.Operador;
import br.com.repository.OperadorRepository;
import br.com.security.PerfisConstants;
import br.com.service.OperadorService;
import br.com.service.dto.OperadorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OperadorResource {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperadorService operadorService;

    private final OperadorRepository operadorRepository;

    public OperadorResource(OperadorService operadorService, OperadorRepository operadorRepository) {
        this.operadorService = operadorService;
        this.operadorRepository = operadorRepository;
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.ADMINISTRADOR + "\")")
    public ResponseEntity<OperadorDTO> createUser(@Valid @RequestBody OperadorDTO operadorDTO) throws URISyntaxException {
        if (operadorDTO.getId() != null) {
            throw new RuntimeException("Um novo Operador não pode ter um ID");
        } else if (operadorRepository.findOneByLogin(operadorDTO.getLogin().toLowerCase()).isPresent()) {
            throw new RuntimeException("Login já cadastrado");
        } else {
            OperadorDTO newOperador = operadorService.createUser(operadorDTO);
            return ResponseEntity.created(new URI("/api/users/" + newOperador.getLogin()))
                .headers(HeaderUtil.createAlert(applicationName,  "Operador criado: " + newOperador.getLogin(), newOperador.getLogin()))
                .body(newOperador);
        }
    }

    @PutMapping("/users")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.ADMINISTRADOR + "\")")
    public ResponseEntity<OperadorDTO> updateUser(@Valid @RequestBody OperadorDTO operadorDTO) {
        Optional<Operador> existingUser = operadorRepository.findOneByLogin(operadorDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(operadorDTO.getId()))) {
            throw new RuntimeException("Login já cadastrado");
        }
        Optional<OperadorDTO> updatedUser = operadorService.updateUser(operadorDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert(applicationName, "Operador atualizado: " + operadorDTO.getLogin(), operadorDTO.getLogin()));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.ADMINISTRADOR + "\")")
    public ResponseEntity<List<OperadorDTO>> getAllUsers(Pageable pageable) {
        final Page<OperadorDTO> page = operadorService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users/authorities")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.ADMINISTRADOR + "\")")
    public List<String> getAuthorities() {
        return operadorService.getAuthorities()
            .stream().filter(perfil -> !perfil.equals(PerfisConstants.ADMINISTRADOR))
            .collect(Collectors.toList());
    }

    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<OperadorDTO> getUser(@PathVariable String login) {
        return ResponseUtil.wrapOrNotFound(
            operadorService.getUserWithAuthoritiesByLogin(login)
                .map(OperadorDTO::new));
    }

    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.ADMINISTRADOR + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        operadorService.deleteUser(login);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName,  "Operador excluído: " + login, login)).build();
    }
}
