package br.com.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import br.com.domain.enumeration.TipoPessoa;

@Entity
@Table(name = "pessoa")
public class Pessoa extends EntidadeAuditada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "documento")
    private String documento;

    @Column(name = "data_nascimento")
    private Instant dataNascimento;

    @Column(name = "nome_mae")
    private String nomeMae;

    @Column(name = "nome_pai")
    private String nomePai;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoPessoa tipo;

    @OneToMany(mappedBy = "pessoa")
    private Set<Telefone> telefones = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public Pessoa documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Instant getDataNascimento() {
        return dataNascimento;
    }

    public Pessoa dataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public Pessoa nomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
        return this;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public Pessoa nomePai(String nomePai) {
        this.nomePai = nomePai;
        return this;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public Pessoa tipo(TipoPessoa tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public Pessoa telefones(Set<Telefone> telefones) {
        this.telefones = telefones;
        return this;
    }

    public Pessoa addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setPessoa(this);
        return this;
    }

    public Pessoa removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setPessoa(null);
        return this;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

}
