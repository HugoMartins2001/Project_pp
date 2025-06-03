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

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;

/**
 * Represents a football formation used by a team (excluding goalkeeper). A
 * valid formation must have exactly 10 outfield players.
 */
public class Formation implements IFormation {

    private String displayName;
    private int forwards;
    private int defenders;
    private int midfielders;

    /**
     * Constructs a Formation with specified roles and display name.
     *
     * @param displayName The name of the formation (e.g., "4-3-3")
     * @param defenders Number of defenders in the formation
     * @param midfielders Number of midfielders in the formation
     * @param forwards Number of forwards in the formation
     * @throws IllegalArgumentException if the sum is not exactly 10 players
     */
    public Formation(String displayName, int defenders, int midfielders, int forwards) {
        if (defenders + midfielders + forwards != 10) {
            throw new IllegalArgumentException("A formation must have exactly 10 players (excluding the goalkeeper).");
        }
        this.displayName = displayName;
        this.defenders = defenders;
        this.midfielders = midfielders;
        this.forwards = forwards;
    }

    /**
     * @return Number of defenders in the formation.
     */
    public int getDefenders() {
        return defenders;
    }

    /**
     * @return Number of midfielders in the formation.
     */
    public int getMidfielders() {
        return midfielders;
    }

    /**
     * @return Number of forwards in the formation.
     */
    public int getForwards() {
        return forwards;
    }

    /**
     * @return A string representation of the formation in the format "D-M-F".
     */
    public String toString() {
        return defenders + "-" + midfielders + "-" + forwards;
    }

    /**
     * Calculates the tactical advantage against another formation.
     *
     * @param formation The opponent's formation
     * @return 0 if equal, 1 if this formation has an advantage, -1 otherwise
     * @throws IllegalStateException if either formation is not defined
     */
    @Override
    public int getTacticalAdvantage(IFormation formation) {
        if (this.displayName == null || formation.getDisplayName() == null) {
            throw new IllegalStateException("Formations are not set!");
        }

        if (this.displayName.equals(formation.getDisplayName())) {
            return 0;
        }

        return calculateAdvantageTacticalValue((Formation) formation);
    }

    /**
     * Returns the number of players assigned to a specific position type.
     *
     * @param playerPosition The player's position
     * @return Number of players for that position in the formation
     */
    public int getPositionFormation(IPlayerPosition playerPosition) {
        String description = playerPosition.getDescription();

        if (description.equals("Defender")) {
            return this.defenders;
        } else if (description.equals("Midfielder")) {
            return this.midfielders;
        } else if (description.equals("Forward")) {
            return this.forwards;
        } else {
            return 1; // Default for other roles like goalkeeper
        }
    }

    /**
     * Internal method that compares this formation against another.
     *
     * @param formation The opponent's formation
     * @return 1 if this is stronger, -1 if weaker, 0 if balanced
     */
    private int calculateAdvantageTacticalValue(Formation formation) {
        int[] homeTeam = {this.forwards, this.midfielders, this.defenders};
        int[] awayTeam = {formation.getForwards(), formation.getMidfielders(), formation.getDefenders()};

        for (int i = 0; i < 3; i++) {
            if (homeTeam[i] > awayTeam[i]) {
                return 1;
            }
            if (homeTeam[i] < awayTeam[i]) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * @return The display name of the formation.
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }
}
