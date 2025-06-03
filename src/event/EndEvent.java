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

/**
 * Represents the end of a football match. This event indicates that the match
 * has concluded and includes the minute it occurred. Inherits from
 * {@link Event}.
 *
 * The description is fixed as "-> Match ended".
 *
 */
public class EndEvent extends Event {

    /**
     * Constructs an EndEvent indicating the match has ended at a specific
     * minute.
     *
     * @param minute The minute the match ended.
     */
    public EndEvent(int minute) {
        super("-> Match ended", minute);
    }
}
