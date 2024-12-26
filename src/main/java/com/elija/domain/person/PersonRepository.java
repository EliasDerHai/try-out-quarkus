package com.elija.domain.person;

import io.vavr.control.Option;

public interface PersonRepository {

    Option<PersonId> savePerson(PersonDescriptionWithUserGroup personDescription);

    Option<Person> findPersonByFullName(String lastName, String firstName);
}
