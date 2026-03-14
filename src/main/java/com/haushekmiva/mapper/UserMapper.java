package com.haushekmiva.mapper;

import com.haushekmiva.dto.UserDto;
import com.haushekmiva.dto.UserDtoWithLocations;
import com.haushekmiva.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LocationMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserDto toDto(User user);
    UserDtoWithLocations toDtoWithLocations(User user);

}
