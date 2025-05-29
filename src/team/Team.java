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
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import player.Player;

import java.io.IOException;

/**
 * Represents a football team, including its associated club, tactical formation, and selected players.
 * <p>
 * The {@code Team} class encapsulates the structure of a team for a match or season,
 * holding references to the club, the formation being used, and the array of players
 * chosen for the team. It implements the {@link ITeam} interface for compatibility
 * with the Football Manager API.
 * </p>
 *
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@code club} - The {@link IClub} associated with this team</li>
 *   <li>{@code formation} - The {@link IFormation} used by the team</li>
 *   <li>{@code playersTeam} - The array of {@link IPlayer} objects selected for the team</li>
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
 * @see ITeam
 * @see IClub
 * @see IFormation
 * @see IPlayer
 */
public class Team implements ITeam {

    private IClub club;
    private IFormation formation;
    private IPlayer[] playersTeam;

    /**
     * Constructs a new {@code Team} instance for the specified club.
     * <p>
     * The team is initialized without a formation or player lineup.
     * </p>
     *
     * @param club the {@link IClub} associated with this team
     * @throws IllegalArgumentException if the club is {@code null}
     */
    public Team(IClub club) {
        if (club == null) {
            throw new IllegalArgumentException("The club is not defined!");
        }
        this.club = club;
        this.formation = null;
        this.playersTeam = null;
    }

    /**
     * Returns the club associated with this team.
     *
     * @return the {@link IClub} for this team
     */
    @Override
    public IClub getClub() {
        return club;
    }

    /**
     * Returns the formation used by this team.
     *
     * @return the {@link IFormation} for this team
     * @throws IllegalStateException if the formation is not set
     */
    @Override
    public IFormation getFormation() {
        if (formation == null) {
            throw new IllegalStateException("The formation is not setted!");
        }
        return formation;
    }

