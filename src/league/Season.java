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

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import event.GoalEvent;
import match.Match;

import java.io.IOException;

public class Season implements ISeason {
    private String name;
    private int year;
    private int currentRound;
    private int maxTeams;
    private IClub[] clubs;
    private IMatch[] match;
    private ISchedule schedule;
    private MatchSimulatorStrategy matchSimulatorStrategy;
    private IStanding[] leagueStandings;
    private int numberOfTeams;

    public Season(String name, int year, int maxTeams) {
        this.name = name;
        this.year = year;
        this.maxTeams = maxTeams;
        this.clubs = new IClub[maxTeams];
        this.match = new IMatch[maxTeams];
        this.currentRound = 0;
        this.numberOfTeams = 0;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public boolean addClub(IClub club) {
        if (club == null) {
            throw new IllegalArgumentException("The club is null");
        }
        if (clubs.length >= maxTeams) {
            throw new IllegalArgumentException("The maximum number of teams has been reached");
        }
        for (IClub c : clubs) {
            if (c != null && c.getName().equals(club.getName())) {
                throw new IllegalArgumentException("The club already exists");
            }
        }

        clubs[numberOfTeams++] = club;
        System.out.println("Club added: " + clubs);
        return true;
    }

    @Override
    public boolean removeClub(IClub iClub) {
        if (iClub == null) {
            throw new IllegalArgumentException("The club is null");
        }

        for (int i = 0; i < numberOfTeams; i++) {
            if (clubs[i].getName().equals(iClub.getName())) {
                clubs[i] = null;
                for (int j = i; j < numberOfTeams - 1; j++) {
                    clubs[j] = clubs[j + 1];
                }
                clubs[numberOfTeams - 1] = null;
                numberOfTeams--;
                return true;
            }
        }
        throw new IllegalArgumentException("The club does not exist");
    }

    @Override
    public void generateSchedule() {

    }

    @Override
    public IMatch[] getMatches() {
        return match;
    }

    //nao sei se esta certo
    @Override
    public IMatch[] getMatches(int i) {
        for (i = 0; i < match.length; i++) {
            if (match[i].getRound() == i) {
                return match;
            }
        }
        throw new IllegalArgumentException("The match does not exist");
    }

    @Override
    public void simulateRound() {
        for (IMatch iMatch : match) {
            if (iMatch.getRound() == currentRound) {
                matchSimulatorStrategy.simulate(iMatch);
            }
        }
        currentRound++;
    }

    @Override
    public void simulateSeason() {
        while (!isSeasonComplete()) {
            simulateRound();
        }
    }

    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public boolean isSeasonComplete() {
        for (IMatch iMatch : match) {
            if (iMatch.getRound() != currentRound) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void resetSeason() {
        currentRound = 0;
        if (match != null) {
            for (IMatch match : match) {
                if (match != null && match.isPlayed()) {
                    ((Match) match).reset();
                }
            }
        }
    }

    @Override
    public String displayMatchResult(IMatch match) {
        String homeClub = match.getHomeClub().getName();
        String awayClub = match.getAwayClub().getName();
        int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
        int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
        String result = homeClub + " " + homeGoals + " - " + awayGoals + " " + awayClub;
        return result;
    }

    @Override
    public void setMatchSimulator(MatchSimulatorStrategy matchSimulatorStrategy) {
        this.matchSimulatorStrategy = matchSimulatorStrategy;
    }

    @Override
    public IStanding[] getLeagueStandings() {
        return leagueStandings;
    }

    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    @Override
    public int getPointsPerWin() {
        return 3;
    }

    @Override
    public int getPointsPerDraw() {
        return 1;
    }

    @Override
    public int getPointsPerLoss() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxTeams() {
        return maxTeams;
    }

    @Override
    public int getMaxRounds() {
        return (maxTeams - 1) / 2;
    }

    @Override
    public int getCurrentMatches() {
        int currentMatches = 0;
        for (IMatch iMatch : match) {
            if (iMatch.getRound() == currentRound) {
                currentMatches++;
            }
        }
        return currentMatches;
    }

    @Override
    public int getNumberOfCurrentTeams() {
        return numberOfTeams;
    }

    @Override
    public IClub[] getCurrentClubs() {
        IClub[] currentClubs = new IClub[numberOfTeams];
        for (int i = 0; i < numberOfTeams; i++) {
            currentClubs[i] = clubs[i];
        }
        return currentClubs;
    }

    @Override
    public void exportToJson() throws IOException {
        //TODO implementar exportToJson
    }
}
