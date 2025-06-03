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

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents a shot on goal attempt made by a player during a football match.
 * This event stores the player who attempted the shot and the minute it
 * occurred. Inherits from {@link PlayerEvent}.
 *
 * The event description is automatically generated in the format: "-> [Player
 * Name] shot on goal at [Minute] minutes".
 *
 * Useful for match statistics, analytics, or live commentary systems.
 *
 */
public class ShotOnGoalEvent extends PlayerEvent {

    /**
     * Constructs a new ShotOnGoalEvent.
     *
     * @param player The player who made the shot on goal.
     * @param minute The minute the shot occurred.
     */
    public ShotOnGoalEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " shot on goal at " + minute + " minutes");
    }
}
