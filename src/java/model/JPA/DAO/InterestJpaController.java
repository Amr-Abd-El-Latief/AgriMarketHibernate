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
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import model.JPA.DAO.exceptions.PreexistingEntityException;
import eg.agrimarket.model.pojo.Interest;

/**
 *
 * @author Israa
 */
public class InterestJpaController implements Serializable {

    private static EntityManager em;

    public InterestJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {

        if (em == null) {
            
            System.out.println("create interest session");
            return emf.createEntityManager();
        } else {
            return em;
        }
    }

    public void create(Interest interest) throws PreexistingEntityException, Exception {
        if (interest.getUserList() == null) {
            interest.setUserList(new ArrayList<User>());
        }
        em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<User> attachedUserList = new ArrayList<User>();
            for (User userListUserToAttach : interest.getUserList()) {
                userListUserToAttach = em.getReference(userListUserToAttach.getClass(), userListUserToAttach.getEmail());
                attachedUserList.add(userListUserToAttach);
            }
            interest.setUserList(attachedUserList);
            em.persist(interest);
            for (User userListUser : interest.getUserList()) {
                userListUser.getInterestList().add(interest);
                userListUser = em.merge(userListUser);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInterest(interest.getId()) != null) {
                throw new PreexistingEntityException("Interest " + interest + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                // em.close();
            }
        }
    }

    public void edit(Interest interest) throws NonexistentEntityException, Exception {
        em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interest persistentInterest = em.find(Interest.class, interest.getId());
            List<User> userListOld = persistentInterest.getUserList();
            List<User> userListNew = interest.getUserList();
            List<User> attachedUserListNew = new ArrayList<User>();
            for (User userListNewUserToAttach : userListNew) {
                userListNewUserToAttach = em.getReference(userListNewUserToAttach.getClass(), userListNewUserToAttach.getEmail());
                attachedUserListNew.add(userListNewUserToAttach);
            }
            userListNew = attachedUserListNew;
            interest.setUserList(userListNew);
            interest = em.merge(interest);
            for (User userListOldUser : userListOld) {
                if (!userListNew.contains(userListOldUser)) {
                    userListOldUser.getInterestList().remove(interest);
                    userListOldUser = em.merge(userListOldUser);
                }
            }
            for (User userListNewUser : userListNew) {
                if (!userListOld.contains(userListNewUser)) {
                    userListNewUser.getInterestList().add(interest);
                    userListNewUser = em.merge(userListNewUser);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = interest.getId();
                if (findInterest(id) == null) {
                    throw new NonexistentEntityException("The interest with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                // em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interest interest;
            try {
                interest = em.getReference(Interest.class, id);
                interest.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The interest with id " + id + " no longer exists.", enfe);
            }
            List<User> userList = interest.getUserList();
            for (User userListUser : userList) {
                userListUser.getInterestList().remove(interest);
                userListUser = em.merge(userListUser);
            }
            em.remove(interest);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                // em.close();
            }
        }
    }

    public List<Interest> findInterestEntities() {
        return findInterestEntities(true, -1, -1);
    }

    public List<Interest> findInterestEntities(int maxResults, int firstResult) {
        return findInterestEntities(false, maxResults, firstResult);
    }

    private List<Interest> findInterestEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Interest.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Interest findInterest(Integer id) {
        em = getEntityManager();
        try {
            return em.find(Interest.class, id);
        } finally {
            //  em.close();
        }
    }

    public int getInterestCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Interest> rt = cq.from(Interest.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            // em.close();
        }
    }

    public List<Interest> findAllInterestForEntity(String email) {
        em = getEntityManager();
        User user = (User) em.find(User.class, email);
        
        List result = em.createQuery("from Interest where :givenUser member of userList").setParameter("givenUser", user).getResultList();
        Iterator pairs2 = result.iterator();
        List<Interest> interests = new ArrayList<>();
        while (pairs2.hasNext()) {
            Interest interest = (Interest) pairs2.next();
            // System.out.println(interest.getName());
            interests.add(interest);
        }

        return interests;
    }

}
