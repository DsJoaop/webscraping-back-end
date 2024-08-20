package br.com.webscraping.mapper;

import br.com.webscraping.dto.RoleDTO;
import br.com.webscraping.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    @Override
    RoleDTO toDto(Role entity);

    @Override
    Role toEntity(RoleDTO dto);
}
