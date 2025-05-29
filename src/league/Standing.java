/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */
package league;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import event.GoalEvent;

/**
 * Represents the league standing (classification) data for a team.
 * Implements the {@link IStanding} interface.
 *
 * <p>This class tracks points, match outcomes (wins, draws, losses),
 * goals scored/conceded, and the team to which the standing belongs.</p>
 *
 * <p>All fields include flags to check whether they were properly initialized,
 * providing safer access with runtime validation.</p>
 *
 *
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
     * Constructs a fully initialized Standing object with statistics.
     *
     * @param points        Total points of the team.
     * @param wins          Number of wins.
     * @param draws         Number of draws.
     * @param losses        Number of losses.
     * @param totalMatches  Total number of matches played.
     * @param goalsScored   Goals scored by the team.
     * @param goalsConceded Goals conceded by the team.
     * @param team          The team associated with this standing.
     * @throws IllegalArgumentException if team is null or any numeric value is negative.
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
     * Constructs a new Standing object with all statistics set to zero.
     *
     * @param team The team to associate with this standing.
     * @throws IllegalArgumentException if team is null.
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
     * @return The {@link ITeam} instance representing the team.
     * @throws IllegalStateException if the team has not been initialized.
     */
    @Override
    public ITeam getTeam() {
        if (!teamInitialized) {
            throw new IllegalStateException("Team not initialized");
        }
        return this.team;
    }

    /**
     * Returns the number of points accumulated by the team.
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
     * Adds the specified number of points to the team's total.
     *
     * @param points the number of points to add
     * @throws IllegalArgumentException if the points value is negative
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
     * Registers a win for the team and updates the points and match statistics accordingly.
     *
     * @param pointsPerWin the number of points awarded for a win
     * @throws IllegalArgumentException if the pointsPerWin value is negative
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
     * Registers a draw for the team and updates the points and match statistics accordingly.
     *
     * @param pointsPerDraw the number of points awarded for a draw
     * @throws IllegalArgumentException if the pointsPerDraw value is negative
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
     * Registers a loss for the team and updates the match statistics accordingly.
     * Losses typically do not award points, but the logic allows for customization.
     *
     * @param pointsPerLoss the number of points awarded for a loss
     * @throws IllegalArgumentException if the pointsPerLoss value is negative
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
     * Returns the number of wins recorded by the team.
     *
     * @return the number of wins
     * @throws IllegalStateException if the wins have not been initialized
     */
    @Override
    public int getWins() {
        if (!winsInitialized) {
            throw new IllegalStateException("Wins not initialized");
        }
        return this.wins;
    }

    /**
     * Returns the number of draws recorded by the team.
     *
     * @return the number of draws
     * @throws IllegalStateException if the draw count has not been initialized
     */
    @Override
    public int getDraws() {
        if (!drawsInitialized) {
            throw new IllegalStateException("Draws not initialized");
        }
        return this.draws;
    }

    /**
     * Returns the number of losses recorded by the team.
     *
     * @return the number of losses
     * @throws IllegalStateException if the loss count has not been initialized
     */
    @Override
    public int getLosses() {
        if (!lossesInitialized) {
            throw new IllegalStateException("Losses not initialized");
        }
        return this.losses;
    }

    /**
     * Returns the total number of matches played by the team.
     *
     * @return the total matches played
     * @throws IllegalStateException if the match count has not been initialized
     */
    @Override
    public int getTotalMatches() {
        if (!totalMatchesInitialized) {
            throw new IllegalStateException("Total matches not initialized");
        }
        return this.totalMatches;
    }

    /**
     * Returns the total number of goals scored by the team.
     *
     * @return the number of goals scored
     * @throws IllegalStateException if the goals scored count has not been initialized
     */
    @Override
    public int getGoalScored() {
        if (!goalsScoredInitialized) {
            throw new IllegalStateException("Goals scored not initialized");
        }
        return this.goalsScored;
    }

    /**
     * Returns the total number of goals conceded by the team.
     *
     * @return the number of goals conceded
     * @throws IllegalStateException if the goals conceded count has not been initialized
     */
    @Override
    public int getGoalsConceded() {
        if (!goalsConcededInitialized) {
            throw new IllegalStateException("Goals conceded not initialized");
        }
        return this.goalsConceded;
    }

    /**
     * Calculates and returns the goal difference (goals scored minus goals conceded).
     *
     * @return the goal difference
     * @throws IllegalStateException if either the goals scored or goals conceded count has not been initialized
     */
    @Override
    public int getGoalDifference() {
        if (!goalsScoredInitialized || !goalsConcededInitialized) {
            throw new IllegalStateException("Goal difference not initialized");
        }
        return this.goalsScored - this.goalsConceded;
    }

    /**
     * Updates the standing statistics of this team based on the result of a given match.
     *
     * <p>This method increments goals scored and conceded, and updates the number of
     * wins, draws, or losses depending on the result. It assumes:
     * - 3 points are awarded for a win.
     * - 1 point is awarded for a draw.
     * - 0 points are awarded for a loss.</p>
     *
     * @param match the match to use for updating the standings
     * @throws IllegalArgumentException if the match is null
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