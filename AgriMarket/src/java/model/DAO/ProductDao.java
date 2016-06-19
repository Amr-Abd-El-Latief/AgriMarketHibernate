/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.util.ArrayList;
import java.util.List;
import model.pojo.Product;

/**
 *
 * @author muhammad
 */

public interface ProductDao {
    
 
   public List<Product> getAllProducts();
   public Product getProduct(String name);
   public boolean addProduct(Product product);
   public void updateProduct(Product product);
   public ArrayList<Product> searchProduct(String name);
}
