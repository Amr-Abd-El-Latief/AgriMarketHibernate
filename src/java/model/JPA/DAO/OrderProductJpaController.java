/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.JPA.DAO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import model.JPA.DAO.exceptions.PreexistingEntityException;
import eg.agrimarket.model.pojo.Product;
import eg.agrimarket.model.pojo.Order1;
import eg.agrimarket.model.pojo.OrderProduct;
import eg.agrimarket.model.pojo.OrderProductPK;
import eg.agrimarket.util.EntityFactory;

/**
 *
 * @author Amr
 */
public class OrderProductJpaController implements Serializable {

    private static EntityManager em;

    public OrderProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public synchronized EntityManager getEntityManager() {
        if (em == null) {
            System.out.println("create op session");
            return emf.createEntityManager();
        }
//        else if (!em.isOpen()){
//            em= emf.createEntityManager();
//        }
        return em;
    }

    public void create(OrderProduct orderProduct) throws PreexistingEntityException, Exception {
        if (orderProduct.getOrderProductPK() == null) {
            orderProduct.setOrderProductPK(new OrderProductPK());
        }
        orderProduct.getOrderProductPK().setProductId(orderProduct.getProduct().getName());
        orderProduct.getOrderProductPK().setOrderId(orderProduct.getOrder1().getId());

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product = orderProduct.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getName());
                orderProduct.setProduct(product);
            }
            Order1 order1 = orderProduct.getOrder1();
            if (order1 != null) {
                order1 = em.getReference(order1.getClass(), order1.getId());
                orderProduct.setOrder1(order1);
            }
            em.persist(orderProduct);
            if (product != null) {
                product.getOrderProductList().add(orderProduct);
                product = em.merge(product);
            }
            if (order1 != null) {
                order1.getOrderProductList().add(orderProduct);
                order1 = em.merge(order1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrderProduct(orderProduct.getOrderProductPK()) != null) {
                throw new PreexistingEntityException("OrderProduct " + orderProduct + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(OrderProduct orderProduct) throws NonexistentEntityException, Exception {
        orderProduct.getOrderProductPK().setProductId(orderProduct.getProduct().getName());
        orderProduct.getOrderProductPK().setOrderId(orderProduct.getOrder1().getId());

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderProduct persistentOrderProduct = em.find(OrderProduct.class, orderProduct.getOrderProductPK());
            Product productOld = persistentOrderProduct.getProduct();
            Product productNew = orderProduct.getProduct();
            Order1 order1Old = persistentOrderProduct.getOrder1();
            Order1 order1New = orderProduct.getOrder1();
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getName());
                orderProduct.setProduct(productNew);
            }
            if (order1New != null) {
                order1New = em.getReference(order1New.getClass(), order1New.getId());
                orderProduct.setOrder1(order1New);
            }
            orderProduct = em.merge(orderProduct);
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getOrderProductList().remove(orderProduct);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getOrderProductList().add(orderProduct);
                productNew = em.merge(productNew);
            }
            if (order1Old != null && !order1Old.equals(order1New)) {
                order1Old.getOrderProductList().remove(orderProduct);
                order1Old = em.merge(order1Old);
            }
            if (order1New != null && !order1New.equals(order1Old)) {
                order1New.getOrderProductList().add(orderProduct);
                order1New = em.merge(order1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                OrderProductPK id = orderProduct.getOrderProductPK();
                if (findOrderProduct(id) == null) {
                    throw new NonexistentEntityException("The orderProduct with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void destroy(OrderProductPK id) throws NonexistentEntityException {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderProduct orderProduct = new OrderProduct();
            try {
                orderProduct = em.find(OrderProduct.class, id);
                orderProduct.getOrderProductPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderProduct with id " + id + " no longer exists.", enfe);
            }
            Product product = orderProduct.getProduct();
            if (product != null) {
                product.getOrderProductList().remove(orderProduct);
                product = em.merge(product);
            }
            Order1 order1 = orderProduct.getOrder1();
            if (order1 != null) {
                order1.getOrderProductList().remove(orderProduct);
                order1 = em.merge(order1);
            }
            em.remove(orderProduct);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }
    

    public List<OrderProduct> findOrderProductEntities() {
        return findOrderProductEntities(true, -1, -1);
    }

    public List<OrderProduct> findOrderProductEntities(int maxResults, int firstResult) {
        return findOrderProductEntities(false, maxResults, firstResult);
    }

    private List<OrderProduct> findOrderProductEntities(boolean all, int maxResults, int firstResult) {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderProduct.class));
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

    public OrderProduct findOrderProduct(OrderProductPK id) {
        em = getEntityManager();
        try {
            return em.find(OrderProduct.class, id);
        } finally {
            //em.close();
        }
    }

    public int getOrderProductCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderProduct> rt = cq.from(OrderProduct.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
    
    public List<OrderProduct> findOrderProductOf(int order_id){
        
        String hql = "from OrderProduct o where o.orderProductPK.orderId = :orderId";
        Query query = getEntityManager().createQuery(hql).setParameter("orderId", order_id);
        List<OrderProduct> hiberOrderProducts = (List<OrderProduct>) query.getResultList();
        return hiberOrderProducts;
    }

}
