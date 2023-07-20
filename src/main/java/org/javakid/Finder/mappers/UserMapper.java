package org.javakid.Finder.mappers;

import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.payload.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = CompanyMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserRequest userRequest);
}
