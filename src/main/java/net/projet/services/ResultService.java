package net.projet.services;

import net.projet.dao.ResultDoa;
import net.projet.entity.Result;
import net.projet.util.DataBaseConnection;

public class ResultService {
    private ResultDoa resultDoa;

    public ResultService(){
        resultDoa = new ResultDoa(DataBaseConnection.getConnection());
    }

    public boolean addResult(Result result){
        return resultDoa.addResult(result);
    }
}
