/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ian Coto
 * Desc: solamente conexión a BD_Planta
 */
public class Conectar_Planta {
    
    // Atributos
    private static String driver = "com.mysql.cj.jdbc.Driver";
    
    private static String host = "localhost";
    private static String puerto = "3306";
    
    private static String usuario = "root";
    private static String contrasenna = "1234";
    
    // Métodos
    public static Connection conectar(String baseDatos) {
        Connection conexion = null;

        try {
            // Hace conexión a la base
            String url = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDatos + "?useSSL=false";
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, contrasenna);
            
            // Verificar conexión
            if (conexion != null) {
                System.out.println("Conexión realizada");
            }
            else {
                System.out.println("Conexión fallida");
            }
      
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de MySQL.");
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
        }
        return conexion;
    }
    
}
