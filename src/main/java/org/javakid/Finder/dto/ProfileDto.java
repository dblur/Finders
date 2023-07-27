package org.javakid.Finder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javakid.Finder.enums.ESex;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long id;
    private String firstName;
    private String secondName;
    private Integer age;
    private ESex sex;
    private String city;
    private String country;
    private String experience;
    private CompanyDto company;
}
