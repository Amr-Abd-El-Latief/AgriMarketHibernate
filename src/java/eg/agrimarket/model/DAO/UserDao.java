/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.agrimarket.model.DAO;

import java.util.List;
import eg.agrimarket.model.dto.Credit;
import eg.agrimarket.model.dto.User;

/**
 *
 * @author muhammad
 */
public interface UserDao {

    public List<User> getAllUsers();

    public User signIn(String email, String Password);

    public void updateUser(User user);

    public boolean signUp(User user);
    //We need get user

    public User getUser(String email);

    // if user exist return boolean needed to check if email stored before sign up

    public boolean isUserExist(String email, String Password);

    public boolean isUserExist(String email);

    public boolean isCreditNumberAssigned(Credit Credit);
}
