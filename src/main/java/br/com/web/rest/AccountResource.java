package br.com.web.rest;

import br.com.domain.Operador;
import br.com.repository.OperadorRepository;
import br.com.security.SecurityUtils;
import br.com.service.OperadorService;
import br.com.service.dto.OperadorDTO;
import br.com.service.dto.PasswordChangeDTO;
import br.com.web.rest.vm.ManagedOperadorVM;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final OperadorRepository operadorRepository;

    private final OperadorService operadorService;

    public AccountResource(OperadorRepository operadorRepository, OperadorService operadorService) {

        this.operadorRepository = operadorRepository;
        this.operadorService = operadorService;
    }

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @GetMapping("/account")
    public OperadorDTO getAccount() {
        return operadorService.getUserWithAuthorities()
            .map(OperadorDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody OperadorDTO operadorDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<Operador> user = operadorRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        operadorService.updateUser(operadorDTO.getNome());
    }

    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new RuntimeException("Senha inválida");
        }
        operadorService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedOperadorVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedOperadorVM.PASSWORD_MAX_LENGTH;
    }
}
