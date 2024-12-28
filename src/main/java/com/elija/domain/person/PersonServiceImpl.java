package com.elija.domain.person;

import com.elija.domain.person.values.*;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public Option<PersonId> addPerson(
            FirstName firstName,
            LastName lastName,
            PhoneNumber phoneNumber,
            UserGroup userGroup
    ) {
        return personRepository.savePerson(
                firstName,
                lastName,
                phoneNumber,
                userGroup
        );
    }

    @Override
    public Option<Person> getLeastBusyChef() {
        return personRepository.findLeastBusy(UserGroup.CHEF);
    }

    @Override
    public Option<Person> getLeastBusyDeliveryDriver() {
        return personRepository.findLeastBusy(UserGroup.DELIVERY_DRIVER);
    }


}
