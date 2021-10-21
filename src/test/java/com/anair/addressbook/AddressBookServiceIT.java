package com.anair.addressbook;

import com.anair.addressbook.io.Reader;
import com.anair.addressbook.model.Person;
import com.anair.addressbook.service.AddressBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AddressBookServiceIT {
    @Mock
    Reader reader;

    private AddressBookService addressBookService;

    @BeforeEach
    void initialise() throws IOException {
        given(reader.read(any())).willReturn(
                List.of(
                        person("Bill McKnight", "Male", LocalDate.of(1977, 3, 16)),
                        person("Paul Robinson", "Male", LocalDate.of(1985, 1, 15)),
                        person("Gemma Lane", "Female", LocalDate.of(1991, 11, 20)),
                        person("Sarah Stone", "Female", LocalDate.of(1980, 9, 20)),
                        person("Wes Jackson", "Male", LocalDate.of(1974, 8, 14))
                )
        );
        var people = reader.read("/path/to/file");
        addressBookService = new AddressBookService(people);
    }

    @Test
    @DisplayName("How many males are in the address book?")
    void countOfMalesTest() {
        assertThat(addressBookService.countOfMales()).isEqualTo(3);
    }

    @Test
    @DisplayName("Who is the oldest person in the address book?")
    void oldestPersonTest() {
        assertThat(addressBookService.getOldestPerson().getFullName()).isEqualTo("Wes Jackson");
    }

    @Test
    @DisplayName("How many days older is Bill than Paul?")
    void daysBillOlderThanPaulTest() {
        var bill = addressBookService.personByName("bill").get();
        var paul = addressBookService.personByName("paul").get();

        assertThat(addressBookService.getOldestPerson().getFullName()).isEqualTo("Wes Jackson");
    }

    private Person person(final String name, final String gender, LocalDate dateOfBirth) {
        return Person.builder()
                .fullName(name)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .build();
    }
}
