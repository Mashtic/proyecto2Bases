/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyecto2bases_sistemademarcascorp_fabianianjoselynjeison;

import com.mycompany.proyecto2bases_sistemademarcascorp_fabianianjoselynjeison.SQLconnection;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kuron
 */
public class Estadisticas extends javax.swing.JFrame {

    /**
     * Creates new form Estadisticas
     */
    public Estadisticas() {
        initComponents();
        grupoBotones();
        opcionNull();
    }
    
    private void grupoBotones (){
        ButtonGroup grupoBotones = new ButtonGroup ();
        grupoBotones.add(btnMontoTotal);
        grupoBotones.add(btnDesglose);
        grupoBotones.add(btnTop);
        grupoBotones.add(btnHistorico);
    }    
    
        private void opcionNull (){
        lblFechaFiA.setVisible(false);
        tfFechaFiA.setVisible(false);
        lblFechaInA.setVisible(false);
        tfFechaInA.setVisible(false);
        btnConsulA.setVisible(false);
        lblFechaInB.setVisible(false);
        tfFechaInB.setVisible(false);
        lblFechaFiB.setVisible(false);
        tfFechaFiB.setVisible(false);
        btnConsulB.setVisible(false);
        lblFechaInC.setVisible(false);
        tfFechaInC.setVisible(false);
        lblFechaFiC.setVisible(false);
        tfFechaFiC.setVisible(false);
        btnConsulC.setVisible(false);
        lblCodEmp.setVisible(false);
        tfCodEmp.setVisible(false);
        btnConsulEmp.setVisible(false);
       
    }
        
