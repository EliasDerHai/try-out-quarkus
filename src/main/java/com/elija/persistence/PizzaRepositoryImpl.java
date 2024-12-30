package com.elija.persistence;

import com.elija.domain.pizza.Pizza;
import com.elija.domain.pizza.PizzaRepository;
import com.elija.domain.pizza.values.PizzaDescription;
import com.elija.domain.pizza.values.PizzaId;
import com.elija.domain.pizza.values.PizzaName;
import com.elija.domain.pizza.values.Price;
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
    ) {
        return Option.of(dsl.insertInto(PIZZA)
                        .set(PIZZA.NAME, pizzaName.value())
                        .set(PIZZA.DESCRIPTION, pizzaDescription.toNullableString())
                        .set(PIZZA.PRICE, price.inEuroCent())
                        .returning(PIZZA.ID)
                        .fetchOne())
                .map(PizzaRecord::getId)
                .map(PizzaId::fromInt);
    }

    @Override
    public Set<Pizza> findAll() {
        return HashSet.ofAll(dsl
                        .selectFrom(PIZZA)
                        .fetch())
                .map(PizzaRepositoryImpl::getPizzaFromRecord);
    }

    @Override
    public Option<Pizza> find(PizzaId id) {
        return Option.of(dsl
                        .selectFrom(PIZZA)
                        .where(PIZZA.ID.eq(id.toInt()))
                        .fetchOne())
                .map(PizzaRepositoryImpl::getPizzaFromRecord);
    }

    @Override
    public Option<Pizza> findByName(String name) {
        return Option.of(dsl
                        .selectFrom(PIZZA)
                        .where(PIZZA.NAME.eq(name))
                        .fetchOne())
                .map(PizzaRepositoryImpl::getPizzaFromRecord);
    }

    public static Pizza getPizzaFromRecord(PizzaRecord record) {
        return new Pizza(
                PizzaId.fromInt(record.getId()),
                PizzaName.fromString(record.getName()),
                PizzaDescription.fromNullableString(record.getDescription()),
                Price.fromEuroCents(record.getPrice())
        );
    }
}
