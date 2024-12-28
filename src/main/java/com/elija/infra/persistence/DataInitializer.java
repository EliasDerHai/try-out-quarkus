package com.elija.infra.persistence;

import com.elija.domain.person.values.FirstName;
import com.elija.domain.person.values.LastName;
import com.elija.domain.person.values.PhoneNumber;
import com.elija.domain.person.values.UserGroup;
import com.elija.domain.pizza.values.PizzaDescription;
import com.elija.domain.pizza.values.PizzaName;
import com.elija.domain.pizza.values.Price;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.vavr.API.None;
import static io.vavr.API.Some;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class DataInitializer {
    private final PizzaRepositoryImpl pizzaRepository;
    private final PersonRepositoryImpl personRepository;

    @Transactional
    void onStart(@Observes StartupEvent startupEvent) {
        var inserts =
                insertSamplePizzas() + insertSamplePersons();
        if (inserts == 0) {
            log.info("Skipped inserts, because all sample data already present");
        } else {
            log.info("Sample data was added - {} elements where added", inserts);
        }
    }

    private int insertSamplePizzas() {
        PizzaName margherita = PizzaName.fromString("Margherita");
        PizzaName funghi = PizzaName.fromString("Funghi");
        PizzaName hawaii = PizzaName.fromString("Hawaii");
        PizzaName parma = PizzaName.fromString("Parma");
        PizzaName quattroStagioni = PizzaName.fromString("Quattro-Stagioni");

        var inserts = 0;
        if (pizzaRepository.findByName(margherita.value()).isEmpty()) {
            pizzaRepository.save(
                    margherita,
                    PizzaDescription.fromOption(Some("Great for picky eaters")),
                    Price.fromEuroCents(750)
            );
            inserts++;
        }
        if (pizzaRepository.findByName(funghi.value()).isEmpty()) {
            pizzaRepository.save(
                    funghi,
                    PizzaDescription.fromOption(Some("At least something other than cheese...")),
                    Price.fromEuroCents(820)
            );
            inserts++;
        }
        if (pizzaRepository.findByName(hawaii.value()).isEmpty()) {
            pizzaRepository.save(
                    hawaii,
                    PizzaDescription.fromOption(Some("Does anyone even like this??")),
                    Price.fromEuroCents(850)
            );
            inserts++;
        }
        if (pizzaRepository.findByName(parma.value()).isEmpty()) {
            pizzaRepository.save(
                    parma,
                    PizzaDescription.fromOption(None()),
                    Price.fromEuroCents(960)
            );
            inserts++;
        }
        if (pizzaRepository.findByName(quattroStagioni.value()).isEmpty()) {
            pizzaRepository.save(
                    quattroStagioni,
                    PizzaDescription.fromOption(None()),
                    Price.fromEuroCents(990)
            );
            inserts++;
        }
        return inserts;
    }

    private int insertSamplePersons() {
        var inserts = 0;
        if (personRepository.findPersonByFullName("Parmigiano", "Luigi").isEmpty()) {
            personRepository.savePerson(
                    FirstName.fromString("Luigi"),
                    LastName.fromString("Parmigiano"),
                    PhoneNumber.fromNullableString("1234-567890"),
                    UserGroup.CHEF
            );
            inserts++;
        }
        if (personRepository.findPersonByFullName("Parmigiano", "Mario").isEmpty()) {
            personRepository.savePerson(
                    FirstName.fromString("Mario"),
                    LastName.fromString("Parmigiano"),
                    PhoneNumber.fromNullableString("0987-654321"),
                    UserGroup.DELIVERY_DRIVER
            );
            inserts++;
        }
        return inserts;
    }
}