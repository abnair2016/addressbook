package com.anair.addressbook;

import com.anair.addressbook.io.CsvFileReader;
import com.anair.addressbook.io.Reader;
import com.anair.addressbook.service.AddressBookService;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        final Reader reader = new CsvFileReader();
        var people = reader.read("AddressBook.csv");
        if (null == people || people.isEmpty()) {
            System.err.println("Address book contains no entries");
        }

        var addressBook = new AddressBookService(people);
        System.out.printf(
                "1. How many males are in the address book? %s%n",
                addressBook.countOfMales()
        );

        System.out.printf(
                "2. Who is the oldest person in the address book? %s%n",
                addressBook.getOldestPerson().getFullName()
        );

        var bill = addressBook.personByName("bill");
        var paul = addressBook.personByName("paul");
        if (bill.isPresent() && paul.isPresent()) {
            System.out.printf(
                    "3. How many days older is Bill than Paul? %s days",
                    addressBook.daysBetween(bill.get(), paul.get())
            );
        }
    }
}
