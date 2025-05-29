package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents a shot on goal attempt by a player during a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log when a player
 * attempts a shot on goal at a specific minute of the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> John Doe shot on goal at 78 minutes"}
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
public class ShotOnGoalEvent extends PlayerEvent {

    /**
     * Constructs a new ShotOnGoalEvent with the given player and match minute.
     *
     * @param player The player who attempted the shot on goal.
     * @param minute The minute in the match when the shot on goal occurred.
     */
    public ShotOnGoalEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " shot on goal at " + minute + " minutes");
    }
}
