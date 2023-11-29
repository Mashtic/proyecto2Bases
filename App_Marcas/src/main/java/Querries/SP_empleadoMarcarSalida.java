/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querries;

import Conexion.Conectar_Planta;
import Extras.Conversion;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;

/**
 *
 * @author sotic
 */
public class SP_empleadoMarcarSalida {
    
    public static int Insertar_Salida(String idEmpleado, String fecha, String hora) throws ParseException{
         CallableStatement cstmt = null;
         int tipoSolucion = -1;
                 
        try {
            String procedimiento = "CALL empleadoMarcarSalida(?, ?, ?, ?);";
            cstmt = Conectar_Planta.conectar().prepareCall(procedimiento);
            
            cstmt.setInt(1, Integer. parseInt(idEmpleado));
            cstmt.setDate(2, java.sql.Date.valueOf(fecha));    
            cstmt.setTime(3, Conversion.strAHora(hora));
            cstmt.registerOutParameter(4, Types.INTEGER);

            cstmt.execute();

            tipoSolucion = cstmt.getInt(4);
                 
        }
        catch (SQLException ex){
            tipoSolucion = -1;
        }
        finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException sqlEx) { } // ignore
                cstmt = null;
            }
        }
        return tipoSolucion;
    }
}
