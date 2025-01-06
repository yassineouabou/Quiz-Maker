package net.projet.services;

import net.projet.dao.ReponseDoa;
import net.projet.entity.EtudiantReponse;
import net.projet.util.DataBaseConnection;

import java.util.ArrayList;

public class ReponseService {
    private ReponseDoa reponseDoa;

    public ReponseService(){
        reponseDoa = new ReponseDoa(DataBaseConnection.getConnection());
    }

    public boolean addReponse(EtudiantReponse etudiantReponse){
        return reponseDoa.saveReponse(etudiantReponse);
    }

    public ArrayList<EtudiantReponse> getAllReponseByEtudiantId(Long id){
        return reponseDoa.getAllReponseByEtudiantId(id);
    }
}
