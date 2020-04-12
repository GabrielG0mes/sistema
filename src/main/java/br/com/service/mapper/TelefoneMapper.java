package br.com.service.mapper;


import br.com.domain.*;
import br.com.service.dto.TelefoneDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface TelefoneMapper extends EntityMapper<TelefoneDTO, Telefone> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    TelefoneDTO toDto(Telefone telefone);

    @Mapping(source = "pessoaId", target = "pessoa")
    Telefone toEntity(TelefoneDTO telefoneDTO);

    default Telefone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Telefone telefone = new Telefone();
        telefone.setId(id);
        return telefone;
    }
}
