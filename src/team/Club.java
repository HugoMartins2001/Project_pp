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

/**
 * Represents a football club, containing information such as its name, code,
 * nationality, stadium, logo, date of foundation, and a list of players.
 */
public class Club implements IClub {

    private String name;
    private String code;
    private String clubNationality;
    private String stadiumName;
    private String clubLogo;
    private int dateOfFoundation;
    private IPlayer[] players;
    private int playerCount;
    private boolean isvalid;

    /**
     * Constructs a new Club instance with basic information and an empty player
     * list.
     *
     * @param name the name of the club
     * @param code the unique code identifying the club
     * @param clubNationality the nationality of the club
     * @param stadiumName the name of the club's stadium
     * @param clubLogo the path or identifier of the club's logo
     * @param dateOfFoundation the year the club was founded
     */
    public Club(String name, String code, String clubNationality, String stadiumName,
            String clubLogo, int dateOfFoundation) {
        this.name = name;
        this.code = code;
        this.clubNationality = clubNationality;
        this.stadiumName = stadiumName;
        this.clubLogo = clubLogo;
        this.dateOfFoundation = dateOfFoundation;
        this.players = new IPlayer[30];
        this.playerCount = 0;
    }

    /**
     * Constructs a new Club instance with basic information and an initial list
     * of players.
     *
     * @param name the name of the club
     * @param code the unique code identifying the club
     * @param clubNationality the nationality of the club
     * @param stadiumName the name of the club's stadium
     * @param clubLogo the path or identifier of the club's logo
     * @param dateOfFoundation the year the club was founded
     * @param players the array of players initially assigned to the club
     */
    public Club(String name, String code, String clubNationality, String stadiumName,
            String clubLogo, int dateOfFoundation, IPlayer[] players) {
        this.name = name;
        this.code = code;
        this.clubNationality = clubNationality;
        this.stadiumName = stadiumName;
        this.clubLogo = clubLogo;
        this.dateOfFoundation = dateOfFoundation;
        this.players = new IPlayer[players.length];
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                this.players[playerCount++] = players[i];
            }
        }
    }

    /**
     * Returns the name of the club.
     *
     * @return the club's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns a clone of the array of players registered to the club. Each
     * player is cloned individually to ensure the array is a deep copy.
     *
     * @return a cloned array of IPlayer objects, or an empty array if cloning
     * fails
     */
    @Override
    public IPlayer[] getPlayers() {
        IPlayer[] playersClone = new IPlayer[getPlayerCount()];
        try {
            for (int i = 0; i < players.length; i++) {
                if (players[i] != null) {
                    playersClone[i] = ((Player) players[i]).clone();
                }
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Error while cloning players!");
            return new IPlayer[0];
        }
        return playersClone;
    }

    /**
     * Returns the club's unique code.
     *
     * @return the club's code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Returns the nationality of the club.
     *
     * @return the club's country
     */
    @Override
    public String getCountry() {
        return clubNationality;
    }

    /**
     * Returns the year the club was founded.
     *
     * @return the foundation year
     */
    @Override
    public int getFoundedYear() {
        return dateOfFoundation;
    }

    /**
     * Returns the name of the club's stadium.
     *
     * @return the stadium name
     */
    @Override
    public String getStadiumName() {
        return stadiumName;
    }

    /**
     * Returns the logo associated with the club.
     *
     * @return the logo path or identifier
     */
    @Override
    public String getLogo() {
        return clubLogo;
    }

    /**
     * Adds a player to the club's roster.
     *
     * This method checks for:
     * <ul>
     * <li>If the player is null</li>
     * <li>If the player is already in the club</li>
     * <li>If the club has space for new players</li>
     * </ul>
     *
     * @param player the player to be added
     * @throws IllegalArgumentException if the player is null or already exists
     * in the club
     * @throws IllegalStateException if the club is full
     */
    @Override
    public void addPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("The player is not selected!");
        }
        for (IPlayer p : players) {
            if (p != null && p.equals(player)) {
                throw new IllegalArgumentException("The player is already in the club!");
            }
        }
        boolean hasSpace = false;
        for (IPlayer p : players) {
            if (p == null) {
                hasSpace = true;
                break;
            }
        }
        if (!hasSpace) {
            throw new IllegalStateException("The club is already full!");
        }
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }

    /**
     * Checks if a given player is registered in the club.
     *
     * A player is considered valid if it has a non-null name and position.
     *
     * @param player the player to be checked
     * @return true if the player belongs to the club, false otherwise
     * @throws IllegalArgumentException if the player is null or has invalid
     * data
     */
    @Override
    public boolean isPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("The player is not selected!");
        }
        if (player.getName() == null || player.getPosition() == null) {
            throw new IllegalArgumentException("The player is not valid!");
        }
        for (IPlayer p : players) {
            if (p != null && p.equals(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a player from the club's roster.
     *
     * This method looks for the player in the array and sets its slot to null
     * if found.
     *
     * @param player the player to be removed
     * @throws IllegalArgumentException if the player is null or not found in
     * the club
     */
    @Override
    public void removePlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("The player is not selected!");
        }
        boolean playerfound = false;
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null && players[i].equals(player)) {
                players[i] = null;
                playerfound = true;
                break;
            }
        }
        if (!playerfound) {
            throw new IllegalArgumentException("The player is not in the club!");
        }
    }

    /**
     * Returns the total number of players currently in the club.
     *
     * @return the number of non-null players
     */
    @Override
    public int getPlayerCount() {
        int count = 0;
        for (IPlayer p : players) {
            if (p != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Selects a player from the club using a given selector strategy and
     * position.
     *
     * The selector is used to find the best player for the provided position.
     *
     * @param selector the selection strategy to use
     * @param position the required player position
     * @return the selected player
     * @throws IllegalArgumentException if the position is null
     * @throws IllegalStateException if the club has no players
     */
    @Override
    public IPlayer selectPlayer(IPlayerSelector selector, IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("The position is not selected!");
        }
        if (getPlayerCount() == 0) {
            throw new IllegalStateException("The club is empty!");
        }
        return selector.selectPlayer(this, position);
    }

    /**
     * Sets the list of players for the club and updates the player count
     * accordingly.
     *
     * @param players an array of Player objects to assign to the club
     */
    public void setPlayers(Player[] players) {
        this.players = players;
        int count = 0;
        for (Player p : players) {
            if (p != null) {
                count++;
            }
        }
        this.playerCount = count;
    }

    /**
     * Validates whether the club is correctly initialized.
     *
     * Checks that required fields are set, the club has at least 16 players,
     * and that it includes players in each of the main positions: Goalkeeper,
     * Defender, Midfielder, and Forward.
     *
     * @return true if the club is valid
     * @throws IllegalStateException if any required attribute is missing or if
     * position constraints are not met
     */
    @Override
    public boolean isValid() {
        if (name == null || code == null || clubNationality == null || stadiumName == null || clubLogo == null || dateOfFoundation == 0) {
            throw new IllegalStateException("The club is empty!");
        }
        if (getPlayerCount() == 0) {
            throw new IllegalStateException("The club has no players!");
        }
        if (getPlayerCount() < 16) {
            throw new IllegalStateException("The club must have at least 16 players!");
        }

        boolean hasGoalkeeper = false;
        boolean hasDefender = false;
        boolean hasMidfielder = false;
        boolean hasForward = false;

        for (IPlayer p : players) {
            if (p == null || p.getPosition() == null) {
                continue;
            }
            switch (p.getPosition().getDescription()) {
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
        }

        if (!hasGoalkeeper) {
            throw new IllegalStateException("The club has no goalkeepers!");
        }
        if (!hasDefender || !hasMidfielder || !hasForward) {
            throw new IllegalStateException("The club doesn't have enough players for each position!");
        }

        return true;
    }

    /**
     * Checks whether another object is equal to this club.
     *
     * Two clubs are considered equal if they have the same name.
     *
     * @param obj the object to compare
     * @return true if the object is a Club with the same name, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Club)) {
            return false;
        }
        Club club = (Club) obj;
        return this.name.equals(club.name);
    }

    /**
     *
     * {@inheritDoc}
     *
     *
     * <b>Note:</b> This method is intentionally left unimplemented in this
     * class, as JSON export is handled centrally by a component responsible for
     * exporting, the complete state of the application.
     *
     * This implementation exists solely to satisfy the requirements of the,
     * {@code Exportable} interface and has no practical use in this specific
     * class.
     *
     * @throws IOException Not applicable in this implementation
     */
    @Override
    public void exportToJson() throws IOException {// Not applicable in this class}
        // Not implemented
    }

    /**
     * Returns a string representation of the club, including name, code,
     * country, nationality, stadium name, logo, and date of foundation.
     *
     * @return a formatted string describing the club
     */
    @Override
    public String toString() {
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
