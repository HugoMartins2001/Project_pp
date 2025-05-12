package team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import player.Player;

import java.io.IOException;

public class Team implements ITeam {

    private IClub club;
    private IFormation formation;
    private IPlayer[] playersTeam;

    @Override
    public IClub getClub() {
        return club;
    }

    @Override
    public IFormation getFormation() {
        if(formation == null){
            throw new IllegalStateException("The formation is not setted!");
        }
        return formation;
    }

    @Override
    public IPlayer[] getPlayers() {
        IPlayer[] playersClone = new IPlayer[playersTeam.length];
        try {
            for (int i = 0; i < playersTeam.length; i++) {
                playersClone[i] = ((Player) playersTeam[i]).clone();
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Error while cloning players!");
            return new IPlayer[0];
        }
        return playersClone;
    }

    @Override
    public void addPlayer(IPlayer player) {
        if(player == null) {
            throw new IllegalArgumentException("The player is not selected!");
        }
        for(IPlayer p : playersTeam) {
            if(p != null && p.equals(player)){
                throw new IllegalStateException("The player is already in the team!");
            }
        }
        boolean hasSpace = false;
        for(IPlayer p : playersTeam) {
            if(p == null){
                hasSpace = true;
                break;
            }
        }
        if(!hasSpace) {
            throw new IllegalStateException("The team is already full!");
        }
        if(!club.isPlayer(player)) {
            throw new IllegalStateException("The player is not in the club!");
        }
        if(formation == null) {
            throw new IllegalStateException("The formation is not setted!");
        }
        for(int i = 0; i < playersTeam.length; i++) {
            if(playersTeam[i] == null){
                playersTeam[i] = player;
                break;
            }
        }
    }

    @Override
    public int getPositionCount(IPlayerPosition position) {
        if(position == null){
            throw new IllegalArgumentException("The position is not defined!");
        }
        int counter = 0;
        for( IPlayer p : playersTeam ){
            if(p != null && p.getPosition().equals(position)){
                counter++;
            }
        }
        return counter;
    }

    //TODO falta fazer
    @Override
    public boolean isValidPositionForFormation(IPlayerPosition position) {
        if(position == null){
            throw new IllegalArgumentException("The position is not defined!");
        }
        return true;
    }

    @Override
    public int getTeamStrength() {
        int teamStrength = 0;
        int playerCount = 0;
        for (IPlayer p : playersTeam) {
            if(p != null){
                int playerAverage = (p.getShooting() + p.getPassing() + p.getStamina() + p.getSpeed()) / 4;
                teamStrength += playerAverage;
                playerCount++;
            }
        }
        if(playerCount == 0){
            throw new IllegalStateException("There is no player in the team!");
        }
        return teamStrength / playerCount;
    }

    @Override
    public void setFormation(IFormation formation) {
        if(formation == null) {
            throw new IllegalArgumentException("The formation is not setted!");
        }
        this.formation = formation;
    }

    //TODO FALTA FAZER
    @Override
    public void exportToJson() throws IOException {

    }

}
