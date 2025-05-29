package event;

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents a goal event scored by a player during a football match.
 * <p>
 * This class extends {@link PlayerEvent} and implements {@link IGoalEvent}.
 * It is used to log a goal scored by a specific player at a given minute of the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Goal by John Doe at 42 minutes"}
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
 * @see IGoalEvent
 * @see IPlayer
 */
public class GoalEvent extends PlayerEvent implements IGoalEvent {

    /**
     * Constructs a new GoalEvent with the given player and match minute.
     *
     * @param player The player who scored the goal.
     * @param minute The minute in the match when the goal was scored.
     */
    public GoalEvent(IPlayer player, int minute) {
        super(player, minute, "-> Goal by " + player.getName() + " at " + minute + " minutes");
    }
}
