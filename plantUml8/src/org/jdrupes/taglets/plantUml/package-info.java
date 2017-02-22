/**
 * A JDK8 taglet that generates UML diagrams with 
 * [PlantUML](http://plantuml.sourceforge.net/) for inclusion in the javadoc.
 * 
 * Just use the `@plantUml` tag:
 *
 * ```
 * /**
 *  * Description.
 *  *
 *  * <img src="example.png">
 *  *
 *  * @plantUml example.png
 *  * Alice -> Bob: Authentication Request
 *  * Bob --> Alice: Authentication Response
 * {@literal *}/
 * ```
 *
 * Renders as:
 * 
 * ![Example Diagram](example.png)
 * 
 * It's also possible to use `@startuml` and `@enduml` instead, as usual. `@startuml` is
 * simply a synonym for `@plantUml` and `@enduml` will be ignored entirely. Use this for
 * compatibility with other tools, like e.g. the 
 * [PlantUML Eclipse Plugin](http://plantuml.com/eclipse) or the
 * [PlantUML IDEA Plugin](https://github.com/esteinberg/plantuml4idea).
 *
 * This taglet is based on the PlantUML integration of
 * [Abnaxos'](https://github.com/Abnaxos) 
 * great [pegdown-doclet](https://github.com/Abnaxos/pegdown-doclet).
 * 
 * Note that this taglet makes use of the internal javadoc API as provided with
 * JDK8 to access the RootDoc from the taglet. At the time of this writing, this 
 * is a reasonable choice because JDK7 has reached end of life and the taglet API
 * will change dramatically in JDK9. 
 *  
 * @plantUml example.png
 * Alice -> Bob: Authentication Request
 * Bob --> Alice: Authentication Response
 */
package org.jdrupes.taglets.plantUml;
