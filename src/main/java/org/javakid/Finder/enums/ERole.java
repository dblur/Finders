package org.javakid.Finder.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ERole {

    CANDIDATE("Candidate"),
    RECRUITER("Recruiter"),
    ADMIN("Admin");

    private final String name;
}
