/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2bases_sistemademarcascorp_fabianianjoselynjeison;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vmrjo
 */
public class ConexionPlantaGuayabo {
   
    private static final String connectionString =
                "jdbc:mysql://localhost:3306/plantaguayabo"+"?user=root&password=eladios";          // cambiar bd
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection;
            connection = DriverManager.getConnection(connectionString);
            //JOptionPane.showMessageDialog(null, "se conecto a la base");
            return connection;
        } catch (SQLException ex) {System.out.println("cae en catch"+ ex);} catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionPlantaGuayabo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
