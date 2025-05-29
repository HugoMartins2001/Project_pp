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
 * Represents an injury event involving a player during a football match.
 * This event records the player who got injured and the minute it occurred.
 * Inherits from {@link PlayerEvent}.
 *
 * <p>The event description is automatically generated in the format:
 * "-> [Player Name] got injured at [Minute] minutes".</p>
 *
 * <p>Useful for tracking match incidents and affecting game logic or player status.</p>
 *
 */
public class InjuryEvent extends PlayerEvent {

    /**
     * Constructs a new InjuryEvent.
     *
     * @param player The player who got injured.
     * @param minute The minute when the injury occurred.
     */
    public InjuryEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " got injured at " + minute + " minutes");
    }
}
