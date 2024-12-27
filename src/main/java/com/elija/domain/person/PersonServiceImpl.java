package com.elija.domain.person;

import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public Option<PersonId> addPerson(PersonDescription personDescription, UserGroup userGroup) {
        return personRepository.savePerson(PersonDescriptionWithUserGroup.fromPersonDescription(personDescription, userGroup));
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
