package edu.mcckc.dao;

import edu.mcckc.domain.*;
import org.apache.logging.log4j.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TaskDao
        extends BaseDao
        implements IPersistable
{
    private static Logger logger = LogManager.getLogger(TaskDao.class);

    public TaskDao()
    {
        super();
    }

    public TaskDao(String configFileName)
    {
        super(configFileName);
    }


    @Override
    public Object getSingleObject(Object obj) throws Exception
    {
        Task task = null;
        Task filter;
        filter = (Task)obj;

        try
        {
            createDBConnnection();
            sql = "SELECT ID, NAME, DESCRIPTION, COMPLETE, PRIORITY, TYPE, DATEDUE  " +
                    " FROM TASK   "  +
                    " WHERE ID = ?  ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, filter.id);
            rs = ps.executeQuery();

            TaskMapper mapper = new TaskMapper(rs);
            while (rs.next())
            {
                task = (Task)mapper.mapSingleObject();

                String output = String.format("DB TASK: %d  /  %s  /  %s", task.id,
                        task.name, task.dateDue);
                logger.debug(output);
            }
            cleanUpResources();
        }
        catch (SQLException sqlex)
        {
            logger.error(sqlex.toString());
            throw sqlex;
        }
        return task;
    }

    @Override
    public ArrayList<Object> getManyObjects(Object obj)  throws Exception
    {
        ArrayList<Object> tasks = new ArrayList<Object>();
        Task task = null;
        Task filter;
        filter = (Task)obj;

        try
        {
            //      where    name   like   '%ham%'
            //      and       complete  = true   ;
            createDBConnnection();
            /*
            sql = "SELECT ID, NAME, DESCRIPTION, PRICE, ACTIVE, SIZE, COLOR, DATEPURCHASED  " +
                    " FROM PRODUCT   "  +
                    " WHERE NAME  LIKE   ? " +
                    " AND   PRICE <  ?     " +
                    " AND   ACTIVE  = ?    " +
                    " AND   SIZE  = ?     " +
                    " AND   COLOR  = ?    ; ";
            */

            sql = "SELECT ID, NAME, DESCRIPTION, COMPLETE, PRIORITY, TYPE, DATEDUE  " +
                    " FROM TASK   "  +
                    " WHERE NAME  LIKE   ? ";


            if (filter.complete != null)
            {
                sql += " AND   COMPLETE  = ?    ";
            }
            if (filter.priority != null)
            {
                sql += " AND   PRIORITY  = ?     ";
            }
            if (filter.type != null)
            {
                sql += " AND   TYPE  = ?    ";
            }
            sql += " ;";




            String nameFilter = "%" +  filter.name + "%";

            ps = conn.prepareStatement(sql);

            logger.debug("SQL: " + sql);

            int count = 1;
            ps.setString(count, nameFilter);

            if (filter.complete != null)
            {
                count++;
                ps.setBoolean(count, filter.complete);
            }
            if (filter.priority != null)
            {
                count++;
                ps.setInt(count, filter.priority);
            }
            if (filter.type != null)
            {
                count++;
                ps.setInt(count, filter.type);
            }



            rs = ps.executeQuery();

            TaskMapper mapper = new TaskMapper(rs);
            while (rs.next())
            {
                task = (Task)mapper.mapSingleObject();

                tasks.add(task);

                //String output = String.format("DB PRODUCT: %d  /  %s  /  %s", task.id,
                //        task.name, task.dateDue);

                logger.debug(task.toStringFull());
            }
            cleanUpResources();
        }
        catch (SQLException sqlex)
        {
            logger.error(sqlex.toString());
            throw sqlex;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString());
            throw ex;
        }
        return tasks;
    }

    @Override
    public void createSingleObject(Object obj) throws Exception
    {
        try
        {
            createDBConnnection();
            Task task = (Task)obj;

            logger.debug("Adding Task: " + task.toStringFull());

            sql = "INSERT INTO TASK " +
                    " ( NAME, DESCRIPTION, COMPLETE, PRIORITY, TYPE, DATEDUE ) " +
                    " VALUES " +
                    "  ( ?,  ?,  ?,  ?,  ?,  ?  );   ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, task.name);
            ps.setString(2, task.description);
            //ps.setDouble(3, task.price);
            ps.setBoolean (3, task.complete);
            ps.setInt(4, task.priority);
            ps.setInt(5, task.type);

            //  ("YYYY-MM-DD HH:MM:SS.SSS")
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String dbDate = df.format(task.dateDue);
            ps.setString(6, dbDate);

            //ps.setDate(7, task.dateDue);


            conn.setAutoCommit(true);
            ps.executeUpdate();
            cleanUpResources();
        }
        catch (SQLException sqlex)
        {
            logger.error(sqlex.toString());
            throw sqlex;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString());
            throw ex;
        }
        finally
        {
        }


        //return null;
    }

    @Override
    public void createManyObjects(ArrayList<Object> objList) {
        //return null;
    }

    /*@Override
    public void createTable(){

    }*/

    @Override
    public void updateSingleObject(Object obj) throws Exception
    {
        try
        {
            createDBConnnection();
            Task task = (Task)obj;

            sql = "UPDATE TASK  " +
                    " SET NAME = ? " +
                    " , DESCRIPTION = ? " +
                    " , COMPLETE = ? " +
                    " , PRIORITY = ? " +
                    " , TYPE = ? " +
                    " , DATEDUE = ? " +
                    " WHERE  ID = ? ; ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, task.name);
            ps.setString(2, task.description);
            //ps.setDouble(3, product.price);
            ps.setBoolean (3, task.complete);
            ps.setInt(4, task.priority);
            ps.setInt(5, task.type);

            //  ("YYYY-MM-DD HH:MM:SS.SSS")
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String dbDate = df.format(task.dateDue);
            ps.setString(6, dbDate);

            ps.setInt(7, task.id );

            conn.setAutoCommit(true);
            ps.executeUpdate();
            cleanUpResources();
        }
        catch (SQLException sqlex)
        {
            logger.error(sqlex.toString());
            throw sqlex;
        }
        finally
        {
        }


    }

    @Override
    public void updateManyObjects(ArrayList<Object> objList) {
        //return null;
    }

    @Override
    public void deleteSingleObject(Object obj)  throws Exception
    {
        try
        {
            createDBConnnection();
            Task product = (Task)obj;

            sql = "DELETE FROM  TASK  " +
                    " WHERE  ID = ? ; ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, product.id );

            conn.setAutoCommit(true);
            ps.executeUpdate();
            cleanUpResources();
        }
        catch (SQLException sqlex)
        {
            logger.error(sqlex.toString());
            throw sqlex;
        }
        catch (Exception ex)
        {
            logger.error(ex.toString());
            throw ex;
        }
        finally
        {
        }

    }

    @Override
    public void deleteManyObjects(ArrayList<Object> objList) {

    }
}
