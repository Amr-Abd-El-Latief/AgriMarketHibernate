/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.model.DAO;

import java.util.ArrayList;
import java.util.List;
import eg.agrimarket.model.pojo.Product;
 import eg.agrimarket.util.Search;

/**
 *
 * @author muhammad
 */
public interface ProductDao {

    public List<eg.agrimarket.model.dto.Product> getAllProducts();

    public eg.agrimarket.model.dto.Product getProduct(String name);

    public boolean addProduct(Product product);

    public boolean updateProduct(Product product);

    public ArrayList<eg.agrimarket.model.dto.Product> searchProduct(String name, Search type);
    public boolean removeProduct(String name);

    public boolean isExistProduct(Product product);

 
}
