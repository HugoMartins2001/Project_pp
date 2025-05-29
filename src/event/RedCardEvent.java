package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents a red card event in a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log when a player
 * receives a red card at a specific minute of the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Red card to John Doe at 65 minutes"}
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
public class RedCardEvent extends PlayerEvent {

    /**
     * Constructs a new RedCardEvent with the given player and match minute.
     *
     * @param player The player who received the red card.
     * @param minute The minute in the match when the red card was given.
     */
    public RedCardEvent(IPlayer player, int minute) {
        super(player, minute, "-> Red card to " + player.getName() + " at " + minute + " minutes");
    }
}
