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

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import event.*;
import match.Match;
import team.Club;
import team.Team;

import java.io.IOException;

public class Season implements ISeason {

    private String name;
    private int year;
    private int currentRound;
    private int maxTeams;
    private IClub[] clubs;
    private ISchedule schedule;
    private MatchSimulatorStrategy matchSimulatorStrategy;
    private IStanding[] leagueStandings;
    private int numberOfTeams;
    private boolean isManager;

    public Season(String name, int year, int maxTeams, boolean isManager) {
        this.name = name;
        this.year = year;
        this.maxTeams = maxTeams;
        this.clubs = new IClub[maxTeams];
        this.leagueStandings = new IStanding[maxTeams];
        this.currentRound = 0;
        this.numberOfTeams = 0;
        this.isManager = isManager;
    }

    public boolean isManager() {
        return isManager;
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
        if (numberOfTeams >= maxTeams) {
            throw new IllegalArgumentException("The maximum number of teams has been reached");
        }
        for (IClub c : clubs) {
            if (c != null && c.getName().equals(club.getName())) {
                throw new IllegalArgumentException("The club already exists");
            }
        }

        clubs[numberOfTeams++] = club;
        leagueStandings[numberOfTeams - 1] = new Standing(new Team(club));
        System.out.println("Club added: " + club.getName());
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
        throw new IllegalStateException("The club does not exist");
    }

    @Override
    public IMatch[] getMatches() {
        return schedule != null ? schedule.getAllMatches() : new IMatch[0];
    }


    @Override
    public IMatch[] getMatches(int round) {
        return schedule != null ? schedule.getMatchesForRound(round) : new IMatch[0];
    }

