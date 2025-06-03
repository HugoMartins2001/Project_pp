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
 * Represents a football team, encapsulating its club, formation, and players.
 * Implements the {@link ITeam} interface.
 *
 *
 * This class provides methods to manage players, check positions, and calculate
 * team strength.
 *
 * @author Rúben Tiago Martins Pereira
 * @author Hugo Leite Martins
 */
public class Team implements ITeam {

    private IClub club;
    private IFormation formation;
    private IPlayer[] playersTeam;
    private int currentPlayers = 0;

    /**
     * Constructs a new Team with the specified club, formation, and players.
     *
     * @param club The club associated with the team.
     * @param formation The formation of the team.
     * @param playersTeam An array of players in the team.
     */
    public Team(IClub club, IFormation formation, IPlayer[] playersTeam) {
        this.club = club;
        this.formation = formation;
        this.playersTeam = new IPlayer[0];
        for (int i = 0; i < playersTeam.length; i++) {
            if (playersTeam[i] != null) {
                this.addPlayer(playersTeam[i]);
            }
        }
    }

    /**
     * Constructs a new Team with the specified club. The formation and players
     * are initialized to null or empty.
     *
     * @param club The club associated with the team.
     * @throws IllegalArgumentException If the club is null.
     */
    public Team(IClub club) {
        if (club == null) {
            throw new IllegalArgumentException("The club is not defined!");
        }
        this.club = club;
        this.formation = null;
        this.playersTeam = new IPlayer[0];
    }

    /**
     * @return The club the team belongs to.
     */
    @Override
    public IClub getClub() {
        return club;
    }

    /**
     * @return The tactical formation of the team.
     * @throws IllegalStateException if the formation is not set.
     */
    @Override
    public IFormation getFormation() {
        if (formation == null) {
            throw new IllegalStateException("The formation is not setted!");
        }
        return formation;
    }

    /**
     * @return A clone of the array of players in the team.
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
     * Adds a player to the team, ensuring formation limits and club membership.
     *
     * @param player The player to add.
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

        if (!club.isPlayer(player)) {
            throw new IllegalStateException("The player is not in the club!");
        }
        if (formation == null) {
            throw new IllegalStateException("The formation is not setted!");
        }
        expandArray();
        this.playersTeam[currentPlayers++] = player;
    }

    /**
     * Returns the count of players in the team for a given position.
     *
     * @param position The position to count.
     * @return The number of players in that position.
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
     * Checks if a new player can be added to a specific position according to
     * formation.
     *
     * @param position The player's position.
     * @return True if allowed, false otherwise.
     */
    @Override
    public boolean isValidPositionForFormation(IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("The position is not defined!");
        }
        if (formation == null) {
            throw new IllegalStateException("The formation is not setted!");
        }
        return ((Formation) this.formation).getPositionFormation(position) >= getPositionCount(position);
    }

    /**
     * Calculates the average strength of the team based on player attributes.
     *
     * @return The team strength.
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
     * Sets the team's formation and resets the players array to fit.
     *
     * @param formation The new formation.
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
     * Automatically sets a valid team from a list of players using the given
     * formation.
     *
     * @param players List of players.
     * @param formation Desired formation.
     */
    public void setAutomaticTeam(IPlayer[] players, Formation formation) {
        if (players == null || formation == null) {
            throw new IllegalArgumentException("Players and formation must be set!");
        }
        int keepercount = 0, defcount = 0, midcount = 0, forcount = 0;
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
     * Sets the team manually with a fixed list of 11 players.
     *
     * @param playersTeam The fixed team.
     */
    public void setManualTeam(IPlayer[] playersTeam) {
        if (playersTeam == null || playersTeam.length != 11) {
            throw new IllegalArgumentException("The players team must have 11 players!");
        }
        this.playersTeam = playersTeam;
    }

    private void expandArray() {
        IPlayer[] newArray = new IPlayer[playersTeam.length + 1];
        System.arraycopy(playersTeam, 0, newArray, 0, playersTeam.length);
        playersTeam = newArray;
    }


    /**
     * Checks if another team is the same based on the club.
     *
     * @param obj Another object.
     * @return True if both teams belong to the same club.
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
}
