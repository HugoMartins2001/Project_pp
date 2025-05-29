package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Represents a substitution event in a football match.
 * <p>
 * This class extends {@link Event} and is used to log when one player is substituted
 * by another at a specific minute in the match.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Substitution: John Smith out, Alex Johnson in"}
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
 * @see Event
 * @see IPlayer
 */

public class SubstitutionEvent extends Event {
    private final IPlayer playerIn;
    private final IPlayer playerOut;

    /**
     * Constructs a new SubstitutionEvent with the given players and match minute.
     *
     * @param playerOut The player who is leaving the field.
     * @param playerIn The player who is entering the field.
     * @param minute The minute when the substitution occurred.
     */
    public SubstitutionEvent(IPlayer playerOut, IPlayer playerIn, int minute) {
        super("-> Substitution: " + playerOut.getName() + " out, " + playerIn.getName() + " in", minute);
        this.playerOut = playerOut;
        this.playerIn = playerIn;
    }

    /**
     * Gets the player who left the field.
     *
     * @return The outgoing player.
     */
    public IPlayer getPlayerOut() {
        return playerOut;
    }

    /**
     * Gets the player who entered the field.
     *
     * @return The incoming player.
     */
    public IPlayer getPlayerIn() {
        return playerIn;
    }
}
