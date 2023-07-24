package org.javakid.Finder.payload.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {

    private String type;
    private String accessToken;
    private String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {
        this.type = "Bearer";
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
