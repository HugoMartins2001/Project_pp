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
 * Represents an offside event committed by a player during a football match.
 * This event captures the player involved and the minute it occurred. Inherits
 * from {@link PlayerEvent}.
 *
 * The event description is automatically generated in the format: "-> Offside
 * by [Player Name] at [Minute] minutes".
 *
 * This can be used for event tracking, statistics, or match commentary.
 *
 */
public class OffSideEvent extends PlayerEvent {

    /**
     * Constructs a new OffSideEvent.
     *
     * @param player The player who committed the offside.
     * @param minute The minute when the offside occurred.
     */
    public OffSideEvent(IPlayer player, int minute) {
        super(player, minute, "-> Offside by " + player.getName() + " at " + minute + " minutes");
    }
}
