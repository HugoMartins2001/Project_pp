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
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.io.IOException;

/**
 * Represents a schedule of matches organized into rounds. Implements the
 * {@link ISchedule} interface.
 *
 *
 * Each round is represented as an array of {@link IMatch} objects. The schedule
 * provides methods to retrieve matches by round or team, as well as to assign
 * teams and export data.
 *
 *
 * This class assumes that each match has a home and away team, and matches are
 * properly set before usage.
 *
 * @see IMatch
 * @see ITeam
 * @see ISchedule
 *
 */
public class Schedule implements ISchedule {

    private IMatch[][] matches;
    private int numberOfRounds;

    /**
     * Constructs an empty schedule. Initializes the schedule with zero rounds
     * and no matches.
     */
    public Schedule() {
        this.matches = new IMatch[0][];
        this.numberOfRounds = 0;
    }

    /**
     * Constructs a schedule with predefined rounds and matches.
     *
     * @param matches A 2D array where each row is a round containing matches.
     */
    public Schedule(IMatch[][] matches) {
        if (matches == null) {
            throw new IllegalArgumentException("Matches cannot be null");
        }
        this.matches = matches;
        this.numberOfRounds = matches.length;
    }

    /**
     * Retrieves the matches for a specific round.
     *
     * @param round The round number (0-based).
     * @return An array of matches for that round.
     * @throws IllegalArgumentException If the round number is invalid.
     * @throws IllegalStateException If no matches are set for that round.
     */
    @Override
    public IMatch[] getMatchesForRound(int round) {
        if (round < 0 || round >= matches.length) {
            throw new IllegalArgumentException("Invalid round number: " + round);
        }
        if (matches[round] == null) {
            throw new IllegalStateException("Matches for round are not set");
        }
        return matches[round];
    }

    /**
     * Retrieves all matches involving a specific team.
     *
     * @param team The team whose matches are to be retrieved.
     * @return An array of matches involving the team.
     * @throws IllegalArgumentException If the team is null.
     * @throws IllegalStateException If no matches are set.
     */
    @Override
    public IMatch[] getMatchesForTeam(ITeam team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        if (matches == null || matches.length == 0) {
            throw new IllegalStateException("Matches are not set");
        }

        int count = 0;
        for (IMatch[] round : matches) {
            if (round != null) {
                for (IMatch match : round) {
                    if (match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team)) {
                        count++;
                    }
                }
            }
        }

        IMatch[] teamMatches = new IMatch[count];
        int index = 0;
        for (IMatch[] round : matches) {
            if (round != null) {
                for (IMatch match : round) {
                    if (match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team)) {
                        teamMatches[index++] = match;
                    }
                }
            }
        }

        return teamMatches;
    }

    /**
     * Gets the number of rounds in the schedule.
     *
     * @return The number of rounds.
     */
    @Override
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    /**
     * Retrieves all matches from all rounds.
     *
     * @return A flat array containing all matches in the schedule.
     * @throws IllegalStateException If matches are not initialized.
     */
    @Override
    public IMatch[] getAllMatches() {
        if (matches == null) {
            throw new IllegalStateException("Matches are not set");
        }

        int totalMatches = 0;
        for (IMatch[] round : matches) {
            if (round != null) {
                totalMatches += round.length;
            }
        }

        IMatch[] allMatches = new IMatch[totalMatches];
        int index = 0;
        for (IMatch[] round : matches) {
            if (round != null) {
                for (IMatch match : round) {
                    allMatches[index++] = match;
                }
            }
        }

        return allMatches;
    }

    /**
     * Assigns a team object to a scheduled match in a specific round. Ensures
     * the team is actually participating in that match.
     *
     * @param iTeam The team to set.
     * @param round The round in which the match is located.
     * @throws IllegalArgumentException If the team or round is invalid.
     * @throws IllegalStateException If the match or club relationship is
     * invalid.
     */
    @Override
    public void setTeam(ITeam iTeam, int round) {
        if (iTeam == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        if (round < 0 || round >= matches.length) {
            throw new IllegalArgumentException("Invalid round number");
        }
        if (matches[round] == null) {
            throw new IllegalStateException("Matches for round " + round + " are not set");
        }

        boolean teamFound = false;
        for (IMatch match : matches[round]) {
            if (match.getHomeTeam().equals(iTeam) || match.getAwayTeam().equals(iTeam)) {
                if (!match.getHomeTeam().getClub().equals(iTeam.getClub())
                        && !match.getAwayTeam().getClub().equals(iTeam.getClub())) {
                    throw new IllegalStateException("Team's club is not associated with the match");
                }
                match.setTeam(iTeam);
                teamFound = true;
                break;
            }
        }

        if (!teamFound) {
            throw new IllegalStateException("Team is not scheduled for this round");
        }
    }

    /**
     *
     * {@inheritDoc}
     *
     *
     * <b>Note:</b> This method is intentionally left unimplemented in this
     * class, as JSON export is handled centrally by a component responsible for
     * exporting, the complete state of the application.
     *
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
}
