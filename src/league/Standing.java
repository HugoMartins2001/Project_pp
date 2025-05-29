package league;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import event.GoalEvent;

/**
 * Represents the league standing for a football team.
 * <p>
 * The {@code Standing} class tracks all relevant statistics for a team in a league, including:
 * <ul>
 *   <li>Points</li>
 *   <li>Wins</li>
 *   <li>Draws</li>
 *   <li>Losses</li>
 *   <li>Total matches played</li>
 *   <li>Goals scored</li>
 *   <li>Goals conceded</li>
 *   <li>The associated team</li>
 * </ul>
 * It also maintains initialization flags for each field to ensure data integrity and proper initialization.
 * </p>
 * <p>
 * This class implements the {@link IStanding} interface, making it compatible with the Football Manager API.
 * </p>
 *
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 *
 * @see IStanding
 * @see ITeam
 * @see IMatch
 * @see GoalEvent
 */
public class Standing implements IStanding {
    private int points;
    private int wins;
    private int draws;
    private int losses;
    private int totalMatches;
    private int goalsScored;
    private int goalsConceded;
    private ITeam team;
    private boolean pointsInitialized;
    private boolean winsInitialized;
    private boolean drawsInitialized;
    private boolean lossesInitialized;
    private boolean totalMatchesInitialized;
    private boolean goalsScoredInitialized;
    private boolean goalsConcededInitialized;
    private boolean teamInitialized;

    /**
     * Constructs a new {@code Standing} object with the specified statistics and associated team.
     * <p>
     * All statistical values must be non-negative and the team must not be {@code null}.
     * If any validation fails, an {@link IllegalArgumentException} is thrown.
     * All initialization flags are set to {@code true} upon successful construction.
     * </p>
     *
     * @param points        the number of points the team has earned
     * @param wins          the number of matches won by the team
     * @param draws         the number of matches drawn by the team
     * @param losses        the number of matches lost by the team
     * @param totalMatches  the total number of matches played by the team
     * @param goalsScored   the total number of goals scored by the team
     * @param goalsConceded the total number of goals conceded by the team
     * @param team          the {@link ITeam} associated with this standing
     * @throws IllegalArgumentException if the team is {@code null} or any statistical value is negative
     */
    public Standing(int points, int wins, int draws, int losses, int totalMatches, int goalsScored, int goalsConceded, ITeam team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        if (points < 0 || wins < 0 || draws < 0 || losses < 0 || totalMatches < 0 || goalsScored < 0 || goalsConceded < 0) {
            throw new IllegalArgumentException("Statistics values cannot be negative");
        }
        this.points = points;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.totalMatches = totalMatches;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
        this.team = team;
        this.pointsInitialized = true;
        this.winsInitialized = true;
        this.drawsInitialized = true;
        this.lossesInitialized = true;
        this.totalMatchesInitialized = true;
        this.goalsScoredInitialized = true;
        this.goalsConcededInitialized = true;
        this.teamInitialized = true;
    }

    /**
     * Constructs a new {@code Standing} object for the specified team with all statistics initialized to zero.
     * <p>
     * The team must not be {@code null}. All initialization flags are set to {@code true} upon successful construction.
     * </p>
     *
     * @param team the {@link ITeam} associated with this standing
     * @throws IllegalArgumentException if the team is {@code null}
     */
    public Standing(ITeam team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        this.points = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.totalMatches = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
        this.team = team;
        this.pointsInitialized = true;
        this.winsInitialized = true;
        this.drawsInitialized = true;
        this.lossesInitialized = true;
        this.totalMatchesInitialized = true;
        this.goalsScoredInitialized = true;
        this.goalsConcededInitialized = true;
        this.teamInitialized = true;
    }

    /**
     * Returns the team associated with this standing.
     *
     * @return the {@link ITeam} for this standing
     * @throws IllegalStateException if the team has not been initialized
     */
    @Override
    public ITeam getTeam() {
        if (!teamInitialized) {
            throw new IllegalStateException("Team not initialized");
        }
        return this.team;
    }

    /**
     * Returns the number of points for this standing.
     *
     * @return the current points
     * @throws IllegalStateException if the points have not been initialized
     */
    @Override
    public int getPoints() {
        if (!pointsInitialized) {
            throw new IllegalStateException("Points not initialized");
        }
        return this.points;
    }

