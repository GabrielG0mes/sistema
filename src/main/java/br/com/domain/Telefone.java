package br.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import br.com.domain.enumeration.TipoTelefone;

@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "d_dd")
    private String dDD;

    @Column(name = "numero")
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTelefone tipo;

    @Column(name = "data_cadastro")
    private Instant dataCadastro;

    @Column(name = "login_operador")
    private String loginOperador;

    @ManyToOne
    @JsonIgnoreProperties("telefones")
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getdDD() {
        return dDD;
    }

    public Telefone dDD(String dDD) {
        this.dDD = dDD;
        return this;
    }

    public void setdDD(String dDD) {
        this.dDD = dDD;
    }

    public String getNumero() {
        return numero;
    }

    public Telefone numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public Telefone tipo(TipoTelefone tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public Telefone dataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getLoginOperador() {
        return loginOperador;
    }

    public Telefone loginOperador(String loginOperador) {
        this.loginOperador = loginOperador;
        return this;
    }

    public void setLoginOperador(String loginOperador) {
        this.loginOperador = loginOperador;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Telefone pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
