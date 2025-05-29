package simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import event.*;
import match.Match;
import team.Formation;
import team.Team;

import java.util.Random;

/**
 * Implements a strategy for simulating football (soccer) matches.
 * <p>
 * The {@code MatchSimulatorStrat} class provides logic for simulating various match events,
 * such as red cards, injuries, and substitutions. It keeps track of affected players and
 * enforces rules such as the maximum number of substitutions allowed per team.
 * </p>
 * <p>
 * This class implements the {@link MatchSimulatorStrategy} interface and uses a random number
 * generator to introduce stochastic elements into the simulation.
 * </p>
 *
 * <h2>Key Features</h2>
 * <ul>
 *   <li>Tracks players who receive red cards, are injured, or are substituted.</li>
 *   <li>Enforces a maximum of {@value #MAX_SUBSTITUTIONS} substitutions per team.</li>
 *   <li>Supports separate substitution counts for home and away teams.</li>
 *   <li>Utilizes a {@link Random} instance for event simulation.</li>
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
 * @see MatchSimulatorStrategy
 * @see IMatch
 * @see IPlayer
 * @see ITeam
 * @see IClub
 */
public class MatchSimulatorStrat implements MatchSimulatorStrategy {

    private final Random rng = new Random();
    private IPlayer[] redCardPlayers = new IPlayer[1];
    private IPlayer[] injuredPlayers = new IPlayer[1];
    private IPlayer[] substitutedPlayers = new IPlayer[6];
    private int redCardCount = 0;
    private int injuredCount = 0;
    private int substitutedCount = 0;
    private int homeSubstitutions = 0;
    private int awaySubstitutions = 0;
    private static final int MAX_SUBSTITUTIONS = 3;

    /**
     * Simulates the progression of a football match, generating events and updating match state.
     * <p>
     * This method performs several validation checks before simulation:
     * <ul>
     *   <li>Ensures the {@code match} parameter is not {@code null}.</li>
     *   <li>Checks that the match is initialized and valid.</li>
     *   <li>Prevents simulation if the match has already been played.</li>
     * </ul>
     * <p>
     * The simulation process includes:
     * <ul>
     *   <li>Adding a {@link StartEvent} at the beginning of the match.</li>
     *   <li>Generating match events for both the home and away teams.</li>
     *   <li>Adding an {@link EndEvent} at the end of the match.</li>
     *   <li>Marking the match as played.</li>
     * </ul>
     *
     * @param match the match to simulate; must not be {@code null} and must be initialized, valid, and not already played
     * @throws IllegalArgumentException if {@code match} is {@code null}
     * @throws IllegalStateException    if the match is not initialized, not valid, or has already been played
     */
    public void simulate(IMatch match) {
        if (match == null) throw new IllegalArgumentException("Match cannot be null");
        if (!((Match) match).isInitialized()) throw new IllegalStateException("Match is not initialized");
        if (match.isPlayed()) throw new IllegalStateException("Match has already been played");
        if (!match.isValid()) throw new IllegalStateException("Match is not valid");

        ITeam homeTeam = match.getHomeTeam();
        ITeam awayTeam = match.getAwayTeam();

        match.addEvent(new StartEvent(0));
        generateMatchEvents(match, homeTeam, true);
        generateMatchEvents(match, awayTeam, false);
        match.addEvent(new EndEvent(90));
        match.setPlayed();
    }

