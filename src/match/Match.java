/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */
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
 * Class that represents a football match between two clubs. Manages events,
 * teams, and match state (played, winner, etc.).
 */
public class Match implements IMatch {

    private IClub awayClub;
    private IClub homeClub;
    private ITeam awayTeam;
    private ITeam homeTeam;
    private int round;
    private IEventManager events;
    private boolean isPlayed;

    /**
     * Constructor for a match without defined teams.
     *
     * @param homeClub Home club
     * @param awayClub Away club
     * @param round Round number of the match
     */
    public Match(IClub homeClub, IClub awayClub, int round) {
        if (homeClub == null || awayClub == null) {
            throw new IllegalArgumentException("Clubs cannot be null");
        }
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.round = round;
        this.events = new EventManager();
        this.isPlayed = false;
    }

    /**
     * Constructor for a fully defined match.
     *
     * @param homeClub Home club
     * @param awayClub Away club
     * @param homeTeam Home team
     * @param awayTeam Away team
     * @param round Match round
     * @param events Event manager instance
     * @param isPlayed Match played state
     */
    public Match(IClub homeClub, IClub awayClub, ITeam homeTeam, ITeam awayTeam, int round, IEventManager events, boolean isPlayed) {
        if (homeClub == null || awayClub == null) {
            throw new IllegalArgumentException("Clubs cannot be null");
        }
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.round = round;
        this.events = events;
        this.isPlayed = isPlayed;
    }

    /**
     * Gets the home club of the match.
     *
     * @return Home club
     * @throws IllegalStateException if home club is not initialized
     */
    @Override
    public IClub getHomeClub() {
        if (homeClub == null) {
            throw new IllegalStateException("Home Club is not initialized!");
        }
        return homeClub;
    }

    /**
     * Gets the away club of the match.
     *
     * @return Away club
     * @throws IllegalStateException if away club is not initialized
     */
    @Override
    public IClub getAwayClub() {
        if (awayClub == null) {
            throw new IllegalStateException("Away Club is not initialized");
        }
        return awayClub;
    }

    /**
     * Checks if the match has been played.
     *
     * @return true if the match is played, false otherwise
     */
    @Override
    public boolean isPlayed() {
        return isPlayed;
    }

    /**
     * Gets the home team of the match.
     *
     * @return Home team
     * @throws IllegalStateException if home team is not initialized
     */
    @Override
    public ITeam getHomeTeam() {
        if (homeTeam == null) {
            throw new IllegalStateException("Home Team is not initialized");
        }
        return homeTeam;
    }

    /**
     * Gets the away team of the match.
     *
     * @return Away team
     * @throws IllegalStateException if away team is not initialized
     */
    @Override
    public ITeam getAwayTeam() {
        if (awayTeam == null) {
            throw new IllegalStateException("Away Team is not inicialized");
        }
        return awayTeam;
    }

    /**
     * Sets the match as played. Once set, no further changes to teams or events
     * are allowed.
     *
     * @throws IllegalStateException if the match has already been played
     */
    @Override
    public void setPlayed() {
        isPlayed = true;
    }

    /**
     * Gets the total number of events of a specific class for a given team.
     *
     * @param eventClass The class of the event to count
     * @param team The team for which to count events
     * @return Total number of events of the specified class for the team
     * @throws NullPointerException if eventClass or team is null
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
     * Checks if the match is initialized with both teams and clubs.
     *
     * @return true if both teams and clubs are set, false otherwise
     */
    public boolean isInitialized() {
        return homeClub != null && awayClub != null && homeTeam != null && awayTeam != null;
    }

    /**
     * Checks if the match is valid. A match is valid if both teams are set and
     * they are not the same team.
     *
     * @return true if the match is valid, false otherwise
     */
    @Override
    public boolean isValid() {
        if (awayTeam == null || homeTeam == null || homeTeam.equals(awayTeam)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the winner of the match. If the match has not been played or is
     * invalid, an exception is thrown.
     *
     * @return The winning team, or null if it's a draw
     * @throws IllegalStateException if the match has not been played or is
     * invalid
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
     * @return The round number
     */
    @Override
    public int getRound() {
        return round;
    }

    /**
     * Sets the team for the match. The team must belong to either the home or
     * away club.
     *
     * @param team The team to set
     * @throws IllegalArgumentException if the team is null or not selected
     * @throws IllegalStateException if the match has already been played or the
     * team does not belong to the match
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

    /**
     * Adds an event to the match. The event must be of type IEvent.
     *
     * @param iEvent The event to add
     * @throws IllegalArgumentException if the event is null
     */
    @Override
    public void addEvent(IEvent iEvent) {
        events.addEvent(iEvent);
    }

    /**
     * Gets the events associated with the match. Returns an array of events
     * ordered by minute.
     *
     * @return An array of IEvent instances
     */
    @Override
    public IEvent[] getEvents() {
        return ((EventManager) events).getEventsOrderedByMinute();
    }

    /**
     * Gets the total number of events in the match. This includes all types of
     * events, not just goals.
     *
     * @return The count of events
     */
    @Override
    public int getEventCount() {
        return events.getEventCount();
    }

    /**
     * Resets the match state, clearing teams and events. This method is useful
     * for reusing the match object.
     */
    public void reset() {
        awayTeam = null;
        homeTeam = null;
        isPlayed = false;

    }
}
