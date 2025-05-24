/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */
package team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

public class PlayerSelector implements IPlayerSelector {

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