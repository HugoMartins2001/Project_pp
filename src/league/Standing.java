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
    private boolean pointsInitialized; // foi adicionado para evitar o erro de inicialização pois estava a mete a 0 e no inicio da época começas os clubes todos a 0 e ia dar erro
    private boolean winsInitialized;
    private boolean drawsInitialized;
    private boolean lossesInitialized;
    private boolean totalMatchesInitialized;
    private boolean goalsScoredInitialized;
    private boolean goalsConcededInitialized;
    private boolean goalDifferenceInitialized;
    private ITeam team;

    public Standing(int points, int wins, int draws, int losses, int totalMatches, int goalsScored, int goalsConceded, ITeam team) {
        this.points = points;
        this.pointsInitialized = true;
        this.wins = wins;
        this.winsInitialized = true;
        this.draws = draws;
        this.drawsInitialized = true;
        this.losses = losses;
        this.lossesInitialized = true;
        this.totalMatches = totalMatches;
        this.totalMatchesInitialized = true;
        this.goalsScored = goalsScored;
        this.goalsScoredInitialized = true;
        this.goalsConceded = goalsConceded;
        this.goalsConcededInitialized = true;
        this.goalDifference = this.goalsScored - this.goalsConceded;
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
        if (!pointsInitialized) {
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
            throw new IllegalStateException("Total Matches not initialized");
        }
        return this.totalMatches;
    }

    @Override
    public int getGoalScored() {
        if (!goalsScoredInitialized) {
            throw new IllegalStateException("Goals Scored not initialized");
        }
        return this.goalsScored;
    }

    @Override
    public int getGoalsConceded() {
        if (!goalsConcededInitialized) {
            throw new IllegalStateException("Goals Conceded not initialized");
        }
        return this.goalsConceded;
    }

    @Override
    public int getGoalDifference() {
        if (!goalDifferenceInitialized) {
            throw new IllegalStateException("Goal Difference not initialized");
        }
        return this.goalsScored - this.goalsConceded;
    }
}
