package com.haushekmiva.mapper;

import com.haushekmiva.model.User;
import com.haushekmiva.dto.UserDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LocationMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserDto toDto(User user);

}
