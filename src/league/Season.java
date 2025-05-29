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
 * Represents a football league season, including its structure, participating clubs, schedule,
 * matches, and league standings.
 * <p>
 * The {@code Season} class encapsulates all relevant data and logic for managing a football league season.
 * It tracks the season's name, year, current round, maximum number of teams, participating clubs,
 * scheduled matches, the match simulator strategy, and league standings.
 * </p>
 * <p>
 * This class implements the {@link ISeason} interface, making it compatible with the Football Manager API.
 * </p>
 *
 * <h2>Main Features</h2>
 * <ul>
 *   <li>Stores the season's name, year, and current round</li>
 *   <li>Manages the list of participating clubs and their teams</li>
 *   <li>Handles match scheduling and simulation</li>
 *   <li>Keeps track of league standings</li>
 *   <li>Supports manager mode and configurable maximum teams</li>
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
 * @see ISeason
 * @see IClub
 * @see IMatch
 * @see ISchedule
 * @see IStanding
 * @see MatchSimulatorStrategy
 */
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
    private boolean isManager;

    /**
     * Constructor for the Season class.
     * Initializes a new season with the given name, year, maximum number of teams, and manager status.
     *
     * @param name      The name of the season.
     * @param year      The year of the season.
     * @param maxTeams  The maximum number of teams allowed in the season.
     * @param isManager Indicates if the user is a manager.
     */
    public Season(String name, int year, int maxTeams, boolean isManager) {
        this.name = name;
        this.year = year;
        this.maxTeams = maxTeams;
        this.clubs = new IClub[maxTeams];
        this.leagueStandings = new IStanding[maxTeams];
        this.match = new IMatch[0];
        this.currentRound = 0;
        this.numberOfTeams = 0;
        this.isManager = isManager;
    }

    /**
     * Checks if the current user is a manager.
     *
     * @return True if the user is a manager, false otherwise.
     */
    public boolean isManager() {
        return isManager;
    }

    /**
     * Gets the year of the season.
     *
     * @return The year of the season.
     */
    @Override
    public int getYear() {
        return year;
    }

    /**
     * Adds a club to the season.
     * Throws an exception if the club is null, already exists, or the maximum number of teams is reached.
     *
     * @param club The club to add.
     * @return True if the club was added successfully.
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
        System.out.println("Club added: " + club.getName());
        return true;
    }

    /**
     * Removes a club from the season.
     * Throws an exception if the club is null or does not exist.
     *
     * @param iClub The club to remove.
     * @return True if the club was removed successfully.
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
     * @return An array of all matches.
     */
    @Override
    public IMatch[] getMatches() {
        return match;
    }

    /**
     * Retrieves all matches for a specific round.
     * Throws an exception if the round number is invalid.
     *
     * @param round The round number.
     * @return An array of matches for the specified round.
     */
    @Override
    public IMatch[] getMatches(int round) {
        if (round < 0 || round >= getMaxRounds()) {
            throw new IllegalArgumentException("Invalid round number");
        }
        int count = 0;
        for (IMatch m : match) {
            if (m != null && m.getRound() == round) {
                count++;
            }
        }
        IMatch[] result = new IMatch[count];
        int index = 0;
        for (IMatch m : match) {
            if (m != null && m.getRound() == round) {
                result[index++] = m;
            }
        }
        return result;
    }

    /**
     * Generates the full match schedule for the current season using a double round-robin format.
     * <p>
     * Each team plays every other team twice, once at home and once away. If the number of teams is odd,
     * a dummy team (represented as {@code null}) is added to ensure an even number of teams, resulting in a bye each round.
     * This method prevents schedule generation if there are fewer than two teams or if any match has already been played.
     * </p>
     *
     * <p>
     * The generated matches are stored in the {@code match} array field, and each match is assigned to the appropriate round.
     * </p>
     *
     * @throws IllegalStateException if there are fewer than two teams or if any match has already been played
     */

        @Override
    public void generateSchedule() {
        if (numberOfTeams < 2) {
            throw new IllegalStateException("Not enough teams to generate a schedule.");
        }

        if (match != null) {
            for (int i = 0; i < match.length; i++) {
                if (match[i] != null && match[i].isPlayed()) {
                    throw new IllegalStateException("A match was already played.");
                }
            }
        }

        int teamCount = numberOfTeams;
        boolean isOdd = false;
        Club[] scheduleTeams;

        if (teamCount % 2 != 0) {
            isOdd = true;
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
        int totalMatches = totalRounds * matchesPerRound;

        match = new IMatch[totalMatches];
        int matchIndex = 0;

        for (int round = 0; round < roundsPerLeg; round++) {
            for (int game = 0; game < matchesPerRound; game++) {
                int homeIndex = (round + game) % (teamCount - 1);
                int awayIndex = (teamCount - 1 - game + round) % (teamCount - 1);

                if (game == 0) {
                    awayIndex = teamCount - 1;
                }

                Club homeClub = scheduleTeams[homeIndex];
                Club awayClub = scheduleTeams[awayIndex];

                if (!isOdd || (homeClub != null && awayClub != null)) {
                    match[matchIndex++] = new Match(homeClub, awayClub, round);
                    match[matchIndex++] = new Match(awayClub, homeClub, round + roundsPerLeg);
                }
            }
        }
    }

    /**
     * Simulates the current round of the season.
     * <p>
     * This method uses the configured {@link MatchSimulatorStrategy} to simulate all matches
     * scheduled for the current round. The round number is incremented after simulation.
     * </p>
     *
     * @throws IllegalStateException if the match simulator strategy is not initialized or if the maximum number of rounds is reached
     */
    @Override
    public void simulateRound() {
        if (matchSimulatorStrategy == null) {
            throw new IllegalStateException("Match simulator is not initialized.");
        }

        if (currentRound >= getMaxRounds()) {
            throw new IllegalStateException("Max rounds reached");
        }

        for (IMatch iMatch : match) {
            if (iMatch.getRound() == currentRound) {
                matchSimulatorStrategy.simulate(iMatch);
            }
        }

        currentRound++;
    }

    /**
     * Simulates the entire season by running all rounds until the season is complete.
     * <p>
     * This method repeatedly calls {@link #simulateRound()} until all matches in the season have been played.
     * </p>
     */
    @Override
    public void simulateSeason() {
        while (!isSeasonComplete()) {
            simulateRound();
        }
    }

    /**
     * Returns the current round number of the season.
     *
     * @return the current round number
     */
    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Checks if the season is complete, i.e., if all matches have been played.
     *
     * @return {@code true} if all matches are played; {@code false} otherwise
     */
    @Override
    public boolean isSeasonComplete() {
        for (IMatch iMatch : match) {
            if (iMatch != null && !iMatch.isPlayed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resets the season to its initial state.
     * <p>
     * This method resets the current round to zero and marks all matches as unplayed.
     * </p>
     */
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

    /**
     * Returns a formatted string representing the result of the specified match.
     * <p>
     * The result is formatted as: {@code HomeClubName HomeGoals - AwayGoals AwayClubName}.
     * </p>
     *
     * @param match the {@link IMatch} whose result is to be displayed
     * @return a string representing the match result
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
     * Sets the match simulator strategy to be used for match simulations in this season.
     *
     * @param matchSimulatorStrategy the {@link MatchSimulatorStrategy} to use
     */
    @Override
    public void setMatchSimulator(MatchSimulatorStrategy matchSimulatorStrategy) {
        this.matchSimulatorStrategy = matchSimulatorStrategy;
    }

    /**
     * Returns the current league standings for this season.
     *
     * @return an array of {@link IStanding} objects representing the league standings
     */
    @Override
    public IStanding[] getLeagueStandings() {
        return leagueStandings;
    }

    /**
     * Returns the match schedule for this season.
     *
     * @return the {@link ISchedule} object representing the season's schedule
     */
    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    /**
     * Returns the number of points awarded for a win in this season.
     *
     * @return the number of points per win (default is 3)
     */
    @Override
    public int getPointsPerWin() {
        return 3;
    }

    /**
     * Returns the number of points awarded for a draw in this season.
     *
     * @return the number of points per draw (default is 1)
     */
    @Override
    public int getPointsPerDraw() {
        return 1;
    }

    /**
     * Returns the number of points awarded for a loss in this season.
     *
     * @return the number of points per loss (default is 0)
     */
    @Override
    public int getPointsPerLoss() {
        return 0;
    }

    /**
     * Returns the name of the season.
     *
     * @return the season name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the maximum number of teams allowed in the season.
     *
     * @return the maximum number of teams
     */
    @Override
    public int getMaxTeams() {
        return maxTeams;
    }

    /**
     * Returns the maximum number of rounds in the season.
     * <p>
     * For a double round-robin tournament, this is calculated as {@code 2 * (numberOfTeams - 1)}.
     * </p>
     *
     * @return the maximum number of rounds
     */
    @Override
    public int getMaxRounds() {
        return (2 * (numberOfTeams - 1));
    }

    /**
     * Returns the number of matches scheduled for the current round.
     *
     * @return the number of matches in the current round
     */
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

    /**
     * Returns the current number of teams participating in the season.
     *
     * @return the number of current teams
     */
    @Override
    public int getNumberOfCurrentTeams() {
        return numberOfTeams;
    }

    /**
     * Returns an array of the current clubs participating in the season.
     *
     * @return an array of {@link IClub} objects representing the current clubs
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
     * Displays the statistics for the specified player across all played matches in the season.
     * <p>
     * This includes goals, corner kicks, fouls, cards, substitutions, shots, penalties, offside, injuries, and free kicks.
     * Statistics are printed to the standard output.
     * </p>
     *
     * @param player the {@link IPlayer} whose statistics are to be displayed
     * @throws IllegalArgumentException if {@code player} is {@code null}
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

        for (IMatch iMatch : match) {
            if (match != null && iMatch.isPlayed()) {
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

    /**
     * Prints the full match schedule for the current season to the standard output.
     * <p>
     * For each round, all scheduled matches are displayed in the format:
     * {@code HomeClub vs AwayClub - Round: roundNumber}.
     * If no matches are scheduled, a message is printed to indicate this.
     * </p>
     */
    public void printSchedule() {
        if (match == null || match.length == 0) {
            System.out.println("No matches scheduled.");
            return;
        }

        for (int i = 0; i < getMaxRounds(); i++) {
            System.out.println("Round " + i);
            for (IMatch iMatch : match) {
                if (iMatch != null && iMatch.getRound() == i) {
                    System.out.println(iMatch.getHomeClub().getName() + " vs " + iMatch.getAwayClub().getName() + " - Round: " + iMatch.getRound());
                }
            }
        }
    }

    /**
     * Exports the season's data to a JSON file.
     * <p>
     * This method is not yet implemented.
     * </p>
     *
     * @throws IOException if an I/O error occurs during export
     */
    @Override
    public void exportToJson() throws IOException {
        // TODO implementar exportToJson
    }

}
