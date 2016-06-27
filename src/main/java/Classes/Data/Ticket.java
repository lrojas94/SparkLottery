package Classes.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by MEUrena on 6/23/16.
 * All rights reserved.
 */

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue
    @Expose
    private int id;
    @Column(name = "emit_date")
    @Expose
    private Date emitDate;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @Expose
    private Game.GameType type;
    @Column(name = "bet_amount")
    @Expose
    private double betAmount; // Should it be double type?
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    @Expose
    private User owner;
    @OneToOne(mappedBy = "winningTicket")
    private Game winnerIn = null;
    @ManyToOne
    private Game issuedIn;

    // Missing Number Relation, should be added

    public Ticket() {}

    public Ticket(Date emitDate, Game.GameType type, int betAmount, Bool isWinner) {
        this.emitDate = emitDate;
        this.type = type;
        this.betAmount = betAmount;
    }

    public int getId() {
        return id;
    }

    public Date getEmitDate() {
        return emitDate;
    }

    public void setEmitDate(Date emitDate) {
        this.emitDate = emitDate;
    }

    public Game.GameType getType() {
        return type;
    }

    public void setType(Game.GameType type) {
        this.type = type;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    public Game getWinnerIn() {
        return winnerIn;
    }

    public void setWinnerIn(Game winnerIn) {
        this.winnerIn = winnerIn;
    }


    public Game getIssuedIn() {
        return issuedIn;
    }

    public void setIssuedIn(Game issuedIn) {
        this.issuedIn = issuedIn;
    }
}