    /**
     * Generates and simulates random events for a given team during a football match.
     * <p>
     * This method iterates through the match duration, randomly selecting available players
     * and generating events such as injuries, shots, fouls, cards, penalties, free kicks,
     * offsides, and corner kicks. The probability and type of each event are determined
     * using random rolls. The method also handles event consequences, such as substitutions
     * after injuries or red cards.
     * </p>
     * <p>
     * Events are added directly to the provided {@link IMatch} instance.
     * </p>
     *
     * @param match      the match in which events are to be generated; must not be {@code null}
     * @param team       the team for which events are being generated; must not be {@code null}
     * @param isHomeTeam {@code true} if the team is the home team, {@code false} if away
     */
    private void generateMatchEvents(IMatch match, ITeam team, boolean isHomeTeam) {
        IPlayer[] players = team.getPlayers();
        int playerCount = 0;
        for (IPlayer player : players) {
            if (player != null) {
                playerCount++;
            }
        }
        if (players == null || players.length == 0) return;

        int matchDuration = 90 + rng.nextInt(10) + 1;

        for (int minute = 1; minute <= matchDuration; minute += rng.nextInt(10) + 1) {
            IPlayer selectedPlayer = null;
            int attempts = 0;

            while (selectedPlayer == null && attempts < 50) {
                IPlayer randomPlayer = players[rng.nextInt(playerCount)];
                if (!isPlayerUnavailable(randomPlayer)) selectedPlayer = randomPlayer;
                attempts++;
            }

            if (selectedPlayer == null) continue;

            double eventRoll = rng.nextDouble();

            if (eventRoll < 0.01) {
                handleInjuryAndSubstitution(match, team.getClub(), selectedPlayer, minute, isHomeTeam);
            } else if (eventRoll < 0.07) {
                if (!selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    match.addEvent(new ShotEvent(selectedPlayer, minute));
                }
            } else if (eventRoll < 0.13) {
                if (!selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    match.addEvent(new ShotOnGoalEvent(selectedPlayer, minute));
                    handleGoalChance(match, selectedPlayer, minute);
                }
            } else if (eventRoll < 0.26) {
                IPlayer taker = findAvailableOpponent(match, isHomeTeam);
                match.addEvent(new FoulEvent(selectedPlayer, minute));

                double consequenceFoulRoll = rng.nextDouble();

                if (consequenceFoulRoll < 0.03) {
                    handleInjuryAndSubstitution(match, team.getClub(), selectedPlayer, minute, isHomeTeam);
                } else if (consequenceFoulRoll < 0.08) {
                    match.addEvent(new RedCardEvent(selectedPlayer, minute));
                    if (redCardCount == redCardPlayers.length) expandRedCardPlayersArray();
                    redCardPlayers[redCardCount++] = selectedPlayer;
                } else if (consequenceFoulRoll < 0.30) {
                    match.addEvent(new YellowCardEvent(selectedPlayer, minute));
                } else if (consequenceFoulRoll < 0.34) {
                    if (taker != null && !taker.getPosition().getDescription().equals("GoalKeeper")) {
                        match.addEvent(new PenaltiesEvent(taker, minute));
                        handleGoalChance(match, taker, minute);
                    }
                } else if (consequenceFoulRoll < 0.60) {
                    if (taker != null) {
                        match.addEvent(new FreeKickEvent(taker, minute));
                        double consequenceFreeKickRoll = rng.nextDouble();

                        if (consequenceFreeKickRoll < 0.07 && !taker.getPosition().getDescription().equals("GoalKeeper")) {
                            match.addEvent(new ShotEvent(taker, minute));
                        } else if (consequenceFreeKickRoll < 0.13 && !taker.getPosition().getDescription().equals("GoalKeeper")) {
                            match.addEvent(new ShotOnGoalEvent(taker, minute));
                            handleGoalChance(match, selectedPlayer, minute);
                        }
                    }
                }
            } else if (eventRoll < 0.33) {
                if (!selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    match.addEvent(new OffSideEvent(selectedPlayer, minute));
                }
            } else if (eventRoll < 0.40) {
                if (!selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    match.addEvent(new CornerKickEvent(selectedPlayer, minute));
                    double consequenceCornerKickRoll = rng.nextDouble();

                    if (consequenceCornerKickRoll < 0.07) {
                        match.addEvent(new ShotEvent(selectedPlayer, minute));
                    } else if (consequenceCornerKickRoll < 0.13) {
                        match.addEvent(new ShotOnGoalEvent(selectedPlayer, minute));
                        handleGoalChance(match, selectedPlayer, minute);
                    }
                }
            }
        }
    }

    /**
     * Handles the occurrence of a player injury during a match and performs a substitution if possible.
     * <p>
     * This method adds an {@link InjuryEvent} for the injured player at the specified minute,
     * tracks the player as injured, and, if the team has substitutions remaining, finds a suitable
     * substitute and adds a {@link SubstitutionEvent}. The method also updates the internal counts
     * and arrays for injured and substituted players, as well as the team's substitution count.
     * </p>
     *
     * @param match      the match in which the injury and substitution occur; must not be {@code null}
     * @param team       the club to which the injured player belongs; must not be {@code null}
     * @param player     the player who is injured; must not be {@code null}
     * @param minute     the minute in the match when the injury occurs
     * @param isHomeTeam {@code true} if the team is the home team, {@code false} if away
     */
    private void handleInjuryAndSubstitution(IMatch match, IClub team, IPlayer player, int minute, boolean isHomeTeam) {
        match.addEvent(new InjuryEvent(player, minute));
        if (injuredCount == injuredPlayers.length) expandInjuredPlayersArray();
        injuredPlayers[injuredCount++] = player;

        if ((isHomeTeam && homeSubstitutions < MAX_SUBSTITUTIONS) || (!isHomeTeam && awaySubstitutions < MAX_SUBSTITUTIONS)) {
            IPlayer substitute = findSubstitute(team, player.getPosition().getDescription());
            if (substitute != null) {
                match.addEvent(new SubstitutionEvent(player, substitute, minute));
                if (substitutedCount == substitutedPlayers.length) expandSubstitutedPlayersArray();
                substitutedPlayers[substitutedCount++] = player;

                if (isHomeTeam) homeSubstitutions++;
                else awaySubstitutions++;
            }
        }
    }

