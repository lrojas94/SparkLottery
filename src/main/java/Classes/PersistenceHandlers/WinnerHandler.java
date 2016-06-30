package Classes.PersistenceHandlers;

import Classes.Data.Winner;
import Classes.HelperClasses.DatabaseHandler;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by MEUrena on 6/30/16.
 * All rights reserved.
 */
public class WinnerHandler extends DatabaseHandler<Winner> {

    private static WinnerHandler instance;

    private WinnerHandler() { super(Winner.class); }

    public static WinnerHandler getInstance() {
        if (instance == null) {
            instance = new WinnerHandler();
        }

        return instance;
    }

    public List<Winner> findWinnersWithLimit(int limit, int offset) {
        EntityManager em = getEntityManager();
        try {
            return (List<Winner>) em.createNamedQuery(Winner.QUERY_NAME_FIND_WINNERS_IN_DESC_ORDER)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }


    public long winnerCount() {
        EntityManager em = getEntityManager();
        try {
            return (long) em.createNamedQuery(Winner.QUERY_NAME_COUNT_WINNERS).getSingleResult();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

}
