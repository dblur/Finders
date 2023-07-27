package org.javakid.Finder.payload.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.javakid.Finder.annotations.RoleSubset;
import org.javakid.Finder.enums.ERole;
import org.javakid.Finder.enums.ESex;

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
    private ESex sex;

    @NotNull
    @RoleSubset(anyOf = { ERole.CANDIDATE, ERole.RECRUITER })
    private ERole role;

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