    @Override
    public void generateSchedule() {
        if (numberOfTeams < 2) {
            throw new IllegalStateException("Not enough teams to generate a schedule.");
        }

        if (schedule != null) {
            for (IMatch match : schedule.getAllMatches()) {
                if (match != null && match.isPlayed()) {
                    throw new IllegalStateException("A match was already played.");
                }
            }
        }

        int teamCount = numberOfTeams;
        boolean isOdd = (teamCount % 2 != 0);
        Club[] scheduleTeams;

        if (isOdd) {
            teamCount++;
            scheduleTeams = new Club[teamCount];
            for (int i = 0; i < numberOfTeams; i++) {
                scheduleTeams[i] = (Club) clubs[i];
            }
            scheduleTeams[teamCount - 1] = null;
        } else {
            scheduleTeams = new Club[teamCount];
            for (int i = 0; i < teamCount; i++) {
                scheduleTeams[i] = (Club) clubs[i];
            }
        }

        int roundsPerLeg = teamCount - 1;
        int totalRounds = roundsPerLeg * 2;
        int matchesPerRound = teamCount / 2;

        IMatch[][] allMatches = new IMatch[totalRounds][matchesPerRound];

        for (int round = 0; round < roundsPerLeg; round++) {
            for (int game = 0; game < matchesPerRound; game++) {
                int homeIndex = (round + game) % (teamCount - 1);
                int awayIndex = (teamCount - 1 - game + round) % (teamCount - 1);

                if (game == 0) awayIndex = teamCount - 1;

                Club home = scheduleTeams[homeIndex];
                Club away = scheduleTeams[awayIndex];

                if (!isOdd || (home != null && away != null)) {
                    allMatches[round][game] = new Match(home, away, round);
                    allMatches[round + roundsPerLeg][game] = new Match(away, home, round + roundsPerLeg);
                }
            }
        }

        this.schedule = new Schedule(allMatches);
    }
    @Override
    public void simulateRound() {
        if (matchSimulatorStrategy == null) {
            throw new IllegalStateException("Match simulator is not initialized.");
        }

        if (currentRound >= getMaxRounds()) {
            throw new IllegalStateException("Max rounds reached");
        }

        IMatch[] roundMatches = schedule.getMatchesForRound(currentRound);
        for (IMatch match : roundMatches) {
            if (!match.isPlayed()) {
                matchSimulatorStrategy.simulate(match);
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
        for (IMatch match : schedule.getAllMatches()) {
            if (!match.isPlayed()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void resetSeason() {
        currentRound = 0;
        for (IMatch match : schedule.getAllMatches()) {
            if (match.isPlayed()) {
                ((Match) match).reset();
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
        return schedule != null ? schedule.getNumberOfRounds() : 0;
    }

    @Override
    public int getCurrentMatches() {
        int count = 0;
        IMatch[] roundMatches = schedule.getMatchesForRound(currentRound);
        for (IMatch match : roundMatches) {
            if (!match.isPlayed()) count++;
        }
        return count;
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

    public void getPlayerStatistics(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        int cornerKicks = 0;
        int foul = 0;
        int yellowCards = 0;
        int redCards = 0;
        int substitutions = 0;
        int shotOnGoal = 0;
        int shot = 0;
        int penalties = 0;
        int offSide = 0;
        int injury = 0;
        int goals = 0;
        int freeKicks = 0;

        for(IMatch iMatch : schedule.getAllMatches()) {
            if(iMatch != null && iMatch.isPlayed()){
                for(IEvent event : iMatch.getEvents()) {
                    if(event != null && event instanceof PlayerEvent && ((PlayerEvent) event).getPlayer().equals(player)) {
                        if (event instanceof GoalEvent) {
                            goals++;
                        } else if (event instanceof CornerKickEvent) {
                            cornerKicks++;
                        } else if (event instanceof FoulEvent) {
                            foul++;
                        } else if (event instanceof YellowCardEvent) {
                            yellowCards++;
                        } else if (event instanceof RedCardEvent) {
                            redCards++;
                        }  else if (event instanceof ShotOnGoalEvent) {
                            shotOnGoal++;
                        } else if (event instanceof ShotEvent) {
                            shot++;
                        } else if (event instanceof PenaltiesEvent) {
                            penalties++;
                        } else if (event instanceof OffSideEvent) {
                            offSide++;
                        } else if (event instanceof InjuryEvent) {
                            injury++;
                        } else if (event instanceof FreeKickEvent) {
                            freeKicks++;
                        }
                    } else if(event != null && event instanceof SubstitutionEvent) {
                        SubstitutionEvent substitutionEvent = (SubstitutionEvent) event;
                        if (substitutionEvent.getPlayerIn().equals(player) || substitutionEvent.getPlayerOut().equals(player)) {
                            substitutions++;
                        }
                    }
                }
            }
        }

        String playerStats = String.format(
                "Player: %-20s%n" +
                        "Number: %-10d%n" +
                        "Goals: %-10d Corner Kicks: %-10d%n" +
                        "Fouls: %-10d Yellow Cards: %-10d%n" +
                        "Red Cards: %-10d Substitutions: %-10d%n" +
                        "Shots on Goal: %-10d Shots: %-10d%n" +
                        "Penalties: %-10d Offside: %-10d%n" +
                        "Injuries: %-10d Free Kicks: %-10d",
                player.getName(), player.getNumber(),
                goals, cornerKicks,
                foul, yellowCards,
                redCards, substitutions,
                shotOnGoal, shot,
                penalties, offSide,
                injury, freeKicks
        );
        System.out.println(playerStats);
    }

    public void printSchedule() {
        if (schedule == null) {
            System.out.println("No matches scheduled.");
            return;
        }

        for (int i = 0; i < schedule.getNumberOfRounds(); i++) {
            System.out.println("Round " + i);
            for (IMatch iMatch : schedule.getMatchesForRound(i)) {
                System.out.println(iMatch.getHomeClub().getName() + " vs " + iMatch.getAwayClub().getName() + " - Round: " + iMatch.getRound());
            }
        }
    }

    @Override
    public void exportToJson() throws IOException {
        //TODO implementar exportToJson
    }

    public void setShedule(ISchedule schedule) {
        this.schedule = schedule;
    }

    public void setStandings(IStanding[] standings) {
        this.leagueStandings = standings;
    }

}
