package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad {@link Proveedor}. Encapsula operaciones JDBC sobre la tabla
 * <code>proveedor</code>: registrar, listar, eliminar y modificar.
 *
 * <p><b>Buenas prácticas (solo comentarios, sin cambiar código):</b></p>
 * <ul>
 *   <li>Cerrar siempre <code>rs</code>, <code>ps</code> y <code>con</code> (try-with-resources). <!-- TODO --></li>
 *   <li>Validar RUC (11 dígitos) y normalizar textos antes de persistir. <!-- TODO --></li>
 *   <li>Definir índice UNIQUE para <code>ruc</code> en la BD. <!-- TODO --></li>
 * </ul>
 *
 * @author USUARIO
 */
public class ProveedorDao {
    Connection con;            // Conexión activa a la BD
    Conexion cn = new Conexion(); // Proveedor de conexiones
    PreparedStatement ps;      // Sentencia preparada
    ResultSet rs;              // Conjunto de resultados

    /**
     * Inserta un nuevo proveedor en la tabla <code>proveedor</code>.
     *
     * @param pr objeto Proveedor con ruc, nombre, telefono y direccion
     * @return true si el INSERT fue exitoso; false si ocurrió un error
     */
    public boolean RegistrarProveedor(Proveedor pr){
        String sql = "INSERT INTO proveedor(ruc, nombre, telefono, direccion) VALUES (?,?,?,?)";
        try {
           con = cn.getConnection();          // obtiene conexión
           ps = con.prepareStatement(sql);    // prepara sentencia
           // Asignación de parámetros (en orden de los ?)
           ps.setString(1, pr.getRuc());
           ps.setString(2, pr.getNombre());
           ps.setString(3, pr.getTelefono());
           ps.setString(4, pr.getDireccion());
           ps.execute();                      // ejecuta INSERT
           return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            // Cierre de conexión. // TODO: cerrar también ps con manejo seguro.
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    /**
     * Recupera todos los proveedores.
     *
     * @return lista de proveedores; si falla, devuelve lista (posiblemente vacía)
     */
    public List ListarProveedor(){
        List<Proveedor> Listapr = new ArrayList(); // TODO: tipar también la firma: List<Proveedor>
        String sql = "SELECT * FROM proveedor";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            // Mapeo fila → objeto Proveedor
            while (rs.next()) {                
                Proveedor pr = new Proveedor();
                pr.setId(rs.getInt("id"));
                pr.setRuc(rs.getString("ruc"));
                pr.setNombre(rs.getString("nombre"));
                pr.setTelefono(rs.getString("telefono"));
                pr.setDireccion(rs.getString("direccion"));
                Listapr.add(pr);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        // TODO: cerrar rs, ps y con.
        return Listapr;
    }
    
    /**
     * Elimina un proveedor por su ID.
     *
     * @param id identificador del proveedor a eliminar
     * @return true si el DELETE fue exitoso; false si ocurrió un error
     */
    public boolean EliminarProveedor(int id){
        String sql = "DELETE FROM proveedor WHERE id = ? ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            // TODO: cerrar ps además de con.
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    /**
     * Actualiza los datos de un proveedor existente.
     *
     * @param pr proveedor con los nuevos valores (incluye id para WHERE)
     * @return true si el UPDATE fue exitoso; false si ocurrió un error
     */
    public boolean ModificarProveedor(Proveedor pr){
        String sql = "UPDATE proveedor SET ruc=?, nombre=?, telefono=?, direccion=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getRuc());
            ps.setString(2, pr.getNombre());
            ps.setString(3, pr.getTelefono());
            ps.setString(4, pr.getDireccion());
            ps.setInt(5, pr.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            // TODO: cerrar ps además de con.
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}
