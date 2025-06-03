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
 * Represents a penalty kick event taken by a player during a football match.
 * This event includes the player involved and the minute it occurred. Inherits
 * from {@link PlayerEvent}.
 *
 * The event description is automatically generated in the format: "-> Penalty
 * by [Player Name] at [Minute] minutes".
 *
 * Useful for tracking key match moments and statistics involving penalties.
 *
 */
public class PenaltiesEvent extends PlayerEvent {

    /**
     * Constructs a new PenaltiesEvent.
     *
     * @param player The player who took the penalty.
     * @param minute The minute the penalty occurred.
     */
    public PenaltiesEvent(IPlayer player, int minute) {
        super(player, minute, "-> Penalty by " + player.getName() + " at " + minute + " minutes");
    }
}
