package com.anair.addressbook.service;

import com.anair.addressbook.exception.InvalidPersonException;
import com.anair.addressbook.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressBookServiceTest {

    private AddressBookService addressBookService;

    @Test
    @DisplayName("Should get the number of males in an address book")
    void countOfMalesTest() {
        givenAddressBook(
                person("Test A", "Male", LocalDate.of(2000, 11, 11)),
                person("Test B", "Female", LocalDate.of(1999, 12, 12)),
                person("Test C", "Unknown", LocalDate.of(1998, 12, 12)),
                person("Test D", "Male", LocalDate.of(1997, 12, 12))
        );

        assertThat(addressBookService.countOfMales()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should get the oldest person from the address book")
    void getOldestPersonTest() {
        givenAddressBook(
                person("Test A", "Male", LocalDate.of(2000, 11, 11)),
                person("Test B", "Female", LocalDate.of(1999, 12, 12)),
                person("Test C", "Unknown", LocalDate.of(1998, 12, 12)),
                person("Test D", "Male", LocalDate.of(1997, 12, 12))
        );

        assertThat(addressBookService.getOldestPerson().getFullName()).isEqualTo("Test D");
    }

    @Test
    @DisplayName("Should return true when Paul exists in address book")
    void personByNameExistsTest() {
        givenAddressBook(
                person("Test A", "Male", LocalDate.of(2000, 11, 11)),
                person("Test B", "Female", LocalDate.of(1999, 12, 12)),
                person("Test C", "Unknown", LocalDate.of(1998, 12, 12)),
                person("Paul Jones", "Male", LocalDate.of(1997, 12, 11)),
                person("Test D", "Male", LocalDate.of(1997, 12, 12))
        );

        assertThat(addressBookService.personByName("paul")).isPresent();
    }

    @Test
    @DisplayName("Should return false for null and blank names")
    void invalidPersonByNameTest() {
        givenAddressBook(emptyList());

        assertThat(addressBookService.personByName(null)).isNotPresent();
        assertThat(addressBookService.personByName(" ")).isNotPresent();
    }

    @Test
    @DisplayName("Should return false when Bill does not exist in address book")
    void personByNameDoesNotExistTest() {
        givenAddressBook(
                person("Test A", "Male", LocalDate.of(2000, 11, 11)),
                person("Test B", "Female", LocalDate.of(1999, 12, 12)),
                person("Test C", "Unknown", LocalDate.of(1998, 12, 12)),
                person("Test D", "Male", LocalDate.of(1997, 12, 12))
        );

        assertThat(addressBookService.personByName("bill")).isNotPresent();
    }

    @Test
    @DisplayName("Should get days between Bill and Pauls age")
    void daysBetweenBillAndPaulsAgeTest() {
        givenAddressBook(
                person("Bill Jones", "Male", LocalDate.of(1997, 11, 11)),
                person("Paul Jones", "Male", LocalDate.of(1997, 11, 12)),
                person("Test A", "Male", LocalDate.of(2000, 11, 11)),
                person("Test B", "Female", LocalDate.of(1999, 12, 12)),
                person("Test C", "Unknown", LocalDate.of(1998, 12, 12)),
                person("Test D", "Male", LocalDate.of(1997, 12, 12))
        );

        var bill = addressBookService.personByName("bill").get();
        var paul = addressBookService.personByName("paul").get();

        assertThat(addressBookService.daysBetween(bill, paul)).isEqualTo(1);
    }

    @Test
    @DisplayName("Should throw InvalidPersonException for null inputs")
    void daysBetweenExceptionTest() {
        givenAddressBook(emptyList());

        assertThatThrownBy(() -> addressBookService.daysBetween(null, null))
                .isInstanceOf(InvalidPersonException.class)
                .hasMessage("Person does not exist");
    }

    private Person person(final String name, final String gender, LocalDate dateOfBirth) {
        return Person.builder()
                .fullName(name)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .build();
    }

    private void givenAddressBook(final Person... people) {
        this.addressBookService = new AddressBookService(Arrays.asList(people));
    }

    private void givenAddressBook(final List<Person> people) {
        this.addressBookService = new AddressBookService(people);
    }
}
