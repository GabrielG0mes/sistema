package br.com.service.mapper;


import br.com.domain.*;
import br.com.service.dto.PessoaDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {})
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {


    @Mapping(target = "telefones", ignore = true)
    @Mapping(target = "removeTelefone", ignore = true)
    Pessoa toEntity(PessoaDTO pessoaDTO);

    default Pessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        return pessoa;
    }
}
