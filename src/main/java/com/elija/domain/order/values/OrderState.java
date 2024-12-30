package com.elija.domain.order.values;

import io.vavr.Tuple;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderState {
    /** awaits payment verification - otherwise deleted after X minutes */
    AWAITING_PAYMENT("awaiting_payment"),
    /** payment received; queued up for chef */
    PLACED("placed"),
    /** chef is cookin' */
    COOKING("cooking"),
    /** delivery boy is on the road */
    DELIVERING("delivering"),
    /** delivery found it's orderer */
    DELIVERED("delivered"),
    /** oh no! 😭 */
    LOST("lost");

    private final String value;



    private static final Map<String, OrderState> lookup = Vector
            .of(OrderState.values())
            .toMap(v -> Tuple.of(v.value, v));

    public static OrderState fromValue(String orderState) {
        return lookup.get(orderState).getOrElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return value;
    }
}
