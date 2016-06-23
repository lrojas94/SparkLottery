package Classes.HelperClasses;

import Classes.Data.Constants;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by MEUrena on 6/23/16.
 * All rights reserved.
 */
public class DatabaseHandler<T> {
    private static EntityManagerFactory emf = null;
    private Class<T> entityClass = null;

    private static DatabaseHandler instance = null;
    protected  DatabaseHandler() {}

    protected  DatabaseHandler(Class<T> entityClass) {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT_NAME);
        }
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() { return emf.createEntityManager(); }

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public boolean insertIntoDatabase(T entity) throws Exception {
        boolean success = false;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(entity);
            em.getTransaction().commit();
            success = true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            success = false;
            throw ex;
        } finally {
            em.close();
            return success;
        }
    }

    public T findObjectWithId(Object id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<T> getAllObjects() throws Exception {
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
            criteriaQuery.select(criteriaQuery.from(entityClass));
            return em.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    public void updateObject(T entity) throws Exception {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public boolean deleteObjectWithId(Object id) throws Exception {
        boolean success = false;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            T entity = em.find(entityClass, id);
            em.remove(entity);
            em.getTransaction().commit();
            success = true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            success = false;
            throw ex;
        } finally {
            em.close();
            return success;
        }
    }

}
