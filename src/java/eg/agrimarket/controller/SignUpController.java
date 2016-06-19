/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import eg.agrimarket.model.DAO.CreditDao;
import eg.agrimarket.model.DAO.UserDao;
import model.DAOImp.CreditDaoImpl;
import model.DAOImp.UserDaoImpl;
import model.JPA.DAO.InterestJpaController;
import eg.agrimarket.model.dto.Credit;
import eg.agrimarket.model.dto.User;
import eg.agrimarket.model.pojo.Interest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import eg.agrimarket.util.EntityFactory;

/**
 *
 * @author Amr
 */
public class SignUpController extends HttpServlet {

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
        System.out.println("iam in get signup ");
        boolean CreditMsgIndicator = true;
        User user = new User();
        Credit credit = new Credit();
        UserDao userDaoImpl = new UserDaoImpl();
        String creditNumber = request.getParameter("creditnumber");

        System.out.println("iam in get signup credit number is :" + creditNumber);
        user.setCreditNumber(creditNumber);
        credit.setNumber(creditNumber);
        CreditDao creditDao = new CreditDaoImpl();
        PrintWriter printWriter = response.getWriter();

        if (creditDao.checkCredit(credit)) {//credit number is exist is 

            if (!(userDaoImpl.isCreditNumberAssigned(credit))) {
                System.out.println("creditExist = true;");
                printWriter.write("");
            } else {
                printWriter.write("we are sorry.. credit number is Assigened to other client .. !");

                CreditMsgIndicator = false;
                System.out.println("creditExist = falsefalse;");

            }
        } else {

            if (CreditMsgIndicator) {
                printWriter.write("This credit number doesn't exist");
                System.out.println("creditExist=false;");

            }
            CreditMsgIndicator = true;
            System.out.println("creditExist=false;");

        }

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

        CreditDao creditDao = new CreditDaoImpl();

        try {

            boolean creditExist = false;

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> it = items.iterator();
            HttpSession session = request.getSession(false);
            User user = new User();
            Credit credit = new Credit();
            UserDao userDaoImpl = new UserDaoImpl();
            ArrayList<eg.agrimarket.model.dto.Interest> newInterests = new ArrayList<>();
            while (it.hasNext()) {
                FileItem item = it.next();
                if (!item.isFormField()) {
                    byte[] image = item.get();
                    if (image != null && image.length != 0) {
                        user.setImage(image);
                    }
                    System.out.println(user.getImage());
                } else {
                    switch (item.getFieldName()) {
                        case "name":
                            user.setUserName(item.getString());
                            break;
                        case "mail":
                            user.setEmail(item.getString());

                            break;
                        case "password":
                            user.setPassword(item.getString());
                            break;
                        case "job":
                            user.setJob(item.getString());
                            break;
                        case "date":
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate date = LocalDate.parse(item.getString(), formatter);
                            user.setDOB(date);
                            break;
                        case "address":
                            user.setAddress(item.getString());
                            break;
                        case "credit":
                            user.setCreditNumber(item.getString());
                            credit.setNumber(item.getString());
                            if (creditDao.checkCredit(credit)) {//credit number is exist is 
                                if (!(userDaoImpl.isCreditNumberAssigned(credit))) {
                                    creditExist = true;
                                    System.out.println("creditExist = true;");
                                } else {

                                    creditExist = false;
                                    System.out.println("creditExist = falsefalse;");

                                }
                            } else {
                                creditExist = false;

                                System.out.println("creditExist=false;");

                            }
                            break;

                        default:
                            eg.agrimarket.model.dto.Interest interest=new eg.agrimarket.model.dto.Interest();
                            interest.setId(Integer.parseInt(item.getString()));
                            interest.setName(item.getFieldName());
                            newInterests.add(interest);
                            System.out.println(item.getFieldName() + " : " + item.getString());
                    }
                }
            }

            // check if user exist in Db 
            if (creditExist) {
                user.setInterests(newInterests);
                UserDaoImpl userDao = new UserDaoImpl();

                //
                userDao.signUp(user);
                session.setAttribute("user", user);

                System.out.println(user.getInterests());
                System.out.println(user.getImage());

                response.sendRedirect("index.jsp");
            } else {

                response.sendRedirect("sign_up.jsp");
                System.out.println("user didnt saved");

            }
        } catch (FileUploadException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
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
