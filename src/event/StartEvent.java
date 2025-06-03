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
 * Represents the start of a football match. This event indicates that the match
 * has officially begun and includes the starting minute. Inherits from
 * {@link Event}.
 *
 * The event description is fixed as "-> Match started".
 *
 * The {@code exportToJson()} method is left empty and can be implemented later
 * if needed.
 *
 */
public class StartEvent extends Event {

    /**
     * Constructs a new StartEvent.
     *
     * @param minute The minute the match started.
     */
    public StartEvent(int minute) {
        super("-> Match started", minute);
    }

    /**
     * Exports the event to JSON format. Currently not implemented. You may
     * override this if needed for output.
     */
    @Override
    public void exportToJson() {
        // Implement JSON export logic here if needed
    }
}
