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
    private IPlayer[] redCardPlayers = new IPlayer[1];
    private int redCardCount = 0;

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

        for (int minute = 1; minute <= 90; minute += rng.nextInt(10) + 1) {
            IPlayer selectedPlayer = null;
            do{
                IPlayer randomPlayer = players[rng.nextInt(team.getPlayerCount())];
                if(playerHasRedCard(randomPlayer)) {
                    continue;
                }
                selectedPlayer = randomPlayer;
            }while(selectedPlayer == null);

            double eventRoll = rng.nextDouble();

            if (eventRoll < 0.02) {
                match.addEvent(new RedCardEvent(selectedPlayer, minute));
                if(redCardCount == redCardPlayers.length) {
                    expandRedCardPlayersArray();
                }
                redCardPlayers[redCardCount++] = selectedPlayer;
            }
            else if (eventRoll < 0.05) {
                match.addEvent(new PenaltiesEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.09) {
                if(selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    continue;
                }
                match.addEvent(new GoalEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.14) {
                match.addEvent(new YellowCardEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.2) {
                match.addEvent(new FreeKickEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.27) {
                if(selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    continue;
                }
                match.addEvent(new OffSideEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.35) {
                if(selectedPlayer.getPosition().getDescription().equals("GoalKeeper")) {
                    continue;
                }
                match.addEvent(new CornerKickEvent(selectedPlayer, minute));
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

    private boolean playerHasRedCard(IPlayer player) {
        for (int i = 0; i < redCardCount; i++) {
            if (redCardPlayers[i] != null && redCardPlayers[i].equals(player)) {
                return true;
            }
        }
        return false;
    }
}