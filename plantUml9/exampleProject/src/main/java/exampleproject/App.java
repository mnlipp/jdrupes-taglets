package exampleproject;

/**
 * <img src="example.svg" alt="UML diagram showing Alice authenticating at Bob">
 *
 * @startuml example.svg
 * Alice --> Bob: Authentication Request
 * Bob --> Alice: Authentication Response
 * @enduml
 *
 * Just run the application
 */
public class App {

    /**
     * No initialization needed
     */
    public App() {
    }

    /**
     * Main method ignoring the arguments and outputting "Hello world!"
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
