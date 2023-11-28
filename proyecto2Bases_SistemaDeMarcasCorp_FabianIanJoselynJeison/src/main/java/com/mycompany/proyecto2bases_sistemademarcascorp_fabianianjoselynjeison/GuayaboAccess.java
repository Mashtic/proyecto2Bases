/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2bases_sistemademarcascorp_fabianianjoselynjeison;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author vmrjo
 */
public class GuayaboAccess {
    
    public static JComboBox<String> inicializarBoxDepartamento (JComboBox<String> box){
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getDepartamentos()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            // Print results from select statement
            while (resultSet.next()) {
                box.addItem(resultSet.getString(2));    //va añadiendo los nombres de departamento al comboBox
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return box;
    }
    
    public static JComboBox<String> inicializarBoxSupervisor (JComboBox<String> box){
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getSupervisores()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            // Print results from select statement
            while (resultSet.next()) {
                box.addItem(resultSet.getString(1));    //va añadiendo los nombres de departamento al comboBox
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return box;
    }
    
    public static JComboBox<String> inicializarBoxTipoPagos (JComboBox<String> box){
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getTipoPagos()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            // Print results from select statement
            while (resultSet.next()) {
                box.addItem(resultSet.getString(2));    //va añadiendo los nombres de departamento al comboBox
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return box;
    }
    
    public static String getEmpleados(){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getEmpleados()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            str+="Id\tNombre\tApellidos\tFecha de entrada\tFecha de salida\tActivo(1=si, 0=no)\tDepartamento\tSupervisor\tCalendario\n";
            
            // Print results from select statement
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" + 
                        resultSet.getString(6) +"\t" + 
                        resultSet.getString(7) +"\t" + 
                        resultSet.getString(8) +"\t" + 
                        resultSet.getString(9) +"\t" + "\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getEmpleadoId(int id){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL getEmpleadoPorID (?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setInt(1, id); // Establecer el valor del parámetro
            
            ResultSet resultSet = null;

            resultSet = statement.executeQuery();
            str+="Id\tNombre\tApellidos\tFecha de entrada\tFecha de salida\tActivo(1=si, 0=no)\tDepartamento\tSupervisor\tCalendario\n";

            if (resultSet.next()){
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" + 
                        resultSet.getString(6) +"\t" + 
                        resultSet.getString(7) +"\t" + 
                        resultSet.getString(8) +"\t" + 
                        resultSet.getString(9) +"\t" +"\n";
            } else {
                str = "Ese empleado no está registrado en la base de datos.";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getEmpleadosDepartamento(int id){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL getEmpleadosDepartamento (?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setInt(1, id); // Establecer el valor del parámetro
            
            ResultSet resultSet = null;

            resultSet = statement.executeQuery();
            str+="Id\tNombre\tApellidos\tFecha de entrada\tFecha de salida\tActivo(1=si, 0=no)\tDepartamento\tSupervisor\tCalendario\n";

            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" + 
                        resultSet.getString(6) +"\t" + 
                        resultSet.getString(7) +"\t" + 
                        resultSet.getString(8) +"\t" + 
                        resultSet.getString(9) +"\t" +"\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getEmpleadosSupervisor(int id){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL getEmpleadosSupervisor (?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setInt(1, id); // Establecer el valor del parámetro
            
            ResultSet resultSet = null;

            resultSet = statement.executeQuery();
            str+="Id\tNombre\tApellidos\tFecha de entrada\tFecha de salida\tActivo(1=si, 0=no)\tDepartamento\tSupervisor\tCalendario\n";

            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" + 
                        resultSet.getString(6) +"\t" + 
                        resultSet.getString(7) +"\t" + 
                        resultSet.getString(8) +"\t" + 
                        resultSet.getString(9) +"\t" +"\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getEmpleadosDadosDeBaja(Date desde, Date hasta){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL getEmpleadosDadosDeBaja (?,?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setDate(1, new java.sql.Date(desde.getTime())); // Establecer el valor del parámetro
            statement.setDate(2, new java.sql.Date(hasta.getTime())); // Establecer el valor del parámetro
 
            ResultSet resultSet = null;

            resultSet = statement.executeQuery();
            str+="Id\tNombre\tApellidos\tFecha de entrada\tFecha de salida\tActivo(1=si, 0=no)\tDepartamento\tSupervisor\tCalendario\n";
            
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" + 
                        resultSet.getString(6) +"\t" + 
                        resultSet.getString(7) +"\t" + 
                        resultSet.getString(8) +"\t" + 
                        resultSet.getString(9) +"\t" +"\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String insertEmpleado(String nombre, String apellidos, Date fechaEntrada,
                                        int departamento, String supervisor, int calendario, String fechaSalida){
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL insertEmpleado (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setString(1, nombre); 
            statement.setString(2, apellidos);
            statement.setDate(3, new java.sql.Date(fechaEntrada.getTime())); 
            
            if (fechaSalida.equals("null")) {
                statement.setNull(4, java.sql.Types.DATE);
            } else {
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
                Date fechaSalidaDate = null;
                try {fechaSalidaDate = formatoFecha.parse(fechaSalida);
                } catch (ParseException ex) {Logger.getLogger(GuayaboAccess.class.getName()).log(Level.SEVERE, null, ex);}
                statement.setDate(4, new java.sql.Date(fechaSalidaDate.getTime()));
            }

            statement.setInt(5, departamento);
            
            if (supervisor.equals("null")) {
                statement.setNull(6, java.sql.Types.INTEGER);
            } else {
                statement.setInt(6, Integer.parseInt(supervisor));
            }
            statement.setInt(7, calendario);
            
            int filasActualizadas = statement.executeUpdate();
            connection.close();
            if (filasActualizadas == 1) return "El empleado se ha ingresado exitosamente.";
            else return "No se pudo ingresar el empleado.";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }
    
    public static String actualizarEmpleado(int id, String nombre, String apellidos, Date fechaEntrada,
                                        int departamento, String supervisor, int calendario, String fechaSalida){
        try{            
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL setEmpleadoPorID (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setInt(1, id);
            statement.setString(2, nombre); 
            statement.setString(3, apellidos);
            statement.setDate(4, new java.sql.Date(fechaEntrada.getTime())); 
            
            if (fechaSalida.equals("null")) {
                statement.setNull(5, java.sql.Types.DATE);
            } else {
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
                Date fechaSalidaDate = null;
                try {fechaSalidaDate = formatoFecha.parse(fechaSalida);
                } catch (ParseException ex) {Logger.getLogger(GuayaboAccess.class.getName()).log(Level.SEVERE, null, ex);}
                statement.setDate(5, new java.sql.Date(fechaSalidaDate.getTime()));
            }
            
            statement.setInt(6, departamento);
            
            if (supervisor.equals("null")) {
                statement.setNull(7, java.sql.Types.INTEGER);
            } else {
                statement.setInt(7, Integer.parseInt(supervisor));
            }
            statement.setInt(8, calendario);
            
            int filasActualizadas = statement.executeUpdate();
            connection.close();
            if (filasActualizadas == 1) return "La información del empleado se ha actualizado exitosamente.";
            else return "No se pudo actualizar la información del empleado.";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }
    
    public static String eliminarEmpleado(int id, Date fecha){
        try{            
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL eliminarEmpleado (?,?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setInt(1, id);
            statement.setDate(2, new java.sql.Date(fecha.getTime())); 
           
            int filasActualizadas = statement.executeUpdate();
            connection.close();
            if (filasActualizadas == 1) return "El empleado se ha dado de baja exitosamente.";
            else return "No se dio de baja a ningun empleado.";

        } catch (SQLException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }
    
    public static String getCalendarios(){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getTipoCalendarios()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            str+="Id\tTipo de empleado\tPago hora normal\tPago hora extra\tPago hora doble\tTipo de pago\tJornada\tSalarioBruto\tHoras semanales\n";
            
            // Print results from select statement
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" + 
                        resultSet.getString(6) +"\t" + 
                        resultSet.getString(7) +"\t" + 
                        resultSet.getString(8) +"\t" + 
                        resultSet.getString(9) +"\t" + "\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getJornadas(){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getJornadasLaborales()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            str+="Id\tHora de entrada\tHora de salida\tJornada\n";
            
            // Print results from select statement
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + "\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getFeriados(){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getFeriados()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            str+="Calendario\tDia\tLaborable(1=Si, 0=No)\tPago(N=normal, D=doble)\n";
            
            // Print results from select statement
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2).substring(5) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + "\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getMarcasEmpleados(){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getMarcasPlanta()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            str+="Id\tEmpleado\tFecha\tHora de entrada\tHora de salida\n";
            
            // Print results from select statement
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" + "\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getPlanillas(){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getPlanillas()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            str+="Id\tEmpleado\tFecha de inicio\tFecha final\tSalario bruto\tObligaciones\tSalarioNeto\n";
            
            // Print results from select statement
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" + 
                        resultSet.getString(6) +"\t" +
                        resultSet.getString(7) +"\t" + "\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getAusencias(Date desde, Date hasta){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL getAusencias (?,?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setDate(1, new java.sql.Date(desde.getTime())); // Establecer el valor del parámetro
            statement.setDate(2, new java.sql.Date(hasta.getTime())); // Establecer el valor del parámetro
 
            ResultSet resultSet = null;

            resultSet = statement.executeQuery();
            str+="Id\tId Empleado\tNombre\tApellidos\tFecha de marca\tHora de entrada\tHora de salida\n";
            
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" + 
                        resultSet.getString(6) +"\t" + 
                        resultSet.getString(7) +"\t" + 
                        resultSet.getString(8) +"\t" + "\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getTardias(Date desde, Date hasta){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL getTardiasDesdeHasta (?,?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setDate(1, new java.sql.Date(desde.getTime())); // Establecer el valor del parámetro
            statement.setDate(2, new java.sql.Date(hasta.getTime())); // Establecer el valor del parámetro
 
            ResultSet resultSet = null;

            resultSet = statement.executeQuery();
            
            
            str+="Id\tId Empleado\tNombre\tApellidos\tTipo de empleado\tFecha de marca\tHora de entrada\tHora de salida\n";
            
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" +
                        resultSet.getString(6) +"\t" +
                        resultSet.getString(7) +"\t" +
                        resultSet.getString(8) +"\t" +"\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String getSinMarcaSalida(Date desde, Date hasta){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL getSinMarcaSalida (?,?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setDate(1, new java.sql.Date(desde.getTime())); // Establecer el valor del parámetro
            statement.setDate(2, new java.sql.Date(hasta.getTime())); // Establecer el valor del parámetro
 
            ResultSet resultSet = null;

            resultSet = statement.executeQuery();
            
            
            str+="Id\tId Empleado\tNombre\tApellidos\tFecha de marca\tHora de entrada\tHora de salida\n";
            
            while (resultSet.next()) {
                str += resultSet.getInt(1) +"\t" + 
                        resultSet.getString(2) +"\t" + 
                        resultSet.getString(3) +"\t" + 
                        resultSet.getString(4) +"\t" + 
                        resultSet.getString(5) +"\t" +
                        resultSet.getString(6) +"\t" +
                        resultSet.getString(7) +"\t" + "\n";
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static String calcularPlanilla(int tipoPago, int mes, int semana){
        String str = "";
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            String mySql = "CALL calcularPagoPlanilla (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(mySql);
            statement.setInt(1, tipoPago); // Establecer el valor del parámetro
            statement.setInt(2, mes); 
            statement.setInt(3, semana); 
            
            ResultSet resultSet = null;

            resultSet = statement.executeQuery();
            str+="Id\tId empleado\tFecha Inicio\tFecha Final\tSalario bruto\tObligaciones\tSalario neto\n";

            while (resultSet.next()) {
                str += resultSet.getString(1) +"\t" + 
                    resultSet.getString(2) +"\t" + 
                    resultSet.getString(3) +"\t" + 
                    resultSet.getString(4) +"\t" + 
                    resultSet.getString(5) +"\t" +
                    resultSet.getString(6) +"\t" +
                    resultSet.getString(7) +"\t" + "\n";
            }
            
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return str;
    }
    
    public static void actualizarDatosPlanilla (){
        try{
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL setPlanilla()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void createCSV (){
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Formatear la fecha y hora en un formato específico
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss");
        String fechaFormateada = fechaHoraActual.format(formatter);
        
        try {
            PrintWriter pw = new PrintWriter(new File("C:\\Users\\vmrjo\\OneDrive\\Escritorio\\planilla_"+fechaFormateada.toString()+".csv"));
            StringBuilder sb = new StringBuilder();
            
            Connection connection = ConexionPlantaGuayabo.getConnection();
            Statement statement = null;
            statement = connection.createStatement();

            // Create and execute a SELECT SQL statement.
            String selectMySql = "CALL getPlanillaBoceto()";
            ResultSet resultSet = null;

            resultSet = statement.executeQuery(selectMySql);

            // Print results from select statement
            while (resultSet.next()) {
                sb.append(resultSet.getString(1));  sb.append(",");
                sb.append(resultSet.getString(2));  sb.append(",");
                sb.append(resultSet.getString(3));  sb.append(",");
                sb.append(resultSet.getString(4));  sb.append(",");
                sb.append(resultSet.getString(5));  sb.append(",");
                sb.append(resultSet.getString(6));  sb.append(",");
                sb.append(resultSet.getString(7));  sb.append("\r\n");
            }
            connection.close();
            
            pw.write(sb.toString());
            pw.close();  
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    
}
