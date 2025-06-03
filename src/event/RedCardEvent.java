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
 * Represents a red card event given to a player during a football match. This
 * event includes the player who received the red card and the minute it
 * occurred. Inherits from {@link PlayerEvent}.
 *
 * The event description is automatically generated in the format: "-> Red card
 * to [Player Name] at [Minute] minutes".
 *
 * Used to track disciplinary actions in match simulations or logs.
 *
 */
public class RedCardEvent extends PlayerEvent {

    /**
     * Constructs a new RedCardEvent.
     *
     * @param player The player who received the red card.
     * @param minute The minute the red card was given.
     */
    public RedCardEvent(IPlayer player, int minute) {
        super(player, minute, "-> Red card to " + player.getName() + " at " + minute + " minutes");
    }
}
