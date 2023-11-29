/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyecto2bases_sistemademarcascorp_fabianianjoselynjeison;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.ButtonGroup;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kuron
 */
public class VistaCorporacion extends javax.swing.JFrame {

    /**
     * Creates new form VistaCorporacion
     */
    public VistaCorporacion() {
        initComponents();
        grupoBotones();
        opcionNull();
    
    }

    public void close(){
            WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
}
    
    private void grupoBotones (){
        ButtonGroup grupoBotones = new ButtonGroup ();
        grupoBotones.add(btnAguinaldo);
        grupoBotones.add(btnCargarPlanilla);
        grupoBotones.add(btnEmpleados);
        grupoBotones.add(btnHistoricoP);
        grupoBotones.add(btnPlantas);
    }    
    
      private void opcionNull (){
        lblTitulo.setText("");
        btnModificar.setVisible(false);
        lblCodInv.setVisible(false);
        tfCodEmpleado.setVisible(false);
        btnConsultar.setVisible(false);
        btnCalcAguinaldos.setVisible(false);
        lblCodInv.setVisible(false);
        lblFechaConsul.setVisible(false);
        lblAguinaldo.setVisible(false);
        tfCodEmpleado.setVisible(false);
        tfFechaEm.setVisible(false);

       
    }
      
  private void opcionEmpleados (){    
        opcionNull();
        btnModificar.setVisible(true);
        lblTitulo.setText("Información general de empleados");
        try{
            
        Connection connection = SQLconnection.getConnection();
        Statement stmt = connection.createStatement();
        String selectsql = "EXEC ObtenerEmpleados";
        ResultSet rs = stmt.executeQuery(selectsql);
        
        DefaultTableModel modeloTabla = new DefaultTableModel ();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellidos");
        modeloTabla.addColumn("Fecha Ingreso");
        modeloTabla.addColumn("Fecha Salida");
        modeloTabla.addColumn("Activo");
        modeloTabla.addColumn("Planta");
        modeloTabla.addColumn("Departamento");
        modeloTabla.addColumn("Supervisor");
        modeloTabla.addColumn("Tipo Calendario");

        while(rs.next()){
            modeloTabla.addRow(new Object[]{rs.getString("Id"),rs.getString("nombreEmpleado"),rs.getString("apellidosEmpleado"),rs.getString("fechaEntrada"),rs.getString("fechaSalida"),rs.getString("activo"),rs.getString("Planta"),rs.getString("Departamento"),rs.getString("Supervisor"),rs.getString("Calendario")});
            
            }
        tblVista.setModel(modeloTabla);
        
        connection.close();
        
        } 
        
        
        
        catch(Exception e){
            System.out.println(e);
        }
        
    }
  
   private void opcionConsultarPlantas (){    
        opcionNull();
        lblTitulo.setText("Información de las Plantas");
        try{
            
        Connection connection = SQLconnection.getConnection();
        Statement stmt = connection.createStatement();
        String selectsql = "EXEC ObtenerEstadisticasPorPlanta";
        ResultSet rs = stmt.executeQuery(selectsql);

        DefaultTableModel modeloTabla = new DefaultTableModel ();
        modeloTabla.addColumn("Id Planta");
        modeloTabla.addColumn("Nombre Planta");
        modeloTabla.addColumn("Cantidad Empleados");
        modeloTabla.addColumn("Total pagado en salarios brutos");
        modeloTabla.addColumn("Promedio de salarios brutos");
        
        while(rs.next()){
            modeloTabla.addRow(new Object[]{rs.getString("IdPlanta"),rs.getString("NombrePlanta"),rs.getString("CantidadEmpleados"),rs.getString("MontoTotalSalariosBrutos"),rs.getString("PromedioSalarioBruto")});
            }
        tblVista.setModel(modeloTabla);
        
        connection.close();
        
        } 
        
        
        
        catch(Exception e){
            System.out.println(e);
        }
        
    }
  
