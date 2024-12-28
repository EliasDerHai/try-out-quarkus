 ## Goal
 
The goal of this repo is: 
 - to exercise various technical and conceptual ideas in a fun environment [^1]
 - fix everything, that's wrong with the mess of enterprise java [^2]

[^1]: (somewhat realistic)
[^2]: (bit less realistic)


### Technical Commitment
 
We (a.k.a. I) commit ourselves to:
 1. relying as much as possible on the compiler. This means we: 
    * are as tight as possible with mutability (`final`)
    * are as tight as possible with nullability (use lombok's `@NonNull` despite it's known runtime- & buildtime overhead)
 