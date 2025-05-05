package team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import player.Player;

import java.io.IOException;

public class Team implements ITeam {

    private static IClub club;
    private Player[] team;
    private int teamSize;

    @Override
    public IClub getClub() {
        return club;
    }

    @Override
    public IFormation getFormation() {
        return null;
    }

    @Override
    public IPlayer[] getPlayers() {
        return new IPlayer[0];
    }

    @Override
    public void addPlayer(IPlayer iPlayer) {
        if(iPlayer == null){
            throw new IllegalArgumentException();
        }
        for( IPlayer x : team ){
            if(x.equals(iPlayer)) {
                throw new IllegalStateException();
            }
        }
        if(team.length + 1 > 24 ){
            throw new IllegalStateException();
        }
        if(!((Player)iPlayer).getClub().equals(club)){
            throw new IllegalStateException();
        }
        if(getFormation() == null){
            throw new IllegalStateException();
        }
        team[teamSize] = (Player)iPlayer;
        teamSize++;
    }

    @Override
    public int getPositionCount(IPlayerPosition iPlayerPosition) {
        if(iPlayerPosition == null){
            throw new IllegalArgumentException();
        }
        int counter = 0;
        for( IPlayer x : team ){
            if(x.getPosition().equals(iPlayerPosition)){
                counter++;
            }
        }
        return counter;
    }

    @Override
    public boolean isValidPositionForFormation(IPlayerPosition iPlayerPosition) {
        return false;
    }

    @Override
    public int getTeamStrength() {
        return 0;
    }

    @Override
    public void setFormation(IFormation iFormation) {

    }

    //TODO FALTA FAZER
    @Override
    public void exportToJson() throws IOException {

    }

}
