/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eg.agrimarket.model.DAO.ProductDao;
import model.DAOImp.ProductDaoImp;
import eg.agrimarket.model.dto.Product;

/**
 *
 * @author muhammad
 */
public class GetProductsContoller extends HttpServlet {
//get all products
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
              ProductDao daoImp = new ProductDaoImp();
            List<Product> products = daoImp.getAllProducts();
            request.getServletContext().setAttribute("products", products);
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