    /**
     * Evaluates the chance for a player to score a goal and adds a {@link GoalEvent} to the match if successful.
     * <p>
     * The probability of scoring depends on the player's shooting skill:
     * <ul>
     *   <li>If {@code player.getShooting() > 50}, the player has a 50% chance to score.</li>
     *   <li>If {@code player.getShooting() <= 50}, the player has a 30% chance to score.</li>
     * </ul>
     * </p>
     *
     * @param match  the match in which the goal event may be added; must not be {@code null}
     * @param player the player attempting to score; must not be {@code null}
     * @param minute the minute in the match when the goal chance occurs
     */
    private void handleGoalChance(IMatch match, IPlayer player, int minute) {
        double goalChance = rng.nextDouble();
        if (player.getShooting() > 50 && goalChance < 0.50) {
            match.addEvent(new GoalEvent(player, minute));
        } else if (player.getShooting() <= 50 && goalChance < 0.30) {
            match.addEvent(new GoalEvent(player, minute));
        }
    }

    /**
     * Doubles the capacity of the {@code redCardPlayers} array to accommodate more players.
     * <p>
     * This method is called when the array is full and a new player needs to be added.
     * </p>
     */
    private void expandRedCardPlayersArray() {
        IPlayer[] newArray = new IPlayer[redCardPlayers.length * 2];
        for (int i = 0; i < redCardPlayers.length; i++) newArray[i] = redCardPlayers[i];
        redCardPlayers = newArray;
    }

    /**
     * Doubles the capacity of the {@code injuredPlayers} array to accommodate more players.
     * <p>
     * This method is called when the array is full and a new player needs to be added.
     * </p>
     */
    private void expandInjuredPlayersArray() {
        IPlayer[] newArray = new IPlayer[injuredPlayers.length * 2];
        for (int i = 0; i < injuredPlayers.length; i++) newArray[i] = injuredPlayers[i];
        injuredPlayers = newArray;
    }

    /**
     * Doubles the capacity of the {@code substitutedPlayers} array to accommodate more players.
     * <p>
     * This method is called when the array is full and a new player needs to be added.
     * </p>
     */
    private void expandSubstitutedPlayersArray() {
        IPlayer[] newArray = new IPlayer[substitutedPlayers.length * 2];
        for (int i = 0; i < substitutedPlayers.length; i++) newArray[i] = substitutedPlayers[i];
        substitutedPlayers = newArray;
    }

    /**
     * Checks if a player is currently unavailable due to a red card, injury, or substitution.
     *
     * @param player the player to check; must not be {@code null}
     * @return {@code true} if the player is unavailable for selection; {@code false} otherwise
     */
    private boolean isPlayerUnavailable(IPlayer player) {
        for (int i = 0; i < redCardCount; i++)
            if (redCardPlayers[i] != null && redCardPlayers[i].equals(player)) return true;
        for (int i = 0; i < injuredCount; i++)
            if (injuredPlayers[i] != null && injuredPlayers[i].equals(player)) return true;
        for (int i = 0; i < substitutedCount; i++)
            if (substitutedPlayers[i] != null && substitutedPlayers[i].equals(player)) return true;
        return false;
    }

    /**
     * Finds a suitable substitute for a given position from the team's players.
     * <p>
     * Only players beyond the starting 11 are considered. The substitute must match the specified position
     * and must not be currently unavailable (due to red card, injury, or prior substitution).
     * </p>
     *
     * @param team     the club whose bench is being searched; must not be {@code null}
     * @param position the position description to match (e.g., "Defender")
     * @return an available substitute player for the given position, or {@code null} if none found
     */
    private IPlayer findSubstitute(IClub team, String position) {
        IPlayer[] players = team.getPlayers();
        if (players.length <= 11) return null;
        for (int i = 11; i < players.length; i++) {
            if (players[i] != null) {
                IPlayer player = players[i];
                if (!isPlayerUnavailable(player) && player.getPosition().getDescription().equals(position))
                    return player;
            }
        }
        return null;
    }

    /**
     * Finds the first available opponent player who is not unavailable (not sent off, injured, or substituted).
     *
     * @param match      the current match context; must not be {@code null}
     * @param isHomeTeam {@code true} if searching for an opponent of the home team, {@code false} for away
     * @return the first available opponent player, or {@code null} if none are available
     */
    private IPlayer findAvailableOpponent(IMatch match, boolean isHomeTeam) {
        IClub opponent = isHomeTeam ? match.getAwayClub() : match.getHomeClub();
        IPlayer[] players = opponent.getPlayers();
        for (IPlayer player : players) {
            if (!isPlayerUnavailable(player)) return player;
        }
        return null;
    }

}
