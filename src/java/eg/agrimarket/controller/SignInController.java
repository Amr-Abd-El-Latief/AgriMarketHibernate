/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import eg.agrimarket.model.DAO.UserDao;
import model.DAOImp.UserDaoImpl;
import eg.agrimarket.model.dto.User;

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
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if (request.getParameterNames() != null) {
            if (request.getParameter("email") != null) {
                if (request.getParameter("password") != null) {
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    System.out.println("iam in do filter name is :" + email + "   password is :  " + password);
                    User user = new User();
                    UserDao userDao = new UserDaoImpl();
                    user = userDao.signIn(email, password);
                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
//                        response.sendRedirect("index.jsp");
                        out.print("success");

                    } else {
                        HttpServletResponse res = (HttpServletResponse) response;
//                        res.sendRedirect("error.html");

                        out.print("failure");
                        // System.out.println("Filter didnt pass me");

                    }

                } else {
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.sendRedirect("error.html");
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
