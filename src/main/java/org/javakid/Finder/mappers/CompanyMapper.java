package org.javakid.Finder.mappers;

import org.javakid.Finder.dto.CompanyDto;
import org.javakid.Finder.entity.Company;
import org.javakid.Finder.payload.requests.CompanyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompanyMapper {

    CompanyDto toDto(Company company);
    Company toEntity(CompanyRequest companyRequest);
}
