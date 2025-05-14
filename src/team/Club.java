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
import player.Player;

import java.io.IOException;
import java.util.Arrays;

public class Club implements IClub {

    private String name;
    private String code;
    private String clubNationality;
    private String stadiumName;
    private String clubLogo;
    private int dateOfFoundation;
    private IPlayer[] players;
    private boolean isvalid;

    public Club(String name, String code, String clubNationality, String stadiumName,
                String clubLogo, int dateOfFoundation) {
        this.name = name;
        this.code = code;
        this.clubNationality = clubNationality;
        this.stadiumName = stadiumName;
        this.clubLogo = clubLogo;
        this.dateOfFoundation = dateOfFoundation;
        this.players = new IPlayer[25];
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public IPlayer[] getPlayers() {
        IPlayer[] playersClone = new IPlayer[players.length];
        try {
            for (int i = 0; i < players.length; i++) {
                playersClone[i] = ((Player) players[i]).clone();
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Error while cloning players!");
            return new IPlayer[0];
        }
        return playersClone;
    }

    @Override
    public String getCode() {
        return code;
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
            throw new IllegalArgumentException("The player is not selected!");
        }
        for(IPlayer p : players) {
            if(p != null && p.equals(player)){
                throw new IllegalArgumentException("The player is already in the club!");
            }
        }
        boolean hasSpace = false;
        for(IPlayer p : players) {
            if(p == null){
                hasSpace = true;
                break;
            }
        }
        if(!hasSpace) {
            throw new IllegalStateException("The club is already full!");
        }
        for(int i = 0; i < players.length; i++) {
            if(players[i] == null){
                players[i] = player;
                break;
            }
        }
    }

    @Override
    public boolean isPlayer(IPlayer player) {
        if(player == null) {
            throw new IllegalArgumentException("The player is not selected!");
        }
        if(player.getName() == null || player.getPosition() == null) {
            throw new IllegalArgumentException("The player is not valid!");
        }
        for(IPlayer p : players) {
            if(p != null && p.equals(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removePlayer(IPlayer player) {
        if(player == null) {
            throw new IllegalArgumentException("The player is not selected!");
        }
        boolean playerfound = false;
        for(int i = 0; i < players.length; i++) {
            if(players[i] != null && players[i].equals(player)) {
                players[i] = null;
                playerfound = true;
                break;
            }
        }
        if(!playerfound) {
            throw new IllegalArgumentException("The player is not in the club!");
        }
    }

    @Override
    public int getPlayerCount() {
        int count = 0;
        for(IPlayer p : players) {
            if(p != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public IPlayer selectPlayer(IPlayerSelector selector, IPlayerPosition position) {
        if(position == null){
            throw new IllegalArgumentException("The position is not selected!");
        }
        if(getPlayerCount() == 0) {
            throw new IllegalStateException("The club is empty!");
        }
        return selector.selectPlayer(this, position);
    }

    @Override
    public boolean isValid() {
        if(name == null || code == null || clubNationality == null || stadiumName == null || clubLogo == null || dateOfFoundation == 0) {
            throw new IllegalStateException("The club is empty!");
        }
        if(getPlayerCount() == 0){
            throw new IllegalStateException("The club has no players!");
        }
        if(getPlayerCount() < 16){
            throw new IllegalStateException("The club has no players in a specific position!");
        }

        boolean hasGoalkeeper = false;
        boolean hasDefender = false;
        boolean hasMidfielder = false;
        boolean hasForward = false;

        for(IPlayer p : players) {
            switch(p.getPosition().getDescription()){
                case "Goalkeeper":
                    hasGoalkeeper = true;
                    break;
                case "Defender":
                    hasDefender = true;
                    break;
                case "Midfielder":
                    hasMidfielder = true;
                    break;
                case "Forward":
                    hasForward = true;
                    break;
            }
            if(hasGoalkeeper != true){
                throw new IllegalStateException("The club has no goalkeepers!");
            }
            if(hasGoalkeeper != true || hasDefender != true || hasMidfielder !=true || hasForward != true){
                throw new IllegalStateException("The club doesn't have enought players for each position!");
            }
        }
        return true;
    }

    //TODO fazer no final
    @Override
    public void exportToJson() throws IOException {

    }

    @Override
    public String toString(){
        String s = "Name: " + name + "\n";
        s += "Code: " + code + "\n";
        s += "Country: " + getCountry() + "\n";
        s += "Nationality: " + clubNationality + "\n";
        s += "Stadium name: " + stadiumName + "\n";
        s += "Logo: " + clubLogo + "\n";
        s += "Date of Foundation: " + dateOfFoundation + "\n";
        s += "----------------------------------------\n";

        return s;
    }
}
