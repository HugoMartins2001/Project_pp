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
 * Represents a yellow card given to a player during a football match.
 * This event stores the player who received the card and the minute it occurred.
 * Inherits from {@link PlayerEvent}.
 *
 * <p>The event description is automatically generated in the format:
 * "-> Yellow card to [Player Name] at [Minute] minutes".</p>
 *
 * <p>This is used for tracking disciplinary actions and in-game consequences.</p>
 *
 */
public class YellowCardEvent extends PlayerEvent {

    /**
     * Constructs a new YellowCardEvent.
     *
     * @param player The player who received the yellow card.
     * @param minute The minute the yellow card was issued.
     */
    public YellowCardEvent(IPlayer player, int minute) {
        super(player, minute, "-> Yellow card to " + player.getName() + " at " + minute + " minutes");
    }
}
