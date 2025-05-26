package simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import event.*;
import match.Match;

import java.util.Random;

public class MatchSimulatorStrat implements MatchSimulatorStrategy{

    private final Random rng = new Random();
    private IPlayer[] redCardPlayers;
    private IPlayer[] injuredPlayers;
    private IPlayer[] substitutedPlayers;
    private int redCardCount;
    private int injuredCount;
    private int substitutedCount;

    public void simulate(IMatch match) {
        if(match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        if (!((Match)match).isInitialized()) {
            throw new IllegalStateException("Match is not initialized");
        }

        if (match.isPlayed()) {
            throw new IllegalStateException("Match has already been played");
        }

        if (!match.isValid()) {
            throw new IllegalStateException("Match is not valid");
        }

        match.addEvent(new StartEvent(0));

        IClub homeTeam = match.getHomeClub();
        IClub awayTeam = match.getAwayClub();

        generateMatchEvents(match, homeTeam);
        generateMatchEvents(match, awayTeam);

        match.addEvent(new EndEvent(90));
        match.setPlayed();
    }

    private void generateMatchEvents(IMatch match, IClub team) {
        IPlayer[] players = team.getPlayers();
        if (players == null || players.length == 0) return;

        redCardPlayers = new IPlayer[1];
        injuredPlayers = new IPlayer[1];
        substitutedPlayers = new IPlayer[3];
        redCardCount = 0;
        injuredCount = 0;
        substitutedCount = 0;

        int matchDuration = 90 + rng.nextInt(10) + 1;

        for (int minute = 1; minute <= matchDuration; minute += rng.nextInt(10) + 1) {
            IPlayer selectedPlayer = null;
            int attempts = 0;

            while (selectedPlayer == null && attempts < 50) {
                IPlayer randomPlayer = players[rng.nextInt(team.getPlayerCount())];
                if (!isPlayerUnavailable(randomPlayer)) {
                    selectedPlayer = randomPlayer;
                }
                attempts++;
            }

            if (selectedPlayer == null) continue;

            double eventRoll = rng.nextDouble();

            if(eventRoll < 0.006) {
                match.addEvent(new InjuryEvent(selectedPlayer, minute));
                if (injuredCount == injuredPlayers.length) {
                    expandInjuredPlayersArray();
                }
                injuredPlayers[injuredCount++] = selectedPlayer;
                if (substitutedCount < substitutedPlayers.length) {
                    IPlayer substitute = findSubstitute(team, selectedPlayer.getPosition().getDescription());
                    if (substitute != null) {
                        match.addEvent(new SubstitutionEvent(selectedPlayer, substitute, minute));
                        substitutedPlayers[substitutedCount++] = selectedPlayer;
                    }
                }
            }
            else if (eventRoll < 0.05) {
                if (!selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    match.addEvent(new ShotEvent(selectedPlayer, minute));
                }
            } else if (eventRoll < 0.08) {
                if (!selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    match.addEvent(new ShotOnGoalEvent(selectedPlayer, minute));

                    double goalChance = rng.nextDouble();
                    if (goalChance < 0.30) {
                        match.addEvent(new GoalEvent(selectedPlayer, minute));
                    }
                }
            }
            else if(eventRoll < 0.20) {
                match.addEvent(new FoulEvent(selectedPlayer, minute));

                double consequenceRoll = rng.nextDouble();

                if(consequenceRoll < 0.04) {
                    match.addEvent(new InjuryEvent(selectedPlayer, minute));
                    if (injuredCount == injuredPlayers.length) expandInjuredPlayersArray();
                    injuredPlayers[injuredCount++] = selectedPlayer;

                    if (substitutedCount < substitutedPlayers.length) {
                        IPlayer substitute = findSubstitute(team, selectedPlayer.getPosition().getDescription());
                        if (substitute != null) {
                            match.addEvent(new SubstitutionEvent(selectedPlayer, substitute, minute));
                            substitutedPlayers[substitutedCount++] = selectedPlayer;
                        }
                    }
                }
                else if (consequenceRoll < 0.10) {
                    match.addEvent(new RedCardEvent(selectedPlayer, minute));
                    if (redCardCount == redCardPlayers.length) {
                        expandRedCardPlayersArray();
                    }
                    redCardPlayers[redCardCount++] = selectedPlayer;
                }
                else if (consequenceRoll < 0.30) {
                    match.addEvent(new YellowCardEvent(selectedPlayer, minute));
                }
                else if (consequenceRoll < 0.40) {
                    if (!selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                        match.addEvent(new PenaltiesEvent(selectedPlayer, minute));
                    }
                }
                else if (consequenceRoll < 0.60) {
                    match.addEvent(new FreeKickEvent(selectedPlayer, minute));
                }
            }
            else if (eventRoll < 0.30) {
                if (!selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    match.addEvent(new OffSideEvent(selectedPlayer, minute));
                }
            }
            else if (eventRoll < 0.45) {
                if (!selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    match.addEvent(new CornerKickEvent(selectedPlayer, minute));
                }
            }
        }
    }

    private void expandRedCardPlayersArray() {
        IPlayer[] newArray = new IPlayer[redCardPlayers.length * 2];
        for (int i = 0; i < redCardPlayers.length; i++) {
            newArray[i] = redCardPlayers[i];
        }
        redCardPlayers = newArray;
    }

    private void expandInjuredPlayersArray() {
        IPlayer[] newArray = new IPlayer[injuredPlayers.length * 2];
        for (int i = 0; i < injuredPlayers.length; i++) {
            newArray[i] = injuredPlayers[i];
        }
        injuredPlayers = newArray;
    }

    private boolean isPlayerUnavailable(IPlayer player) {
        for (int i = 0; i < redCardCount; i++) {
            if (redCardPlayers[i] != null && redCardPlayers[i].equals(player)) return true;
        }
        for (int i = 0; i < injuredCount; i++) {
            if (injuredPlayers[i] != null && injuredPlayers[i].equals(player)) return true;
        }
        for (int i = 0; i < substitutedCount; i++) {
            if (substitutedPlayers[i] != null && substitutedPlayers[i].equals(player)) return true;
        }
        return false;
    }

    private IPlayer findSubstitute(IClub team, String position) {
        IPlayer[] players = team.getPlayers();
        for (int i = 11; i < players.length; i++) {
            IPlayer player = players[i];
            if (!isPlayerUnavailable(player) && player.getPosition().getDescription().equals(position)) {
                return player;
            }
        }
        return null;
    }
}