package test;

/**
 * <img src="RenderConletRequestSeq.svg" alt="RenderConletRequestSeq">
 *
 * @startuml RenderConletRequestSeq.svg
 * hide footbox
 *
 * Browser -> WebConsole: "renderConletRequest"
 * activate WebConsole
 * WebConsole -> Conlet: RenderConletRequest
 * deactivate WebConsole
 * activate Conlet
 * Conlet -> WebConsole: RenderConlet
 * deactivate Conlet
 * activate WebConsole
 * WebConsole -> Browser: "renderConlet"
 * deactivate WebConsole
 *
 * @enduml
 *
 */
public class Test2 {


}
