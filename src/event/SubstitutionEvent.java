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
 * Represents a substitution event during a football match.
 * This event includes the player who is substituted out and the player who comes in,
 * along with the minute the substitution took place.
 * Inherits from {@link Event}.
 *
 * The event description is automatically generated in the format:
 * "-> Substitution: [PlayerOut Name] out, [PlayerIn Name] in".
 *
 * Useful for updating lineups, tracking fatigue, and managing team strategies.
 *
 */
public class SubstitutionEvent extends Event {
    private final IPlayer playerIn;
    private final IPlayer playerOut;

    /**
     * Constructs a new SubstitutionEvent.
     *
     * @param playerOut The player who is leaving the field.
     * @param playerIn  The player who is entering the field.
     * @param minute    The minute the substitution occurred.
     */
    public SubstitutionEvent(IPlayer playerOut, IPlayer playerIn, int minute) {
        super("-> Substitution: " + playerOut.getName() + " out, " + playerIn.getName() + " in", minute);
        this.playerOut = playerOut;
        this.playerIn = playerIn;
    }

    /**
     * Gets the player who was substituted out.
     *
     * @return The player who left the field.
     */
    public IPlayer getPlayerOut() {
        return playerOut;
    }

    /**
     * Gets the player who was substituted in.
     *
     * @return The player who entered the field.
     */
    public IPlayer getPlayerIn() {
        return playerIn;
    }
}
