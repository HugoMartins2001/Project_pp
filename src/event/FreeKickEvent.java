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
 * Represents a free kick event taken by a player during a football match.
 * This event includes the player involved and the minute it occurred.
 * Inherits from {@link PlayerEvent}.
 *
 * <p>The event description is automatically generated in the format:
 * "-> Free kick by [Player Name] at [Minute] minutes".</p>
 *
 */
public class FreeKickEvent extends PlayerEvent {

    /**
     * Constructs a new FreeKickEvent.
     *
     * @param player The player who took the free kick.
     * @param minute The minute when the free kick occurred.
     */
    public FreeKickEvent(IPlayer player, int minute) {
        super(player, minute, "-> Free kick by " + player.getName() + " at " + minute + " minutes");
    }
}
