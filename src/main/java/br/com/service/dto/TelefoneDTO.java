package br.com.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import br.com.domain.enumeration.TipoTelefone;

public class TelefoneDTO implements Serializable {

    private Long id;

    private String dDD;

    private String numero;

    private TipoTelefone tipo;

    private Instant dataCadastro;

    private String loginOperador;


    private Long pessoaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getdDD() {
        return dDD;
    }

    public void setdDD(String dDD) {
        this.dDD = dDD;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
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

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

}