    /**
     * Adds the specified number of points to the current total.
     * <p>
     * Points must be non-negative. If the value is negative, an exception is thrown.
     * The {@code pointsInitialized} flag is set to {@code true} after successful addition.
     * </p>
     *
     * @param points the number of points to add
     * @throws IllegalArgumentException if {@code points} is negative
     */
    @Override
    public void addPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.points += points;
        this.pointsInitialized = true;
    }

    /**
     * Increments the win count, total matches, and adds points for a win.
     * <p>
     * The number of points awarded for a win must be non-negative.
     * All relevant initialization flags are updated after the operation.
     * </p>
     *
     * @param pointsPerWin the number of points to add for a win
     * @throws IllegalArgumentException if {@code pointsPerWin} is negative
     */
    @Override
    public void addWin(int pointsPerWin) {
        if (pointsPerWin < 0) {
            throw new IllegalArgumentException("Wins cannot give negative points");
        }
        this.wins += 1;
        this.totalMatches += 1;
        this.points += pointsPerWin;
        this.winsInitialized = true;
        this.totalMatchesInitialized = true;
        this.pointsInitialized = true;
    }

    /**
     * Increments the draw count, total matches, and adds points for a draw.
     * <p>
     * The number of points awarded for a draw must be non-negative.
     * All relevant initialization flags are updated after the operation.
     * </p>
     *
     * @param pointsPerDraw the number of points to add for a draw
     * @throws IllegalArgumentException if {@code pointsPerDraw} is negative
     */
    @Override
    public void addDraw(int pointsPerDraw) {
        if (pointsPerDraw < 0) {
            throw new IllegalArgumentException("Draws cannot be negative");
        }
        this.draws += 1;
        this.totalMatches += 1;
        this.points += pointsPerDraw;
        this.drawsInitialized = true;
        this.totalMatchesInitialized = true;
        this.pointsInitialized = true;
    }

    /**
     * Increments the loss count, total matches, and adds points for a loss.
     * <p>
     * The number of points awarded for a loss must be non-negative.
     * All relevant initialization flags are updated after the operation.
     * </p>
     *
     * @param pointsPerLoss the number of points to add for a loss
     * @throws IllegalArgumentException if {@code pointsPerLoss} is negative
     */
    @Override
    public void addLoss(int pointsPerLoss) {
        if (pointsPerLoss < 0) {
            throw new IllegalArgumentException("Losses cannot be negative");
        }
        this.losses += 1;
        this.totalMatches += 1;
        this.points += pointsPerLoss;
        this.lossesInitialized = true;
        this.totalMatchesInitialized = true;
        this.pointsInitialized = true;
    }


    /**
     * Returns the number of wins for this standing.
     *
     * @return the number of wins
     * @throws IllegalStateException if the wins value has not been initialized
     */
    @Override
    public int getWins() {
        if (!winsInitialized) {
            throw new IllegalStateException("Wins not initialized");
        }
        return this.wins;
    }

    /**
     * Returns the number of draws for this standing.
     *
     * @return the number of draws
     * @throws IllegalStateException if the draws value has not been initialized
     */
    @Override
    public int getDraws() {
        if (!drawsInitialized) {
            throw new IllegalStateException("Draws not initialized");
        }
        return this.draws;
    }

    /**
     * Returns the number of losses for this standing.
     *
     * @return the number of losses
     * @throws IllegalStateException if the losses value has not been initialized
     */
    @Override
    public int getLosses() {
        if (!lossesInitialized) {
            throw new IllegalStateException("Losses not initialized");
        }
        return this.losses;
    }

    /**
     * Returns the total number of matches played for this standing.
     *
     * @return the total number of matches played
     * @throws IllegalStateException if the total matches value has not been initialized
     */
    @Override
    public int getTotalMatches() {
        if (!totalMatchesInitialized) {
            throw new IllegalStateException("Total matches not initialized");
        }
        return this.totalMatches;
    }

    /**
     * Returns the total number of goals scored for this standing.
     *
     * @return the total number of goals scored
     * @throws IllegalStateException if the goals scored value has not been initialized
     */
    @Override
    public int getGoalScored() {
        if (!goalsScoredInitialized) {
            throw new IllegalStateException("Goals scored not initialized");
        }
        return this.goalsScored;
    }


    /**
     * Returns the total number of goals conceded for this standing.
     *
     * @return the total number of goals conceded
     * @throws IllegalStateException if the goals conceded value has not been initialized
     */
    @Override
    public int getGoalsConceded() {
        if (!goalsConcededInitialized) {
            throw new IllegalStateException("Goals conceded not initialized");
        }
        return this.goalsConceded;
    }

    /**
     * Returns the goal difference (goals scored minus goals conceded) for this standing.
     *
     * @return the goal difference
     * @throws IllegalStateException if either the goals scored or goals conceded value has not been initialized
     */
    @Override
    public int getGoalDifference() {
        if (!goalsScoredInitialized || !goalsConcededInitialized) {
            throw new IllegalStateException("Goal difference not initialized");
        }
        return this.goalsScored - this.goalsConceded;
    }

    /**
     * Updates the team's standing based on the result of the specified match.
     * <p>
     * This method updates goals scored, goals conceded, and match results (win, draw, loss)
     * for the team associated with this standing. It determines whether the team was the
     * home or away team and applies the appropriate updates.
     * </p>
     *
     * @param match the {@link IMatch} whose result is used to update the standings
     * @throws IllegalArgumentException if {@code match} is {@code null}
     * @throws IllegalStateException    if the match has not been played yet
     */
    public void updateStandings(IMatch match) {
        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        if (!match.isPlayed()) {
            throw new IllegalStateException("Match has not been played yet");
        }

        ITeam homeTeam = match.getHomeTeam();
        ITeam awayTeam = match.getAwayTeam();

        int homeGoals = match.getTotalByEvent(GoalEvent.class, homeTeam.getClub());
        int awayGoals = match.getTotalByEvent(GoalEvent.class, awayTeam.getClub());

        if (homeTeam.equals(this.team)) {
            this.goalsScored += homeGoals;
            this.goalsConceded += awayGoals;
            if (homeGoals > awayGoals) {
                addWin(3);
            } else if (homeGoals < awayGoals) {
                addLoss(0);
            } else {
                addDraw(1);
            }
        } else if (awayTeam.equals(this.team)) {
            this.goalsScored += awayGoals;
            this.goalsConceded += homeGoals;
            if (awayGoals > homeGoals) {
                addWin(3);
            } else if (awayGoals < homeGoals) {
                addLoss(0);
            } else {
                addDraw(1);
            }
        }
    }

}