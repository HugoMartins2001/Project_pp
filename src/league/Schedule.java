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

public class Schedule implements ISchedule {
    private IMatch[][] matches;
    private int numberOfRounds;

    public Schedule() {
        this.matches = new IMatch[0][];
        this.numberOfRounds = 0;
    }

    public Schedule(IMatch[][] matches) {
        if (matches == null) throw new IllegalArgumentException("Matches cannot be null");
        this.matches = matches;
        this.numberOfRounds = matches.length;
    }


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

    @Override
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

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


    @Override
    public void exportToJson() throws IOException {

    }
}
