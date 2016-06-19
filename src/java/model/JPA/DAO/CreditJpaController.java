/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.JPA.DAO;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import eg.agrimarket.model.pojo.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.JPA.DAO.exceptions.IllegalOrphanException;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import model.JPA.DAO.exceptions.PreexistingEntityException;
import eg.agrimarket.model.pojo.Credit;

/**
 *
 * @author Amr
 */
public class CreditJpaController implements Serializable {

    private static EntityManager em;

    public CreditJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public synchronized EntityManager getEntityManager() {
        if (em == null) {
            
            System.out.println("create credit session");
            return emf.createEntityManager();
        }
        return em;
    }

    public void create(Credit credit) throws PreexistingEntityException, Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = credit.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getEmail());
                credit.setUser(user);
            }
            em.persist(credit);
            if (user != null) {
                Credit oldCreditNumberOfUser = user.getCreditNumber();
                if (oldCreditNumberOfUser != null) {
                    oldCreditNumberOfUser.setUser(null);
                    oldCreditNumberOfUser = em.merge(oldCreditNumberOfUser);
                }
                user.setCreditNumber(credit);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCredit(credit.getNumber()) != null) {
                throw new PreexistingEntityException("Credit " + credit + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                //     em.close();
            }
        }
    }

    public void edit(Credit credit) throws IllegalOrphanException, NonexistentEntityException, Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Credit persistentCredit = em.find(Credit.class, credit.getNumber());
            User userOld = persistentCredit.getUser();
            User userNew = credit.getUser();
            List<String> illegalOrphanMessages = null;
            if (userOld != null && !userOld.equals(userNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain User " + userOld + " since its creditNumber field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getEmail());
                credit.setUser(userNew);
            }
            credit = em.merge(credit);
            if (userNew != null && !userNew.equals(userOld)) {
                Credit oldCreditNumberOfUser = userNew.getCreditNumber();
                if (oldCreditNumberOfUser != null) {
                    oldCreditNumberOfUser.setUser(null);
                    oldCreditNumberOfUser = em.merge(oldCreditNumberOfUser);
                }
                userNew.setCreditNumber(credit);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = credit.getNumber();
                if (findCredit(id) == null) {
                    throw new NonexistentEntityException("The credit with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                //      em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Credit credit;
            try {
                credit = em.getReference(Credit.class, id);
                credit.getNumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The credit with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            User userOrphanCheck = credit.getUser();
            if (userOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Credit (" + credit + ") cannot be destroyed since the User " + userOrphanCheck + " in its user field has a non-nullable creditNumber field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(credit);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //      em.close();
            }
        }
    }

    public List<Credit> findCreditEntities() {
        return findCreditEntities(true, -1, -1);
    }

    public List<Credit> findCreditEntities(int maxResults, int firstResult) {
        return findCreditEntities(false, maxResults, firstResult);
    }

    private List<Credit> findCreditEntities(boolean all, int maxResults, int firstResult) {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Credit.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            //   em.close();
        }
    }

    public Credit findCredit(String id) {
        em = getEntityManager();
        try {
            return em.find(Credit.class, id);
        } finally {
            //        em.close();
        }
    }

    public int getCreditCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Credit> rt = cq.from(Credit.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //        em.close();
        }
    }

    public Credit findCreditForEntity(String email) {
        em = getEntityManager();
        User user = (User) em.find(User.class, email);
        Credit result = (Credit) em.createQuery("from Credit c where c.user.email = :givenUser").setParameter("givenUser", email).getSingleResult();

        return result;
    }

}
