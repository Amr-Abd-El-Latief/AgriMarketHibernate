/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.controller;

import java.io.IOException;
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
import model.DAOImp.UserDaoImpl;
import eg.agrimarket.model.dto.Interest;
import eg.agrimarket.model.dto.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Israa
 */
public class EditProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> it = items.iterator();

            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            if (user == null) {
                user = new User();
            }
            ArrayList<Interest> newInterests = new ArrayList<>();
            while (it.hasNext()) {
                FileItem item = it.next();
                if (!item.isFormField()) {
                    byte[] image = item.get();
                    if (image != null && image.length != 0) {
                        user.setImage(image);
                    }
//                    System.out.println(user.getImage());
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
            user.setInterests(newInterests);
            UserDaoImpl userDao = new UserDaoImpl();
            userDao.updateUser(user);

            session.setAttribute("user", user);
        } catch (FileUploadException ex) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("profile.jsp");
    }

}
