package org.javakid.Finder.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "recruiters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NonNull
    private String firstName;

    @Column(name = "second_name", nullable = false)
    @NonNull
    private String secondName;

    @Column(name = "age", nullable = false)
    @NonNull
    private Integer age;

    @Column(name = "sex", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private Sex sex;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
