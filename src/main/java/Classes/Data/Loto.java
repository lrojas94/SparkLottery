package Classes.Data;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MEUrena on 6/23/16.
 * All rights reserved.
 */
@Entity
@Table(name = "Lotos")
public class Loto implements Serializable {

    @Id
    @GeneratedValue
    @Expose
    private int id;

    public Loto() {}


}
