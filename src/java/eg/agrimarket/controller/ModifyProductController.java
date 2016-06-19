/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eg.agrimarket.model.DAO.ProductDao;
import model.DAOImp.ProductDaoImp;
import eg.agrimarket.model.dto.Product;
import eg.agrimarket.model.pojo.Category;
 
/**
 *
 * @author muhammad
 */
public class ModifyProductController extends HttpServlet {
//get product

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String productName = request.getParameter("productName");
        System.out.println(productName);
        ProductDao productDao = new ProductDaoImp();
        Product product = productDao.getProduct(productName);
        
       
        GsonBuilder builder = new GsonBuilder();
        
        Gson gson = builder.create();
        
        out.print(gson.toJson(product));

    }

//update product
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");
        String desc = request.getParameter("desc");
        String categoryId = request.getParameter("pcategory");
        System.out.println("category"+categoryId);
        eg.agrimarket.model.pojo.Product product = new eg.agrimarket.model.pojo.Product();
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setQuantity(Integer.valueOf(quantity));
        product.setDesc(desc);
        Category category = new Category();
        category.setId(Integer.valueOf("1"));
        product.setCategoryId(category);
        List<Product> products = (List<Product>) request.getServletContext().getAttribute("products");
        if (products != null) {
            for (Product p : products) {
                if (p.getName().equals(name)) {
                    p.setDesc(desc);
                    p.setPrice(Float.valueOf(price));
                    p.setQuantity(Integer.valueOf(quantity));
                    request.getServletContext().setAttribute("products", products);
                }
            }
        }
        ProductDaoImp daoImp = new ProductDaoImp();
        daoImp.updateProduct(product);

        response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?#product-div");

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
