package com.elija.domain.pizza;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import static com.example.generated.Tables.PIZZA;

@Singleton
@RequiredArgsConstructor
public class PizzaRepository implements PizzaPersistencePort {
    private final DSLContext dsl;

    @Override
    public void save(com.elija.domain.pizza.Pizza pizza) {
        dsl.insertInto(PIZZA)
                .set(PIZZA.NAME, pizza.getName())
                .set(PIZZA.DESCRIPTION, pizza.getDescription().getOrNull())
                .execute();
    }
}
