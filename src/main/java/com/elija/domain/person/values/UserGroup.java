package com.elija.domain.person.values;

import io.vavr.Tuple;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserGroup {
    CHEF("chef"),
    DELIVERY_DRIVER("delivery-driver"),
    CUSTOMER("customer");

    @Getter
    private final String value;
    private static final Map<String, UserGroup> lookup = Vector
            .of(UserGroup.values())
            .toMap(v -> Tuple.of(v.value, v));

    public static UserGroup fromValue(String userGroup) {
        return lookup.get(userGroup).getOrElseThrow(IllegalArgumentException::new);
    }
}
