package com.anair.addressbook.io;

import com.anair.addressbook.exception.InvalidResourceException;
import com.anair.addressbook.exception.ResourceNotFoundException;
import com.anair.addressbook.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CsvFileReaderTest {
    private CsvFileReader csvFileReader;

    @BeforeEach
    void initialise() {
        csvFileReader = new CsvFileReader();
    }

    @Test
    @DisplayName("Should read all values for a given valid file")
    void readValidFileTest() throws IOException {
        var people = csvFileReader.read("TestAddressBook.csv");

        assertThat(people).isNotNull().hasSize(5);
        assertThat(people.stream().map(Person::getFullName))
                .containsExactlyInAnyOrder("Test A", "Test B", "Test C", "Test D", "Test E");
        assertThat(people.stream().map(Person::getGender))
                .containsExactlyInAnyOrder("Female", "Male", "Female", "Male", "Female");
        assertThat(people.stream().map(Person::getDateOfBirth))
                .containsExactlyInAnyOrder(
                        LocalDate.of(1997, 4, 26),
                        LocalDate.of(1995, 2, 25),
                        LocalDate.of(2001, 12, 30),
                        LocalDate.of(1990, 10, 30),
                        LocalDate.of(1984, 9, 24)
                );
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException if file does not exist")
    void readMissingFileTest() {
        assertThatThrownBy(() -> csvFileReader.read("MissingAddressBook.csv"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource 'MissingAddressBook.csv' cannot be found");
    }

    @Test
    @DisplayName("Should throw InvalidResourceException while reading file with invalid values")
    void readInvalidFileTest() {
        assertThatThrownBy(() -> csvFileReader.read("InvalidAddressBook.csv"))
                .isInstanceOf(InvalidResourceException.class)
                .hasMessage("Invalid content in resource");
    }
}
