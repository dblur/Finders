package org.javakid.Finder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "email", nullable = false)
    @NonNull
    private String email;

    @Column(name = "phone", nullable = false)
    @NonNull
    private String phone;

    @Column(name = "city", nullable = false)
    @NonNull
    private String city;

    @Column(name = "country", nullable = false)
    @NonNull
    private String country;

    @Column(name = "scope", nullable = false)
    @NonNull
    private String scope;
}
