package edu.mcckc.tests;

import edu.mcckc.dao.TaskDao;
import edu.mcckc.domain.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

class TaskDaoTest {

    @Before
    void beforeTests() {
        //Insert an object into the database, so that tests have something to get from DB with predictable values
        TaskDao task = new TaskDao("sqliteconfig.properties");
        try {
            task.createSingleObject(task);
        }catch(Exception ex){
            System.out.println(ex);
        }
        //Fill in this task, insert into database
    }

    @Test
    void testGetSingleObject() {
        TaskDao task = new TaskDao("sqliteconfig.properties");
        try {
            task.getSingleObject(task);
            Assert.assertNotNull(task);
        }catch(Exception ex){

        }
    }
    @Test
    void testGetManyObjects() {
        TaskDao task = new TaskDao("sqliteconfig.properties");
        try {
            task.getManyObjects(task);
            Assert.assertNotNull(task);
        }catch(Exception ex){

        }
    }

    @After
    void afterTests() {
        TaskDao task = new TaskDao("sqliteconfig.properties");
        try {
            task.deleteSingleObject(task);
        }catch(Exception ex){
            System.out.println(ex);
        }
        //Remove the previously inserted record.
    }
}