   private void opcionHistoricoPlanillas (){    
        opcionNull();
        lblTitulo.setText("Histórico de Planillas");
        try{
            
        Connection connection = SQLconnection.getConnection();
        Statement stmt = connection.createStatement();
        String selectsql = "EXEC ObtenerHistoricoPlanillas";
        ResultSet rs = stmt.executeQuery(selectsql);
       
        DefaultTableModel modeloTabla = new DefaultTableModel ();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Id Empleado");
        modeloTabla.addColumn("Fecha Inicio");
        modeloTabla.addColumn("Fecha Final");
        modeloTabla.addColumn("Salario Bruto");
        modeloTabla.addColumn("Obligaciones");
        modeloTabla.addColumn("Salario Neto");
        modeloTabla.addColumn("Pagada");
    
        
        while(rs.next()){
            modeloTabla.addRow(new Object[]{rs.getString("id"),rs.getString("idEmpleado"),rs.getString("fechaInicio"),rs.getString("fechaFinal"),rs.getString("salarioBruto"),rs.getString("obligaciones"),rs.getString("salarioNeto"),rs.getString("pagada")});
            }
        tblVista.setModel(modeloTabla);
        
        connection.close();
        
        } 
        
        
        
        catch(Exception e){
            System.out.println(e);
        }
        
    }
  
  
  private void opcionAguinaldos (){
        opcionNull();
        lblCodInv.setVisible(true);
        tfCodEmpleado.setVisible(true);
        btnConsultar.setVisible(true);
        btnCalcAguinaldos.setVisible(true);
        lblCodInv.setVisible(true);
        lblFechaConsul.setVisible(true);
        tfCodEmpleado.setVisible(true);
        tfFechaEm.setVisible(true);
        lblTitulo.setText("Información de Aguinaldos");
        try {
            
            Connection connection = SQLconnection.getConnection();
            Statement stmt = connection.createStatement();
            String selectsql = "EXEC ObtenerAguinaldos";
            ResultSet rs = stmt.executeQuery(selectsql);
            
            DefaultTableModel modeloTabla = new DefaultTableModel ();
            modeloTabla.addColumn("Código");
            modeloTabla.addColumn("ID Empleado");
            modeloTabla.addColumn("Fecha");
            modeloTabla.addColumn("Obligaciones");
            modeloTabla.addColumn("Salarios Bruto");
            modeloTabla.addColumn("Aguinaldo Neto");
            
            while(rs.next()){
            modeloTabla.addRow(new Object[]{rs.getString("Id"),rs.getString("idEmpleado"),rs.getString("fecha"),rs.getString("obligaciones"),rs.getString("salariosBrutos"),rs.getString("aguinaldoNeto")});
            }
            tblVista.setModel(modeloTabla);
        
            connection.close();
           
      } catch(Exception e){
            System.out.println(e);
        }
            
        
        
        
  }
  
  private void opcionCalcularAguinaldos (){
        lblTitulo.setText("Se calcularon los aguinaldos");
        try {
            
            Connection connection = SQLconnection.getConnection();
            Statement stmt = connection.createStatement();
            String selectsql = "EXEC ProcDan1";
            ResultSet rs = stmt.executeQuery(selectsql);
            
            connection.close();
           
            
      } catch(Exception e){
            System.out.println(e);
        }
            
        
        
        
  }
  
  private void opcionConsultarAguinaldo(String Id, String fecha) {
      lblAguinaldo.setVisible(true);
      try {
        Connection connection = SQLconnection.getConnection();
        String selectsql = "EXEC AguiDan ?, ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectsql)) {
            stmt.setString(1, Id);
            stmt.setString(2, fecha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String resultado = rs.getString(1);
                    lblAguinaldo.setText("Resultado: " + resultado);
                } else {
                    lblAguinaldo.setText("No se encontraron resultados.");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            connection.close();
        }
    } catch(Exception e){
            System.out.println(e);
        }
}
     
