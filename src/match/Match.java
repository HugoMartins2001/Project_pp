package match;

import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import event.GoalEvent;

import java.awt.color.ICC_ColorSpace;
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
            throw new IllegalStateException(" Home Club is not inicialized");
        }
        return homeClub;
    }

    @Override
    public IClub getAwayClub() {
        if (awayClub == null) {
            throw new IllegalStateException(" Away Club is not inicialized");
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
            throw new IllegalStateException(" Home Team is not inicialized");
        }
        return homeTeam;
    }

    @Override
    public ITeam getAwayTeam() {
        if (awayTeam == null) {
            throw new IllegalStateException(" Away Team is not inicialized");
        }
        return awayTeam;
    }

    @Override
    public void setPlayed() {
        isPlayed = true;
    }

    @Override
    public int getTotalByEvent(Class aClass, IClub iClub) {
        return 0;
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

        int homeGoals = 0;
        int awayGoals = 0;

        IEvent[] matchEvents = events.getEvents();

        for (int i = 0; i < matchEvents.length; i++) {
            if (matchEvents[i].getClass().equals(GoalEvent.class)) {
                GoalEvent goalEvent = (GoalEvent) matchEvents[i];
                if (goalEvent.getTeam().equals(homeTeam)) {
                    homeGoals++;
                } else {
                    awayGoals++;
                }
            }
        }

        if (homeGoals > awayGoals) {
            return homeTeam;
        } else if (awayGoals > homeGoals) {
            return awayTeam;
        } else {
            return null;
        }
    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public void setTeam(ITeam iTeam) {
        if (iTeam == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        if (isPlayed) {
            throw new IllegalStateException("Cannot set team - match already played");
        }

        if(homeClub.equals(iTeam.getClub())) {
            homeTeam = iTeam;
        } else if(awayClub.equals(iTeam.getClub())) {
            awayTeam = iTeam;
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
}
