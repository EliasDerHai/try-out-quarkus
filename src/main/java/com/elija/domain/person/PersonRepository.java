package com.elija.domain.person;

import com.elija.domain.person.values.*;
import io.vavr.control.Option;

public interface PersonRepository {

    Option<PersonId> savePerson(
            FirstName firstName,
            LastName lastName,
            PhoneNumber phoneNumber,
            UserGroup userGroup
    );

    Option<Person> findPersonByFullName(String lastName, String firstName);

    Option<Person> findLeastBusy(UserGroup userGroup);
}
