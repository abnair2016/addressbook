package com.anair.addressbook.io;

import com.anair.addressbook.exception.InvalidResourceException;
import com.anair.addressbook.exception.ResourceNotFoundException;
import com.anair.addressbook.model.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.anair.addressbook.util.DateUtils.parseDateString;

public class CsvFileReader implements Reader {
    private static final String COMMA = ",";

    @Override
    public List<Person> read(final String fileName) throws IOException {
        final List<Person> people = new ArrayList<>();

        var bufferedReader = getBufferedReaderFrom(fileName);
        for (String line; (line = bufferedReader.readLine()) != null; ) {
            var values = line.split(COMMA);
            people.add(buildPersonUsing(values));
        }


        return Collections.unmodifiableList(people);
    }

    private Person buildPersonUsing(final String[] values) {
        if (values.length != 3) {
            throw new InvalidResourceException("Invalid content in resource");
        }
        return Person.builder()
                .fullName(values[0].trim())
                .gender(values[1].trim())
                .dateOfBirth(parseDateString(values[2].trim()))
                .build();
    }

    private BufferedReader getBufferedReaderFrom(final String fileName) {
        var classloader = Thread.currentThread().getContextClassLoader();
        var inputStream = classloader.getResourceAsStream(fileName);
        if (null == inputStream) {
            throw new ResourceNotFoundException(String.format("Resource '%s' cannot be found", fileName));
        }
        var streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(streamReader);
    }
}
