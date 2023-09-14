package testMore;

/**
 * <img src="RunnerStates.svg">
 * 
 * @startuml RunnerStates.svg
 * [*] --> Initializing
 * Initializing -> Initializing: InitialConfiguration/configure Runner
 * Initializing -> Initializing: Start/start Runner
 * 
 * state "Starting (Processes)" as StartingProcess {
 * 
 *     state which <<choice>>
 *     state "Start swtpm" as swtpm
 *     state "Start qemu" as qemu
 *     state "Open monitor" as monitor
 *     state success <<exitPoint>>
 *     state error <<exitPoint>>
 *      
 *     which --> swtpm: [use swtpm]
 *     which --> qemu: [else]
 * 
 *     swtpm: entry/start swtpm
 *     swtpm -> error: StartProcessError/stop
 *     swtpm -> qemu: FileChanged[swtpm socket created]
 * 
 *     qemu: entry/start qemu
 *     qemu -> error: StartProcessError/stop
 *     qemu --> monitor : FileChanged[monitor socket created] 
 * 
 *     monitor: entry/fire OpenSocketConnection
 *     monitor --> success: ClientConnected[for monitor]
 *     monitor -> error: ConnectError[for monitor]
 * }
 * 
 * Initializing --> which: Started
 * 
 * success --> Running
 * error --> [*]
 * 
 * state Terminating {
 *     state terminated <<exitPoint>>
 *     state which2 <<choice>>
 * 
 *     state "Powerdown qemu" as qemuPowerdown
 *     state "Await process termination" as terminateProcesses
 *     qemuPowerdown: entry/suspend Stop, send powerdown to qemu, start timer
 *     
 *     qemuPowerdown --> which2: Closed[for monitor]/resume Stop
 *     qemuPowerdown --> terminateProcesses: Timeout/resume Stop
 *     which2 --> terminateProcesses: [use swtmp]
 *     which2 --> terminated: [else]
 *     terminateProcesses --> terminated
 * }
 * 
 * Running --> qemuPowerdown: Stop
 * terminated --> [*]
 *
 * @enduml
 */
public class Test3 {


}
