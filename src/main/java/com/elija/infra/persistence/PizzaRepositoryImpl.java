package com.elija.infra.persistence;

import com.elija.domain.pizza.*;
import com.elija.generated.tables.records.PizzaRecord;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import static com.elija.generated.Tables.PIZZA;

@Singleton
@RequiredArgsConstructor
class PizzaRepositoryImpl implements PizzaRepository {
    private final DSLContext dsl;

    @Override
    public Option<PizzaId> save(
            PizzaName pizzaName,
            PizzaDescription pizzaDescription,
            Price price
    ){
        return Option.of(dsl.insertInto(PIZZA)
                        .set(PIZZA.NAME, pizzaName.value())
                        .set(PIZZA.DESCRIPTION, pizzaDescription.toNullableString())
                        .set(PIZZA.PRICE, price.inEuroCent())
                        .returning(PIZZA.ID)
                        .fetchOne()
                )
                .map(PizzaRecord::getId)
                .map(PizzaId::fromInt);
    }

    @Override
    public Set<Pizza> findAll() {
        return HashSet.ofAll(dsl.select().from(PIZZA).fetch())
                .map(PizzaRepositoryImpl::recordToPizza);
    }

    @Override
    public Option<Pizza> find(int id) {
        return Option
                .of(dsl.select().from(PIZZA).where(PIZZA.ID.eq(id)).fetchOne())
                .map(PizzaRepositoryImpl::recordToPizza);
    }

    @Override
    public Option<Pizza> findByName(String name) {
        return Option
                .of(dsl.select().from(PIZZA).where(PIZZA.NAME.eq(name)).fetchOne())
                .map(PizzaRepositoryImpl::recordToPizza);
    }

    private static Pizza recordToPizza(org.jooq.Record record) {
        return new Pizza(
                PizzaId.fromInt(record.get(PIZZA.ID)),
                PizzaName.fromString(record.get(PIZZA.NAME)),
                PizzaDescription.fromNullableString(record.get(PIZZA.DESCRIPTION)),
                Price.fromEuroCents(record.get(PIZZA.PRICE))
        );
    }
}
