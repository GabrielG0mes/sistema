package br.com.web.rest;

import br.com.SistemaApp;
import br.com.domain.Telefone;
import br.com.repository.TelefoneRepository;
import br.com.service.TelefoneService;
import br.com.service.dto.TelefoneDTO;
import br.com.service.mapper.TelefoneMapper;

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

import br.com.domain.enumeration.TipoTelefone;
/**
 * Integration tests for the {@link TelefoneResource} REST controller.
 */
@SpringBootTest(classes = SistemaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TelefoneResourceIT {

    private static final String DEFAULT_D_DD = "AAAAAAAAAA";
    private static final String UPDATED_D_DD = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final TipoTelefone DEFAULT_TIPO = TipoTelefone.CELULAR;
    private static final TipoTelefone UPDATED_TIPO = TipoTelefone.FIXO;

    private static final Instant DEFAULT_DATA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOGIN_OPERADOR = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_OPERADOR = "BBBBBBBBBB";

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Autowired
    private TelefoneService telefoneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelefoneMockMvc;

    private Telefone telefone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createEntity(EntityManager em) {
        Telefone telefone = new Telefone()
            .dDD(DEFAULT_D_DD)
            .numero(DEFAULT_NUMERO)
            .tipo(DEFAULT_TIPO)
            .dataCadastro(DEFAULT_DATA_CADASTRO)
            .loginOperador(DEFAULT_LOGIN_OPERADOR);
        return telefone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createUpdatedEntity(EntityManager em) {
        Telefone telefone = new Telefone()
            .dDD(UPDATED_D_DD)
            .numero(UPDATED_NUMERO)
            .tipo(UPDATED_TIPO)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .loginOperador(UPDATED_LOGIN_OPERADOR);
        return telefone;
    }

    @BeforeEach
    public void initTest() {
        telefone = createEntity(em);
    }

    @Test
    @Transactional
    public void createTelefone() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);
        restTelefoneMockMvc.perform(post("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON));

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate + 1);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getdDD()).isEqualTo(DEFAULT_D_DD);
        assertThat(testTelefone.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTelefone.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTelefone.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testTelefone.getLoginOperador()).isEqualTo(DEFAULT_LOGIN_OPERADOR);
    }

    @Test
    @Transactional
    public void createTelefoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();

        // Create the Telefone with an existing ID
        telefone.setId(1L);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoneMockMvc.perform(post("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON));

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTelefones() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList
        restTelefoneMockMvc.perform(get("/api/telefones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefone.getId().intValue())))
            .andExpect(jsonPath("$.[*].dDD").value(hasItem(DEFAULT_D_DD)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].loginOperador").value(hasItem(DEFAULT_LOGIN_OPERADOR)));
    }

    @Test
    @Transactional
    public void getTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get the telefone
        restTelefoneMockMvc.perform(get("/api/telefones/{id}", telefone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telefone.getId().intValue()))
            .andExpect(jsonPath("$.dDD").value(DEFAULT_D_DD))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
            .andExpect(jsonPath("$.loginOperador").value(DEFAULT_LOGIN_OPERADOR));
    }

    @Test
    @Transactional
    public void getNonExistingTelefone() throws Exception {
        // Get the telefone
        restTelefoneMockMvc.perform(get("/api/telefones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone
        Telefone updatedTelefone = telefoneRepository.findById(telefone.getId()).get();
        // Disconnect from session so that the updates on updatedTelefone are not directly saved in db
        em.detach(updatedTelefone);
        updatedTelefone
            .dDD(UPDATED_D_DD)
            .numero(UPDATED_NUMERO)
            .tipo(UPDATED_TIPO)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .loginOperador(UPDATED_LOGIN_OPERADOR);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(updatedTelefone);

        restTelefoneMockMvc.perform(put("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON));

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getdDD()).isEqualTo(UPDATED_D_DD);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTelefone.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTelefone.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testTelefone.getLoginOperador()).isEqualTo(UPDATED_LOGIN_OPERADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoneMockMvc.perform(put("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON));

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeDelete = telefoneRepository.findAll().size();

        // Delete the telefone
        restTelefoneMockMvc.perform(delete("/api/telefones/{id}", telefone.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
