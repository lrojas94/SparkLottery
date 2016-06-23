package Classes.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * Created by MEUrena on 6/23/16.
 * All rights reserved.
 */

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "User.findUserByUsername",
                query = "SELECT u FROM User u WHERE u.username = :username"
        )
})

public class User implements Serializable {
    public static String QUERY_NAME_FIND_BY_USERNAME = "User.findUserByUsername";

    @Id
    @GeneratedValue
    @Expose
    private int id;
    @Column(name = "first_name")
    @Expose
    private String firstName;
    @Column(name = "last_name")
    @Expose
    private String lastName;
    @Column(name = "email")
    @Expose
    private String email;
    @Column(name = "username")
    @Expose
    private String username;
    @Column(name = "is_administrator")
    @Expose
    private Boolean isAdmin;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Account account;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Ticket> tickets = new HashSet<>();

    public User() {}

    public User(String firstName, String lastName, String email, String username, Boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }

}
