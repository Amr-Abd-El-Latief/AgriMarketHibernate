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
import eg.agrimarket.model.pojo.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.JPA.DAO.exceptions.IllegalOrphanException;
import model.JPA.DAO.exceptions.NonexistentEntityException;
import eg.agrimarket.model.pojo.Category;

/**
 *
 * @author Amr
 */
public class CategoryJpaController implements Serializable {

    private static EntityManager em;
    private EntityManagerFactory emf = null;

    public CategoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;

    }

    public synchronized EntityManager getEntityManager() {
        if (em == null) {
            return emf.createEntityManager();
        } else {
            return em;
        }
    }

    public void create(Category category) {
        if (category.getProductList() == null) {
            category.setProductList(new ArrayList<Product>());
        }

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Product> attachedProductList = new ArrayList<Product>();
            for (Product productListProductToAttach : category.getProductList()) {
                productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getName());
                attachedProductList.add(productListProductToAttach);
            }
            category.setProductList(attachedProductList);
            em.persist(category);
            for (Product productListProduct : category.getProductList()) {
                Category oldCategoryIdOfProductListProduct = productListProduct.getCategoryId();
                productListProduct.setCategoryId(category);
                productListProduct = em.merge(productListProduct);
                if (oldCategoryIdOfProductListProduct != null) {
                    oldCategoryIdOfProductListProduct.getProductList().remove(productListProduct);
                    oldCategoryIdOfProductListProduct = em.merge(oldCategoryIdOfProductListProduct);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
//                em.close();
            }
        }
    }

    public void edit(Category category) throws IllegalOrphanException, NonexistentEntityException, Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category persistentCategory = em.find(Category.class, category.getId());
            List<Product> productListOld = persistentCategory.getProductList();
            List<Product> productListNew = category.getProductList();
            List<String> illegalOrphanMessages = null;
            for (Product productListOldProduct : productListOld) {
                if (!productListNew.contains(productListOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productListOldProduct + " since its categoryId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Product> attachedProductListNew = new ArrayList<Product>();
            for (Product productListNewProductToAttach : productListNew) {
                productListNewProductToAttach = em.getReference(productListNewProductToAttach.getClass(), productListNewProductToAttach.getName());
                attachedProductListNew.add(productListNewProductToAttach);
            }
            productListNew = attachedProductListNew;
            category.setProductList(productListNew);
            category = em.merge(category);
            for (Product productListNewProduct : productListNew) {
                if (!productListOld.contains(productListNewProduct)) {
                    Category oldCategoryIdOfProductListNewProduct = productListNewProduct.getCategoryId();
                    productListNewProduct.setCategoryId(category);
                    productListNewProduct = em.merge(productListNewProduct);
                    if (oldCategoryIdOfProductListNewProduct != null && !oldCategoryIdOfProductListNewProduct.equals(category)) {
                        oldCategoryIdOfProductListNewProduct.getProductList().remove(productListNewProduct);
                        oldCategoryIdOfProductListNewProduct = em.merge(oldCategoryIdOfProductListNewProduct);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = category.getId();
                if (findCategory(id) == null) {
                    throw new NonexistentEntityException("The category with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
//                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category category;
            try {
                category = em.getReference(Category.class, id);
                category.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The category with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Product> productListOrphanCheck = category.getProductList();
            for (Product productListOrphanCheckProduct : productListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Category (" + category + ") cannot be destroyed since the Product " + productListOrphanCheckProduct + " in its productList field has a non-nullable categoryId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(category);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
//                em.close();
            }
        }
    }

    public List<Category> findCategoryEntities() {
//        em = getEntityManager();
        return findCategoryEntities(true, -1, -1);
    }

    public List<Category> findCategoryEntities(int maxResults, int firstResult) {
        return findCategoryEntities(false, maxResults, firstResult);
    }

    private List<Category> findCategoryEntities(boolean all, int maxResults, int firstResult) {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Category.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        finally {
            if (em != null) {
//            em.close();
            }
        }
    }

    public Category findCategory(Integer id) {
        em = getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
//            em.close();
        }
    }

    public int getCategoryCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Category> rt = cq.from(Category.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
//            em.close();
        }
    }

}