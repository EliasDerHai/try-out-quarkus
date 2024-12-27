package com.elija.infra.persistence;

import com.elija.domain.person.PersonDescriptionWithUserGroup;
import com.elija.domain.person.UserGroup;
import com.elija.domain.pizza.CreatePizzaCommand;
import com.elija.domain.shared.Price;
import io.quarkus.runtime.StartupEvent;
import io.vavr.control.Option;
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
        String margherita = "Margherita";
        String funghi = "Funghi";
        String hawaii = "Hawaii";
        String parma = "Parma";
        String quattroStagioni = "Quattro-Stagioni";
        var inserts = 0;
        if (pizzaRepository.findByName(margherita).isEmpty()) {
            pizzaRepository.save(new CreatePizzaCommand(
                    margherita,
                    Some("Great for picky eaters"),
                    Price.fromEuroCents(750)
            ));
            inserts++;
        }
        if (pizzaRepository.findByName(funghi).isEmpty()) {
            pizzaRepository.save(new CreatePizzaCommand(
                    funghi,
                    Some("At least something other than cheese..."),
                    Price.fromEuroCents(820)
            ));
            inserts++;
        }
        if (pizzaRepository.findByName(hawaii).isEmpty()) {
            pizzaRepository.save(new CreatePizzaCommand(
                    hawaii,
                    Some("Does anyone even like this??"),
                    Price.fromEuroCents(850)
            ));
            inserts++;
        }
        if (pizzaRepository.findByName(parma).isEmpty()) {
            pizzaRepository.save(new CreatePizzaCommand(
                    parma,
                    None(),
                    Price.fromEuroCents(960)
            ));
            inserts++;
        }
        if (pizzaRepository.findByName(quattroStagioni).isEmpty()) {
            pizzaRepository.save(new CreatePizzaCommand(
                    quattroStagioni,
                    None(),
                    Price.fromEuroCents(990)
            ));
            inserts++;
        }
        return inserts;
    }

    private int insertSamplePersons() {
        var inserts = 0;
        if (personRepository.findPersonByFullName("Parmigiano", "Luigi").isEmpty()) {
            personRepository.savePerson(new PersonDescriptionWithUserGroup(
                    "Luigi",
                    "Parmigiano",
                    Option.of("1234-567890"),
                    UserGroup.CHEF
            ));
            inserts++;
        }
        if (personRepository.findPersonByFullName("Parmigiano", "Mario").isEmpty()) {
            personRepository.savePerson(new PersonDescriptionWithUserGroup(
                    "Mario",
                    "Parmigiano",
                    Option.of("0987-654321"),
                    UserGroup.DELIVERY_DRIVER
            ));
            inserts++;
        }
        return inserts;
    }
}