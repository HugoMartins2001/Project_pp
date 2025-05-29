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
 * Represents a shot attempt by a player during a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log when a player
 * takes a shot at a specific minute of the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> John Doe shot at 32 minutes"}
 * </p>
 *
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 *
 * @see PlayerEvent
 * @see IPlayer
 */
public class ShotEvent extends PlayerEvent {

    /**
     * Constructs a new ShotEvent with the given player and match minute.
     *
     * @param player The player who made the shot.
     * @param minute The minute in the match when the shot occurred.
     */
    public ShotEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " shot at " + minute + " minutes");
    }
}
