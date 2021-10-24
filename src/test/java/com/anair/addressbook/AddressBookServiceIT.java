package com.anair.addressbook;

import com.anair.addressbook.io.CsvFileReader;
import com.anair.addressbook.io.Reader;
import com.anair.addressbook.model.Person;
import com.anair.addressbook.service.AddressBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AddressBookServiceIT {
    final Reader reader = new CsvFileReader();

    private AddressBookService addressBookService;

    @BeforeEach
    void initialise() throws IOException {
        var people = reader.read("TestAddressBook.csv");
        addressBookService = new AddressBookService(people);
    }

    @Test
    @DisplayName("How many males are in the address book?")
    void countOfMalesTest() {
        assertThat(addressBookService.countOfMales()).isEqualTo(2);
    }

    @Test
    @DisplayName("Who is the oldest person in the address book?")
    void oldestPersonTest() {
        assertThat(addressBookService.getOldestPerson().getFullName()).isEqualTo("Test E");
    }

    @Test
    @DisplayName("How many days older is Test D person than Test B person?")
    void daysPersonOlderThanAnotherPersonTest() {
        var person = addressBookService.personByName("d").get();
        var anotherPerson = addressBookService.personByName("b").get();

        assertThat(addressBookService.daysBetween(person, anotherPerson))
                .isEqualTo(1579L);
    }

    private Person person(final String name, final String gender, LocalDate dateOfBirth) {
        return Person.builder()
                .fullName(name)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .build();
    }
}
