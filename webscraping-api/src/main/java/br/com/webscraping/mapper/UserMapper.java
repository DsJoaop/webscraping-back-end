package br.com.webscraping.mapper;

import br.com.webscraping.dto.UserDTO;
import br.com.webscraping.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {
    @Override
    @Mapping(target = "roles", source = "roles")
    UserDTO toDto(User entity);

    @Override
    @Mapping(target = "roles", source = "roles")
    User toEntity(UserDTO dto);
}
