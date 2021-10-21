package com.anair.addressbook.service;

import com.anair.addressbook.exception.InvalidPersonException;
import com.anair.addressbook.model.Person;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

@Getter
@RequiredArgsConstructor
public class AddressBookService {
    private final List<Person> people;

    public int countOfMales() {
        return (int) males().count();
    }

    public Person getOldestPerson() {
        return oldestPerson()
                .orElse(null);
    }

    public Optional<Person> personByName(final String name) {
        if (null == name || name.isBlank()) {
            return Optional.empty();
        }
        return people.stream()
                .filter(person -> person.getFullName().toLowerCase(Locale.getDefault()).contains(name.toLowerCase(Locale.getDefault())))
                .findFirst();
    }

    public long daysBetween(final Person person, final Person anotherPerson) {
        if (null == person || null == anotherPerson) {
            throw new InvalidPersonException("Person does not exist");
        }
        return DAYS.between(person.getDateOfBirth(), anotherPerson.getDateOfBirth());
    }

    private Optional<Person> oldestPerson() {
        return people.stream()
                .min(Comparator.comparing(Person::getDateOfBirth));
    }

    private Stream<Person> males() {
        return people.stream()
                .filter(this::isMale);
    }

    private boolean isMale(final Person person) {
        return "male".equalsIgnoreCase(person.getGender());
    }
}
