package team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;
import player.Player;

import java.io.IOException;
import java.util.Arrays;

/**
 * Represents a football club, including its basic information, players, and club-specific attributes.
 * <p>
 * The {@code Club} class encapsulates all relevant data for a football club, such as its name,
 * code, nationality, stadium name, logo, foundation date, and an array of players.
 * It implements the {@link IClub} interface, allowing integration with the broader Football Manager API.
 * </p>
 * <p>
 * This class provides methods for managing club details and its roster of players.
 * </p>
 *
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@code name} - The club's name</li>
 *   <li>{@code code} - The club's unique code</li>
 *   <li>{@code clubNationality} - The nationality of the club</li>
 *   <li>{@code stadiumName} - The name of the club's stadium</li>
 *   <li>{@code clubLogo} - The path or URL to the club's logo</li>
 *   <li>{@code dateOfFoundation} - The year the club was founded</li>
 *   <li>{@code players} - The array of players belonging to the club</li>
 *   <li>{@code playerCount} - The current number of players in the club</li>
 *   <li>{@code isvalid} - Indicates if the club is valid for competition</li>
 * </ul>
 *
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 *
 * @see IClub
 * @see IPlayer
 * @see player.Player
 */
public class Club implements IClub {

    private String name;
    private String code;
    private String clubNationality;
    private String stadiumName;
    private String clubLogo;
    private int dateOfFoundation;
    private IPlayer[] players;
    private Player player;
    private int playerCount;
    private boolean isvalid;

    /**
     * Constructs a new {@code Club} instance with the specified attributes.
     *
     * @param name             the name of the club
     * @param code             the unique code identifying the club
     * @param clubNationality  the nationality of the club
     * @param stadiumName      the name of the club's stadium
     * @param clubLogo         the path or URL to the club's logo
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
     * Returns the name of the club.
     *
     * @return the club's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns a clone of the array of players belonging to the club.
     * <p>
     * Each player in the array is individually cloned to avoid exposing internal references.
     * If cloning fails for any player, an empty array is returned and an error message is printed.
     * </p>
     *
     * @return a cloned array of {@link IPlayer} objects representing the club's players,
     * or an empty array if cloning fails
     */
    @Override
    public IPlayer[] getPlayers() {
        IPlayer[] playersClone = new IPlayer[players.length];
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
     * Returns the unique code identifying the club.
     *
     * @return the club's code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Returns the country or nationality of the club.
     *
     * @return the club's nationality
     */
    @Override
    public String getCountry() {
        return clubNationality;
    }

    /**
     * Returns the year the club was founded.
     *
     * @return the club's foundation year
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
     * Returns the path or URL to the club's logo.
     *
     * @return the club logo
     */
    @Override
    public String getLogo() {
        return clubLogo;
    }

    /**
     * Adds a player to the club's roster.
     * <p>
     * This method checks for the following conditions before adding a player:
     * <ul>
     *   <li>The player is not {@code null}.</li>
     *   <li>The player is not already present in the club.</li>
     *   <li>There is space available in the club's player array.</li>
     * </ul>
     * If any condition fails, an appropriate exception is thrown.
     * </p>
     *
     * @param player the {@link IPlayer} to add to the club
     * @throws IllegalArgumentException if the player is {@code null} or already in the club
     * @throws IllegalStateException    if the club's player array is full
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
     * Checks if the given player is currently a member of the club.
     * <p>
     * Validates that the player and its essential properties are not {@code null}.
     * </p>
     *
     * @param player the {@link IPlayer} to check for membership in the club
     * @return {@code true} if the player is in the club; {@code false} otherwise
     * @throws IllegalArgumentException if the player or its essential properties are {@code null}
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
     * Removes the specified player from the club's roster.
     * <p>
     * If the player is not found in the club, an exception is thrown.
     * </p>
     *
     * @param player the {@link IPlayer} to remove from the club
     * @throws IllegalArgumentException if the player is {@code null} or not in the club
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
     * Returns the number of players currently in the club.
     *
     * @return the count of non-null players in the club's roster
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
     * Selects a player from the club using the provided selector and position.
     * <p>
     * Validates that the position is not {@code null} and that the club is not empty.
     * Delegates the selection logic to the given {@link IPlayerSelector}.
     * </p>
     *
     * @param selector the {@link IPlayerSelector} strategy to use for selecting a player
     * @param position the {@link IPlayerPosition} to match when selecting a player
     * @return the selected {@link IPlayer}, or {@code null} if no suitable player is found
     * @throws IllegalArgumentException if the position is {@code null}
     * @throws IllegalStateException    if the club has no players
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
     * Sets the club's player array and updates the player count.
     *
     * @param players the array of {@link Player} objects to set for the club
     */
    public void setPlayers(Player[] players) {
        this.players = players;
        int count = 0;
        for (Player p : players) {
            if (p != null) count++;
        }
        this.playerCount = count;
    }

    /**
     * Checks if the club instance is valid for competition.
     * <p>
     * Validation includes:
     * <ul>
     *   <li>All club attributes (name, code, nationality, stadium name, logo, and foundation year) must be set.</li>
     *   <li>The club must have at least 16 players.</li>
     *   <li>The club must have at least one player for each position: Goalkeeper, Defender, Midfielder, and Forward.</li>
     * </ul>
     * Throws an {@link IllegalStateException} if any validation rule fails.
     * </p>
     *
     * @return {@code true} if the club is valid; otherwise, an exception is thrown
     * @throws IllegalStateException if any club attribute is missing, there are not enough players,
     *                               or not all positions are filled
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
            throw new IllegalStateException("The club has no players in a specific position!");
        }

        boolean hasGoalkeeper = false;
        boolean hasDefender = false;
        boolean hasMidfielder = false;
        boolean hasForward = false;

        for (IPlayer p : players) {
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
        if (!hasGoalkeeper || !hasDefender || !hasMidfielder || !hasForward) {
            throw new IllegalStateException("The club doesn't have enough players for each position!");
        }
        return true;
    }

    /**
     * Checks if this club is equal to another object.
     * <p>
     * Two clubs are considered equal if their names are equal.
     * </p>
     *
     * @param obj the object to compare with
     * @return {@code true} if the given object is a {@code Club} with the same name; {@code false} otherwise
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
     * Exports the club's data to a JSON file.
     * <p>
     * This method is not yet implemented.
     * </p>
     *
     * @throws IOException if an I/O error occurs during export
     */
    @Override
    public void exportToJson() throws IOException {
        // TODO: Implement JSON export functionality
    }

    /**
     * Returns a string representation of the club, including all main attributes.
     *
     * @return a formatted string with the club's details
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
