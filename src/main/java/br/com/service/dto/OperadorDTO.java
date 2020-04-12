package br.com.service.dto;

import br.com.config.Constants;

import br.com.domain.Perfil;
import br.com.domain.Operador;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public class OperadorDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(max = 15)
    private String login;

    @Size(max = 100)
    private String nome;

    @Size(max = 100)
    private String senha;

    private Instant dataCadastro;

    private Set<String> perfis;

    public OperadorDTO() {

    }

    public OperadorDTO(Operador operador) {
        this.id = operador.getId();
        this.login = operador.getLogin();
        this.nome = operador.getNome();
        this.dataCadastro = operador.getDataCadastro();
        this.senha = operador.getSenha();
        this.perfis = operador.getPerfis().stream()
            .map(Perfil::getNome)
            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Instant dataCriacao) {
        this.dataCadastro = dataCriacao;
    }

    public Set<String> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<String> perfis) {
        this.perfis = perfis;
    }

}
