package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents a corner kick event in a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log a corner kick
 * performed by a specific player at a specific minute of the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Corner kick by John Doe at 25 minutes"}
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
 * @see PlayerEvent
 * @see IPlayer
 */
public class CornerKickEvent extends PlayerEvent {

    /**
     * Constructs a new {@code CornerKickEvent} with the given player and match minute.
     *
     * @param player the {@link IPlayer} who performed the corner kick
     * @param minute the minute in the match when the corner kick occurred
     */
    public CornerKickEvent(IPlayer player, int minute) {
        super(player, minute, "-> Corner kick by " + player.getName() + " at " + minute + " minutes");
    }
}
