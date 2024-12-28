package com.elija.domain.person;

import com.elija.domain.person.values.*;
import io.vavr.control.Option;

public interface PersonService {

    Option<PersonId> addPerson(
            FirstName firstName,
            LastName lastName,
            PhoneNumber phoneNumber,
            UserGroup userGroup
    );

    Option<Person> getLeastBusyChef();

    Option<Person> getLeastBusyDeliveryDriver();
}
