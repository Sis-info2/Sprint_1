
package Aplicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    Connection con;

    public Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            
            String myBD = "jdbc:postgresql://dpg-coal268l6cac73emur1g-a.oregon-postgres.render.com/sisinfo2";
            con = DriverManager.getConnection(myBD, "sisinfo2_user", "MjJfeyXg5JB1hbCf0vYs14Gmw8MVjo3N");
            //JOptionPane.showMessageDialog(null, "Se conectó correctamente a la BD");
            return con;
            
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "Error al conectar a la BD");
            System.out.println(e.toString());
        }
        return null;
    }

}

