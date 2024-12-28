 ## Goal
 
The goal of this repo is: 
 - to exercise various technical and conceptual ideas in a fun environment [^1]
 - fix everything, that's wrong with the mess of enterprise java [^2]

### Technical Commitment
 
We [^3] commit ourselves to:
 1. relying as much as possible on the compiler. This means we: 
    * are as tight as possible with mutability - this means: 
      * mutation can only happen within the scope of a function
      * `final` everywhere [^4]
    * are as tight as possible with nullability - this means: 
      * using lombok's `@NonNull` despite it's known runtime & buildtime overhead
      * using java's primitive types over it's object types (int > Integer, float > Float, ...) [^5]
 2. // TODO add more...

### Architectural Commitment
// TODO add more...

### Footnotes
* [^1]: somewhat realistic
* [^2]: bit less realistic
* [^3]: a.k.a. I
* [^4]: some implications and consequences are discussed/decided in [this ADR](./decisions/03-domain-objects.md)
* [^5]: only after checking for null at the application boundary ofc - otherwise we risk `Integer x = null; int y = (int) x; // y == 0`