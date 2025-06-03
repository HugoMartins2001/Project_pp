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
 * Represents a corner kick event in a football match. This event is associated
 * with a specific player and the minute in which it occurred. Inherits from
 * {@link PlayerEvent}.
 *
 * The description of the event is automatically generated in the format: "->
 * Corner kick by [Player Name] at [Minute] minutes".
 *
 */
public class CornerKickEvent extends PlayerEvent {

    /**
     * Constructs a new CornerKickEvent.
     *
     * @param player The player who performed the corner kick.
     * @param minute The minute in which the corner kick occurred.
     */
    public CornerKickEvent(IPlayer player, int minute) {
        super(player, minute, "-> Corner kick by " + player.getName() + " at " + minute + " minutes");
    }
}
