package match;

import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.awt.color.ICC_ColorSpace;
import java.io.IOException;

public class Match implements IMatch {

    private IClub awayClub;
    private IClub homeClub;
    private ITeam awayTeam;
    private ITeam homeTeam;
    private ITeam winnerTeam;
    private int round;
    private IEvent[] events;
    private boolean isPlayed;


    @Override
    public IClub getHomeClub() {
        if(homeClub == null){
            throw new IllegalStateException(" Home Club is not inicialized");
        }
        return homeClub;
    }

    @Override
    public IClub getAwayClub() {
        if(awayClub == null){
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
        if(homeTeam == null){
            throw new IllegalStateException(" Home Team is not inicialized");
        }
        return homeTeam;
    }

    @Override
    public ITeam getAwayTeam() {
        if(awayTeam == null){
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
        if(awayTeam == null || homeTeam == null || homeTeam.equals(awayTeam)){
            return false;
        }
        if(
        return true;
    }

    @Override
    public ITeam getWinner() {
        return null;
    }

    @Override
    public int getRound() {
        return 0;
    }

    @Override
    public void setTeam(ITeam iTeam) {

    }

    @Override
    public void exportToJson() throws IOException {

    }

    @Override
    public void addEvent(IEvent iEvent) {

    }

    @Override
    public IEvent[] getEvents() {
        return new IEvent[0];
    }

    @Override
    public int getEventCount() {
        return 0;
    }
}
