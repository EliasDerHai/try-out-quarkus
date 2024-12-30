package com.elija.persistence;

import com.elija.domain.address.Address;
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
class AddressRepositoryImpl implements AddressRepository {
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
        var insertion = new AddressRecord();
        insertion.setCity(city.toString());
        insertion.setZipCode(zipCode.toInt());
        insertion.setStreetName(street.toString());
        insertion.setHouseNumber(house.toString());
        insertion.setLatitude(latitude.toDouble());
        insertion.setLongitude(longitude.toDouble());

        return Option.of(dsl.insertInto(ADDRESS)
                        .set(insertion)
                        .returning(ADDRESS.ID)
                        .fetchOne())
                .map(AddressRecord::getId)
                .map(AddressId::fromInt);
    }

    public static Address getAddressFromRecord(AddressRecord record) {
        return new Address(
                AddressId.fromInt(record.getId()),
                Street.fromString(record.getStreetName()),
                House.fromString(record.getHouseNumber()),
                ZipCode.fromInteger(record.getZipCode()),
                City.fromString(record.getCity()),
                Latitude.fromDouble(record.getLatitude()),
                Longitude.fromDouble(record.getLongitude())
        );
    }
}
