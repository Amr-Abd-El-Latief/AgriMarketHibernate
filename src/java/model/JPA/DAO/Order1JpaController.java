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
import eg.agrimarket.model.pojo.OrderProduct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.JPA.DAO.exceptions.IllegalOrphanException;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import eg.agrimarket.model.pojo.Order1;

/**
 *
 * @author Amr
 */
public class Order1JpaController implements Serializable {

    private static EntityManager em;

    public Order1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public synchronized EntityManager getEntityManager() {
        if (em == null){ 
            
            System.out.println("create order session");
            return emf.createEntityManager();
        }
        return em;
    }

    public void create(Order1 order1) {
        if (order1.getOrderProductList() == null) {
            order1.setOrderProductList(new ArrayList<OrderProduct>());
        }

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userEmail = order1.getUserEmail();
            if (userEmail != null) {
                userEmail = em.getReference(userEmail.getClass(), userEmail.getEmail());
                order1.setUserEmail(userEmail);
            }
            List<OrderProduct> attachedOrderProductList = new ArrayList<OrderProduct>();
            for (OrderProduct orderProductListOrderProductToAttach : order1.getOrderProductList()) {
                orderProductListOrderProductToAttach = em.getReference(orderProductListOrderProductToAttach.getClass(), orderProductListOrderProductToAttach.getOrderProductPK());
                attachedOrderProductList.add(orderProductListOrderProductToAttach);
            }
            order1.setOrderProductList(attachedOrderProductList);
            em.persist(order1);
            if (userEmail != null) {
                userEmail.getOrder1List().add(order1);
                userEmail = em.merge(userEmail);
            }
            for (OrderProduct orderProductListOrderProduct : order1.getOrderProductList()) {
                Order1 oldOrder1OfOrderProductListOrderProduct = orderProductListOrderProduct.getOrder1();
                orderProductListOrderProduct.setOrder1(order1);
                orderProductListOrderProduct = em.merge(orderProductListOrderProduct);
                if (oldOrder1OfOrderProductListOrderProduct != null) {
                    oldOrder1OfOrderProductListOrderProduct.getOrderProductList().remove(orderProductListOrderProduct);
                    oldOrder1OfOrderProductListOrderProduct = em.merge(oldOrder1OfOrderProductListOrderProduct);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Order1 order1) throws IllegalOrphanException, NonexistentEntityException, Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Order1 persistentOrder1 = em.find(Order1.class, order1.getId());
            User userEmailOld = persistentOrder1.getUserEmail();
            User userEmailNew = order1.getUserEmail();
            List<OrderProduct> orderProductListOld = persistentOrder1.getOrderProductList();
            List<OrderProduct> orderProductListNew = order1.getOrderProductList();
            List<String> illegalOrphanMessages = null;
            for (OrderProduct orderProductListOldOrderProduct : orderProductListOld) {
                if (!orderProductListNew.contains(orderProductListOldOrderProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderProduct " + orderProductListOldOrderProduct + " since its order1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userEmailNew != null) {
                userEmailNew = em.getReference(userEmailNew.getClass(), userEmailNew.getEmail());
                order1.setUserEmail(userEmailNew);
            }
            List<OrderProduct> attachedOrderProductListNew = new ArrayList<OrderProduct>();
            for (OrderProduct orderProductListNewOrderProductToAttach : orderProductListNew) {
                orderProductListNewOrderProductToAttach = em.getReference(orderProductListNewOrderProductToAttach.getClass(), orderProductListNewOrderProductToAttach.getOrderProductPK());
                attachedOrderProductListNew.add(orderProductListNewOrderProductToAttach);
            }
            orderProductListNew = attachedOrderProductListNew;
            order1.setOrderProductList(orderProductListNew);
            order1 = em.merge(order1);
            if (userEmailOld != null && !userEmailOld.equals(userEmailNew)) {
                userEmailOld.getOrder1List().remove(order1);
                userEmailOld = em.merge(userEmailOld);
            }
            if (userEmailNew != null && !userEmailNew.equals(userEmailOld)) {
                userEmailNew.getOrder1List().add(order1);
                userEmailNew = em.merge(userEmailNew);
            }
            for (OrderProduct orderProductListNewOrderProduct : orderProductListNew) {
                if (!orderProductListOld.contains(orderProductListNewOrderProduct)) {
                    Order1 oldOrder1OfOrderProductListNewOrderProduct = orderProductListNewOrderProduct.getOrder1();
                    orderProductListNewOrderProduct.setOrder1(order1);
                    orderProductListNewOrderProduct = em.merge(orderProductListNewOrderProduct);
                    if (oldOrder1OfOrderProductListNewOrderProduct != null && !oldOrder1OfOrderProductListNewOrderProduct.equals(order1)) {
                        oldOrder1OfOrderProductListNewOrderProduct.getOrderProductList().remove(orderProductListNewOrderProduct);
                        oldOrder1OfOrderProductListNewOrderProduct = em.merge(oldOrder1OfOrderProductListNewOrderProduct);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = order1.getId();
                if (findOrder1(id) == null) {
                    throw new NonexistentEntityException("The order1 with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Order1 order1;
            try {
                order1 = em.getReference(Order1.class, id);
                order1.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The order1 with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<OrderProduct> orderProductListOrphanCheck = order1.getOrderProductList();
            for (OrderProduct orderProductListOrphanCheckOrderProduct : orderProductListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Order1 (" + order1 + ") cannot be destroyed since the OrderProduct " + orderProductListOrphanCheckOrderProduct + " in its orderProductList field has a non-nullable order1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userEmail = order1.getUserEmail();
            if (userEmail != null) {
                userEmail.getOrder1List().remove(order1);
                userEmail = em.merge(userEmail);
            }
            em.remove(order1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Order1> findOrder1Entities() {
        return findOrder1Entities(true, -1, -1);
    }

    public List<Order1> findOrder1Entities(int maxResults, int firstResult) {
        return findOrder1Entities(false, maxResults, firstResult);
    }

    private List<Order1> findOrder1Entities(boolean all, int maxResults, int firstResult) {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Order1.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            //em.close();
        }
    }

    public Order1 findOrder1(Integer id) {
        em = getEntityManager();
        try {
            return em.find(Order1.class, id);
        } finally {
            //em.close();
        }
    }

    public int getOrder1Count() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Order1> rt = cq.from(Order1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }

        
       public List<Order1> findAllOrdersForEntity(String email) {
        em = getEntityManager();
        User user = (User) em.find(User.class, email);
        List result = em.createQuery("from Order1 o where o.userEmail.email = :givenUser").setParameter("givenUser", user.getEmail()).getResultList();
        Iterator pairs2 = result.iterator();
        List<Order1> orders = new ArrayList<>();
        while (pairs2.hasNext()) {
            Order1 order1 = (Order1) pairs2.next();
           // System.out.println(order1.getDate());
            orders.add(order1);
        }

        return orders;
    }

}
