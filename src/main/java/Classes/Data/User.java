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
        ),
        @NamedQuery(
                name = "User.findUserByUsernameAndPassword",
                query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password"
        ),
        @NamedQuery(
                name = "User.findAllUsersToTransferTo",
                query = "SELECT u from User u WHERE u.username <> :username AND u.username <> 'admin'"
        ),
        @NamedQuery(
                name = "User.findAllUsers",
                query = "select u from User u where u.username <> 'admin' order by u.firstName"
        )
})

public class User implements Serializable {
    public static String QUERY_NAME_FIND_BY_USERNAME = "User.findUserByUsername";
    public static String QUERY_NAME_FIND_BY_USERNAME_AND_PASSWORD = "User.findUserByUsernameAndPassword";
    public static String QUERY_NAME_FIND_TRANSFERABLE = "User.findAllUsersToTransferTo";
    public static String QUERY_NAME_FIND_ALL_USERS = "User.findAllUsers";
    @Id
    @GeneratedValue
    @Expose
    private int id;
    @Column(name = "first_name")
    @Expose
    private String firstName = "";
    @Column(name = "last_name")
    @Expose
    private String lastName = "";
    @Column(name = "email")
    @Expose
    private String email = "";
    @Column(name = "username", nullable = false)
    @Expose
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "is_administrator")
    private Boolean isAdmin = false;
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Account account;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Ticket> tickets = new HashSet<>();
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<Winner> wins = new HashSet<>();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Winner> getWins() {
        return wins;
    }

    public void setWins(Set<Winner> wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }

    public boolean canPublishInWinners(){
        return tickets.stream().filter(ticket -> ticket.getWinnerIn() != null && !ticket.getWinnerIn().isWinnerCommented()).findAny().isPresent();
    }

}
