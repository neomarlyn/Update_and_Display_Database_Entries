package edu.mcckc.dao;

import edu.mcckc.domain.Task;

import java.sql.ResultSet;


public class TaskMapper extends BaseMapper{

    public TaskMapper(ResultSet rs){
        super(rs);
    }

    public Object mapSingleObject(){

        Task temp = new Task();
        temp.id = mapValidInt("ID");
        temp.name = mapValidString("NAME");
        temp.description = mapValidString("DESCRIPTION");
       // temp.price = rs.mapValidDouble("PRICE");
        temp.complete = mapValidBoolean("COMPLETE");
        temp.priority = mapValidInt("PRIORITY");
        temp.type = mapValidInt("TYPE");
        temp.dateDue = mapValidDate("DATEDUE");
        return temp;
    }

}
