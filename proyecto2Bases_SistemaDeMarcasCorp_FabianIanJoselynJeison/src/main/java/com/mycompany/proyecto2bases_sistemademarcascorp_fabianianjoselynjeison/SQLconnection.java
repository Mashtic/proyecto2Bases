
package com.mycompany.proyecto2bases_sistemademarcascorp_fabianianjoselynjeison;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLconnection {
    
    public static Connection getConnection(){
        String connectionUrl = "jdbc:sqlserver://server-proyecto-bd.database.windows.net:1433;databaseName=Prueba;user=adminPry;password=password01%;encrypt=true;trustServerCertificate=true";
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
