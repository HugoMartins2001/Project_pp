package league;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.io.IOException;

/**
 * Represents a match schedule for a league season.
 * <p>
 * The {@code Schedule} class implements the {@link ISchedule} interface and organizes matches by rounds.
 * It provides access to matches per round, per team, and all matches in the season. The schedule
 * also allows assigning teams to specific matches and supports various schedule queries.
 * </p>
 *
 * <h2>Main Features</h2>
 * <ul>
 *   <li>Stores all matches in a two-dimensional array, organized by rounds</li>
 *   <li>Provides methods to retrieve matches for a specific round or team</li>
 *   <li>Allows assignment of teams to matches</li>
 *   <li>Supports exporting schedule data if needed</li>
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
 * @see ISchedule
 * @see IMatch
 * @see ITeam
 */
public class Schedule implements ISchedule {
    private IMatch[][] matches;
    private int numberOfRounds;

    /**
     * Constructs a schedule with the specified array of matches grouped by round.
     *
     * @param matches a 2D array of matches where each row represents one round
     * @throws IllegalArgumentException if the matches array is null
     */
    public Schedule(IMatch[][] matches) {
        if (matches == null) throw new IllegalArgumentException("Matches cannot be null");
        this.matches = matches;
        this.numberOfRounds = matches.length;
    }

    /**
     * Returns all matches for the specified round.
     *
     * @param round the round number (0-based index)
     * @return an array of matches for the given round
     * @throws IllegalArgumentException if the round number is invalid
     * @throws IllegalStateException    if matches for the round are not set
     */
    @Override
    public IMatch[] getMatchesForRound(int round) {
        if (round < 0 || round >= matches.length) {
            throw new IllegalArgumentException("Invalid round number");
        }
        if (matches[round] == null) {
            throw new IllegalStateException("Matches for round are not set");
        }
        return matches[round];
    }

    /**
     * Returns all matches that involve the specified team across all rounds.
     *
     * @param team the team to search matches for
     * @return an array of matches involving the given team
     * @throws IllegalArgumentException if the team is null
     * @throws IllegalStateException    if matches have not been set
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
     * Returns the number of rounds in the schedule.
     *
     * @return the number of rounds
     */
    @Override
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    /**
     * Returns all matches in the schedule across all rounds.
     *
     * @return an array containing all matches
     * @throws IllegalStateException if matches have not been set
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
     * Assigns a team to its corresponding match in a specified round.
     * The team must already be part of the match (home or away) and from the correct club.
     *
     * @param iTeam the team to assign
     * @param round the round number
     * @throws IllegalArgumentException if the team or round is invalid
     * @throws IllegalStateException    if the team is not scheduled for the round or club mismatch occurs
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
                if (!match.getHomeTeam().getClub().equals(iTeam.getClub()) &&
                        !match.getAwayTeam().getClub().equals(iTeam.getClub())) {
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
     * Exports the schedule data to a JSON file or format.
     * (Currently not implemented.)
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void exportToJson() throws IOException {
        // To be implemented
    }
}
