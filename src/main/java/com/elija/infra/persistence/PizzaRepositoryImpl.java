package com.elija.infra.persistence;

import com.elija.domain.pizza.CreatePizzaCommand;
import com.elija.domain.pizza.Pizza;
import com.elija.domain.pizza.PizzaId;
import com.elija.domain.pizza.PizzaRepository;
import com.elija.domain.shared.Price;
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
    public Option<PizzaId> save(CreatePizzaCommand createPizzaCommand) {
        var created = Option.of(
                dsl.insertInto(PIZZA)
                        .set(PIZZA.NAME, createPizzaCommand.name())
                        .set(PIZZA.DESCRIPTION, createPizzaCommand.description().getOrNull())
                        .set(PIZZA.PRICE, createPizzaCommand.price().inEuroCent())
                        .returning(PIZZA.ID)
                        .fetchOne()
        );

        return created
                .map(PizzaRecord::getId)
                .map(PizzaId::fromPrimitive);
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

    private static Pizza recordToPizza(org.jooq.Record record) {
        return new Pizza(
                PizzaId.fromPrimitive(record.get(PIZZA.ID)),
                record.get(PIZZA.NAME),
                Option.of(record.get(PIZZA.DESCRIPTION)),
                Price.fromEuroCents(record.get(PIZZA.PRICE))
        );
    }
}
