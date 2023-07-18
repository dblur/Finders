package org.javakid.Finder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javakid.Finder.enums.Role;
import org.javakid.Finder.enums.Sex;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String secondName;
    private Integer age;
    private Sex sex;
    private Role role;
    private String email;
    private String phone;
    private String city;
    private String country;
    private String experience;
    private CompanyDto company;
}
