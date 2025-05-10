package team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

import java.io.IOException;

public class Club implements IClub {

    private String name;
    private String acronymName;
    private String clubNationality;
    private String stadiumName;
    private String clubLogo;
    private int dateOfFoundation;
    private IPlayer[] players;

    @Override
    public String getName() {
        return name;
    }

    //TODO FALTA FAZER
    @Override
    public IPlayer[] getPlayers() {
    }

    @Override
    public String getCode() {
        return acronymName;
    }

    @Override
    public String getCountry() {
        return clubNationality;
    }

    @Override
    public int getFoundedYear() {
        return dateOfFoundation;
    }

    @Override
    public String getStadiumName() {
        return stadiumName;
    }

    @Override
    public String getLogo() {
        return clubLogo;
    }

    @Override
    public void addPlayer(IPlayer player) {
        if(player == null) {
            throw new IllegalStateException("The player is not selected!");
        }
        for(int i = 0; i < players.length; i++) {
            if(players[i].equals(player)) {
                throw new IllegalStateException("The player is already in the club!");
            }
        }
    }

    @Override
    public boolean isPlayer(IPlayer player) {
        if(player == null) {
            throw new IllegalStateException("The player is not selected!");
        }
        for(IPlayer IPlayer : players) {
            if(player.equals(player)) {
                throw new IllegalStateException("The player is already in the club!");
            }
        }
        return true;
    }

    @Override
    public void removePlayer(IPlayer player) {

    }

    @Override
    public int getPlayerCount() {
        return 0;
    }

    @Override
    public IPlayer selectPlayer(IPlayerSelector playerSelector, IPlayerPosition iPlayerPosition) {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void exportToJson() throws IOException {

    }
}
