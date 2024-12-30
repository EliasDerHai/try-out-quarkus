/**
 * This package contains all the business logic.
 * It should be the core of the whole app. Which means that:
 * <li><b>THIS PACKAGE SHOULD NOT DEPEND ON ANYTHING<b/></li>
 * <li>anything outside one way or another depends on this package</li>
 * <p>
 * Ideally in a real project this should be a module/sub-project in order to guarantee on an architectural level,
 * that no other infrastructure, persistence, 3rd-party dependencies whatsoever get mixed up with the pure, minimalistic
 * core of our code. Exceptions are:
 * <ul>
 *   <li>vavr (only because java.util sucks)</li>
 *   <li>quarkus @DI annotations (@Singleton) (for pragmatic reasons)</li>
 *   <li>jpa @Transactional annotations (for pragmatic reasons)</li>
 * </ul>
 * <p>
 * Everything in here should be unit-tested. There is no need for integration tests.
 */
package com.elija.domain;