package Classes.Data;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MEUrena on 6/29/16.
 * All rights reserved.
 */

@Entity
@Table(name = "winners")
@NamedQueries({
        @NamedQuery(
                name = "Winner.findWinnersInDescOrder",
                query = "SELECT w FROM Winner w ORDER BY w.id DESC"
        ),
        @NamedQuery(
                name = "Winner.count",
                query = "SELECT count(w) FROM Winner w"
        )
})
public class Winner implements Serializable {

    public static String QUERY_NAME_FIND_WINNERS_IN_DESC_ORDER = "Winner.findWinnersInDescOrder";
    public static String QUERY_NAME_COUNT_WINNERS = "Winner.count";

    @Id
    @GeneratedValue
    @Expose
    private int id;
    @Column(name = "comment")
    @Expose
    private String comment;
    @Column(name = "path")
    @Expose
    private String path;
    @ManyToOne
    @JoinColumn(name = "player", nullable = false)
    @Expose
    private User player;

    public Winner() {}

    public Winner(String comment, String path) {
        this.comment = comment;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

}