    /**
     * Returns a clone of the array of players selected for this team.
     * <p>
     * Each player in the array is individually cloned to avoid exposing internal references.
     * If cloning fails for any player, an empty array is returned and an error message is printed.
     * </p>
     *
     * @return a cloned array of {@link IPlayer} objects representing the team's players,
     * or an empty array if cloning fails
     * @throws IllegalStateException if the team's player array is not set
     */
    @Override
    public IPlayer[] getPlayers() {
        if (playersTeam == null) {
            throw new IllegalStateException("There are no players in the team!");
        }
        IPlayer[] playersClone = new IPlayer[playersTeam.length];
        try {
            for (int i = 0; i < playersTeam.length; i++) {
                if (playersTeam[i] != null) {
                    playersClone[i] = ((Player) playersTeam[i]).clone();
                }
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Error while cloning players!");
            return new IPlayer[0];
        }
        return playersClone;
    }

    /**
     * Adds a player to the team's lineup.
     * <p>
     * This method performs several checks before adding the player:
     * <ul>
     *   <li>There must not be too many players for the player's position (according to the formation).</li>
     *   <li>The player must not be {@code null}.</li>
     *   <li>The player must not already be in the team.</li>
     *   <li>There must be space available in the team's player array.</li>
     *   <li>The player must belong to the associated club.</li>
     *   <li>The team's formation must be set.</li>
     * </ul>
     * If any condition fails, an appropriate exception is thrown.
     * </p>
     *
     * @param player the {@link IPlayer} to add to the team
     * @throws IllegalArgumentException if the player is {@code null}
     * @throws IllegalStateException    if the formation is not set, the player is already in the team,
     *                                  the player is not in the club, the team is full,
     *                                  or there are too many players for the player's position
     */
    @Override
    public void addPlayer(IPlayer player) {
        if (!isValidPositionForFormation(player.getPosition())) {
            throw new IllegalStateException("Too many players for position ");
        }
        if (player == null) {
            throw new IllegalArgumentException("The player is not selected!");
        }
        for (IPlayer p : playersTeam) {
            if (p != null && p.equals(player)) {
                throw new IllegalStateException("The player is already in the team!");
            }
        }
        boolean hasSpace = false;
        for (IPlayer p : playersTeam) {
            if (p == null) {
                hasSpace = true;
                break;
            }
        }
        if (!hasSpace) {
            throw new IllegalStateException("The team is already full!");
        }
        if (!club.isPlayer(player)) {
            throw new IllegalStateException("The player is not in the club!");
        }
        if (formation == null) {
            throw new IllegalStateException("The formation is not setted!");
        }
        for (int i = 0; i < playersTeam.length; i++) {
            if (playersTeam[i] == null) {
                playersTeam[i] = player;
                break;
            }
        }
    }

    /**
     * Returns the number of players in the team for the specified position.
     *
     * @param position the {@link IPlayerPosition} to count
     * @return the number of players in the team for the given position
     * @throws IllegalArgumentException if the position is {@code null}
     */
    @Override
    public int getPositionCount(IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("The position is not defined!");
        }
        int counter = 0;
        for (IPlayer p : playersTeam) {
            if (p != null && p.getPosition().equals(position)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Checks if there is still room for another player in the given position according to the team's formation.
     *
     * @param position the {@link IPlayerPosition} to check
     * @return {@code true} if there is room for another player in the specified position; {@code false} otherwise
     * @throws IllegalArgumentException if the position is {@code null}
     * @throws IllegalStateException    if the formation is not set
     */
    @Override
    public boolean isValidPositionForFormation(IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("The position is not defined!");
        }
        if (formation == null) {
            throw new IllegalStateException("The formation is not setted!");
        }
        if (((Formation) this.formation).getPositionFormation(position) >= getPositionCount(position)) {
            return true;
        }
        return false;
    }


    /**
     * Calculates and returns the average strength of the team based on its players' attributes.
     * <p>
     * The strength for each player is calculated as the average of their shooting, passing, stamina, and speed.
     * The team's strength is the average of all players' strengths.
     * </p>
     *
     * @return the average strength of the team
     * @throws IllegalStateException if there are no players in the team
     */
    @Override
    public int getTeamStrength() {
        if (playersTeam == null) {
            throw new IllegalStateException("There is no player in the team!");
        }
        int teamStrength = 0;
        int playerCount = 0;
        for (IPlayer p : playersTeam) {
            if (p != null) {
                int playerAverage = (p.getShooting() + p.getPassing() + p.getStamina() + p.getSpeed()) / 4;
                teamStrength += playerAverage;
                playerCount++;
            }
        }
        if (playerCount == 0) {
            throw new IllegalStateException("There is no player in the team!");
        }
        return teamStrength / playerCount;
    }

    /**
     * Sets the team's tactical formation and initializes the player array to 11 positions.
     *
     * @param formation the {@link IFormation} to set for the team
     * @throws IllegalArgumentException if the formation is {@code null}
     */
    @Override
    public void setFormation(IFormation formation) {
        if (formation == null) {
            throw new IllegalArgumentException("The formation is not setted!");
        }
        this.formation = formation;
        this.playersTeam = new IPlayer[11];
    }

    /**
     * Automatically selects and assigns players to the team based on the given formation.
     * <p>
     * Ensures that the correct number of players for each position (goalkeeper, defender, midfielder, forward)
     * are added to the team according to the formation's requirements.
     * </p>
     *
     * @param players   the array of {@link IPlayer} objects to choose from
     * @param formation the {@link Formation} to use for selecting players
     * @throws IllegalArgumentException if either {@code players} or {@code formation} is {@code null}
     */
    public void setAutomaticTeam(IPlayer[] players, Formation formation) {
        if (players == null || formation == null) {
            throw new IllegalArgumentException("Players and formation must be set!");
        }
        int keepercount = 0;
        int defcount = 0;
        int midcount = 0;
        int forcount = 0;

        for (IPlayer player : players) {
            if (player == null || player.getPosition() == null) {
                continue;
            }

            String pos = player.getPosition().getDescription();
            if (pos.equals("Goalkeeper") && keepercount < 1) {
                addPlayer(player);
                keepercount++;
            } else if (pos.equals("Defender") && defcount < formation.getDefenders()) {
                addPlayer(player);
                defcount++;
            } else if (pos.equals("Midfielder") && midcount < formation.getMidfielders()) {
                addPlayer(player);
                midcount++;
            } else if (pos.equals("Forward") && forcount < formation.getForwards()) {
                addPlayer(player);
                forcount++;
            }
        }
    }

    /**
     * Manually sets the team's player array.
     *
     * @param playersTeam the array of {@link IPlayer} objects to assign to the team
     * @throws IllegalArgumentException if {@code playersTeam} is {@code null} or does not contain exactly 11 players
     */
    public void setManualTeam(IPlayer[] playersTeam) {
        if (playersTeam == null || playersTeam.length != 11) {
            throw new IllegalArgumentException("The players team must have 11 players!");
        }
        this.playersTeam = playersTeam;
    }

    /**
     * Checks if this team is equal to another object.
     * <p>
     * Two teams are considered equal if their clubs are equal.
     * </p>
     *
     * @param obj the object to compare with
     * @return {@code true} if the given object is a {@code Team} with the same club; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Team)) {
            return false;
        }
        Team team = (Team) obj;
        return club.equals(team.club);
    }

    /**
     * Exports the team's data to a JSON file.
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


}
