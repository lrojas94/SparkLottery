package Classes.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by luis on 6/29/16.
 */


@Entity
@Table(name = "transactions")
public class Transaction {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public enum Method{
        USER,
        CREDITCARD,
        GAMETICKET,
        WINNER
    }
    @Id
    @GeneratedValue
    private int id;
    @Column
    private double ammount;
    @Enumerated(EnumType.STRING)
    private Method method;
    @Column
    private String description = ""; //Server set description (E.G. From user X yo User Y or With CC 0007)
    @Column
    private String message = ""; //A message the user leaves.
    @Column
    private Date issuedDate = new Date();
    @ManyToOne
    private Account owner;

    public Transaction() {
    }

    public Transaction(double ammount, Method method, String description, String message, Date issuedDate, Account owner) {
        this.ammount = ammount;
        this.method = method;
        this.description = description;
        this.message = message;
        this.issuedDate = issuedDate;
        this.owner = owner;
    }
}
