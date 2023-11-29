/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author fabia
 */
public class mySQLConnectionSimul {
    // Librería de MySQL
    public String driver = "com.mysql.jdbc.Driver";

    // Nombre de la base de datos
    

    // Host
    public String hostname = "127.0.0.1";

    // Puerto
    public String port = "3306";

    // Ruta de nuestra base de datos (desactivamos el uso de SSL con "?useSSL=false")
    

    // Nombre de usuario
    public String username = "root";

    // Clave de usuario
    public String password = "Titobladeonyx1";

    public Connection conectarMySQL(int planta) {
        String database = "";
        String url ="";
        Connection conn = null;
        switch (planta) {
            case 1:
                database = "plantacentral";
                url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
                break;
            case 2:
                database = "plantacentral2";
                url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
                break;
            case 3:
                database = "plantacentral3";
                url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
                break;
            default:
                throw new AssertionError();
        }
        
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
