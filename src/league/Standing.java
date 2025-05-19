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
    private int goalDifference;
    private ITeam team;

    public Standing(int points, int wins, int draws, int losses, int totalMatches, int goalsScored, int goalsConceded, int goalDifference, ITeam team) {
        this.points = points;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.totalMatches = totalMatches;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
        this.goalDifference = goalDifference;
        this.team = team;
    }


    @Override
    public ITeam getTeam() {
        if (team == null) {
            throw new IllegalStateException("Team not initialized");
        }
        return this.team;
    }

    @Override
    public int getPoints() {
        if (points == 0) {
            throw new IllegalStateException("Points not initialized");
        }
        return this.points;
    }

    @Override
    public void addPoints(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.points += i;
    }

    @Override
    public void addWin(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Wins cannot be negative");
        }
        this.wins += i;
        this.totalMatches += i;
        this.points += i * 3;
    }

    @Override
    public void addDraw(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Draws cannot be negative");
        }
        this.draws += i;
        this.totalMatches += i;
        this.points += i;
    }

    @Override
    public void addLoss(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Losses cannot be negative");
        }
        this.losses += i;
        this.totalMatches += i;
    }

    @Override
    public int getWins() {
        if (wins == 0) {
            throw new IllegalStateException("Wins not initialized");
        }
        return this.wins;
    }

    @Override
    public int getDraws() {
        if (draws == 0) {
            throw new IllegalStateException("Draws not initialized");
        }
        return this.draws;
    }

    @Override
    public int getLosses() {
        if (losses == 0) {
            throw new IllegalStateException("Losses not initialized");
        }
        return this.losses;
    }

    @Override
    public int getTotalMatches() {
        if (totalMatches == 0) {
            throw new IllegalStateException("Total Matches not initialized");
        }
        return this.totalMatches;
    }

    @Override
    public int getGoalScored() {
        if (goalsScored == 0) {
            throw new IllegalStateException("Goals Scored not initialized");
        }
        return this.goalsScored;
    }

    @Override
    public int getGoalsConceded() {
        if (goalsConceded == 0) {
            throw new IllegalStateException("Goals Conceded not initialized");
        }
        return this.goalsConceded;
    }

    @Override
    public int getGoalDifference() {
        if (goalDifference == 0) {
            throw new IllegalStateException("Goal Difference not initialized");
        }
        return this.goalDifference;
    }
}
