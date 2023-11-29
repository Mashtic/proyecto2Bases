
package com.mycompany.proyecto2bases_sistemademarcascorp_fabianianjoselynjeison;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLconnection {
    
    public static Connection getConnection(){
        String connectionUrl = "jdbc:sqlserver://LAPTOP-ARD07SEQ\\SQLEXPRESS:1433;databaseName=DB_CORP;user=TEST;password=1234;encrypt=true;trustServerCertificate=true";
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
