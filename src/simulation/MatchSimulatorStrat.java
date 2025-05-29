/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */

package simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import event.*;
import match.Match;

import java.util.Random;

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

    private void generateMatchEvents(IMatch match, ITeam team, boolean isHomeTeam) {
        IPlayer[] players = team.getPlayers();
        int playerCount = 0;
        for(IPlayer player : players){
            if(player != null){
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

    private void handleGoalChance(IMatch match, IPlayer player, int minute) {
        double goalChance = rng.nextDouble();
        if (player.getShooting() > 50 && goalChance < 0.50) {
            match.addEvent(new GoalEvent(player, minute));
        } else if (player.getShooting() <= 50 && goalChance < 0.30) {
            match.addEvent(new GoalEvent(player, minute));
        }
    }

    private void expandRedCardPlayersArray() {
        IPlayer[] newArray = new IPlayer[redCardPlayers.length * 2];
        for (int i = 0; i < redCardPlayers.length; i++) newArray[i] = redCardPlayers[i];
        redCardPlayers = newArray;
    }

    private void expandInjuredPlayersArray() {
        IPlayer[] newArray = new IPlayer[injuredPlayers.length * 2];
        for (int i = 0; i < injuredPlayers.length; i++) newArray[i] = injuredPlayers[i];
        injuredPlayers = newArray;
    }

    private void expandSubstitutedPlayersArray() {
        IPlayer[] newArray = new IPlayer[substitutedPlayers.length * 2];
        for (int i = 0; i < substitutedPlayers.length; i++) newArray[i] = substitutedPlayers[i];
        substitutedPlayers = newArray;
    }

    private boolean isPlayerUnavailable(IPlayer player) {
        for (int i = 0; i < redCardCount; i++)
            if (redCardPlayers[i] != null && redCardPlayers[i].equals(player)) return true;
        for (int i = 0; i < injuredCount; i++)
            if (injuredPlayers[i] != null && injuredPlayers[i].equals(player)) return true;
        for (int i = 0; i < substitutedCount; i++)
            if (substitutedPlayers[i] != null && substitutedPlayers[i].equals(player)) return true;
        return false;
    }

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

    private IPlayer findAvailableOpponent(IMatch match, boolean isHomeTeam) {
        IClub opponent = isHomeTeam ? match.getAwayClub() : match.getHomeClub();
        IPlayer[] players = opponent.getPlayers();
        for (IPlayer player : players) {
            if (!isPlayerUnavailable(player)) return player;
        }
        return null;
    }
}
