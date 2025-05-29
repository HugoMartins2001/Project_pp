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

public class EndEvent extends Event {
    public EndEvent(int minute) {
        super("-> Match ended", minute);
    }
}
