package com.elija.domain.address;

import io.vavr.control.Option;

public interface AddressRepository {
     Option<AddressId> saveAddress(AddressDescriptionWithLatLong addressDescription);

}
