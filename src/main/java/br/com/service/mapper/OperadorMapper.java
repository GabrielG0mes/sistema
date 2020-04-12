package br.com.service.mapper;

import br.com.domain.Perfil;
import br.com.domain.Operador;
import br.com.service.dto.OperadorDTO;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperadorMapper {

    public List<OperadorDTO> operadoresToOperadoresDTO(List<Operador> operadors) {
        return operadors.stream()
            .filter(Objects::nonNull)
            .map(this::operadorToOperadorDTO)
            .collect(Collectors.toList());
    }

    public OperadorDTO operadorToOperadorDTO(Operador operador) {
        return new OperadorDTO(operador);
    }

    public List<Operador> operadorDTOsToOperadores(List<OperadorDTO> operadorDTOS) {
        return operadorDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::operadorDTOToOperador)
            .collect(Collectors.toList());
    }

    public Operador operadorDTOToOperador(OperadorDTO operadorDTO) {
        if (operadorDTO == null) {
            return null;
        } else {
            Operador operador = new Operador();
            operador.setId(operadorDTO.getId());
            operador.setLogin(operadorDTO.getLogin());
            operador.setNome(operadorDTO.getNome());
            Set<Perfil> perfis = this.perfisFromStrings(operadorDTO.getPerfis());
            operador.setPerfis(perfis);
            return operador;
        }
    }


    private Set<Perfil> perfisFromStrings(Set<String> perfisAsString) {
        Set<Perfil> perfis = new HashSet<>();

        if (perfisAsString != null) {
            perfis = perfisAsString.stream().map(string -> {
                Perfil auth = new Perfil();
                auth.setNome(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return perfis;
    }

    public Operador operadorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Operador operador = new Operador();
        operador.setId(id);
        return operador;
    }
}
