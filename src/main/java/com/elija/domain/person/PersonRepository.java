package com.elija.domain.person;

import com.elija.domain.person.values.*;
import io.vavr.collection.Set;
import io.vavr.control.Option;

public interface PersonRepository {

    Option<PersonId> save(
            FirstName firstName,
            LastName lastName,
            PhoneNumber phoneNumber,
            UserGroup userGroup
    );
    Option<Person> findByFullName(String lastName, String firstName);
    Option<Person> findLeastBusy(UserGroup userGroup);
    Option<Person> find(PersonId personId);
    Set<Person> findAll();

}
