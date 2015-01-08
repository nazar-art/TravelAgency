package com.zasadnyy.task10.controller.persistance;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static Logger log = Logger.getLogger(ConnectionManager.class);

/*    static {
        PropertyConfigurator.configure("/log4j.properties");
    }*/

    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource datasource = (DataSource) envContext.lookup("jdbc/onlinedb");
            con = datasource.getConnection();
        } catch (NamingException e) {
            log.error(e);
        }
        return con;
    }
}
