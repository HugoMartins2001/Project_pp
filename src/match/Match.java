package match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import event.EventManager;
import event.GoalEvent;
import event.PlayerEvent;

import java.io.IOException;

/**
 * Represents a football match between two clubs.
 * <p>
 * The {@code Match} class encapsulates all relevant data and logic for a football match,
 * including the home and away clubs, their respective teams, the event manager, and the
 * management of match events such as goals and player incidents.
 * </p>
 * <p>
 * This class implements the {@link IMatch} interface and provides methods to:
 * <ul>
 *   <li>Access and manage participating clubs and teams</li>
 *   <li>Track and handle match events via an {@link IEventManager}</li>
 *   <li>Determine the winner based on the match outcome</li>
 *   <li>Export match data (e.g., to JSON)</li>
 * </ul>
 * </p>
 *
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 *
 * @see IMatch
 * @see IClub
 * @see ITeam
 * @see IEvent
 * @see IEventManager
 */
public class Match implements IMatch {

    /** The away club participating in the match */
    private IClub awayClub;

    /** The home club participating in the match */
    private IClub homeClub;

    /** The away team associated with the away club */
    private ITeam awayTeam;

    /** The home team associated with the home club */
    private ITeam homeTeam;

    /** The round number of the match */
    private int round;

    /** Manager for storing and retrieving events that occurred in the match */
    private IEventManager events;

    /** Flag indicating whether the match has already been played */
    private boolean isPlayed;

    /**
     * Constructs a Match instance with specified home and away clubs and the round number.
     *
     * @param homeClub The club playing at home.
     * @param awayClub The club playing away.
     * @param round    The round number of the match.
     * @throws IllegalArgumentException if either club is null.
     */
    public Match(IClub homeClub, IClub awayClub, int round) {
        if (homeClub == null || awayClub == null) throw new IllegalArgumentException("Clubs cannot be null");
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.round = round;
        this.events = new EventManager();
        this.isPlayed = false;
    }

    /**
     * Gets the home club.
     *
     * @return the home club.
     * @throws IllegalStateException if the home club is not initialized.
     */
    @Override
    public IClub getHomeClub() {
        if (homeClub == null) {
            throw new IllegalStateException("Home Club is not inicialized!");
        }
        return homeClub;
    }

    /**
     * Gets the away club.
     *
     * @return the away club.
     * @throws IllegalStateException if the away club is not initialized.
     */
    @Override
    public IClub getAwayClub() {
        if (awayClub == null) {
            throw new IllegalStateException("Away Club is not inicialized");
        }
        return awayClub;
    }

    /**
     * Checks if the match has been played.
     *
     * @return true if the match was played; false otherwise.
     */
    @Override
    public boolean isPlayed() {
        return isPlayed;
    }

    /**
     * Gets the home team.
     *
     * @return the home team.
     * @throws IllegalStateException if the home team is not initialized.
     */
    @Override
    public ITeam getHomeTeam() {
        if (homeTeam == null) {
            throw new IllegalStateException("Home Team is not inicialized");
        }
        return homeTeam;
    }

    /**
     * Gets the away team.
     *
     * @return the away team.
     * @throws IllegalStateException if the away team is not initialized.
     */
    @Override
    public ITeam getAwayTeam() {
        if (awayTeam == null) {
            throw new IllegalStateException("Away Team is not inicialized");
        }
        return awayTeam;
    }

    /**
     * Marks the match as played.
     */
    @Override
    public void setPlayed() {
        isPlayed = true;
    }

    /**
     * Gets the total number of specific events for a team.
     *
     * @param eventClass The class of the event to count.
     * @param team The club to count events for.
     * @return The number of events matching the given class and team.
     * @throws NullPointerException if eventClass or team is null.
     */
    @Override
    public int getTotalByEvent(Class eventClass, IClub team) {
        if (eventClass == null || team == null) {
            throw new NullPointerException("Event class or team is not defined!");
        }

        int total = 0;
        for (IEvent event : getEvents()) {
            if (eventClass.isInstance(event) && event instanceof PlayerEvent) {
                if (team.isPlayer(((PlayerEvent) event).getPlayer())) {
                    total++;
                }
            }
        }
        return total;
    }

    /**
     * Checks if the match is initialized (clubs and teams set).
     *
     * @return true if initialized; false otherwise.
     */
    public boolean isInitialized() {
        return homeClub != null && awayClub != null && homeTeam != null && awayTeam != null;
    }

    /**
     * Checks if the match is valid.
     *
     * @return true if valid; false otherwise.
     */
    @Override
    public boolean isValid() {
        if (awayTeam == null || homeTeam == null || homeTeam.equals(awayTeam)) {
            return false;
        }
        return true;
    }

    /**
     * Determines the winner of the match.
     *
     * @return The winning team or null if it's a draw.
     * @throws IllegalStateException if the match is not played or not valid.
     */
    @Override
    public ITeam getWinner() {
        if (!isPlayed) {
            throw new IllegalStateException("The match has not been played yet");
        }
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalStateException("Teams are not set.");
        }
        if (!isValid()) {
            throw new IllegalStateException("The match is not valid.");
        }

        int homeGoals = getTotalByEvent(GoalEvent.class, homeClub);
        int awayGoals = getTotalByEvent(GoalEvent.class, awayClub);

        if (homeGoals > awayGoals) {
            return homeTeam;
        } else if (awayGoals > homeGoals) {
            return awayTeam;
        } else {
            return null;
        }
    }

    /**
     * Gets the round number of the match.
     *
     * @return The round number.
     */
    @Override
    public int getRound() {
        return round;
    }

    /**
     * Assigns a team to the corresponding club (home or away).
     *
     * @param team The team to assign.
     * @throws IllegalArgumentException if the team is null.
     * @throws IllegalStateException if the match has been played or the team does not belong to either club.
     */
    @Override
    public void setTeam(ITeam team) {
        if (team == null) {
            throw new IllegalArgumentException("Team is not selected!");
        }
        if (isPlayed) {
            throw new IllegalStateException("Cannot set a team because match was already played!");
        }
        if (homeClub.equals(team.getClub())) {
            homeTeam = team;
        } else if (awayClub.equals(team.getClub())) {
            awayTeam = team;
        } else {
            throw new IllegalStateException("The club does not belong to the match");
        }
    }

    /**
     * Exports match data to a JSON file.
     *
     * @throws IOException if an I/O error occurs during export.
     */
    @Override
    public void exportToJson() throws IOException {
        // Implementation placeholder
    }

    /**
     * Adds an event to the match.
     *
     * @param iEvent The event to add.
     */
    @Override
    public void addEvent(IEvent iEvent) {
        events.addEvent(iEvent);
    }

    /**
     * Retrieves all events of the match, ordered by minute.
     *
     * @return An array of ordered events.
     */
    @Override
    public IEvent[] getEvents() {
        return ((EventManager) events).getEventsOrderedByMinute();
    }

    /**
     * Gets the total number of events in the match.
     *
     * @return The number of events.
     */
    @Override
    public int getEventCount() {
        return events.getEventCount();
    }

    /**
     * Resets the match by clearing teams and setting isPlayed to false.
     */
    public void reset() {
        awayTeam = null;
        homeTeam = null;
        isPlayed = false;
    }
}
