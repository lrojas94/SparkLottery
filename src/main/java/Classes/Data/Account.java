package Classes.Data;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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
    private String description = "";
    @Column(name = "balance")
    @Expose
    private double balance = 0;
    @OneToOne
    @JoinColumn(name = "owner", nullable = false)
    private User owner;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<Transaction> transactions;

    public Account() {}

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
        return transactions.stream().mapToDouble(Transaction::getAmmount).sum();
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


    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
