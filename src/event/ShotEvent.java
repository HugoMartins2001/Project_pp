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
 * Represents a shot attempt made by a player during a football match. This
 * event stores the player who took the shot and the minute it occurred.
 * Inherits from {@link PlayerEvent}.
 *
 * The event description is automatically generated in the format: "-> [Player
 * Name] shot at [Minute] minutes".
 *
 * Useful for tracking offensive actions and match statistics.
 *
 */
public class ShotEvent extends PlayerEvent {

    /**
     * Constructs a new ShotEvent.
     *
     * @param player The player who made the shot.
     * @param minute The minute the shot occurred.
     */
    public ShotEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " shot at " + minute + " minutes");
    }
}