  //--------------------------------------------------------------------------
  
  
  
  
  
  
  
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFondo = new javax.swing.JPanel();
        pnlEncabezado = new javax.swing.JPanel();
        lblTituloEncabezado = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        btnCargarPlanilla = new javax.swing.JToggleButton();
        btnEmpleados = new javax.swing.JToggleButton();
        btnHistoricoP = new javax.swing.JToggleButton();
        btnAguinaldo = new javax.swing.JToggleButton();
        btnPlantas = new javax.swing.JToggleButton();
        btnPlantas1 = new javax.swing.JToggleButton();
        pnlScrollPane = new javax.swing.JScrollPane();
        tblVista = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        btnCalcAguinaldos = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        lblCodInv = new javax.swing.JLabel();
        tfCodEmpleado = new javax.swing.JTextField();
        tfFechaEm = new javax.swing.JTextField();
        lblFechaConsul = new javax.swing.JLabel();
        lblAguinaldo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlEncabezado.setBackground(new java.awt.Color(0, 0, 0));

        lblTituloEncabezado.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        lblTituloEncabezado.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloEncabezado.setText("Corporación");

        javax.swing.GroupLayout pnlEncabezadoLayout = new javax.swing.GroupLayout(pnlEncabezado);
        pnlEncabezado.setLayout(pnlEncabezadoLayout);
        pnlEncabezadoLayout.setHorizontalGroup(
            pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, 1443, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlEncabezadoLayout.setVerticalGroup(
            pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblTituloEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        pnlMenu.setBackground(new java.awt.Color(0, 51, 102));
        pnlMenu.setForeground(new java.awt.Color(204, 255, 102));

        btnCargarPlanilla.setBackground(new java.awt.Color(0, 51, 102));
        btnCargarPlanilla.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnCargarPlanilla.setForeground(new java.awt.Color(255, 255, 255));
        btnCargarPlanilla.setText("Cargar planilla");
        btnCargarPlanilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarPlanillaActionPerformed(evt);
            }
        });

        btnEmpleados.setBackground(new java.awt.Color(0, 51, 102));
        btnEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        btnEmpleados.setText("Datos de empleados");
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });

        btnHistoricoP.setBackground(new java.awt.Color(0, 51, 102));
        btnHistoricoP.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnHistoricoP.setForeground(new java.awt.Color(255, 255, 255));
        btnHistoricoP.setText("Histórico de planillas");
        btnHistoricoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoricoPActionPerformed(evt);
            }
        });

        btnAguinaldo.setBackground(new java.awt.Color(0, 51, 102));
        btnAguinaldo.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnAguinaldo.setForeground(new java.awt.Color(255, 255, 255));
        btnAguinaldo.setText("Aguinaldo de empleados");
        btnAguinaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAguinaldoActionPerformed(evt);
            }
        });

        btnPlantas.setBackground(new java.awt.Color(0, 51, 102));
        btnPlantas.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnPlantas.setForeground(new java.awt.Color(255, 255, 255));
        btnPlantas.setText("Consultar plantas");
        btnPlantas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlantasActionPerformed(evt);
            }
        });

        btnPlantas1.setBackground(new java.awt.Color(0, 51, 102));
        btnPlantas1.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnPlantas1.setForeground(new java.awt.Color(255, 255, 255));
        btnPlantas1.setText("Girar pagos");
        btnPlantas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlantas1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHistoricoP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAguinaldo, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addComponent(btnPlantas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCargarPlanilla, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPlantas1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnCargarPlanilla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnHistoricoP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnAguinaldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnPlantas, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnPlantas1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(118, 118, 118))
        );

        tblVista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblVista.setCellSelectionEnabled(true);
        tblVista.setShowGrid(false);
        pnlScrollPane.setViewportView(tblVista);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Título");

        btnModificar.setBackground(new java.awt.Color(0, 51, 102));
        btnModificar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar datos");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnCalcAguinaldos.setBackground(new java.awt.Color(0, 51, 102));
        btnCalcAguinaldos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCalcAguinaldos.setForeground(new java.awt.Color(255, 255, 255));
        btnCalcAguinaldos.setText("Calcular Aguinaldos");
        btnCalcAguinaldos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcAguinaldosActionPerformed(evt);
            }
        });

        btnConsultar.setBackground(new java.awt.Color(0, 51, 102));
        btnConsultar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConsultar.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        lblCodInv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodInv.setText("Código de empleado");

        tfCodEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfCodEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfCodEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCodEmpleadoActionPerformed(evt);
            }
        });

        tfFechaEm.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfFechaEm.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfFechaEm.setText("aaaa-mm-dd");
        tfFechaEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFechaEmActionPerformed(evt);
            }
        });

        lblFechaConsul.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaConsul.setText("Fecha de consulta");

        lblAguinaldo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlScrollPane)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodInv)
                            .addComponent(lblFechaConsul))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFondoLayout.createSequentialGroup()
                                .addComponent(tfCodEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnConsultar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCalcAguinaldos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnModificar))
                            .addGroup(pnlFondoLayout.createSequentialGroup()
                                .addComponent(tfFechaEm, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAguinaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(20, 20, 20))
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addComponent(pnlEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addComponent(pnlEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnModificar)
                                .addComponent(btnCalcAguinaldos))
                            .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfCodEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnConsultar))
                                .addComponent(lblCodInv)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfFechaEm, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaConsul)
                            .addComponent(lblAguinaldo))
                        .addGap(37, 37, 37))
                    .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarPlanillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarPlanillaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCargarPlanillaActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        if (btnEmpleados.isSelected()){
            opcionEmpleados();
        }
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnAguinaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAguinaldoActionPerformed
        if (btnAguinaldo.isSelected()){
            opcionAguinaldos();}
    }//GEN-LAST:event_btnAguinaldoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        close();
        ModificarDatosEmpleados md = new ModificarDatosEmpleados();
        md.setVisible(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnHistoricoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoricoPActionPerformed
        if (btnHistoricoP.isSelected()){
            opcionHistoricoPlanillas();}
    }//GEN-LAST:event_btnHistoricoPActionPerformed

    private void btnCalcAguinaldosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcAguinaldosActionPerformed

            opcionCalcularAguinaldos();
    }//GEN-LAST:event_btnCalcAguinaldosActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed

        String Id = tfCodEmpleado.getText();
        String fecha = tfFechaEm.getText();
        opcionConsultarAguinaldo(Id, fecha);
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void tfCodEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCodEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCodEmpleadoActionPerformed

    private void btnPlantasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlantasActionPerformed
        if (btnPlantas.isSelected()){
            opcionConsultarPlantas();}
    }//GEN-LAST:event_btnPlantasActionPerformed

    private void tfFechaEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFechaEmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFechaEmActionPerformed

    private void btnPlantas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlantas1ActionPerformed
        close();
        GirarPagos gp = new GirarPagos();
        gp.setVisible(true);
    }//GEN-LAST:event_btnPlantas1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaCorporacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaCorporacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaCorporacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCorporacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaCorporacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAguinaldo;
    private javax.swing.JButton btnCalcAguinaldos;
    private javax.swing.JToggleButton btnCargarPlanilla;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JToggleButton btnEmpleados;
    private javax.swing.JToggleButton btnHistoricoP;
    private javax.swing.JButton btnModificar;
    private javax.swing.JToggleButton btnPlantas;
    private javax.swing.JToggleButton btnPlantas1;
    private javax.swing.JLabel lblAguinaldo;
    private javax.swing.JLabel lblCodInv;
    private javax.swing.JLabel lblFechaConsul;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloEncabezado;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JScrollPane pnlScrollPane;
    private javax.swing.JTable tblVista;
    private javax.swing.JTextField tfCodEmpleado;
    private javax.swing.JTextField tfFechaEm;
    // End of variables declaration//GEN-END:variables
}
