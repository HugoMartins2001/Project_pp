package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

/**
 * Represents a foul event committed by a player during a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log a foul committed
 * by a specific player at a given minute of the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Foul committed by John Doe at 15 minutes"}
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
 * @see PlayerEvent
 * @see IPlayer
 */
public class FoulEvent extends PlayerEvent {

    /**
     * Constructs a new FoulEvent with the given player and match minute.
     *
     * @param player The player who committed the foul.
     * @param minute The minute in the match when the foul occurred.
     */
    public FoulEvent(IPlayer player, int minute) {
        super(player, minute, "-> Foul commited by " + player.getName() + " at " + minute + " minutes");
    }
}
