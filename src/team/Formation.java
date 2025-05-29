package team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;

/**
 * Represents a football team formation, specifying the number of defenders, midfielders, and forwards.
 * <p>
 * The {@code Formation} class encapsulates the tactical arrangement of players on the field,
 * including a display name and the counts of players in each outfield position. It implements
 * the {@link IFormation} interface, allowing for integration with the Football Manager API.
 * </p>
 *
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@code displayName} - The human-readable name of the formation (e.g., "4-4-2")</li>
 *   <li>{@code defenders} - The number of defenders in the formation</li>
 *   <li>{@code midfielders} - The number of midfielders in the formation</li>
 *   <li>{@code forwards} - The number of forwards in the formation</li>
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
 * @see IFormation
 * @see IPlayerPosition
 */
public class Formation implements IFormation {

    private String displayName;
    private int forwards;
    private int defenders;
    private int midfielders;

    /**
     * Constructs a new {@code Formation} instance with the specified numbers of defenders, midfielders, and forwards.
     * <p>
     * The total number of outfield players (defenders + midfielders + forwards) must be exactly 10,
     * as the goalkeeper is not included in this count. If the condition is not met, an exception is thrown.
     * </p>
     *
     * @param displayName the display name of the formation (e.g., "4-4-2")
     * @param defenders   the number of defenders in the formation
     * @param midfielders the number of midfielders in the formation
     * @param forwards    the number of forwards in the formation
     * @throws IllegalArgumentException if the total number of outfield players is not exactly 10
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
     * Returns the number of defenders in the formation.
     *
     * @return the number of defenders
     */
    public int getDefenders() {
        return defenders;
    }

    /**
     * Returns the number of midfielders in the formation.
     *
     * @return the number of midfielders
     */
    public int getMidfielders() {
        return midfielders;
    }

    /**
     * Returns the number of forwards in the formation.
     *
     * @return the number of forwards
     */
    public int getForwards() {
        return forwards;
    }

    /**
     * Calculates the tactical advantage of this formation against another formation.
     * <p>
     * Returns:
     * <ul>
     *   <li>0 if both formations have the same display name (i.e., they are tactically identical)</li>
     *   <li>A value computed by {@link #calculateAdvantageTacticalValue(Formation)} otherwise</li>
     * </ul>
     * Throws an exception if either formation's display name is not set.
     * </p>
     *
     * @param formation the opposing {@link IFormation} to compare against
     * @return an integer indicating the tactical advantage:
     * 1 if this formation is superior, -1 if inferior, 0 if equal
     * @throws IllegalStateException if either formation's display name is {@code null}
     */
    @Override
    public int getTacticalAdvantage(IFormation formation) {
        if (this.displayName == null || formation.getDisplayName() == null) {
            throw new IllegalStateException("Formations are not setted!");
        }

        if (this.displayName.equals(formation.getDisplayName())) {
            return 0;
        }

        return calculateAdvantageTacticalValue((Formation) formation);
    }

    /**
     * Returns the number of players in this formation for the given position.
     *
     * @param playerPosition the {@link IPlayerPosition} to query
     * @return the number of players in the specified position; returns 1 for unrecognized positions
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
            return 1;
        }
    }

    /**
     * Compares this formation to another and determines tactical superiority based on
     * the number of forwards, midfielders, and defenders.
     * <p>
     * Returns 1 if this formation has more players in a position than the other,
     * -1 if fewer, or 0 if they are equal in all positions.
     * </p>
     *
     * @param formation the {@link Formation} to compare against
     * @return 1 if this formation is superior, -1 if inferior, 0 if equal
     */
    private int calculateAdvantageTacticalValue(Formation formation) {
        int[] homeTeam = {this.forwards, this.midfielders, this.defenders};
        int[] awayTeam = {formation.getForwards(), formation.getMidfielders(), formation.getDefenders()};

        for (int i = 0; i < 3; i++) {
            if (homeTeam[i] > awayTeam[i]) return 1;
            if (homeTeam[i] < awayTeam[i]) return -1;
        }
        return 0;
    }

    /**
     * Returns the display name of the formation (e.g., "4-4-2").
     *
     * @return the formation's display name
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }

}