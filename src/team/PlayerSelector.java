package team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

/**
 * Implements the {@link IPlayerSelector} interface to select a player from a club
 * based on a specified position.
 * <p>
 * This class provides a simple selection strategy: it returns the first available player
 * in the club who matches the given position.
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
 * @see IPlayerSelector
 * @see IClub
 * @see IPlayerPosition
 */
public class PlayerSelector implements IPlayerSelector {

    /**
     * Selects the first player from the given club who matches the specified position.
     * <p>
     * Validates that the club, position, and club's player array are not {@code null} or empty.
     * Throws an exception if no player for the specified position is found.
     * </p>
     *
     * @param club     the {@link IClub} from which to select a player
     * @param position the {@link IPlayerPosition} to match
     * @return the first {@link IPlayer} in the club who matches the specified position
     * @throws IllegalArgumentException if the club, position, or club's player array is {@code null} or empty
     * @throws IllegalStateException    if no player matching the specified position is found
     */
    @Override
    public IPlayer selectPlayer(IClub club, IPlayerPosition position) {
        if (club == null) {
            throw new IllegalArgumentException("There is no club selected!");
        }
        if (position == null) {
            throw new IllegalArgumentException("There is no position selected!");
        }
        IPlayer[] players = club.getPlayers();
        if (players == null || players.length == 0) {
            throw new IllegalArgumentException("There are no players in the club!");
        }
        for (IPlayer player : players) {
            if (player != null && position.equals(player.getPosition())) {
                return player;
            }
        }
        throw new IllegalStateException("No player found for the specified position.");
    }
}