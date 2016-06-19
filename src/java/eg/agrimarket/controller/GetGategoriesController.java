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
import eg.agrimarket.model.DAO.CategoryDao;
import model.DAOImp.CategoryDaoImp;
import eg.agrimarket.model.pojo.Category;
 
/**
 *
 * @author muhammad
 */
public class GetGategoriesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        CategoryDao categoryDaoImp = new CategoryDaoImp();
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
         List<Category> categories = categoryDaoImp.getAllCategories();
        out.print(gson.toJson(categories));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                String catName = request.getParameter("categoryName");
        CategoryDao categoryDaoImp = new CategoryDaoImp();
        boolean check = categoryDaoImp.addCategory(catName);
        if (check) {
            
            response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?successcat=Successfully#header3-41");
        } else {
            response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/AgriMarket/admin/getProducts?statuscat=Exist!#header3-41");
        }
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
