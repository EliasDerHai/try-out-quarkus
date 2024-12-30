## Goal

The goal of this repo is:

- to exercise various technical and conceptual ideas in a fun environment [^1]
- fix everything, that's wrong with the mess of enterprise java [^2]

### Technical Commitment

We [^3] commit ourselves to relying as much as possible on the compiler. This means we:

#### are as tight as possible with *mutability* - this means:

* mutation can only happen within the scope of a function
* `final` everywhere [^4]

#### are as tight as possible with *nullability* - this means:

* using lombok's `@NonNull` despite it's known runtime & buildtime overhead
* using java's primitive types over it's object types (int > Integer, float > Float, ...) [^5]

#### are as tight as possible with *visibility* - this means:

* everything is private except for interface methods and getters being public [^6]
* we structure our code so that we can utilize package privacy to our advantage
* private inner classes can be utilized to group properties without exposing the class to the global namespace

### Architectural Commitment

We commit ourselves to creating a clear and transparent structure. This means we:

#### are as tight as possible with *visibility* - this means:

* essentially the already mentioned [Visibility](#are-as-tight-as-possible-with-visibility---this-means) [^7]

#### let things, that belong together, live together

* adopt some ideas of DDD [^8]

#### manage the jvm ecosystem so it doesn't ruin everything 

* on top of what's already mentioned this means that we need to create clear boundaries for 3rd-party dependencies and
  nullable trash-code on the edges of our app

### Footnotes

* [^1]: somewhat realistic
* [^2]: bit less realistic
* [^3]: a.k.a. I
* [^4]: some implications and consequences are discussed/decided in [this ADR](./decisions/03-domain-objects.md)
* [^5]: only after checking for null at the application boundary ofc - otherwise we
  risk `Integer x = null; int y = (int) x; // y == 0`
* [^6]: protected is part of inheritance and as such strictly forbidden and devs who use it are immediately sent to
  legacy code debugging prison
* [^7]: here we notice that architecture is nothing more than a fancy term for what happens inevitably - in fact I would
  argue that often a few lines of code can have a greater positive architectural impact than some diagram masters
  UML-sanity
* [^8]: at least my interpretation of it