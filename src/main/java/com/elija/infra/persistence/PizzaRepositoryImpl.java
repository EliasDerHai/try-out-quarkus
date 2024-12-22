package com.elija.infra.persistence;

import com.elija.domain.pizza.Pizza;
import com.elija.domain.pizza.PizzaRepository;
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
    public Option<Integer> save(Pizza pizza) {
        var created = Option.of(dsl.insertInto(PIZZA)
                .set(PIZZA.NAME, pizza.getName())
                .set(PIZZA.DESCRIPTION, pizza.getDescription().getOrNull())
                .returning(PIZZA.ID)
                .fetchOne());

        return created.map(PizzaRecord::getId);
    }

    @Override
    public Set<Pizza> findAll() {
        return HashSet.ofAll(dsl.select().from(PIZZA).fetch())
                .map(record -> new Pizza(
                        record.get(PIZZA.NAME),
                        Option.of(record.get(PIZZA.DESCRIPTION))
                ));
    }

    @Override
    public Option<Pizza> find(int id) {
        return Option
                .of(dsl.select().from(PIZZA).where(PIZZA.ID.eq(id)).fetchOne())
                .map(record -> new Pizza(
                        record.get(PIZZA.NAME),
                        Option.of(record.get(PIZZA.DESCRIPTION))
                ));
    }
}
