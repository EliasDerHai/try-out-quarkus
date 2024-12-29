package com.elija.domain.order.values;

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

    @Override
    public String toString() {
        return value;
    }
}
