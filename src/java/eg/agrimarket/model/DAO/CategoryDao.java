/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.model.DAO;

import java.util.ArrayList;
import java.util.List;
import eg.agrimarket.model.pojo.Category;
 

/**
 *
 * @author muhammad
 */
public interface CategoryDao {
  public List<Category> getAllCategories();
   public boolean addCategory(String name);

}
