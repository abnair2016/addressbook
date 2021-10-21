package com.anair.addressbook.io;

import com.anair.addressbook.model.Person;

import java.io.IOException;
import java.util.List;

public interface Reader {
    List<Person> read(final String fileName) throws IOException;
}
