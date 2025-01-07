package net.projet.dao;

import net.projet.entity.EtudiantReponse;
import net.projet.entity.Question;
import net.projet.entity.User;
import net.projet.services.QuestionService;
import net.projet.services.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReponseDoa {
    private Connection connection;
    private UserService userService;
    private QuestionService questionService;

    public ReponseDoa(Connection connection){
        this.connection = connection;
        userService = new UserService();
        questionService = new QuestionService();
    }

    public boolean saveReponse(EtudiantReponse etudiantReponse){
        String query="INSERT INTO studentresponse (studentId,questionId,selectedOption) VALUES(?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,etudiantReponse.getEtudiant().getId());
            ps.setLong(2,etudiantReponse.getQuestion().getId());
            ps.setString(3,etudiantReponse.getSelectedOption());
            int rowAffected  = ps.executeUpdate();
            return rowAffected > 0;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<EtudiantReponse> getAllReponseByEtudiantId(Long etudiantId){
        String query = "SELECT * FROM studentresponse WHERE studentId = ?";
        ArrayList<EtudiantReponse> etudiantReponses = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,etudiantId);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                User user = userService.findById(resultSet.getLong(2));
                Question question = questionService.findById(resultSet.getLong(3));
                EtudiantReponse etudiantReponse = new EtudiantReponse(resultSet.getLong(1),
                        user,
                        question,
                        resultSet.getString(4));
                etudiantReponses.add(etudiantReponse);
            }
            return etudiantReponses;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
