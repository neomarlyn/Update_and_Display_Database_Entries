package edu.mcckc.dao;

import java.sql.SQLException;
import org.apache.logging.log4j.*;

public class SetupDao
    extends BaseDao
{
    private static Logger logger = LogManager.getLogger(SetupDao.class);

    public SetupDao()
    {
        super();
    }

    public SetupDao(String configFileName)
    {
        super(configFileName);
    }

    public void createDatabaseTables()
    {
        createUserTable();
        createCustomerTable();
        createTaskTable();
    }

    private void createUserTable()
    {
    }

    private void createCustomerTable()
    {
    }

    private void createTaskTable()
    {
        try
        {
            createDBConnnection();
            stmt = conn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS TASK;");

            sql = "CREATE TABLE TASK  " +
                    "(   ID INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL" +
                    "   , NAME    TEXT" +
                    "   , DESCRIPTION    TEXT" +
                    "   , COMPLETE  INTEGER " +
                    "   , PRIORITY    INTEGER " +
                    "   , TYPE   INTEGER  "  +
                    "   , DATEDUE   TEXT ); ";

            stmt.executeUpdate(sql);
            stmt.close();
        }
        catch(SQLException sqlex)
        {
            logger.error(sqlex.toString());
        }
    }
}
