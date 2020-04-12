package br.com.web.rest;

import br.com.SistemaApp;
import br.com.domain.Pessoa;
import br.com.repository.PessoaRepository;
import br.com.service.PessoaService;
import br.com.service.dto.PessoaDTO;
import br.com.service.mapper.PessoaMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.domain.enumeration.TipoPessoa;

@SpringBootTest(classes = SistemaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PessoaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_NASCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_NASCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOME_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_PAI = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PAI = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOGIN_OPERADOR = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_OPERADOR = "BBBBBBBBBB";

    private static final TipoPessoa DEFAULT_TIPO = TipoPessoa.FISICA;
    private static final TipoPessoa UPDATED_TIPO = TipoPessoa.JURIDICA;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .nome(DEFAULT_NOME)
            .documento(DEFAULT_DOCUMENTO)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .nomeMae(DEFAULT_NOME_MAE)
            .nomePai(DEFAULT_NOME_PAI)
            .tipo(DEFAULT_TIPO);
        return pessoa;
    }

    public static Pessoa createUpdatedEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .nome(UPDATED_NOME)
            .documento(UPDATED_DOCUMENTO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .tipo(UPDATED_TIPO);
        return pessoa;
    }

    @BeforeEach
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON));

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPessoa.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testPessoa.getNomeMae()).isEqualTo(DEFAULT_NOME_MAE);
        assertThat(testPessoa.getNomePai()).isEqualTo(DEFAULT_NOME_PAI);
        assertThat(testPessoa.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testPessoa.getLoginOperador()).isEqualTo(DEFAULT_LOGIN_OPERADOR);
        assertThat(testPessoa.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        pessoa.setId(1L);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON));

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPessoas() throws Exception {
        pessoaRepository.saveAndFlush(pessoa);

        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE)))
            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI)))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].loginOperador").value(hasItem(DEFAULT_LOGIN_OPERADOR)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getPessoa() throws Exception {
        pessoaRepository.saveAndFlush(pessoa);

        restPessoaMockMvc.perform(get("/api/pessoas/{id}", pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.nomeMae").value(DEFAULT_NOME_MAE))
            .andExpect(jsonPath("$.nomePai").value(DEFAULT_NOME_PAI))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
            .andExpect(jsonPath("$.loginOperador").value(DEFAULT_LOGIN_OPERADOR))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPessoa() throws Exception {
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa() throws Exception {
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        Pessoa updatedPessoa = pessoaRepository.findById(pessoa.getId()).get();
        em.detach(updatedPessoa);
        updatedPessoa
            .nome(UPDATED_NOME)
            .documento(UPDATED_DOCUMENTO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .tipo(UPDATED_TIPO);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(updatedPessoa);

        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON));

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testPessoa.getNomeMae()).isEqualTo(UPDATED_NOME_MAE);
        assertThat(testPessoa.getNomePai()).isEqualTo(UPDATED_NOME_PAI);
        assertThat(testPessoa.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testPessoa.getLoginOperador()).isEqualTo(UPDATED_LOGIN_OPERADOR);
        assertThat(testPessoa.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(MediaType.APPLICATION_JSON));

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePessoa() throws Exception {
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        restPessoaMockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
