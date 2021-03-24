A taglet that generates UML diagrams with 
[PlantUML](http://plantuml.sourceforge.net/) for inclusion in the javadoc.
 
[![Maven Central](https://img.shields.io/maven-central/v/org.jdrupes.taglets/plantuml-taglet.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.jdrupes.taglets%22%20AND%20a%3A%22plantuml-taglet%22)
  
Simply use the `@plantUml` tag to generate the graphics file from the 
PlantUML source[^1]:

```
/**
 * Description.
 *
 * <img src="example.svg">
 *
 * This package/class ...
 *
 * @plantUml example.svg
 * Alice -> Bob: Authentication Request
 * Alice <-- Bob: Authentication Response
 * 
{@literal *}/
```

This is rendered as:
 
---
 
Description.
 
![Example Diagram](org/jdrupes/taglets/plantUml/example.svg)
 
This package/class ...
 
---

[^1]: The PlantUML source for the example above is actually 
    in the package description instead of the overview source file.
    Java-11 to Java-15 (at least) drop block tags from an overview file.
    (Worked in Java-8.)
 
The usage of "`<`" and "`>`" in PlantUML make javadoc complain about 
illegal HTML tokens. Of course, you can use "`&lt;`" and "`&gt;`" but 
this reduces the readability of the UML descriptions. It is therefore
recommended to disable HTML checks with e.g. `-Xdoclint:-html` when using 
PlantUML.
 
It's also possible to use `@startuml` and `@enduml` instead of `@plantuml`, 
which is the common usage pattern. `@startuml` is simply a synonym for 
`@plantUml` and `@enduml` will be ignored entirely. Use this for
compatibility with other tools, like e.g. the 
[PlantUML Eclipse Plugin](http://plantuml.com/eclipse) or the
[PlantUML IDEA Plugin](https://github.com/esteinberg/plantuml4idea).
 

Invoking
--------

### Gradle
 
Configuring a taglet in the javadoc task is not explicitly shown in the
[DSL Reference](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.javadoc.Javadoc.html).
The required properties can be found in the 
[standard otions](https://docs.gradle.org/current/javadoc/org/gradle/external/javadoc/StandardJavadocDocletOptions.html)
 
Here's an example:
 
```gradle
configurations {
    javadocTaglets
}
 
dependencies {
    javadocTaglets "org.jdrupes.taglets:plantuml-taglet:<version>"
}
 
javadoc {
 
    options.tagletPath = configurations.javadocTaglets.files as List
    options.taglets = ["org.jdrupes.taglets.plantUml.Taglet"]
    ...
}
```
 
The latest version available on maven central is shown in the badge at the
beginning of this page.
 
### Command line
 
Specify the taglet on JavaDoc's command line:
 
```
javadoc -taglet org.jdrupes.taglets.plantUml.Taglet -tagletpath /path/to/plantuml-taglet.jar:/path/to/plantuml.jar
```
 
This could be simplified by providing a "fat jar", but I doubt that anybody would
really use it.
 
 
### Maven
 
Still using this? Well, you're on your own...
 
Notes
-----

This taglet is released under the
[GPL 3.0](http://www.gnu.org/licenses/gpl-3.0-standalone.html).

See [PlantUM](http://plantuml.sourceforge.net).
