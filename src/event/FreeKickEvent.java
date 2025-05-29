package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

/**
 * Represents a free kick event performed by a player during a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log a free kick
 * executed by a specific player at a given minute of the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Free kick by John Doe at 34 minutes"}
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

public class FreeKickEvent extends PlayerEvent {

    /**
     * Constructs a new FreeKickEvent with the given player and match minute.
     *
     * @param player The player who performed the free kick.
     * @param minute The minute in the match when the free kick occurred.
     */
    public FreeKickEvent(IPlayer player, int minute) {
        super(player, minute, "-> Free kick by " + player.getName() + " at " + minute + " minutes");
    }
}
