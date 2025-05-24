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
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

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


    @Override
    public ITeam getTeam() {
        if (!teamInitialized) {
            throw new IllegalStateException("Team not initialized");
        }
        return this.team;
    }


    @Override
    public int getPoints() {
        if (!pointsInitialized) {
            throw new IllegalStateException("Points not initialized");
        }
        return this.points;
    }

    @Override
    public void addPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.points += points;
        this.pointsInitialized = true;
    }

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

    @Override
    public int getWins() {
        if (!winsInitialized) {
            throw new IllegalStateException("Wins not initialized");
        }
        return this.wins;
    }

    @Override
    public int getDraws() {
        if (!drawsInitialized) {
            throw new IllegalStateException("Draws not initialized");
        }
        return this.draws;
    }

    @Override
    public int getLosses() {
        if (!lossesInitialized) {
            throw new IllegalStateException("Losses not initialized");
        }
        return this.losses;
    }

    @Override
    public int getTotalMatches() {
        if (!totalMatchesInitialized) {
            throw new IllegalStateException("Total matches not initialized");
        }
        return this.totalMatches;
    }

    @Override
    public int getGoalScored() {
        if (!goalsScoredInitialized) {
            throw new IllegalStateException("Goals scored not initialized");
        }
        return this.goalsScored;
    }

    @Override
    public int getGoalsConceded() {
        if (!goalsConcededInitialized) {
            throw new IllegalStateException("Goals conceded not initialized");
        }
        return this.goalsConceded;
    }

    @Override
    public int getGoalDifference() {
        if (!goalsScoredInitialized || !goalsConcededInitialized) {
            throw new IllegalStateException("Goal difference not initialized");
        }
        return this.goalsScored - this.goalsConceded;
    }
}