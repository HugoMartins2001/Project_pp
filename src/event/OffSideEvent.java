package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents an offside event committed by a player during a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log an offside
 * committed by a specific player at a particular minute of the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Offside by John Doe at 12 minutes"}
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
public class OffSideEvent extends PlayerEvent {

    /**
     * Constructs a new OffSideEvent with the given player and match minute.
     *
     * @param player The player who committed the offside.
     * @param minute The minute in the match when the offside occurred.
     */
    public OffSideEvent(IPlayer player, int minute) {
        super(player, minute, "-> Offside by " + player.getName() + " at " + minute + " minutes");
    }
}
