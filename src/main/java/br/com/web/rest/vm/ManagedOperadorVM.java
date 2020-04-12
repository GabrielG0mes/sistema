package br.com.web.rest.vm;

import br.com.service.dto.OperadorDTO;

import javax.validation.constraints.Size;

public class ManagedOperadorVM extends OperadorDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedOperadorVM() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
