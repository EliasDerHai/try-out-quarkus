package com.elija.domain.order.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderState {
    /** queued up for chef */
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
}
