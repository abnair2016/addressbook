package com.anair.addressbook.model;

import com.anair.addressbook.exception.InvalidPersonException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@EqualsAndHashCode
public class Person {
    private final String fullName;
    private final String gender;
    private final LocalDate dateOfBirth;

    public Person(final String fullName, final String gender, final LocalDate dateOfBirth) {
        if (null == fullName || fullName.isBlank()) {
            throw new InvalidPersonException("Full name is missing");
        }
        if (null == gender || gender.isBlank()) {
            throw new InvalidPersonException("Gender is missing");
        }
        if (null == dateOfBirth) {
            throw new InvalidPersonException("Date of birth is missing");
        }

        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
}
