/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO.UserDao;
import model.DAOImp.UserDaoImpl;

/**
 *
 * @author Amr
 */
@WebServlet(name = "SignIn", urlPatterns = {"/SignIn"})
public class SignInController extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        
    
    
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          System.out.println("Sign In Servlet");
//                      String email = request.getParameter("email");
//                    String password = request.getParameter("password");
//          System.out.println("Sign In Servlet  name : "+email+"  password  :"+ password);
//           UserDao userDao = new  UserDaoImpl();
//            userDao.signIn(email, password);
//                  
          
        if (request.getParameterNames()!= null) {
            if (request.getParameter("email") != null) {
                if (request.getParameter("password") != null) {
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    System.out.println("iam in do filter name is :"+email+"   password is :  "+password);
    
                    UserDao userDao = new  UserDaoImpl();
                    userDao.signIn(email, password);                 
      
                    } else {
                        HttpServletResponse res = (HttpServletResponse) response;
                        res.sendRedirect("login.html");
                       // System.out.println("Filter didnt pass me");
                     
                    }
                }
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
