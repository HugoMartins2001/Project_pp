/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */
package player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

/**
 * Enum representing the possible positions a football (soccer) player can occupy on the field.
 * <p>
 * The available positions are:
 * <ul>
 *   <li>{@link #GOALKEEPER} - The player who guards the goal.</li>
 *   <li>{@link #DEFENDER} - A player who primarily defends against the opposing team's attacks.</li>
 *   <li>{@link #MIDFIELDER} - A player who operates mainly in the middle of the field, both defending and attacking.</li>
 *   <li>{@link #FORWARD} - A player whose primary role is to score goals.</li>
 * </ul>
 * </p>
 * <p>
 * This enum implements the {@link IPlayerPosition} interface, providing a textual description for each position.
 * </p>
 *
 * @author Rúben Tiago Martins Pereira
 * @author Hugo Leite Martins
 * @see IPlayerPosition
 */
public enum PlayerPosition implements IPlayerPosition {
    GOALKEEPER,
    DEFENDER,
    MIDFIELDER,
    FORWARD;

    /**
     * Returns a human-readable description of the player's position.
     *
     * @return a string describing the position (e.g., "Goalkeeper", "Defender")
     */
    @Override
    public String getDescription() {
        switch (this) {
            case GOALKEEPER:
                return "Goalkeeper";
            case DEFENDER:
                return "Defender";
            case MIDFIELDER:
                return "Midfielder";
            case FORWARD:
                return "Forward";
            default:
                return "Unknown Position";
        }
    }
}