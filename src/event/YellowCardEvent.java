package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

/**
 * Represents a yellow card event in a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log when a specific player
 * receives a yellow card during the match at a specific minute.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Yellow card to John Doe at 45 minutes"}
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
public class YellowCardEvent extends PlayerEvent {

    /**
     * Constructs a new YellowCardEvent with the given player and match minute.
     *
     * @param player The player who received the yellow card.
     * @param minute The minute in the match when the yellow card was given.
     */
    public YellowCardEvent(IPlayer player, int minute) {
        super(player, minute, "-> Yellow card to " + player.getName() + " at " + minute + " minutes");
    }
}
