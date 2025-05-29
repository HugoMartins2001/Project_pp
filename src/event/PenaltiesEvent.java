package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents a penalty event executed by a player during a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log when a specific player
 * takes a penalty at a given minute in the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Penalty by John Doe at 78 minutes"}
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
public class PenaltiesEvent extends PlayerEvent {

    /**
     * Constructs a new PenaltiesEvent with the given player and match minute.
     *
     * @param player The player who took the penalty.
     * @param minute The minute in the match when the penalty was taken.
     */
    public PenaltiesEvent(IPlayer player, int minute) {
        super(player, minute, "-> Penalty by " + player.getName() + " at " + minute + " minutes");
    }
}
