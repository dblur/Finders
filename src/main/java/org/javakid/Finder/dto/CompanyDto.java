package org.javakid.Finder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String city;
    private String country;
    private String scope;
}
