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

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents a goal scored by a player during a football match.
 * Implements the {@link IGoalEvent} interface and extends {@link PlayerEvent}.
 *
 * <p>The event description is automatically generated in the format:
 * "-> Goal by [Player Name] at [Minute] minutes".</p>
 *
 * <p>This class stores the player who scored the goal and the minute it occurred.</p>
 *
 */
public class GoalEvent extends PlayerEvent implements IGoalEvent {

    /**
     * Constructs a new GoalEvent.
     *
     * @param player The player who scored the goal.
     * @param minute The minute the goal was scored.
     */
    public GoalEvent(IPlayer player, int minute) {
        super(player, minute, "-> Goal by " + player.getName() + " at " + minute + " minutes");
    }
}
