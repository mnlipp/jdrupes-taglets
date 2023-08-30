A taglet that generates UML diagrams with
[PlantUML](http://plantuml.sourceforge.net/) for inclusion in the javadoc.

<a href="https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.jdrupes.taglets%22%20AND%20a%3A%22plantuml-taglet%22"><img src="https://img.shields.io/maven-central/v/org.jdrupes.taglets/plantuml-taglet.svg"></a>

**Please note that starting with version 2.0.0 the taglet works with
the API introduced in Java 9. It has been tested with Java-11.**

## Changes of version 3.0.0

- One can use <code>{@</code><code>plantuml}</code> to avoid HTML escaping of `<` and `>`. This is similar to the new [JEP 413: Code Snippets in Java API Documentation](https://openjdk.org/jeps/413).
- `@startuml` now unescapes HTML escapings `<` and `>`.
- `@plantuml` is now completely lower case. In case you want to have mixed case, additionally use `PlantUmlMixedCase`.
- Starting with version 3.0.0 the taglet requires Java-17.

## Quick start

Simply use the `{@plantuml ` tag to generate the graphics file from the
PlantUML source[^1]:

```java
/**
 * Description.
 *
 * <img src="example.svg">
 *
 * This package/class ...
 *
 * {@plantUml example.svg
 * Alice -> Bob: Authentication Request
 * Alice <-- Bob: Authentication Response
 * }
 */
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
    (Used to worked in Java-8.) See the report in the
    [Java Bug Database](https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8264274)

## On HTML escapings

The usage of "`<`" and "`>`" in PlantUML make javadoc complain about
illegal HTML tokens. Of course, you could use "`&amp;lt;`" and "`&amp;gt;`" but
this reduces the readability of the UML descriptions and leads to errors in
PlantUML.

```java
/**
 * Description.
 *
 * <img src="example.svg">
 *
 * This package/class ...
 *
 * @startuml example.svg
 * Alice -> Bob: Authentication Request
 * Alice &lt;-- Bob: Authentication Response
 * @enduml
 *
 */
```

The old preferred approach (versions 2.x) was to put the PlantUML source in
comments as shown below.

```java
/**
 * @plantUml package.svg
 * &lt;!--
 * Bob <-- Alice: Authentication Request
 * Alice <-- Bob: Authentication Response
 * --&gt;
 */
```

The taglet removes the comment delimiters and uses the resulting content
as PlantUML source. Of course, you also have to avoid all "`-->`" arrows in
your PlantUML description as this would terminate the HTML comment
prematurely. Luckily, this isn't too hard because you can always exchange
the left and right side of such a relation.

## `@startuml` and `@enduml`

It's also possible to use `@startuml` and `@enduml` instead of `@plantuml`.
`@startuml` enhances `@plantUml` by unescaping HTML `&lt;` and `&gt;`.

You may also use `@startuml` and `@enduml` for compatibility with other tools,
such as the [PlantUML Eclipse Plugin](http://plantuml.com/eclipse) or the
[PlantUML IDEA Plugin](https://github.com/esteinberg/plantuml4idea).

## Invoking

### Gradle

Configuring a taglet in the javadoc task is not explicitly shown in the
[DSL Reference](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.javadoc.Javadoc.html).
The required properties can be found in the
[standard options](https://docs.gradle.org/current/javadoc/org/gradle/external/javadoc/StandardJavadocDocletOptions.html)

Here's an example:

```groovy
configurations {
    javadocTaglets
}

dependencies {
    javadocTaglets "org.jdrupes.taglets:plantuml-taglet:<version>"
}

javadoc {
    options.tagletPath = configurations.javadocTaglets.files as List
    options.taglets = ["org.jdrupes.taglets.plantUml.PlantUml", "org.jdrupes.taglets.plantUml.StartUml", "org.jdrupes.taglets.plantUml.EndUml"]
    ...
}
```

The latest version available on maven central is shown in the badge at the
beginning of this page.

### Command line

Specify the taglet on JavaDoc's command line:

```terminal
javadoc -taglet org.jdrupes.taglets.plantUml.Taglet -tagletpath /path/to/plantuml-taglet.jar:/path/to/plantuml.jar
```

This could be simplified by providing a "fat jar", but I doubt that anybody would
really use it.

### Maven

Still using this? Well, you're on your own...

## Notes

This taglet is released under the
[GPL 3.0](http://www.gnu.org/licenses/gpl-3.0-standalone.html).

The project's sources can be found on
[GitHub](https://github.com/mnlipp/jdrupes-taglets).
