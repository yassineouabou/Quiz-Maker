package net.projet.dao;

import net.projet.entity.EtudiantReponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReponseDoa {
    private Connection connection;

    public ReponseDoa(Connection connection){
        this.connection = connection;
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
}
