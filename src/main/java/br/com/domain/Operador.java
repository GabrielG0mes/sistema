package br.com.domain;

import br.com.config.Constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "operador")
public class Operador extends EntidadeAuditada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(max = 15, min = 6)
    @Column(length = 15, unique = true, nullable = false, updatable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "senha", length = 60, nullable = false)
    private String senha;

    @Size(max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "operador_perfil",
        joinColumns = {@JoinColumn(name = "operador_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "perfil_nome", referencedColumnName = "nome")})
    @BatchSize(size = 20)
    private Set<Perfil> perfis = new HashSet<>();

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
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

}
