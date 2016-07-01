package Classes.Data;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MEUrena on 6/23/16.
 * All rights reserved.
 */
@Entity
@Table(name = "Game")
@NamedQueries({
        @NamedQuery(
                name = "Game.findActiveLoto",
                query = "SELECT g FROM Game g WHERE g.type = 'LOTO' AND g.winningTicket = null"
        ),
        @NamedQuery(
                name = "Game.findActivePale",
                query = "SELECT g FROM Game g WHERE g.type = 'PALE' AND g.winningTicket = null"
        ),
        @NamedQuery(
                name = "Game.findLatestLoto",
                query = "SELECT g FROM Game g WHERE g.type = 'LOTO' AND g.winningTicket <> null AND g.winnerCommented = false order by g.id desc"
        ),
        @NamedQuery(
                name = "Game.findLatestPale",
                query = "SELECT g FROM Game g WHERE g.type = 'PALE' AND g.winningTicket <> null AND g.winnerCommented = false order by g.id desc"
        ),
})
public class Game implements Serializable {
    public static String NAMED_QUERY_ACTIVE_LOTO = "Game.findActiveLoto";
    public static String NAMED_QUERY_ACTIVE_PALE= "Game.findActivePale";
    public static String NAMED_QUERY_LATEST_PALE= "Game.findLatestPale";
    public static String NAMED_QUERY_LATEST_LOTO= "Game.findLatestLoto";
    @Id
    @GeneratedValue
    @Expose
    private int id;
    @Column(name = "baseAmmount")
    private long baseAmmount = 1000000;
    @Enumerated(EnumType.STRING)
    private GameType type;
    @OneToOne
    private Ticket winningTicket = null;
    @OneToMany(mappedBy = "issuedIn")
    private List<Ticket> issuedTickets = new ArrayList<>();
    @Column
    private boolean winnerCommented = false;

    public Game() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBaseAmmount() {
        return baseAmmount;
    }

    public void setBaseAmmount(long baseAmmount) {
        this.baseAmmount = baseAmmount;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public Ticket getWinningTicket() {
        return winningTicket;
    }

    public void setWinningTicket(Ticket winningTicket) {
        this.winningTicket = winningTicket;
    }

    public List<Ticket> getIssuedTickets() {
        return issuedTickets;
    }

    public void setIssuedTickets(List<Ticket> issuedTickets) {
        this.issuedTickets = issuedTickets;
    }

    public boolean isWinnerCommented() {
        return winnerCommented;
    }

    public void setWinnerCommented(boolean winnerCommented) {
        this.winnerCommented = winnerCommented;
    }

    public enum GameType {
        PALE,
        LOTO
    }

    public double getWinnersAmmount(){
        switch (type){
            case LOTO:
                // Return sum of all issuedIn:
                // Note that Winning ticket is included in issuedTickets.
                return baseAmmount + issuedTickets.stream().mapToDouble(ticket -> ticket.getBetAmount()).sum();
            case PALE:
                if(winningTicket != null)
                    return winningTicket.getBetAmount() * Constants.PALE_MULTIPLIER;
                else
                    return 0; //No winning ticket issued yet. PALE does not accumulate any money.
        }

        return 0;
    }

    public double getMoneySpent(){ //How much money have been spent on a single Game game.
        return baseAmmount + issuedTickets.stream().mapToDouble(Ticket::getBetAmount).sum();
    }


}
