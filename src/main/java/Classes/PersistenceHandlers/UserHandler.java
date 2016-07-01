package Classes.PersistenceHandlers;

import Classes.Data.User;
import Classes.HelperClasses.DatabaseHandler;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by MEUrena on 6/24/16.
 * All rights reserved.
 */
public class UserHandler extends DatabaseHandler<User> {

    private static UserHandler instance;

    private UserHandler() { super(User.class); }

    public static UserHandler getInstance() {
        if (instance == null) {
            instance = new UserHandler();
        }
        return instance;
    }

    public User loginUser(String username,String password) throws Exception {
        EntityManager em = getEntityManager();
        try {
            return (User) em.createNamedQuery(User.QUERY_NAME_FIND_BY_USERNAME_AND_PASSWORD)
                    .setParameter("username",username)
                    .setParameter("password",password)
                    .getSingleResult();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    public User findUserByUsername(String username) throws Exception {
        EntityManager em = getEntityManager();
        try {
            return (User) em.createNamedQuery(User.QUERY_NAME_FIND_BY_USERNAME)
                    .setParameter("username",username)
                    .getSingleResult();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<User> findTransferableUsers(String username) throws Exception {
        EntityManager em = getEntityManager();
        try {
            return (List<User>) em.createNamedQuery(User.QUERY_NAME_FIND_TRANSFERABLE)
                    .setParameter("username",username).getResultList();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<User> findAllUsers() throws Exception{
        EntityManager em = getEntityManager();
        try {
            return ((List<User>)em.createNamedQuery(User.QUERY_NAME_FIND_ALL_USERS).getResultList());
        }catch (Exception e){
            throw e;
        }finally {
            em.close();
        }
    }

}
