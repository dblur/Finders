package org.javakid.Finder.mappers;

import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.payload.requests.UserRequest;
import org.javakid.Finder.payload.requests.SignUpRequest;
import org.javakid.Finder.services.crud.RoleCrudService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        componentModel = "spring",
        uses = CompanyMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder encoder;

    @Autowired
    protected RoleCrudService service;

    public abstract UserDto toDto(User user);
    public abstract User toEntity(UserRequest request);

    @Mapping(target = "password", expression = "java(encoder.encode(request.getPassword()))")
    @Mapping(target = "roles", expression = "java(java.util.Collections.singleton(service.getRoleByName(request.getRole().toString())))")
    public abstract User toEntity(SignUpRequest request);
}
