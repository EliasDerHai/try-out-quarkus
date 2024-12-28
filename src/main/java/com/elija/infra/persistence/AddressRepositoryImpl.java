package com.elija.infra.persistence;

import com.elija.domain.address.AddressRepository;
import com.elija.domain.address.values.*;
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
    public Option<AddressId> saveAddress(
            Street street,
            House house,
            ZipCode zipCode,
            City city,
            Latitude latitude,
            Longitude longitude
    ) {
        return Option.of(dsl.insertInto(ADDRESS)
                        .set(ADDRESS.CITY, city.toString())
                        .set(ADDRESS.ZIP_CODE, zipCode.toInt())
                        .set(ADDRESS.STREET_NAME, street.toString())
                        .set(ADDRESS.HOUSE_NUMBER, house.toString())
                        .set(ADDRESS.LATITUDE, latitude.toDouble())
                        .set(ADDRESS.LONGITUDE, longitude.toDouble())
                        .returning(ADDRESS.ID)
                        .fetchOne())
                .map(AddressRecord::getId)
                .map(AddressId::fromInt);
    }
}
