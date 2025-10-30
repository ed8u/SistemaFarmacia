package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administrador de conexiones JDBC hacia MySQL/MariaDB.
 * <p>Expone el método {@link #getConnection()} para obtener una conexión
 * usando {@code DriverManager} con la URL, usuario y contraseña definidas.</p>
 *
 * <h2>Notas y buenas prácticas (solo comentarios, sin cambiar código)</h2>
 * <ul>
 *   <li>Externalizar credenciales y URL (propiedades/variables de entorno). <!-- TODO --></li>
 *   <li>Usar un pool de conexiones (HikariCP, c3p0) en apps reales. <!-- TODO --></li>
 *   <li>Manejar el cierre en la capa que abre la conexión (try-with-resources). <!-- TODO --></li>
 *   <li>Registrar el driver no suele ser necesario en JDBC 4+, pero si usas
 *       versiones antiguas podrías requerir {@code Class.forName("com.mysql.cj.jdbc.Driver");}. <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class Conexion {

    // Referencia a la conexión actual. Se asigna en getConnection().
    Connection con;

    /**
     * Obtiene una conexión JDBC a la base de datos.
     * <p>Devuelve {@code null} si ocurre una {@link SQLException}.</p>
     *
     * @return conexión activa o {@code null} en caso de error
     */
    public Connection getConnection() {
        try {
            // URL de conexión JDBC (hostname, puerto y schema). Incluye zona horaria.
            String myBD = "jdbc:mysql://localhost:3306/farma_sistem?serverTimezone=UTC";
            // Credenciales de acceso (usuario/contraseña). // TODO: no hardcodear en producción.
            con = DriverManager.getConnection(myBD, "root", "");
            return con;
        } catch (SQLException e) {
            // Log del error. // TODO: sustituir por logger (nivel ERROR) y mensaje contextual.
            System.out.println(e.toString());
        }
        // Si falló, retorna null. // TODO: evaluar lanzar excepción personalizada para manejo superior.
        return null;
    }

}