  private void opcionMontoTotalPagado(String FechaInicio, String FechaFin) {

    try {
        Connection connection = SQLconnection.getConnection();
        String sql = "EXEC CalcularMontoTotalPagado ?,?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, FechaInicio);
            stmt.setString(2, FechaFin);
            
            DefaultTableModel modeloTabla = new DefaultTableModel ();
            modeloTabla.addColumn("Monto Total Pagado");

            // Ejecutar el procedimiento almacenado y obtener el resultado en un ResultSet
            try (ResultSet rs = stmt.executeQuery()) {
                // Suponiendo que el procedimiento devuelve una única columna llamada MontoTotalPagado
                while (rs.next()) {
                    // Obtener el resultado y establecerlo en el JTable
                    modeloTabla.addRow(new Object[]{rs.getString("MontoTotalPagado")});
                    //String montoTotalPagado = rs.getString("MontoTotalPagado");
                    // Ajusta la lógica para establecer el resultado en tu JTable
                    // jTable.setModel(...);
                }
               tblEstad.setModel(modeloTabla);
            }
        }

        connection.close();
    } catch (SQLException ex) {
        System.err.println("Error SQL: " + ex.getMessage());
        System.err.println("Código de estado SQL: " + ex.getSQLState());
        ex.printStackTrace();
    }
}
  
    private void opcionDesglose(String FechaInicio, String FechaFin) {

    try {
        Connection connection = SQLconnection.getConnection();
        String sql = "EXEC CalcularMontoTotalPagadoConObligaciones ?,?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, FechaInicio);
            stmt.setString(2, FechaFin);
            
            DefaultTableModel modeloTabla = new DefaultTableModel ();
            modeloTabla.addColumn("Monto Total Salarios Netos");
            modeloTabla.addColumn("Monto Total de Obligaciones");
            modeloTabla.addColumn("Monto Total Pagado");

            // Ejecutar el procedimiento almacenado y obtener el resultado en un ResultSet
            try (ResultSet rs = stmt.executeQuery()) {
                // Suponiendo que el procedimiento devuelve una única columna llamada MontoTotalPagado
                while (rs.next()) {
                    // Obtener el resultado y establecerlo en el JTable
                    modeloTabla.addRow(new Object[]{rs.getString("MontoTotalSalariosNetos"),rs.getString("MontoTotalObligaciones"),rs.getString("MontoTotalPagado")});
                    //String montoTotalPagado = rs.getString("MontoTotalPagado");
                    // Ajusta la lógica para establecer el resultado en tu JTable
                    // jTable.setModel(...);
                }
               tblEstad.setModel(modeloTabla);
            }
        }

        connection.close();
    } catch (SQLException ex) {
        System.err.println("Error SQL: " + ex.getMessage());
        System.err.println("Código de estado SQL: " + ex.getSQLState());
        ex.printStackTrace();
    }
}
    
    private void opcionTop(String FechaInicio, String FechaFin) {

    try {
        Connection connection = SQLconnection.getConnection();
        String sql = "EXEC ObtenerTop10MejorPagados ?,?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, FechaInicio);
            stmt.setString(2, FechaFin);
            
            DefaultTableModel modeloTabla = new DefaultTableModel ();
            modeloTabla.addColumn("ID Empleado");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Apellido");
            modeloTabla.addColumn("Salario Neto");

            // Ejecutar el procedimiento almacenado y obtener el resultado en un ResultSet
            try (ResultSet rs = stmt.executeQuery()) {
                // Suponiendo que el procedimiento devuelve una única columna llamada MontoTotalPagado
                while (rs.next()) {
                    // Obtener el resultado y establecerlo en el JTable
                    modeloTabla.addRow(new Object[]{rs.getString("idEmpleado"),rs.getString("nombreEmpleado"),rs.getString("apellidosEmpleado"),rs.getString("salarioNeto")});
                    //String montoTotalPagado = rs.getString("MontoTotalPagado");
                    // Ajusta la lógica para establecer el resultado en tu JTable
                    // jTable.setModel(...);
                }
               tblEstad.setModel(modeloTabla);
            }
        }

        connection.close();
    } catch (SQLException ex) {
        System.err.println("Error SQL: " + ex.getMessage());
        System.err.println("Código de estado SQL: " + ex.getSQLState());
        ex.printStackTrace();
    }
}
    
    private void opcionHistorico(String Id) {

    try {
        Connection connection = SQLconnection.getConnection();
        String sql = "EXEC ObtenerHistoricoPagadoEmpleado ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Id);
            
            DefaultTableModel modeloTabla = new DefaultTableModel ();
            modeloTabla.addColumn("ID Planilla");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Apellido");
            modeloTabla.addColumn("Fecha Inicio");
            modeloTabla.addColumn("Fecha Final");
            modeloTabla.addColumn("Salario Bruto");
            modeloTabla.addColumn("Obligaciones");
            modeloTabla.addColumn("Salario Neto");
            modeloTabla.addColumn("Pagada");

            // Ejecutar el procedimiento almacenado y obtener el resultado en un ResultSet
            try (ResultSet rs = stmt.executeQuery()) {
                // Suponiendo que el procedimiento devuelve una única columna llamada MontoTotalPagado
                while (rs.next()) {
                    // Obtener el resultado y establecerlo en el JTable
                    modeloTabla.addRow(new Object[]{rs.getString("idHistorico"),rs.getString("nombreEmpleado"),rs.getString("apellidosEmpleado"),rs.getString("fechaInicio"),rs.getString("fechaFinal"),rs.getString("salarioBruto"),rs.getString("obligaciones"),rs.getString("salarioNeto"),rs.getString("pagada")});
                    //String montoTotalPagado = rs.getString("MontoTotalPagado");
                    // Ajusta la lógica para establecer el resultado en tu JTable
                    // jTable.setModel(...);
                }
               tblEstad.setModel(modeloTabla);
            }
        }

        connection.close();
    } catch (SQLException ex) {
        System.err.println("Error SQL: " + ex.getMessage());
        System.err.println("Código de estado SQL: " + ex.getSQLState());
        ex.printStackTrace();
    }
}

    public void close(){
            WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnMontoTotal = new javax.swing.JToggleButton();
        btnDesglose = new javax.swing.JToggleButton();
        btnTop = new javax.swing.JToggleButton();
        btnHistorico = new javax.swing.JToggleButton();
        btnRegresar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEstad = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        lblFechaInA = new javax.swing.JLabel();
        tfFechaInA = new javax.swing.JTextField();
        lblFechaFiA = new javax.swing.JLabel();
        tfFechaFiA = new javax.swing.JTextField();
        btnConsulA = new javax.swing.JButton();
        btnConsulB = new javax.swing.JButton();
        btnConsulEmp = new javax.swing.JButton();
        lblFechaInB = new javax.swing.JLabel();
        tfFechaInB = new javax.swing.JTextField();
        lblFechaFiB = new javax.swing.JLabel();
        tfFechaFiB = new javax.swing.JTextField();
        lblCodEmp = new javax.swing.JLabel();
        tfCodEmp = new javax.swing.JTextField();
        lblFechaInC = new javax.swing.JLabel();
        tfFechaInC = new javax.swing.JTextField();
        lblFechaFiC = new javax.swing.JLabel();
        tfFechaFiC = new javax.swing.JTextField();
        btnConsulC = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Estadísticas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        btnMontoTotal.setBackground(new java.awt.Color(0, 51, 102));
        btnMontoTotal.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnMontoTotal.setForeground(new java.awt.Color(255, 255, 255));
        btnMontoTotal.setText("Monto total pagado");
        btnMontoTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMontoTotalActionPerformed(evt);
            }
        });

        btnDesglose.setBackground(new java.awt.Color(0, 51, 102));
        btnDesglose.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnDesglose.setForeground(new java.awt.Color(255, 255, 255));
        btnDesglose.setText("Desglose del total");
        btnDesglose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesgloseActionPerformed(evt);
            }
        });

        btnTop.setBackground(new java.awt.Color(0, 51, 102));
        btnTop.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnTop.setForeground(new java.awt.Color(255, 255, 255));
        btnTop.setText("Top 10 pagados");
        btnTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTopActionPerformed(evt);
            }
        });

        btnHistorico.setBackground(new java.awt.Color(0, 51, 102));
        btnHistorico.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        btnHistorico.setForeground(new java.awt.Color(255, 255, 255));
        btnHistorico.setText("Histórico empleado");
        btnHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoricoActionPerformed(evt);
            }
        });

        btnRegresar.setBackground(new java.awt.Color(0, 51, 102));
        btnRegresar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnHistorico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTop, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDesglose, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMontoTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(btnMontoTotal)
                .addGap(18, 18, 18)
                .addComponent(btnDesglose)
                .addGap(18, 18, 18)
                .addComponent(btnTop)
                .addGap(18, 18, 18)
                .addComponent(btnHistorico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addGap(36, 36, 36))
        );

        tblEstad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblEstad);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblFechaInA.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaInA.setText("Fecha Inicio");

        tfFechaInA.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfFechaInA.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblFechaFiA.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaFiA.setText("Fecha Final");

        tfFechaFiA.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfFechaFiA.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnConsulA.setBackground(new java.awt.Color(0, 51, 102));
        btnConsulA.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConsulA.setForeground(new java.awt.Color(255, 255, 255));
        btnConsulA.setText("Consultar");
        btnConsulA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsulAActionPerformed(evt);
            }
        });

        btnConsulB.setBackground(new java.awt.Color(0, 51, 102));
        btnConsulB.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConsulB.setForeground(new java.awt.Color(255, 255, 255));
        btnConsulB.setText("Consultar");
        btnConsulB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsulBActionPerformed(evt);
            }
        });

        btnConsulEmp.setBackground(new java.awt.Color(0, 51, 102));
        btnConsulEmp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConsulEmp.setForeground(new java.awt.Color(255, 255, 255));
        btnConsulEmp.setText("Consultar");
        btnConsulEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsulEmpActionPerformed(evt);
            }
        });

        lblFechaInB.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaInB.setText("Fecha Inicio");

        tfFechaInB.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfFechaInB.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfFechaInB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFechaInBActionPerformed(evt);
            }
        });

        lblFechaFiB.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaFiB.setText("Fecha Final");

        tfFechaFiB.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfFechaFiB.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblCodEmp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodEmp.setText("Código de empleado");

        tfCodEmp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfCodEmp.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblFechaInC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaInC.setText("Fecha Inicio");

        tfFechaInC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfFechaInC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfFechaInC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFechaInCActionPerformed(evt);
            }
        });

        lblFechaFiC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaFiC.setText("Fecha Final");

        tfFechaFiC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfFechaFiC.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnConsulC.setBackground(new java.awt.Color(0, 51, 102));
        btnConsulC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConsulC.setForeground(new java.awt.Color(255, 255, 255));
        btnConsulC.setText("Consultar");
        btnConsulC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsulCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCodEmp)
                        .addGap(12, 12, 12)
                        .addComponent(tfCodEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConsulEmp))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFechaInC, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfFechaInC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFechaInB, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfFechaInB, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFechaInA, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfFechaInA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFechaFiC, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfFechaFiC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnConsulC))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFechaFiA, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfFechaFiA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFechaFiB, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfFechaFiB, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnConsulA)
                                    .addComponent(btnConsulB))))))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblFechaInA)
                                    .addComponent(tfFechaInA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblFechaInB)
                                    .addComponent(tfFechaInB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblFechaInC)
                                    .addComponent(tfFechaInC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblFechaFiA)
                                    .addComponent(tfFechaFiA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnConsulA))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnConsulB)
                                    .addComponent(lblFechaFiB)
                                    .addComponent(tfFechaFiB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnConsulC)
                                    .addComponent(lblFechaFiC)
                                    .addComponent(tfFechaFiC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConsulEmp)
                            .addComponent(lblCodEmp)
                            .addComponent(tfCodEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMontoTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMontoTotalActionPerformed
            if (btnMontoTotal.isSelected()){
            opcionNull();
            lblFechaFiA.setVisible(true);
            tfFechaFiA.setVisible(true);
            lblFechaInA.setVisible(true);
            tfFechaInA.setVisible(true);
            btnConsulA.setVisible(true);
            lblTitulo.setText("Monto total pagado por la empresa en salarios");
        }
            
    }//GEN-LAST:event_btnMontoTotalActionPerformed

    private void btnDesgloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesgloseActionPerformed
        if (btnDesglose.isSelected()){
        opcionNull();
            lblFechaFiB.setVisible(true);
            tfFechaFiB.setVisible(true);
            lblFechaInB.setVisible(true);
            tfFechaInB.setVisible(true);
            btnConsulB.setVisible(true);
            lblTitulo.setText("Desglose del monto total pagado por la empresa");
    }//GEN-LAST:event_btnDesgloseActionPerformed
}
    
    private void btnTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTopActionPerformed
        if(btnTop.isSelected()){
        opcionNull();
            lblFechaFiC.setVisible(true);
            tfFechaFiC.setVisible(true);
            lblFechaInC.setVisible(true);
            tfFechaInC.setVisible(true);
            btnConsulC.setVisible(true);
            lblTitulo.setText("Top 10 empleado mejor pagados");
    }//GEN-LAST:event_btnTopActionPerformed
}
    private void btnHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoricoActionPerformed
        if(btnHistorico.isSelected()){
        opcionNull();
        lblCodEmp.setVisible(true);
        tfCodEmp.setVisible(true);
        btnConsulEmp.setVisible(true);
    }//GEN-LAST:event_btnHistoricoActionPerformed
}
    private void tfFechaInBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFechaInBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFechaInBActionPerformed

    private void btnConsulAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsulAActionPerformed
        String FechaInicio = tfFechaInA.getText();
        String FechaFin = tfFechaFiA.getText();
        opcionMontoTotalPagado(FechaInicio, FechaFin);
    }//GEN-LAST:event_btnConsulAActionPerformed

    private void tfFechaInCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFechaInCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFechaInCActionPerformed

    private void btnConsulBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsulBActionPerformed
        String FechaInicio = tfFechaInB.getText();
        String FechaFin = tfFechaFiB.getText();
        opcionDesglose(FechaInicio, FechaFin);
    }//GEN-LAST:event_btnConsulBActionPerformed

    private void btnConsulCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsulCActionPerformed
        String FechaInicio = tfFechaInC.getText();
        String FechaFin = tfFechaFiC.getText();
        opcionTop(FechaInicio, FechaFin);
    }//GEN-LAST:event_btnConsulCActionPerformed

    private void btnConsulEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsulEmpActionPerformed
        String Id = tfCodEmp.getText();
        opcionHistorico(Id);
    }//GEN-LAST:event_btnConsulEmpActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        close();
        VistaCorporacion vc = new VistaCorporacion();
        vc.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    
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
            java.util.logging.Logger.getLogger(Estadisticas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Estadisticas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Estadisticas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Estadisticas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Estadisticas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulA;
    private javax.swing.JButton btnConsulB;
    private javax.swing.JButton btnConsulC;
    private javax.swing.JButton btnConsulEmp;
    private javax.swing.JToggleButton btnDesglose;
    private javax.swing.JToggleButton btnHistorico;
    private javax.swing.JToggleButton btnMontoTotal;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JToggleButton btnTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodEmp;
    private javax.swing.JLabel lblFechaFiA;
    private javax.swing.JLabel lblFechaFiB;
    private javax.swing.JLabel lblFechaFiC;
    private javax.swing.JLabel lblFechaInA;
    private javax.swing.JLabel lblFechaInB;
    private javax.swing.JLabel lblFechaInC;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblEstad;
    private javax.swing.JTextField tfCodEmp;
    private javax.swing.JTextField tfFechaFiA;
    private javax.swing.JTextField tfFechaFiB;
    private javax.swing.JTextField tfFechaFiC;
    private javax.swing.JTextField tfFechaInA;
    private javax.swing.JTextField tfFechaInB;
    private javax.swing.JTextField tfFechaInC;
    // End of variables declaration//GEN-END:variables
}
