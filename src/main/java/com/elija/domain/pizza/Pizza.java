package com.elija.domain.pizza;

import com.elija.domain.pizza.values.PizzaDescription;
import com.elija.domain.pizza.values.PizzaId;
import com.elija.domain.pizza.values.PizzaName;
import com.elija.domain.pizza.values.Price;
import lombok.NonNull;

public record Pizza(
        @NonNull PizzaId id,
        @NonNull PizzaName name,
        @NonNull PizzaDescription description,
        @NonNull Price price
) {
}
