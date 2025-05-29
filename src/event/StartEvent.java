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
 * Represents the start of a football match.
 * <p>
 * This class extends {@link Event} and is used to log the moment the match begins.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Match started"}
 * </p>
 *
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 *
 * @see Event
 */
public class StartEvent extends Event {

    /**
     * Constructs a new StartEvent for the beginning of the match.
     *
     * @param minute The minute when the match started (usually 0).
     */
    public StartEvent(int minute) {
        super("-> Match started", minute);
    }

    /**
     * Exports the event to JSON format.
     * <p>This method is currently unimplemented and should be overridden if needed.</p>
     */
    @Override
    public void exportToJson() {
        // Implement JSON export logic here if needed
    }
}
