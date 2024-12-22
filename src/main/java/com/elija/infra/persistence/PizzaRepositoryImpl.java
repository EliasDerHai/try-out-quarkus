package com.elija.infra.persistence;

import com.elija.domain.pizza.Pizza;
import com.elija.domain.pizza.PizzaRepository;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import static com.example.generated.Tables.PIZZA;

@Singleton
@RequiredArgsConstructor
class PizzaRepositoryImpl implements PizzaRepository {
    private final DSLContext dsl;

    @Override
    public int save(Pizza pizza) {
       var created = dsl.insertInto(PIZZA)
                .set(PIZZA.NAME, pizza.getName())
                .set(PIZZA.DESCRIPTION, pizza.getDescription().getOrNull())
               .returning(PIZZA.ID)
               .fetchOne();

       return created.getId();
    }

    @Override
    public Set<Pizza> findAll() {
        return HashSet.ofAll(dsl.select().from(PIZZA).fetch())
                .map(record -> new Pizza(
                        record.get(PIZZA.NAME),
                        Option.of(record.get(PIZZA.DESCRIPTION))
                ));
    }
}
