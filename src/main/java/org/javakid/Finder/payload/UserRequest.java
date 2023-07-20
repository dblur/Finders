package org.javakid.Finder.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.javakid.Finder.annotations.RoleSubset;
import org.javakid.Finder.enums.Role;
import org.javakid.Finder.enums.Sex;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String secondName;

    @Min(18)
    private Integer age;

    @NotNull
    private Sex sex;

    @NotNull
    @RoleSubset(anyOf = { Role.ROLE_CANDIDATE, Role.ROLE_RECRUITER })
    private Role role;

    @NotEmpty
    private String email;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String city;

    @NotEmpty
    private String country;

    @NotEmpty
    private String experience;

    @NotNull
    private CompanyRequest company;
}
