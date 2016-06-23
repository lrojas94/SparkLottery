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
    @Expose
    private String type;
    @Column(name = "bet_amount")
    @Expose
    private double betAmount; // Should it be double type?
    @Column(name = "is_winner")
    @Expose
    private Bool isWinner;
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    @Expose
    private User owner;

    // Missing Number Relation, should be added

    public Ticket() {}

    public Ticket(Date emitDate, String type, int betAmount, Bool isWinner) {
        this.emitDate = emitDate;
        this.type = type;
        this.betAmount = betAmount;
        this.isWinner = isWinner;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public Bool getIsWinner() {
        return isWinner;
    }

    public void setIsWinner(Bool isWinner) {
        this.isWinner = isWinner;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
