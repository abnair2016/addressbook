package com.anair.addressbook.model;

import com.anair.addressbook.exception.InvalidPersonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PersonTest {

    @Test
    @DisplayName("Should throw InvalidPersonException for null full name")
    void nullFullNameTest() {
        assertThatThrownBy(() -> givenName(null))
                .isInstanceOf(InvalidPersonException.class)
                .hasMessage("Full name is missing");
    }

    @Test
    @DisplayName("Should throw InvalidPersonException for null gender")
    void nullGenderTest() {
        assertThatThrownBy(() -> givenGender(null))
                .isInstanceOf(InvalidPersonException.class)
                .hasMessage("Gender is missing");
    }

    @Test
    @DisplayName("Should throw InvalidPersonException for null date of birth")
    void nullDateOfBirthTest() {
        assertThatThrownBy(this::givenNullDateOfBirth)
                .isInstanceOf(InvalidPersonException.class)
                .hasMessage("Date of birth is missing");
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    @DisplayName("Should throw InvalidPersonException for person with invalid full name")
    void invalidFullNameTest(final String invalidFullName) {
        assertThatThrownBy(() -> givenName(invalidFullName))
                .isInstanceOf(InvalidPersonException.class)
                .hasMessage("Full name is missing");
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    @DisplayName("Should throw InvalidPersonException for person with invalid gender")
    void invalidGenderTest(final String invalidGender) {
        assertThatThrownBy(() -> givenGender(invalidGender))
                .isInstanceOf(InvalidPersonException.class)
                .hasMessage("Gender is missing");
    }

    private static Stream<Arguments> invalidInputs() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("   ")
        );
    }

    private void givenName(final String fullName) {
        person(fullName, "Male", LocalDate.now());
    }

    private void givenGender(final String gender) {
        person("Test Name", gender, LocalDate.now());
    }

    private void givenNullDateOfBirth() {
        person("Test Name", "Female", null);
    }

    private void person(final String name, final String gender, LocalDate dateOfBirth) {
        Person.builder()
                .fullName(name)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .build();
    }
}