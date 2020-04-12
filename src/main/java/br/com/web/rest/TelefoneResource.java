package br.com.web.rest;

import br.com.security.PerfisConstants;
import br.com.service.TelefoneService;
import br.com.service.dto.TelefoneDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TelefoneResource {

    private static final String ENTITY_NAME = "telefone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelefoneService telefoneService;

    public TelefoneResource(TelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    @PostMapping("/telefones")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.GERENTE + "\")")
    public ResponseEntity<TelefoneDTO> createTelefone(@RequestBody TelefoneDTO telefoneDTO) throws URISyntaxException {
        if (telefoneDTO.getId() != null) {
            throw new RuntimeException("Telefone já cadastrado");
        }
        TelefoneDTO result = telefoneService.save(telefoneDTO);
        return ResponseEntity.created(new URI("/api/telefones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/telefones")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.GERENTE + "\")")
    public ResponseEntity<TelefoneDTO> updateTelefone(@RequestBody TelefoneDTO telefoneDTO) throws URISyntaxException {
        if (telefoneDTO.getId() == null) {
            throw new RuntimeException("Id inválido");
        }
        TelefoneDTO result = telefoneService.save(telefoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telefoneDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/telefones")
    @PreAuthorize("hasAnyRole(\"" + PerfisConstants.GERENTE + "\", \"" + PerfisConstants.ANALISTA + "\")")
    public ResponseEntity<List<TelefoneDTO>> getAllTelefones(Pageable pageable) {
        Page<TelefoneDTO> page = telefoneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/telefones/{id}")
    @PreAuthorize("hasAnyRole(\"" + PerfisConstants.GERENTE + "\", \"" + PerfisConstants.ANALISTA + "\")")
    public ResponseEntity<TelefoneDTO> getTelefone(@PathVariable Long id) {
        Optional<TelefoneDTO> telefoneDTO = telefoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telefoneDTO);
    }

    @DeleteMapping("/telefones/{id}")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.GERENTE + "\")")
    public ResponseEntity<Void> deleteTelefone(@PathVariable Long id) {
        telefoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
