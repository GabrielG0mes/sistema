package br.com.web.rest;

import br.com.security.PerfisConstants;
import br.com.service.PessoaService;
import br.com.service.dto.PessoaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class PessoaResource {

    private static final String ENTITY_NAME = "pessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PessoaService pessoaService;

    public PessoaResource(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/pessoas")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.GERENTE + "\")")
    public ResponseEntity<PessoaDTO> createPessoa(@RequestBody PessoaDTO pessoaDTO) throws URISyntaxException {
        if (pessoaDTO.getId() != null) {
            throw new RuntimeException("Nova Pessoa não pode ter um ID");
        }
        PessoaDTO result = pessoaService.save(pessoaDTO);
        return ResponseEntity.created(new URI("/api/pessoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/pessoas")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.GERENTE + "\")")
    public ResponseEntity<PessoaDTO> updatePessoa(@RequestBody PessoaDTO pessoaDTO) throws URISyntaxException {
        if (pessoaDTO.getId() == null) {
            throw new RuntimeException("Id inválido");
        }
        PessoaDTO result = pessoaService.save(pessoaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pessoaDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/pessoas")
    @PreAuthorize("hasAnyRole(\"" + PerfisConstants.GERENTE + "\", \"" + PerfisConstants.ANALISTA + "\")")
    public ResponseEntity<List<PessoaDTO>> getAllPessoas(Pageable pageable) {
        Page<PessoaDTO> page = pessoaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/pessoas/{id}")
    @PreAuthorize("hasAnyRole(\"" + PerfisConstants.GERENTE + "\", \"" + PerfisConstants.ANALISTA + "\")")
    public ResponseEntity<PessoaDTO> getPessoa(@PathVariable Long id) {
        Optional<PessoaDTO> pessoaDTO = pessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pessoaDTO);
    }

    @DeleteMapping("/pessoas/{id}")
    @PreAuthorize("hasAuthority(\"" + PerfisConstants.GERENTE + "\")")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
