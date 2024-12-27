package com.elija.infra.persistence;

import com.elija.domain.person.*;
import com.elija.generated.tables.records.PersonRecord;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import static com.elija.generated.Tables.PERSON;

@Singleton
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {
    private final DSLContext dsl;

    @Override
    public Option<PersonId> savePerson(PersonDescriptionWithUserGroup personDescription) {
        return Option.of(dsl.insertInto(PERSON)
                .set(PERSON.FIRST_NAME, personDescription.firstName())
                .set(PERSON.LAST_NAME, personDescription.lastName())
                .set(PERSON.PHONE_NUMBER, personDescription.phoneNumber().getOrNull())
                .set(PERSON.USER_GROUP, personDescription.userGroup().getValue())
                .returning(PERSON.ID)
                .fetchOne())
                .map(PersonRecord::getId)
                .map(PersonId::fromUUID);
    }

    @Override
    public Option<Person> findPersonByFullName(String lastName, String firstName) {
        return Option.of(dsl.select()
                .from(PERSON)
                .where(PERSON.FIRST_NAME.eq(firstName))
                .and(PERSON.LAST_NAME.eq(lastName))
                .fetchOne())
                .map(PersonRepositoryImpl::getPersonFromRecord);
    }

    @Override
    public Option<Person> findLeastBusy(UserGroup userGroup) {
        return Option.of(dsl.select()
                .from(PERSON)
                .where(PERSON.USER_GROUP.eq(userGroup.getValue()))
                .fetchOne())
                .map(PersonRepositoryImpl::getPersonFromRecord);
    }

    private static Person getPersonFromRecord(org.jooq.Record r) {
        return new Person(
                PersonId.fromUUID(r.get(PERSON.ID)),
                r.get(PERSON.FIRST_NAME),
                r.get(PERSON.LAST_NAME),
                Option.of(r.get(PERSON.PHONE_NUMBER)),
                UserGroup.fromValue(r.get(PERSON.USER_GROUP))
        );
    }
}
