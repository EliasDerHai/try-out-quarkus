package com.elija.domain.address;

import com.elija.domain.address.values.*;
import io.vavr.control.Option;

public interface AddressRepository {
     Option<AddressId> saveAddress(
             Street street,
             House house,
             ZipCode zipCode,
             City city,
             Latitude latitude,
             Longitude longitude
     );

}
