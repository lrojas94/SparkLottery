package Classes.Data;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MEUrena on 6/23/16.
 * All rights reserved.
 */

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue
    @Expose
    private int id;
    @Column(name = "description")
    @Expose
    private String description;
    @Column(name = "balance")
    @Expose
    private double balance;
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    public Account(String description, double balance) {
        this.description = description;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
