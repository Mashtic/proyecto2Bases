
package com.mycompany.proyecto2bases_sistemademarcascorp_fabianianjoselynjeison;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLconnection {
    
    public static Connection getConnection(){
        String connectionUrl = "jdbc:sqlserver://DESKTOP-VRBEGCS:1433;databaseName=DBCORP;user=DANIEL_LOG;password=12345678;encrypt=true;trustServerCertificate=true";
        System.out.println("Conexión establecida");
        
        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            return conn;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
        
    }
}
