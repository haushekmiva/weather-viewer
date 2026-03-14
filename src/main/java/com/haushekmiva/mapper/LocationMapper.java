package com.haushekmiva.mapper;

import com.haushekmiva.dto.LocationDto;
import com.haushekmiva.model.Location;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LocationMapper {

    LocationDto toDto(Location location);

}
