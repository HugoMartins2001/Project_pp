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
 * Represents the end of a football match.
 * <p>
 * This event is triggered when the match ends at a specific minute.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Match ended"}
 * </p>
 *
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LSIRCT2</li>
 *   <li>Hugo Leite Martins (8230273) - LSIRCT2</li>
 * </ul>
 * </p>
 *
 * @see Event
 */
public class EndEvent extends Event {

    /**
     * Constructs a new EndEvent at the given match minute.
     *
     * @param minute The minute at which the match ended.
     */
    public EndEvent(int minute) {
        super("-> Match ended", minute);
    }
}
