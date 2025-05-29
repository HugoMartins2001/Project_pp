package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents an injury event involving a player during a football match.
 * <p>
 * This class extends {@link PlayerEvent} and is used to log when a player
 * suffers an injury at a specific minute of the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> John Doe got injured at 60 minutes"}
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
public class InjuryEvent extends PlayerEvent {

    /**
     * Constructs a new InjuryEvent with the given player and match minute.
     *
     * @param player The player who got injured.
     * @param minute The minute in the match when the injury occurred.
     */
    public InjuryEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " got injured at " + minute + " minutes");
    }
}
