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
import eg.agrimarket.model.pojo.Credit;
import eg.agrimarket.model.pojo.Interest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.JPA.DAO.exceptions.IllegalOrphanException;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import model.JPA.DAO.exceptions.PreexistingEntityException;
import eg.agrimarket.model.pojo.Order1;
import eg.agrimarket.model.pojo.User;

/**
 *
 * @author Israa
 */
public class UserJpaController implements Serializable {

    private static EntityManager em;

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public synchronized EntityManager getEntityManager() {
        if (em == null) {
            System.out.println("create user session");
            return emf.createEntityManager();
        }
        return em;
    }

    public void create(User user) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (user.getInterestList() == null) {
            user.setInterestList(new ArrayList<Interest>());
        }
        if (user.getOrder1List() == null) {
            user.setOrder1List(new ArrayList<Order1>());
        }
        List<String> illegalOrphanMessages = null;
        Credit creditNumberOrphanCheck = user.getCreditNumber();
        if (creditNumberOrphanCheck != null) {
            User oldUserOfCreditNumber = creditNumberOrphanCheck.getUser();
            if (oldUserOfCreditNumber != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Credit " + creditNumberOrphanCheck + " already has an item of type User whose creditNumber column cannot be null. Please make another selection for the creditNumber field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Credit creditNumber = user.getCreditNumber();
            if (creditNumber != null) {
                creditNumber = em.getReference(creditNumber.getClass(), creditNumber.getNumber());
                user.setCreditNumber(creditNumber);
            }
            List<Interest> attachedInterestList = new ArrayList<Interest>();
            for (Interest interestListInterestToAttach : user.getInterestList()) {
                interestListInterestToAttach = em.getReference(interestListInterestToAttach.getClass(), interestListInterestToAttach.getId());
                attachedInterestList.add(interestListInterestToAttach);
            }
            user.setInterestList(attachedInterestList);
            List<Order1> attachedOrder1List = new ArrayList<Order1>();
            for (Order1 order1ListOrder1ToAttach : user.getOrder1List()) {
                order1ListOrder1ToAttach = em.getReference(order1ListOrder1ToAttach.getClass(), order1ListOrder1ToAttach.getId());
                attachedOrder1List.add(order1ListOrder1ToAttach);
            }
            user.setOrder1List(attachedOrder1List);
            em.persist(user);
            if (creditNumber != null) {
                creditNumber.setUser(user);
                creditNumber = em.merge(creditNumber);
            }
            for (Interest interestListInterest : user.getInterestList()) {
                interestListInterest.getUserList().add(user);
                interestListInterest = em.merge(interestListInterest);
            }
            for (Order1 order1ListOrder1 : user.getOrder1List()) {
                User oldUserEmailOfOrder1ListOrder1 = order1ListOrder1.getUserEmail();
                order1ListOrder1.setUserEmail(user);
                order1ListOrder1 = em.merge(order1ListOrder1);
                if (oldUserEmailOfOrder1ListOrder1 != null) {
                    oldUserEmailOfOrder1ListOrder1.getOrder1List().remove(order1ListOrder1);
                    oldUserEmailOfOrder1ListOrder1 = em.merge(oldUserEmailOfOrder1ListOrder1);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUser(user.getEmail()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                // em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getEmail());
            Credit creditNumberOld = persistentUser.getCreditNumber();
            Credit creditNumberNew = user.getCreditNumber();
            List<Interest> interestListOld = persistentUser.getInterestList();
            List<Interest> interestListNew = user.getInterestList();
            List<Order1> order1ListOld = persistentUser.getOrder1List();
            List<Order1> order1ListNew = user.getOrder1List();
            List<String> illegalOrphanMessages = null;
            if (creditNumberNew != null && !creditNumberNew.equals(creditNumberOld)) {
                User oldUserOfCreditNumber = creditNumberNew.getUser();
                if (oldUserOfCreditNumber != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Credit " + creditNumberNew + " already has an item of type User whose creditNumber column cannot be null. Please make another selection for the creditNumber field.");
                }
            }
//            for (Order1 order1ListOldOrder1 : order1ListOld) {
//                if (!order1ListNew.contains(order1ListOldOrder1)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Order1 " + order1ListOldOrder1 + " since its userEmail field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            if (creditNumberNew != null) {
//                creditNumberNew = em.getReference(creditNumberNew.getClass(), creditNumberNew.getNumber());
//                user.setCreditNumber(creditNumberNew);
//            }
            List<Interest> attachedInterestListNew = new ArrayList<Interest>();
            for (Interest interestListNewInterestToAttach : interestListNew) {
                interestListNewInterestToAttach = em.getReference(interestListNewInterestToAttach.getClass(), interestListNewInterestToAttach.getId());
                attachedInterestListNew.add(interestListNewInterestToAttach);
            }
            interestListNew = attachedInterestListNew;
            user.setInterestList(interestListNew);
            List<Order1> attachedOrder1ListNew = new ArrayList<Order1>();
            for (Order1 order1ListNewOrder1ToAttach : order1ListNew) {
                order1ListNewOrder1ToAttach = em.getReference(order1ListNewOrder1ToAttach.getClass(), order1ListNewOrder1ToAttach.getId());
                attachedOrder1ListNew.add(order1ListNewOrder1ToAttach);
            }
            order1ListNew = attachedOrder1ListNew;
            user.setOrder1List(order1ListNew);
            user = em.merge(user);
            if (creditNumberOld != null && !creditNumberOld.equals(creditNumberNew)) {
                creditNumberOld.setUser(null);
                creditNumberOld = em.merge(creditNumberOld);
            }
            if (creditNumberNew != null && !creditNumberNew.equals(creditNumberOld)) {
                creditNumberNew.setUser(user);
                creditNumberNew = em.merge(creditNumberNew);
            }
            for (Interest interestListOldInterest : interestListOld) {
                if (!interestListNew.contains(interestListOldInterest)) {
                    interestListOldInterest.getUserList().remove(user);
                    interestListOldInterest = em.merge(interestListOldInterest);
                }
            }
            for (Interest interestListNewInterest : interestListNew) {
                if (!interestListOld.contains(interestListNewInterest)) {
                    interestListNewInterest.getUserList().add(user);
                    interestListNewInterest = em.merge(interestListNewInterest);
                }
            }
            for (Order1 order1ListNewOrder1 : order1ListNew) {
                if (!order1ListOld.contains(order1ListNewOrder1)) {
                    User oldUserEmailOfOrder1ListNewOrder1 = order1ListNewOrder1.getUserEmail();
                    order1ListNewOrder1.setUserEmail(user);
                    order1ListNewOrder1 = em.merge(order1ListNewOrder1);
                    if (oldUserEmailOfOrder1ListNewOrder1 != null && !oldUserEmailOfOrder1ListNewOrder1.equals(user)) {
                        oldUserEmailOfOrder1ListNewOrder1.getOrder1List().remove(order1ListNewOrder1);
                        oldUserEmailOfOrder1ListNewOrder1 = em.merge(oldUserEmailOfOrder1ListNewOrder1);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getEmail();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                // em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getEmail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Order1> order1ListOrphanCheck = user.getOrder1List();
            for (Order1 order1ListOrphanCheckOrder1 : order1ListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Order1 " + order1ListOrphanCheckOrder1 + " in its order1List field has a non-nullable userEmail field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Credit creditNumber = user.getCreditNumber();
            if (creditNumber != null) {
                creditNumber.setUser(null);
                creditNumber = em.merge(creditNumber);
            }
            List<Interest> interestList = user.getInterestList();
            for (Interest interestListInterest : interestList) {
                interestListInterest.getUserList().remove(user);
                interestListInterest = em.merge(interestListInterest);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //  em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            // em.close();
        }
    }

    public User findUser(String id) {
        em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            // em.close();
        }
    }

    public int getUserCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            // em.close();
        }
    }

    public User signInUser(String id, String password) {
        em = getEntityManager();
        try {
            User user = (User) em.createQuery("from User u where u.email = :givenEmail and u.password=:givenPassword").setParameter("givenEmail", id).setParameter("givenPassword", password).getSingleResult();
            return user;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean isCreditNumberAssignedForUser(Credit Credit) {
        em = getEntityManager();
        try {
            User u = (User) em.createQuery("from User u where u.creditNumber = :givenCredit").setParameter("givenCredit", Credit).getSingleResult();
            return (u != null) ? true : false;
        } catch (Exception ex) {
            return false;
        }
    }

    public void updateBalance(String creditNumber, int balance) {
        em = getEntityManager();
        Credit credit = (Credit) em.createQuery("from Credit c where c.number= :givenCreditNumber").setParameter("givenCreditNumber", creditNumber).getSingleResult();
        credit.setBalance(balance);
        em.persist(credit);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

}
