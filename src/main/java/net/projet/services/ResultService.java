package net.projet.services;

import net.projet.dao.ResultDoa;
import net.projet.entity.Result;
import net.projet.util.DataBaseConnection;

import java.util.ArrayList;

public class ResultService {
    private ResultDoa resultDoa;

    public ResultService(){
        resultDoa = new ResultDoa(DataBaseConnection.getConnection());
    }

    public boolean addResult(Result result){
        return resultDoa.addResult(result);
    }

    public Result findByEtudiantId(Long etudiantId){
        return resultDoa.findResultOfUser(etudiantId);
    }

    public ArrayList<Result> getAllEtudiantResult(Long etudiantId){
        return resultDoa.getAllEtudiantResult(etudiantId);
    }

    public int nbrResultByetudiantId(Long etudiantId){
        return resultDoa.nbrResultByetudiantId(etudiantId);
    }

    public float moyenneGenerale(Long etudiantId){
        return resultDoa.moyenGenerale(etudiantId);
    }
}
