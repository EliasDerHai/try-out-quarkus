package com.elija.domain.person;

import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

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
        return Option.of(new Person(
                PersonId.fromPrimitive(UUID.randomUUID()),
                "Luigi",
                "Pomodoro",
                Option.of("1058-28582928"),
                UserGroup.CHEF
        ));
    }

    @Override
    public Option<Person> getLeastBusyDeliveryDriver() {
        return Option.of(new Person(
                PersonId.fromPrimitive(UUID.randomUUID()),
                "Mario",
                "Pronto",
                Option.of("5832-3502095033"),
                UserGroup.DELIVERY_DRIVER
        ));
    }


}
