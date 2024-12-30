package com.elija.controller;

import com.elija.domain.person.Person;
import com.elija.domain.person.PersonRepository;
import com.elija.domain.person.values.*;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@Path(PersonController.PERSON_URI)
@RequiredArgsConstructor
public class PersonController {
    public static final String PERSON_URI = "person";

    private final PersonRepository personRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<PersonController.GetPersonDto> getAll() {
        return personRepository.findAll()
                .map(PersonController.GetPersonDto::fromPerson);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") String id) {
        return personRepository.find(PersonId.fromString(id))
                .map(PersonController.GetPersonDto::fromPerson)
                .map(p -> Response.ok(p).build())
                .getOrElse(Response.status(404).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(PersonController.CreatePersonDto personDto) {
        return personRepository.save(
                        personDto.getFirstName(),
                        personDto.getLastName(),
                        personDto.getPhoneNumber(),
                        personDto.getUserGroup()
                )
                .map(id -> URI.create("%s/%s".formatted(PERSON_URI, id.toString())))
                .map(uri -> Response.created(uri).build())
                .getOrElse(Response.serverError().build());
    }



    record CreatePersonDto(
            @NonNull String firstName,
            @NonNull String lastName,
            @NonNull Option<String> phoneNumber,
            @NonNull String userGroup
            ) {

        FirstName getFirstName(){
            return FirstName.fromString(firstName);
        }
        LastName getLastName(){
            return LastName.fromString(lastName);
        }
        PhoneNumber getPhoneNumber(){
            return PhoneNumber.fromOption(phoneNumber);
        }
        UserGroup getUserGroup(){
            return UserGroup.fromValue(userGroup);
        }

    }

    record GetPersonDto(
            @NonNull String id,
            @NonNull String firstName,
            @NonNull String lastName,
            @NonNull Option<String> phoneNumber,
            @NonNull String userGroup
    ) {
        public static GetPersonDto fromPerson(Person orderer) {
            return new GetPersonDto(
                    orderer.id().toString(),
                    orderer.firstName().toString(),
                    orderer.lastName().toString(),
                    orderer.phoneNumber().toOption(),
                    orderer.userGroup().toString()
            );
        }
    }
}
