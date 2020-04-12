package br.com.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import br.com.domain.enumeration.TipoPessoa;

public class PessoaDTO implements Serializable {

    private Long id;

    private String nome;

    private String documento;

    private Instant dataNascimento;

    private String nomeMae;

    private String nomePai;

    private Instant dataCadastro;

    private String loginOperador;

    private TipoPessoa tipo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Instant getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getLoginOperador() {
        return loginOperador;
    }

    public void setLoginOperador(String loginOperador) {
        this.loginOperador = loginOperador;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

}
