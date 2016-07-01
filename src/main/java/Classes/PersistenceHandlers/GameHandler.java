package Classes.PersistenceHandlers;

import Classes.Data.Game;
import Classes.Data.User;
import Classes.HelperClasses.DatabaseHandler;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by MEUrena on 6/24/16.
 * All rights reserved.
 */
public class GameHandler extends DatabaseHandler<Game> {

    public static GameHandler instance;

    public GameHandler() { super(Game.class); }

    public static GameHandler getInstance() {
        if (instance == null) {
            instance = new GameHandler();
        }
        return instance;
    }

    public Game findActiveLoto() throws Exception {
        EntityManager em = getEntityManager();
        try {
            return (Game) em.createNamedQuery(Game.NAMED_QUERY_ACTIVE_LOTO).getSingleResult();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    public Game findActivePale() throws Exception {
        EntityManager em = getEntityManager();
        try {
            return (Game) em.createNamedQuery(Game.NAMED_QUERY_ACTIVE_PALE).getSingleResult();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    public Game findLatestLoto() throws Exception {
        EntityManager em = getEntityManager();
        try {
            return (Game) em.createNamedQuery(Game.NAMED_QUERY_LATEST_LOTO).setMaxResults(1).getSingleResult();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    public Game findLatestPale() throws Exception {
        EntityManager em = getEntityManager();
        try {
            return (Game) em.createNamedQuery(Game.NAMED_QUERY_LATEST_PALE).setMaxResults(1).getSingleResult();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

}
