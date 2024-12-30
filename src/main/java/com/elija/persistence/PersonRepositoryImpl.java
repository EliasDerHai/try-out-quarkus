package com.elija.persistence;

import com.elija.domain.person.Person;
import com.elija.domain.person.PersonRepository;
import com.elija.domain.person.values.*;
import com.elija.generated.tables.records.PersonRecord;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import static com.elija.generated.Tables.CUSTOMER_ORDER;
import static com.elija.generated.Tables.PERSON;

@Singleton
@RequiredArgsConstructor
class PersonRepositoryImpl implements PersonRepository {
    private final DSLContext dsl;

    @Override
    public Option<PersonId> save(
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
    public Option<Person> findByFullName(String lastName, String firstName) {
        return Option.of(dsl
                        .selectFrom(PERSON)
                        .where(PERSON.FIRST_NAME.eq(firstName))
                        .and(PERSON.LAST_NAME.eq(lastName))
                        .fetchOne())
                .map(PersonRepositoryImpl::getPersonFromRecord);
    }

    @Override
    public Option<Person> findLeastBusy(UserGroup userGroup) {
        var userGroupField = switch (userGroup) {
            case CHEF -> CUSTOMER_ORDER.CHEF;
            case DELIVERY_DRIVER -> CUSTOMER_ORDER.DELIVERY_DRIVER;
            default -> throw new IllegalArgumentException("Expected either chef or delivery-driver");
        };

        var leastBusyPerson = dsl
                .select(
                        PERSON.ID,
                        PERSON.FIRST_NAME,
                        PERSON.LAST_NAME,
                        PERSON.PHONE_NUMBER,
                        PERSON.USER_GROUP,
                        DSL.count(CUSTOMER_ORDER.ID).as("total_orders")
                )
                .from(PERSON)
                .leftJoin(CUSTOMER_ORDER).on(userGroupField.eq(PERSON.ID))
                .where(PERSON.USER_GROUP.eq(userGroup.getValue()))
                .groupBy(
                        PERSON.ID,
                        PERSON.FIRST_NAME,
                        PERSON.LAST_NAME,
                        PERSON.PHONE_NUMBER,
                        PERSON.USER_GROUP
                )
                .orderBy(DSL.count(CUSTOMER_ORDER.ID).asc())
                .limit(1)  // limit is needed because fetchOne throws TooManyRowsException
                .fetchOne();            // if the query returns more than one record

        return Option.of(leastBusyPerson)
                .map(PersonRepositoryImpl::getPersonFromRecord);
    }

    @Override
    public Option<Person> find(PersonId personId) {
        return Option.of(dsl
                        .selectFrom(PERSON)
                        .where(PERSON.ID.eq(personId.toUUID()))
                        .fetchOne())
                .map(PersonRepositoryImpl::getPersonFromRecord);
    }

    @Override
    public Set<Person> findAll() {
        return HashSet.ofAll(dsl
                        .selectFrom(PERSON)
                        .fetch())
                .map(PersonRepositoryImpl::getPersonFromRecord);
    }

    public static Person getPersonFromRecord(PersonRecord record) {
        return new Person(
                PersonId.fromUUID(record.getId()),
                FirstName.fromString(record.getFirstName()),
                LastName.fromString(record.getLastName()),
                PhoneNumber.fromNullableString(record.getPhoneNumber()),
                UserGroup.fromValue(record.getUserGroup())
        );
    }

    /**
     * less typesafe than {@link PersonRepositoryImpl#getPersonFromRecord(PersonRecord)}
     * <p>
     * use {@link PersonRepositoryImpl#getPersonFromRecord(PersonRecord)} if possible
     */
    private static Person getPersonFromRecord(org.jooq.Record record) {
        return new Person(
                PersonId.fromUUID(record.get(PERSON.ID)),
                FirstName.fromString(record.get(PERSON.FIRST_NAME)),
                LastName.fromString(record.get(PERSON.LAST_NAME)),
                PhoneNumber.fromNullableString(record.get(PERSON.PHONE_NUMBER)),
                UserGroup.fromValue(record.get(PERSON.USER_GROUP))
        );
    }
}
