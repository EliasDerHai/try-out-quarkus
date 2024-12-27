package com.elija.infra.persistence;

import com.elija.domain.address.AddressDescriptionWithLatLong;
import com.elija.domain.address.AddressId;
import com.elija.domain.address.AddressRepository;
import com.elija.generated.tables.records.AddressRecord;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import static com.elija.generated.Tables.ADDRESS;

@Singleton
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {
    private final DSLContext dsl;

    @Override
    public Option<AddressId> saveAddress(AddressDescriptionWithLatLong addressDescription) {
        return Option.of(dsl.insertInto(ADDRESS)
                        .set(ADDRESS.CITY, addressDescription.city())
                        .set(ADDRESS.ZIP_CODE, addressDescription.zipCode())
                        .set(ADDRESS.STREET_NAME, addressDescription.streetName())
                        .set(ADDRESS.HOUSE_NUMBER, addressDescription.houseNumber())
                        .set(ADDRESS.LATITUDE, addressDescription.latitude().toPrimitive())
                        .set(ADDRESS.LONGITUDE, addressDescription.longitude().toPrimitive())
                        .returning(ADDRESS.ID)
                        .fetchOne())
                .map(AddressRecord::getId)
                .map(AddressId::fromInt);
    }
}
