/**
 * A JDK8 taglet that generates UML diagrams with 
 * [PlantUML](http://plantuml.sourceforge.net/) for inclusion in the javadoc.
 * 
 * [![Release](https://jitpack.io/v/mnlipp/jdrupes-taglets.svg)](https://jitpack.io/#mnlipp/jdrupes-taglets)
 * [![Maven Central](https://img.shields.io/maven-central/v/org.jdrupes/plantuml-taglet.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.jdrupes%22%20AND%20a%3A%22plantuml-taglet%22)
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
 * This is rendered as:
 * 
 * ![Example Diagram](example.png)
 * 
 * It's also possible to use `@startuml` and `@enduml` instead, as usual. `@startuml` is
 * simply a synonym for `@plantUml` and `@enduml` will be ignored entirely. Use this for
 * compatibility with other tools, like e.g. the 
 * [PlantUML Eclipse Plugin](http://plantuml.com/eclipse) or the
 * [PlantUML IDEA Plugin](https://github.com/esteinberg/plantuml4idea).
 *
 * Invoking
 * --------
 * 
 * Specify the taglet on JavaDoc's command line:
 * 
 * ```
 * javadoc -taglet org.jdrupes.taglets.plantUml.Taglet -tagletpath /path/to/org.jdrupes.taglets.plantuml-taglet.jar
 * ```
 * 
 * ### Gradle
 * 
 * Configuring a taglet in the javadoc task is not explicitly shown in the
 * [DSL Reference](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.javadoc.Javadoc.html).
 * The required properties can be found in the 
 * [standard otions](https://docs.gradle.org/current/javadoc/org/gradle/external/javadoc/StandardJavadocDocletOptions.html)
 * 
 * Here's an example:
 * 
 * ```gradle
 * configurations {
 *     javadocTaglets
 * }
 * 
 * dependencies {
 *     javadocTaglets "org.jdrupes:plantuml-taglet:<version>"
 * }
 * 
 * javadoc {
 * 
 *     options.tagletPath = configurations.javadocTaglets.files as List
 *     options.taglets = ["org.jdrupes.taglets.plantUml.Taglet"]
 *     ...
 * }
 * ```
 * 
 * The latest version available on maven central is shown in the badge at the
 * beginning of this page.
 * 
 * Notes
 * -----
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
