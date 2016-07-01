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
    private Date emitDate = new Date();
    @Column(name = "bet_amount")
    @Expose
    private double betAmount; // Should it be double type?
    @Column
    private String numbers = "";
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

    public Ticket(Date emitDate, int betAmount, Bool isWinner) {
        this.emitDate = emitDate;
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

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
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


    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
