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
