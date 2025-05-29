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
 * Enumeration representing the possible positions of a football player.
 * Implements {@link IPlayerPosition} interface to provide a description for each position.
 */
public enum PlayerPosition implements IPlayerPosition {
    GOALKEEPER,
    DEFENDER,
    MIDFIELDER,
    FORWARD;

    /**
     * Returns a human-readable description of the player position.
     *
     * @return a string description of the position
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
