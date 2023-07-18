package org.javakid.Finder.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String city;

    @NotEmpty
    private String country;

    @NotEmpty
    private String scope;
}
