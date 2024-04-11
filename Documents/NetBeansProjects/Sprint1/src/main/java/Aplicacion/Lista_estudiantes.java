
package Aplicacion;

import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Formulario extends javax.swing.JFrame {

    private Conexion conexion;
    private DefaultTableModel modelo = new DefaultTableModel();
    private Statement ejecutor = null;

    private String golesBase = "";
    private String puestoActual = "1";
    private double comparacionActual = 1;
    private boolean bandera = true;

    protected void cargarTabla() {

        modelo.setRowCount(0);
        String datos[] = new String[5];
        String query = "select * from equipos order by puesto";

        ResultSet rs;
        try {
            ejecutor = conexion.getConnection().createStatement();
            ejecutor.setQueryTimeout(20);
            rs = ejecutor.executeQuery(query);
            while (rs.next() == true) {
                datos[0] = rs.getString("id");
                datos[1] = rs.getString("nombre");
                datos[2] = rs.getString("goles");
                datos[3] = rs.getString("puesto");
                datos[4] = rs.getString("comparacion");

                modelo.addRow(datos);
            }
            tablaEquipos.setModel(modelo);

        } catch (Exception e) {

        }

    }

    private boolean buscarNombre(String nombre) {
        Boolean res = false;
        String where = "where 1 = 1";

        if (!nombre.isEmpty()) {
            where = where + " and nombre='" + nombre + "' ";
        }

        String query = "select nombre from equipos " + where + " ;";

        ResultSet rs;
        try {
            ejecutor = conexion.getConnection().createStatement();
            ejecutor.setQueryTimeout(20);
            rs = ejecutor.executeQuery(query);

            if (rs.next()) {
                if (rs.getString("nombre").equals(nombre)) {
                    res = true;
                }
            }

        } catch (Exception e) {

        }
        return res;
    }

    private boolean buscarPuesto(String puesto) {
        boolean res = false;
        String where = "where 1 = 1";

        if (!puesto.isEmpty()) {
            where = where + " and puesto='" + puesto + "' ";
        }

        String query = "select puesto from equipos " + where + " ;";

        ResultSet rs;
        try {
            ejecutor = conexion.getConnection().createStatement();
            ejecutor.setQueryTimeout(20);
            rs = ejecutor.executeQuery(query);

            if (rs.next()) {
                if (rs.getString("puesto").equals(puesto)) {
                    res = true;
                }
            }
        } catch (Exception e) {
        }
        return res;
    }

    private void conectar() throws ClassNotFoundException {
        conexion = new Conexion();
        Connection con = null;
        con = conexion.getConnection();
    }

    protected void agregar() {
        String mensaje = "";
        String mensajeError = "";
        String query = "";
        PreparedStatement preparar = null;

        if (txtNombre.getText().isEmpty() || txtGoles.getText().isEmpty()) {

            if (txtNombre.getText().isEmpty()) {
                mensajeError += "Nombre no puede estar vacio\n";
            }

            if (txtGoles.getText().isEmpty()) {
                mensajeError += "Goles no puede estar vacio\n";
            }

            if (!mensajeError.isEmpty()) {
                JOptionPane.showMessageDialog(null, mensajeError);
            }

        } else if (mensajeError.isEmpty() && !buscarNombre(txtNombre.getText()) && !calcularComparacion(txtGoles.getText()).isEmpty()) {
            query = "insert into equipos(nombre,goles,puesto,comparacion) values"
                    + "('" + txtNombre.getText() + "','" + txtGoles.getText() + "','" + this.puestoActual + "','" + "x " + comparacionActual + "')";
            try {
                preparar = conexion.getConnection().prepareStatement(query);
                preparar.executeUpdate();
                cargarTabla();

                int aux = Integer.parseInt(puestoActual);
                puestoActual = (aux + 1) + "";

            } catch (Exception e) {
            }

        } else {
            if (buscarNombre(txtNombre.getText())) {
                mensaje += "Equipo ya registrado\n";
            }

            double aux = Math.round(Double.parseDouble(txtGoles.getText()) / Double.parseDouble(golesBase));

            if (aux == comparacionActual) {
                mensaje += "Puesto ocupado\n";
            }

            if (calcularComparacion(txtGoles.getText()).isEmpty()) {
                mensaje += "Cantidad de goles insuficiente, para ocupar un nuevo puesto\n";
            }
            JOptionPane.showMessageDialog(null, mensaje);
        }

    }

    private String calcularComparacion(String goles) {
        String comparacion = "";
        double aux = 0;
        ResultSet rs;
        String query = "select nombre from equipos";

        try {
            ejecutor = conexion.getConnection().createStatement();
            ejecutor.setQueryTimeout(20);
            rs = ejecutor.executeQuery(query);

            if (!rs.isBeforeFirst() && bandera) {
                bandera = false;
                this.puestoActual = "1";
                this.golesBase = goles;
                comparacion = " ";
            } else {
                aux = Math.round(Double.parseDouble(goles) / Double.parseDouble(golesBase));

                if (aux > comparacionActual) {
                    comparacionActual = (int) aux;
                    comparacion = " ";
                }
            }

        } catch (Exception e) {
        }

        return comparacion;
    }

    public Formulario() throws ClassNotFoundException {
        initComponents();

        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Goles");
        modelo.addColumn("Puesto");
        modelo.addColumn("Comparacion");

        conectar();
        cargarTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEquipos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtGoles = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaEquipos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "GOLES", "PUESTO"
            }
        ));
        jScrollPane1.setViewportView(tablaEquipos);

        jLabel2.setText("Nombre:");

        jLabel3.setText("Goles:");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(btnAgregar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtGoles, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtGoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addComponent(btnAgregar)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        agregar();
    }//GEN-LAST:event_btnAgregarActionPerformed

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
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Formulario().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Formulario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaEquipos;
    private javax.swing.JTextField txtGoles;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
