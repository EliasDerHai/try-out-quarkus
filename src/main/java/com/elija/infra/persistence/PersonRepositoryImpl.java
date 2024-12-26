package com.elija.infra.persistence;

import com.elija.domain.person.*;
import com.elija.generated.tables.records.PersonRecord;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.util.UUID;

import static com.elija.generated.Tables.PERSON;

@Singleton
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {
    private final DSLContext dsl;

    @Override
    public Option<PersonId> savePerson(PersonDescriptionWithUserGroup personDescription) {
        var created =Option.of( dsl.insertInto(PERSON)
                .set(PERSON.FIRST_NAME, personDescription.firstName())
                .set(PERSON.LAST_NAME, personDescription.lastName())
                .set(PERSON.PHONE_NUMBER, personDescription.phoneNumber().getOrNull())
                .set(PERSON.USER_GROUP, personDescription.userGroup().getValue())
                .returning(PERSON.ID)
                .fetchOne());

        return created
                .map(PersonRecord::getId)
                .map(UUID::fromString)
                .map(PersonId::fromPrimitive);
    }

    @Override
    public Option<Person> findPersonByFullName(String lastName, String firstName) {
        var persisted = Option.of( dsl.select()
                .from(PERSON)
                .where(PERSON.FIRST_NAME.eq(firstName))
                .and(PERSON.LAST_NAME.eq(lastName))
                .fetchOne());

        return persisted
                .map(r -> new Person(
                        PersonId.fromPrimitive(UUID.fromString(r.get(PERSON.ID))),
                        r.get(PERSON.FIRST_NAME),
                        r.get(PERSON.LAST_NAME),
                        Option.of(r.get(PERSON.PHONE_NUMBER)),
                        UserGroup.fromValue(r.get(PERSON.USER_GROUP))
                ));
    }
}
