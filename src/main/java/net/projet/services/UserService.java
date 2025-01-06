package net.projet.services;

import net.projet.dao.UserDoa;
import net.projet.entity.User;
import net.projet.enums.Roles;
import net.projet.util.DataBaseConnection;

import java.util.ArrayList;

public class UserService {
    private final UserDoa userDoa;
    public UserService(){
        userDoa = new UserDoa(DataBaseConnection.getConnection());
    }

    public boolean addUser(User user){
        return userDoa.saveUser(user);
    }

    public boolean deleteUser(Long id){
        return userDoa.deleteUser(id);
    }

    public boolean updateUser(User user){
        return userDoa.updateUser(user);
    }

    public ArrayList<User> findAll(Roles roles){
        return userDoa.findAll(roles);
    }

    public User login(String email,String password){
        return userDoa.login(email,password);
    }

    public User findById(Long id){
        return userDoa.findById(id);
    }
}
