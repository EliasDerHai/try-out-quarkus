package com.elija.domain.order;

import com.elija.domain.address.values.City;
import com.elija.domain.address.values.House;
import com.elija.domain.address.values.Street;
import com.elija.domain.address.values.ZipCode;
import com.elija.domain.person.values.FirstName;
import com.elija.domain.person.values.LastName;
import com.elija.domain.person.values.PhoneNumber;
import com.elija.domain.pizza.values.PizzaId;
import io.vavr.Tuple3;
import io.vavr.Tuple4;
import io.vavr.collection.Map;
import lombok.NonNull;

public record PlaceOrderCommand(
        @NonNull Map<PizzaId, Integer> pizzaIdWithQuantity,
        @NonNull Tuple4<Street, House, ZipCode, City> destination,
        @NonNull Tuple3<FirstName, LastName, PhoneNumber> orderer
) {
}
