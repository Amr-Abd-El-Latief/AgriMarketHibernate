/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAOImp;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import eg.agrimarket.model.DAO.ProductDao;
import model.JPA.DAO.CategoryJpaController;
import model.JPA.DAO.ProductJpaController;
import eg.agrimarket.model.dto.Order;
import eg.agrimarket.model.dto.ProductPerOrder;
import eg.agrimarket.model.pojo.OrderProduct;
import eg.agrimarket.model.pojo.Product;
import eg.agrimarket.util.EntityFactory;
import eg.agrimarket.util.Search;

/**
 *
 * @author muhammad
 */
public class ProductDaoImp implements ProductDao {

    EntityManager em;
    ProductJpaController jpaController;
    EntityManagerFactory managerFactory;
    CategoryJpaController categoryJpaController;

    public ProductDaoImp() {
        managerFactory = EntityFactory.getEmf();
        jpaController = new ProductJpaController(managerFactory);
        em = jpaController.getEntityManager();

    }

    public ArrayList<eg.agrimarket.model.dto.Product> getAllProducts(int order_id) {
        ArrayList<eg.agrimarket.model.dto.Product> products = new ArrayList<>();
         if (order_id != 0) {
            List<OrderProduct> orderProduct = jpaController.getAllProductsForUserOrder(order_id);
            for (OrderProduct Product : orderProduct) {
                eg.agrimarket.model.dto.Product p = new eg.agrimarket.model.dto.Product();
                p.setQuantity(Product.getProduct().getQuantity());
                p.setPrice(Product.getProduct().getPrice());
                p.setName(Product.getProduct().getName());
                p.setImage(Product.getProduct().getImage());
                p.setDesc(Product.getProduct().getDesc());
                p.setCategoryId(Product.getProduct().getCategoryId().getId());
                products.add(p);
            }
        }
        return products;

    }

    @Override
    public ArrayList<eg.agrimarket.model.dto.Product> searchProduct(String name, Search type) {
        ArrayList<eg.agrimarket.model.dto.Product> dtoProducts = new ArrayList<>();
        ArrayList<Product> products;
        if (Search.CATEGORY == type) {
           products = new ArrayList<Product>(jpaController.findProductsOfCategoryId(Integer.valueOf(name)));
        } else if (Search.NAME == type) {
            products = new ArrayList<Product>(jpaController.findNameOfProductLike(name));
        } else if (Search.PRICE == type) {
            products = new ArrayList<Product>(jpaController.findPricesLess(Float.valueOf(name)));
        } else {
            products = new ArrayList<Product>(jpaController.findProductEntities());
        }
        for (int i = 0; i < products.size(); i++) {
            Product get = products.get(i);
            eg.agrimarket.model.dto.Product product = new eg.agrimarket.model.dto.Product();
            product.setName(get.getName());
            product.setDesc(get.getDesc());
            product.setImage(get.getImage());
            product.setPrice(get.getPrice());
            product.setQuantity(get.getQuantity());
//            CategoryDaoImp categoryDao = new CategoryDaoImp();
//            product.setCategoryId(categoryDao.getCategoryIdOf(get.getName()));
            product.setCategoryId(get.getRealCategoryId());
            dtoProducts.add(product);
        }
        return dtoProducts;
    }

    @Override
    public List<eg.agrimarket.model.dto.Product> getAllProducts() {
        ArrayList<eg.agrimarket.model.dto.Product> products = new ArrayList<>();
        for (Product jpProduct : jpaController.findProductEntities()) {
//            for (model.dto.Product product : products) {
            eg.agrimarket.model.dto.Product product = new eg.agrimarket.model.dto.Product();
            product.setDesc(jpProduct.getDesc());
            product.setImage(jpProduct.getImage());
            product.setName(jpProduct.getName());
            product.setPrice(jpProduct.getPrice());
            product.setQuantity(jpProduct.getQuantity());
            product.setCategoryId(jpProduct.getCategoryId().getId());
            products.add(product);
//            }
        }
        return products;
    }

    @Override
    public boolean removeProduct(String productName) {
        Product p = jpaController.findProduct(productName);
        int check = jpaController.deleteProduct(productName);
        
        if (check != 0) {
            jpaController.getEntityManager().detach(p);
            return true;
        }
        return false;
    }

    @Override
    public boolean addProduct(Product product) {
        try {
            if (!isExistProduct(product)) {
             
            jpaController.create(product);
            return true;   
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        try {
            jpaController.updateProduct(product);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ProductDaoImp.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public eg.agrimarket.model.dto.Product getProduct(String name) {
        eg.agrimarket.model.dto.Product product = new eg.agrimarket.model.dto.Product();

        if (!"".equals(name.trim())) {
            Product p = jpaController.findProduct(name.trim());
//            p.getCategoryId();
            product.setCategoryId(1);
            product.setDesc(p.getDesc());
            product.setImage(p.getImage());
            product.setName(p.getName());
            product.setPrice(p.getPrice());
            product.setQuantity(p.getQuantity());
        }
        return product;
    }

    @Override
    public boolean isExistProduct(Product product) {
        Product p= jpaController.findProduct(product.getName());
        if (p != null) {
            return true;
        }
        return false;

    }


    public void emptyCart(Order order) {
        for (int i = 0; i < order.getItems().size(); i++) {
            ProductPerOrder get = order.getItems().get(i);
            
            Product hiberProduct = jpaController.findProduct(get.getProduct().getName());
            hiberProduct.setQuantity(hiberProduct.getQuantity()-get.getQuantity());
//            get.getProduct().setQuantity(get.getProduct().getQuantity() - get.getQuantity());
            if(hiberProduct.getQuantity()<0){
                hiberProduct.setQuantity(0);
            }
//            if (get.getProduct().getQuantity() < 0) {
//                get.getProduct().setQuantity(0);
//            }
//            System.out.println("New Quantity " + get.getProduct().getQuantity());
            System.out.println("New Quantity " + hiberProduct.getQuantity());
            
            
            updateProduct(hiberProduct);
        }
    }
 

}
