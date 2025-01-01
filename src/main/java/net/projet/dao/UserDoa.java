package net.projet.dao;

import net.projet.entity.User;
import net.projet.enums.Roles;
import net.projet.exceptions.UserNotFoundException;

import java.sql.*;
import java.util.ArrayList;


public class UserDoa {
    public Connection connection;


    public UserDoa(Connection connection){
        this.connection = connection;
    }

    public boolean saveUser(User user){
        String sql = "INSERT INTO user(nom,prenom,email,password,role) values(?,?,?,?,?)";
        try(PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1,user.getNom());
            pst.setString(2,user.getPrenom());
            pst.setString(3,user.getEmail());
            pst.setString(4,user.getPassword());
            pst.setString(5,user.getRole().name());
            int rowAffected  =  pst.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteUser(Long userId) {
        String sql = "DELETE FROM user WHERE id = ?";
        try(PreparedStatement prs = connection.prepareStatement(sql)){
            prs.setLong(1,userId);
            int rowAfected = prs.executeUpdate();
            return rowAfected > 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateUser(User user){
        String sql = "UPDATE user SET nom = ?,prenom = ?, email = ?,password = ?";
        try(PreparedStatement prp = connection.prepareStatement(sql)){
            prp.setString(1,user.getNom());
            prp.setString(2,user.getPrenom());
            prp.setString(3,user.getEmail());
            prp.setString(4,user.getPassword());
            int rowAffected = prp.executeUpdate();
            return rowAffected > 0;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> findAll(Roles role) {
        String sql ="SELECT * FROM user WHERE role = ?";
        ArrayList<User> users = new ArrayList<>();

        try(PreparedStatement prp = connection.prepareStatement(sql);){
            prp.setString(1,String.valueOf(role));
            ResultSet resultSet =  prp.executeQuery();
            while (resultSet.next()){
                User user=new User(Long.valueOf(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        Roles.valueOf(resultSet.getString(6)));
                users.add(user);
            }
            return users;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public User login(String email,String password){
        String sql = "SELECT * FROM user WHERE email = ? and password = ?";
        try(PreparedStatement prs = connection.prepareStatement(sql)){
            prs.setString(1,email);
            prs.setString(2,password);
            ResultSet resultSet = prs.executeQuery();
            if(!resultSet.next())
                throw new UserNotFoundException("User Not Found !");
            else{
                return new User(Long.valueOf(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        Roles.valueOf(resultSet.getString(6)));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



}
