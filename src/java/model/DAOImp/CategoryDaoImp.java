/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAOImp;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import eg.agrimarket.model.DAO.CategoryDao;
import model.JPA.DAO.CategoryJpaController;
import model.JPA.DAO.ProductJpaController;
import eg.agrimarket.model.pojo.Category;
import eg.agrimarket.util.EntityFactory;

/**
 *
 * @author muhammad
 */
public class CategoryDaoImp implements CategoryDao {
  EntityManager em;
    CategoryJpaController jpaController;
    EntityManagerFactory managerFactory;
    
    
    public CategoryDaoImp() {
        managerFactory = EntityFactory.getEmf();
        jpaController = new CategoryJpaController(managerFactory);
        em =jpaController.getEntityManager();
        
    }

    @Override
    public List<eg.agrimarket.model.pojo.Category> getAllCategories() {
       return jpaController.findCategoryEntities();
    }

    @Override
    public boolean addCategory(String name) {
        if (name != null) {
            List<Category> cats = (List<Category>)  em.createNamedQuery("Category.findByName").setParameter("name", name).getResultList();
            if (cats.isEmpty()){
                 Category category = new Category();
                category.setName(name);
                jpaController.create(category);
            return true;
            }
        }
        return false;
    }
    public int getCategoryIdOf(String productName) {

        ProductJpaController productHiberDao = new ProductJpaController(EntityFactory.getEmf());
        eg.agrimarket.model.pojo.Product product = productHiberDao.findProduct(productName);
        String hql = "from Category where :product member of productList";
        CategoryJpaController categoryHiberDao = new CategoryJpaController(EntityFactory.getEmf());
        Query query = categoryHiberDao.getEntityManager().createQuery(hql).setParameter("product", product);
        eg.agrimarket.model.pojo.Category hiberCategory = (eg.agrimarket.model.pojo.Category) query.getSingleResult();
        return hiberCategory.getId();

    }

}
