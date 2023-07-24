package org.javakid.Finder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REFRESH_TOKENS")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject", nullable = false, unique = true)
    private String subject;

    @Column(name = "token", nullable = false)
    private String token;

    public RefreshToken(String subject, String token) {
        this.subject = subject;
        this.token = token;
    }
}
