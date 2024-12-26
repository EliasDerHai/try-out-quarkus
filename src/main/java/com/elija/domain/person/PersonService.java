package com.elija.domain.person;

import io.vavr.control.Option;

public interface PersonService {

    Option<PersonId> addPerson(PersonDescription personDescription, UserGroup userGroup);

    Option<Person> getLeastBusyChef();
    Option<Person> getLeastBusyDeliveryDriver();
}
