/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */

package event;

public class StartEvent extends Event {

    public StartEvent(int minute) {
        super("-> Match started", minute);
    }

    @Override
    public void exportToJson() {
        // Implement JSON export logic here if needed
    }
}
