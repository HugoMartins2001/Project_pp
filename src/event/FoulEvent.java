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
 * Represents a foul event committed by a player during a football match. This
 * event stores the player responsible and the minute in which the foul
 * occurred. Inherits from {@link PlayerEvent}.
 *
 * The event description is automatically generated in the format: "-> Foul
 * commited by [Player Name] at [Minute] minutes".
 *
 */
public class FoulEvent extends PlayerEvent {

    /**
     * Constructs a new FoulEvent.
     *
     * @param player The player who committed the foul.
     * @param minute The minute in which the foul occurred.
     */
    public FoulEvent(IPlayer player, int minute) {
        super(player, minute, "-> Foul commited by " + player.getName() + " at " + minute + " minutes");
    }
}
