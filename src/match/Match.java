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

import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import event.GoalEvent;
import event.PlayerEvent;

import java.io.IOException;

public class Match implements IMatch {

    private IClub awayClub;
    private IClub homeClub;
    private ITeam awayTeam;
    private ITeam homeTeam;
    private ITeam winnerTeam;
    private int round;
    private IEventManager events;
    private boolean isPlayed;


    @Override
    public IClub getHomeClub() {
        if (homeClub == null) {
            throw new IllegalStateException("Home Club is not inicialized!");
        }
        return homeClub;
    }

    @Override
    public IClub getAwayClub() {
        if (awayClub == null) {
            throw new IllegalStateException("Away Club is not inicialized");
        }
        return awayClub;
    }

    //TODO: AVISAR O STOR SOBRE RETORNAR MATCH TIME!!!
    @Override
    public boolean isPlayed() {
        return isPlayed;
    }

    @Override
    public ITeam getHomeTeam() {
        if (homeTeam == null) {
            throw new IllegalStateException("Home Team is not inicialized");
        }
        return homeTeam;
    }

    @Override
    public ITeam getAwayTeam() {
        if (awayTeam == null) {
            throw new IllegalStateException("Away Team is not inicialized");
        }
        return awayTeam;
    }

    @Override
    public void setPlayed() {
        isPlayed = true;
    }

    @Override
    public int getTotalByEvent(Class eventClass, IClub team) {
        if(eventClass == null || team == null) {
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

    @Override
    public boolean isValid() {
        if (awayTeam == null || homeTeam == null || homeTeam.equals(awayTeam)) {
            return false;
        }
        return true;
    }


    @Override
    public ITeam getWinner() {
        if (!isPlayed) {
            throw new IllegalStateException("The match has not been played yet");
        }
        if (winnerTeam == null) {
            throw new IllegalStateException("The match has not a winner yet");
        }

        int homeGoals = getTotalByEvent(GoalEvent.class, homeClub);
        int awayGoals = getTotalByEvent(GoalEvent.class, awayClub);

        if (homeGoals > awayGoals) {
            winnerTeam = homeTeam;
        } else if (awayGoals > homeGoals) {
            winnerTeam = awayTeam;
        } else {
            winnerTeam = null;
        }
        return winnerTeam;
    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public void setTeam(ITeam team) {
        if (team == null) {
            throw new IllegalArgumentException("Team is not selected!");
        }
        if (isPlayed) {
            throw new IllegalStateException("Cannot set a team because match was already played!");
        }
        if(homeClub.equals(team.getClub())) {
            homeTeam = team;
        } else if(awayClub.equals(team.getClub())) {
            awayTeam = team;
        } else {
            throw new IllegalStateException("The club does not belong to the match");
        }
    }

    @Override
    public void exportToJson() throws IOException {

    }

    @Override
    public void addEvent(IEvent iEvent) {
        events.addEvent(iEvent);
    }

    @Override
    public IEvent[] getEvents() {
        return events.getEvents();
    }

    @Override
    public int getEventCount() {
        return events.getEventCount();
    }

    public void reset(){
        awayTeam = null;
        homeTeam = null;
        winnerTeam = null;
        isPlayed = false;
    }
}
