/**
 * This package contains all the persistence logic.
 * No persistence specific logic should leak into the wider codebase.
 * <p>
 * Ideally in a real project this should be a module/sub-project in order to guarantee on an architectural level,
 * that the dependencies (in our case jOOQ) can't leak into the wider codebase.
 * <p>
 * Unit-tests are not useful in this package because they wouldn't bear any real meaning. Instead, tight integration
 * tests with database & test-containers should be used to assert that every database interaction works as expected.
 */
package com.elija.persistence;