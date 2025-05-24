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

        int homeGoals = generateRandomGoals();
        int awayGoals = generateRandomGoals();

        generateMatchEvents(match, homeTeam);
        generateMatchEvents(match, awayTeam);

        generateGoalsForTeam(match, homeTeam, homeGoals);
        generateGoalsForTeam(match, awayTeam, awayGoals);

        match.addEvent(new EndEvent(90));
        match.setPlayed();
    }

    private int generateRandomGoals() {
        return rng.nextInt(6) + rng.nextInt(6);
    }

    private void generateGoalsForTeam(IMatch match, IClub team, int goals) {
        IPlayer[] players = team.getPlayers();
        if (players == null || players.length == 0) return;

        for (int i = 0; i < goals; i++) {
            IPlayer scorer = players[rng.nextInt(players.length)];
            int minute = rng.nextInt(90) + 1;
            match.addEvent(new GoalEvent(scorer, minute));
        }
    }

    private void generateMatchEvents(IMatch match, IClub team) {
        IPlayer[] players = team.getPlayers();
        if (players == null || players.length == 0) return;

        for (int minute = 1; minute <= 90; minute += rng.nextInt(10) + 1) {
            IPlayer selectedPlayer = players[rng.nextInt(players.length)];
            double eventRoll = rng.nextDouble();

            if (eventRoll < 0.02) {
                match.addEvent(new RedCardEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.05) {
                match.addEvent(new PenaltiesEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.09) {
                match.addEvent(new YellowCardEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.14) {
                match.addEvent(new FreeKickEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.2) {
                match.addEvent(new OffSideEvent(selectedPlayer, minute));
            }
            else if (eventRoll < 0.27) {
                match.addEvent(new CornerKickEvent(selectedPlayer, minute));
            }
        }
    }
}