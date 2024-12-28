package com.elija.infra.persistence;

import com.elija.domain.person.Person;
import com.elija.domain.person.PersonRepository;
import com.elija.domain.person.values.*;
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
    public Option<PersonId> savePerson(
            FirstName firstName,
            LastName lastName,
            PhoneNumber phoneNumber,
            UserGroup userGroup
    ) {
        return Option.of(dsl.insertInto(PERSON)
                        .set(PERSON.FIRST_NAME, firstName.toString())
                        .set(PERSON.LAST_NAME, lastName.toString())
                        .set(PERSON.PHONE_NUMBER, phoneNumber.toNullableString())
                        .set(PERSON.USER_GROUP, userGroup.getValue())
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
                FirstName.fromString(r.get(PERSON.FIRST_NAME)),
                LastName.fromString(r.get(PERSON.LAST_NAME)),
                PhoneNumber.fromNullableString(r.get(PERSON.PHONE_NUMBER)),
                UserGroup.fromValue(r.get(PERSON.USER_GROUP))
        );
    }
}
