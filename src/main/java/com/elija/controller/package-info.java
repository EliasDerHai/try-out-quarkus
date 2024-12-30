/**
 * This package contains all the request handling logic.
 * No request handling specific logic should leak into the wider codebase.
 * <p>
 * Ideally in a real project this should be a module/sub-project in order to guarantee on an architectural level,
 * that the dependencies (in our case quarkus & jackson) can't leak into the wider codebase.
 * <p>
 * Unit-tests are not useful in this package, because they wouldn't bear any real meaning. Instead, tight integration
 * tests with quarkus should be used to assert that every controller interaction works as expected.
 * Everything but Controller and Dto-mapping logic can and should be mocked (eg. mockito).
 */
package com.elija.controller;