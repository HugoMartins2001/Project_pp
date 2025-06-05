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

/**
 * Represents a football season within a league. A season consists of multiple
 * clubs, a match schedule, and associated league standings. Supports
 * manager-controlled seasons, simulation of matches, and player statistics.
 * Implements the {@link ISeason} interface.
 * <p>
 * <p>
 * Each season tracks the current round, supports schedule generation, and
 * allows integration with a match simulation strategy.
 */
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

    /**
     * Constructs a new Season with the given parameters.
     *
     * @param name      The name of the season.
     * @param year      The year of the season.
     * @param maxTeams  The maximum number of teams allowed.
     * @param isManager True if the season is controlled by a manager, false
     *                  otherwise.
     */
    public Season(String name, int year, int maxTeams, boolean isManager) {
        this.name = name;
        this.year = year;
        this.maxTeams = maxTeams;
        this.clubs = new IClub[maxTeams];
        this.leagueStandings = new IStanding[maxTeams];
        this.currentRound = 0;
        this.numberOfTeams = 0;
        this.isManager = isManager;
        this.schedule = new Schedule(new IMatch[0][0]);
    }

    public void ListClubsWithOutWinsAtHome(IMatch[] matches) {
        boolean found = false;
        for(int i = 0; i < numberOfTeams; i++) {
            IClub club = clubs[i];
            if (club != null) {
                boolean hasWinAtHome = false;
                for (IMatch match : matches) {
                    if (match != null && match.getHomeClub().equals(club) && match.isPlayed()) {
                        if (match.getTotalByEvent(GoalEvent.class, match.getHomeClub()) > match.getTotalByEvent(GoalEvent.class, match.getAwayClub())) {
                            hasWinAtHome = true;
                            break;
                        }
                    }
                }
                if (!hasWinAtHome) {
                    System.out.println("Club without wins at home: " + club.getName());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("All clubs have at least one win at home.");
        }
    }

    public void ListClubsWithOutWinsAtAway(IMatch[] matches) {
        boolean found = false;
        for(int i = 0; i < numberOfTeams; i++) {
            IClub club = clubs[i];
            if (club != null) {
                boolean hasWinAway = false;
                for (IMatch match : matches) {
                    if (match != null && match.getAwayClub().equals(club) && match.isPlayed()) {
                        if (match.getTotalByEvent(GoalEvent.class, match.getAwayClub()) > match.getTotalByEvent(GoalEvent.class, match.getHomeClub())) {
                            hasWinAway = true;
                            break;
                        }
                    }
                }
                if (!hasWinAway) {
                    System.out.println("Club without wins away: " + club.getName());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("All clubs have at least one win away.");
        }
    }

    public void getGameWithLessGoals(IMatch[] matches) {
        if (matches == null || matches.length == 0) {
            System.out.println("No matches available.");
            return;
        }

        IMatch lowestScoringMatch = null;
        int minGoals = Integer.MAX_VALUE;

        for (IMatch match : matches) {
            if (match != null && match.isPlayed()) {
                int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
                int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
                int totalGoals = homeGoals + awayGoals;

                if (totalGoals < minGoals) {
                    minGoals = totalGoals;
                    lowestScoringMatch = match;
                }
            }
        }

        if (lowestScoringMatch != null) {
            System.out.println("Game with less goals: " + displayMatchResult(lowestScoringMatch));
        } else {
            System.out.println("No played matches found.");
        }
    }

    public void getGameWithMoreGoals(IMatch[] matches) {
        if (matches == null || matches.length == 0) {
            System.out.println("No matches available.");
            return;
        }

        IMatch highestScoringMatch = null;
        int maxGoals = 0;

        for (IMatch match : matches) {
            if (match != null && match.isPlayed()) {
                int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
                int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
                int totalGoals = homeGoals + awayGoals;

                if (totalGoals > maxGoals) {
                    maxGoals = totalGoals;
                    highestScoringMatch = match;
                }
            }
        }

        if (highestScoringMatch != null) {
            System.out.println("Game with more goals: " + displayMatchResult(highestScoringMatch));
        } else {
            System.out.println("No played matches found.");
        }
    }

    public void getNumberOfDawsInSeason(IMatch[] matches) {
        if (matches == null || matches.length == 0) {
            System.out.println("No matches available.");
            return;
        }

        int drawCount = 0;

        for (IMatch match : matches) {
            if (match != null && match.isPlayed()) {
                int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
                int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
                if (homeGoals == awayGoals) {
                    drawCount++;
                }
            }
        }

        System.out.println("Number of draws in the season: " + drawCount);
    }

    public void getNumberOfLosesInSeason(IMatch[] matches) {
        if (matches == null || matches.length == 0) {
            System.out.println("No matches available.");
            return;
        }

        int lossCount = 0;

        for (IMatch match : matches) {
            if (match != null && match.isPlayed()) {
                int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
                int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
                if (homeGoals < awayGoals) {
                    lossCount++;
                } else if (awayGoals < homeGoals) {
                    lossCount++;
                }
            }
        }

        System.out.println("Number of losses in the season: " + lossCount);
    }

    public void getNumberOfWinsInSeason(IMatch[] matches) {
        if (matches == null || matches.length == 0) {
            System.out.println("No matches available.");
            return;
        }

        int winCount = 0;

        for (IMatch match : matches) {
            if (match != null && match.isPlayed()) {
                int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
                int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
                if (homeGoals > awayGoals) {
                    winCount++;
                } else if (awayGoals > homeGoals) {
                    winCount++;
                }
            }
        }

        System.out.println("Number of wins in the season: " + winCount);
    }

    public void getGamesWithMoreThanTwoGoals(IMatch[] matches) {
        if (matches == null || matches.length == 0) {
            System.out.println("No matches available.");
            return;
        }

        boolean found = false;

        for (IMatch match : matches) {
            if (match != null && match.isPlayed()) {
                int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
                int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
                if (homeGoals + awayGoals > 2) {
                    System.out.println("Match with more than two goals: " + displayMatchResult(match));
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No matches with more than two goals found.");
        }
    }

    public void getGamesWithSocore2x1(IMatch[] matches) {
        if (matches == null || matches.length == 0) {
            System.out.println("No matches available.");
            return;
        }

        boolean found = false;

        for (IMatch match : matches) {
            if (match != null && match.isPlayed()) {
                int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
                int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
                if ((homeGoals == 2 && awayGoals == 1) || (homeGoals == 1 && awayGoals == 2)) {
                    System.out.println("Match with score 2x1: " + displayMatchResult(match));
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No matches with score 2x1 found.");
        }
    }

    public void getFastestGoalInSeasonByPlayer(IMatch[] matches) {
        if (matches == null || matches.length == 0) {
            System.out.println("No matches available.");
            return;
        }

        IEvent fastestGoal = null;
        int fastestTime = Integer.MAX_VALUE;

        for (IMatch match : matches) {
            if (match != null && match.isPlayed()) {
                for (IEvent event : match.getEvents()) {
                    if (event instanceof GoalEvent) {
                        int eventTime = ((GoalEvent) event).getMinute();
                        if (eventTime < fastestTime) {
                            fastestTime = eventTime;
                            fastestGoal = event;
                        }
                    }
                }
            }
        }

        if (fastestGoal != null) {
            System.out.println("Fastest goal in the season: " + fastestGoal);
        } else {
            System.out.println("No goals found in the season.");
        }
    }




    /**
     * Checks whether the season is managed by a human manager.
     *
     * @return True if managed by a manager, false otherwise.
     */
    public boolean isManager() {
        return isManager;
    }

    /**
     * Gets the year associated with the season.
     *
     * @return The season year.
     */
    @Override
    public int getYear() {
        return year;
    }

    /**
     * Adds a club to the current season. Ensures the club is unique and that
     * the maximum number of teams is not exceeded. Also initializes the club's
     * standing in the league table.
     *
     * @param club The club to be added.
     * @return true if the club was successfully added.
     * @throws IllegalArgumentException if the club is null, already exists, or
     *                                  capacity is full.
     */
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
        return true;
    }

    /**
     * Removes a club from the current season. Shifts all remaining clubs one
     * position forward and updates the team count.
     *
     * @param iClub The club to be removed.
     * @return true if the club was found and removed.
     * @throws IllegalArgumentException if the provided club is null.
     * @throws IllegalStateException    if the club does not exist.
     */
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

    /**
     * Retrieves all matches scheduled in the season.
     *
     * @return An array of all matches, or an empty array if the schedule is not
     * set.
     */
    @Override
    public IMatch[] getMatches() {
        return schedule != null ? schedule.getAllMatches() : new IMatch[0];
    }

    /**
     * Retrieves the matches scheduled for a specific round.
     *
     * @param round The round number (0-based index).
     * @return An array of matches for the specified round, or an empty array if
     * the schedule is not set.
     */
    @Override
    public IMatch[] getMatches(int round) {
        return schedule != null ? schedule.getMatchesForRound(round) : new IMatch[0];
    }

    /**
     * Generates a round-robin schedule for the season.
     * <p>
     * <p>
     * The schedule includes two legs (home and away) and supports odd numbers
     * of teams by inserting a dummy null team. Throws an exception if a
     * schedule already contains played matches or if there are not enough
     * teams.
     * <p>
     * <p>
     * Each round contains (N / 2) matches, where N is the number of clubs (or
     * N+1 if odd). Clubs rotate each round to produce unique pairings.
     *
     * @throws IllegalStateException if less than 2 teams are registered or if
     *                               any match was already played.
     */
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

                if (game == 0) {
                    awayIndex = teamCount - 1;
                }

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

    /**
     * Simulates the current round of matches using the defined simulation
     * strategy.
     * <p>
     * <p>
     * Only unplayed matches are simulated. After simulation, the current round
     * counter is incremented.
     *
     * @throws IllegalStateException if no match simulator is defined or the
     *                               maximum number of rounds is reached.
     */
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

    /**
     * Simulates all remaining rounds in the season until completion.
     * <p>
     * <p>
     * This method repeatedly calls {@code simulateRound()} until all matches
     * are played.
     */
    @Override
    public void simulateSeason() {
        while (!isSeasonComplete()) {
            simulateRound();
        }
    }

    /**
     * Returns the current round index of the season.
     *
     * @return The index of the current round (0-based).
     */
    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Checks if all matches in the season have been played.
     *
     * @return true if the season is complete, false otherwise.
     */
    @Override
    public boolean isSeasonComplete() {
        for (IMatch match : schedule.getAllMatches()) {
            if (!match.isPlayed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resets the season by resetting all matches and setting the current round
     * to zero.
     * <p>
     * <p>
     * Only matches that have been played will be reset.
     */
    @Override
    public void resetSeason() {
        currentRound = 0;
        for (IMatch match : schedule.getAllMatches()) {
            if (match.isPlayed()) {
                ((Match) match).reset();
            }
        }
    }

    /**
     * Returns a formatted string displaying the result of a given match.
     *
     * @param match The match to display.
     * @return A string in the format "HomeTeam X - Y AwayTeam".
     */
    @Override
    public String displayMatchResult(IMatch match) {
        String homeClub = match.getHomeClub().getName();
        String awayClub = match.getAwayClub().getName();
        int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
        int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
        String result = homeClub + " " + homeGoals + " - " + awayGoals + " " + awayClub;
        return result;
    }

    /**
     * Sets the strategy used to simulate matches within the season.
     *
     * @param matchSimulatorStrategy The strategy to use for simulating matches.
     */
    @Override
    public void setMatchSimulator(MatchSimulatorStrategy matchSimulatorStrategy) {
        this.matchSimulatorStrategy = matchSimulatorStrategy;
    }

    /**
     * Returns the current league standings for the season.
     *
     * @return An array of {@link IStanding} representing the league table.
     */
    @Override
    public IStanding[] getLeagueStandings() {
        return leagueStandings;
    }

    /**
     * Returns the match schedule for the season.
     *
     * @return The {@link ISchedule} object containing all rounds and matches.
     */
    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    /**
     * Returns the number of points awarded for a win.
     *
     * @return The number of points per win (default is 3).
     */
    @Override
    public int getPointsPerWin() {
        return 3;
    }

    /**
     * Returns the number of points awarded for a draw.
     *
     * @return The number of points per draw (default is 1).
     */
    @Override
    public int getPointsPerDraw() {
        return 1;
    }

    /**
     * Returns the number of points awarded for a loss.
     *
     * @return The number of points per loss (default is 0).
     */
    @Override
    public int getPointsPerLoss() {
        return 0;
    }

    /**
     * Returns the name of the season.
     *
     * @return The season name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the maximum number of teams allowed in the season.
     *
     * @return The maximum number of teams.
     */
    @Override
    public int getMaxTeams() {
        return maxTeams;
    }

    /**
     * Returns the total number of rounds scheduled in the season.
     *
     * @return The number of rounds, or 0 if no schedule is defined.
     */
    @Override
    public int getMaxRounds() {
        return schedule != null ? schedule.getNumberOfRounds() : 0;
    }

    /**
     * Returns the number of matches remaining to be played in the current
     * round.
     *
     * @return The count of unplayed matches in the current round.
     */
    @Override
    public int getCurrentMatches() {
        int count = 0;
        if (schedule.getNumberOfRounds() == 0 || currentRound >= schedule.getNumberOfRounds()) {
            return count;
        }
        IMatch[] roundMatches = schedule.getMatchesForRound(currentRound);
        for (IMatch match : roundMatches) {
            if (!match.isPlayed()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the number of clubs currently registered in the season.
     *
     * @return The number of active teams.
     */
    @Override
    public int getNumberOfCurrentTeams() {
        return numberOfTeams;
    }

    /**
     * Returns the list of clubs currently registered in the season.
     *
     * @return An array of {@link IClub} objects representing current clubs.
     */
    @Override
    public IClub[] getCurrentClubs() {
        IClub[] currentClubs = new IClub[numberOfTeams];
        for (int i = 0; i < numberOfTeams; i++) {
            currentClubs[i] = clubs[i];
        }
        return currentClubs;
    }

    /**
     * Displays detailed statistics of a given player based on all played
     * matches.
     *
     * <b>Note:</b> This method is not part of the ISeason interface.
     *
     * @param player The player for whom statistics are requested.
     * @throws IllegalArgumentException if the player is null.
     */
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

        for (IMatch iMatch : schedule.getAllMatches()) {
            if (iMatch != null && iMatch.isPlayed()) {
                for (IEvent event : iMatch.getEvents()) {
                    if (event != null && event instanceof PlayerEvent && ((PlayerEvent) event).getPlayer().equals(player)) {
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
                        } else if (event instanceof ShotOnGoalEvent) {
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
                    } else if (event != null && event instanceof SubstitutionEvent) {
                        SubstitutionEvent substitutionEvent = (SubstitutionEvent) event;
                        if (substitutionEvent.getPlayerIn().equals(player) || substitutionEvent.getPlayerOut().equals(player)) {
                            substitutions++;
                        }
                    }
                }
            }
        }

        String playerStats = String.format(
                "Player: %-20s%n"
                        + "Number: %-10d%n"
                        + "Goals: %-10d Corner Kicks: %-10d%n"
                        + "Fouls: %-10d Yellow Cards: %-10d%n"
                        + "Red Cards: %-10d Substitutions: %-10d%n"
                        + "Shots on Goal: %-10d Shots: %-10d%n"
                        + "Penalties: %-10d Offside: %-10d%n"
                        + "Injuries: %-10d Free Kicks: %-10d",
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

    /**
     * Prints the schedule of matches to the console, grouped by round.
     * <p>
     * <p>
     * If no schedule is available, a warning is shown.
     */
    public void printSchedule() {
        if (schedule == null) {
            System.out.println("No matches scheduled.");
            return;
        }

        for (int i = 0; i < schedule.getNumberOfRounds(); i++) {
            System.out.println("Round " + i);
            for (IMatch iMatch : schedule.getMatchesForRound(i)) {
                if (iMatch != null) {
                    System.out.println(iMatch.getHomeClub().getName() + " vs " + iMatch.getAwayClub().getName() + " - Round: " + iMatch.getRound());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     *
     * <b>Note:</b> This method is intentionally left unimplemented in this
     * class, as JSON export is handled centrally by a component responsible for
     * exporting, the complete state of the application.
     * <p>
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

    /**
     * Sets the schedule object used in the season.
     *
     * @param schedule The schedule to assign.
     */
    public void setSchedule(ISchedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Sets the league standings manually.
     *
     * @param standings An array of {@link IStanding} objects.
     */
    public void setStandings(IStanding[] standings) {
        this.leagueStandings = standings;
    }

    /**
     * Sets the current round index of the season.
     *
     * @param round The round (0-based) to set as current.
     */
    public void setCurrentRound(int round) {
        this.currentRound = round;
    }

}
