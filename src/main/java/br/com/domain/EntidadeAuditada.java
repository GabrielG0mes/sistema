package br.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntidadeAuditada implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "login_operador", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String loginOperador;

    @CreatedDate
    @Column(name = "data_cadastro", updatable = false)
    @JsonIgnore
    private Instant dataCadastro = Instant.now();

    public String getLoginOperador() {
        return loginOperador;
    }

    public void setLoginOperador(String loginOperador) {
        this.loginOperador = loginOperador;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}
