package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * DAO de Cliente: encapsula operaciones CRUD contra la tabla `clientes`.
 * <p>Responsable de registrar, listar, eliminar, modificar y buscar clientes
 * usando JDBC (Connection/PreparedStatement/ResultSet).</p>
 *
 * <p><b>Notas:</b></p>
 * <ul>
 *   <li>Gestiona la conexión mediante la clase {@code Conexion} (no mostrada).</li>
 *   <li>Usa {@code PreparedStatement} para evitar SQL Injection.</li>
 *   <li>Mejoras recomendadas marcadas con <code>// TODO:</code>.</li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class ClienteDao {
    
    // Manejador de conexiones (debe proveer getConnection()).
    Conexion cn = new Conexion();
    // Conexión activa a la BD. Se va asignando en cada operación.
    Connection con;
    // Sentencia preparada para ejecutar SQL con parámetros.
    PreparedStatement ps;
    // Resultado de consultas SELECT.
    ResultSet rs;
    
    /**
     * Registra un nuevo cliente en la BD.
     *
     * @param cl objeto Cliente con dni, nombre, telefono y direccion
     * @return true si la inserción fue exitosa; false si ocurrió un error
     */
    public boolean RegistrarCliente(Cliente cl){
        String sql = "INSERT INTO clientes (dni, nombre, telefono, direccion) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection(); // obtiene conexión
            ps = con.prepareStatement(sql); // prepara la sentencia
            // Asigna parámetros en el orden de los placeholders (?)
            ps.setString(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.execute(); // ejecuta INSERT
            return true;
        } catch (SQLException e) {
            // Muestra el error en un diálogo. // TODO: considerar logging y manejo centralizado de errores.
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            // Cierra la conexión para liberar recursos.
            // TODO: cerrar también ps y rs con try-with-resources o utilitario.
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    /**
     * Lista todos los clientes existentes.
     *
     * @return lista de clientes; si falla, retorna una lista (posiblemente vacía)
     */
    public List ListarCliente(){
       // Nota: se usa tipo crudo (raw type) en la firma. // TODO: exponer List<Cliente> como tipo genérico.
       List<Cliente> ListaCl = new ArrayList();
       String sql = "SELECT * FROM clientes";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           // Itera sobre el ResultSet y mapea a objetos Cliente (patrón RowMapper manual).
           while (rs.next()) {               
               Cliente cl = new Cliente();
               cl.setId(rs.getInt("id"));
               cl.setDni(rs.getString("dni"));
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getString("telefono"));
               cl.setDireccion(rs.getString("direccion"));
               ListaCl.add(cl);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       // TODO: cerrar rs y ps. Considerar try-with-resources para evitar fugas.
       return ListaCl;
   }
   
    /**
     * Elimina un cliente por su ID.
     *
     * @param id identificador del cliente a eliminar
     * @return true si se ejecutó el DELETE; false si ocurrió un error
     */
    public boolean EliminarCliente(int id){
       String sql = "DELETE FROM clientes WHERE id = ?";
       try {
           // Nota: aquí se usa 'con' pero no se asigna con getConnection(). 
           // Si 'con' no fue inicializada previamente, podría lanzar NullPointerException.
           // TODO: agregar 'con = cn.getConnection();' como en los otros métodos (sin cambiar lógica ahora).
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           // Cierre de conexión. // TODO: cerrar ps.
           try {
               con.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
   
    /**
     * Modifica los datos de un cliente existente.
     *
     * @param cl cliente con los nuevos datos (incluye id para WHERE)
     * @return true si se ejecutó el UPDATE; false si ocurrió un error
     */
    public boolean ModificarCliente(Cliente cl){
       String sql = "UPDATE clientes SET dni=?, nombre=?, telefono=?, direccion=? WHERE id=?";
       try {
           // Nota: al igual que en EliminarCliente, se usa 'con' sin asignar aquí.
           // TODO: agregar 'con = cn.getConnection();' para consistencia (sin cambiar ahora).
           ps = con.prepareStatement(sql);   
           ps.setString(1, cl.getDni());
           ps.setString(2, cl.getNombre());
           ps.setString(3, cl.getTelefono());
           ps.setString(4, cl.getDireccion());
           ps.setInt(5, cl.getId());
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
     * Busca un cliente por DNI.
     *
     * @param dni documento de identidad del cliente (aquí recibido como int)
     * @return objeto Cliente con los campos encontrados; si no existe, retorna
     *         un Cliente con valores por defecto (id=0, etc.)
     */
    public Cliente Buscarcliente(int dni){
       Cliente cl = new Cliente();
       String sql = "SELECT * FROM clientes WHERE dni = ?";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           // Se usa setInt porque el parámetro del método es int.
           // TODO: si el DNI puede contener ceros a la izquierda o no es numérico, conviene usar String.
           ps.setInt(1, dni);
           rs = ps.executeQuery();
           if (rs.next()) {
               cl.setId(rs.getInt("id"));
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getString("telefono"));
               cl.setDireccion(rs.getString("direccion"));
               // Nota: no se setea el DNI al objeto. // TODO: cl.setDni(rs.getString("dni"));
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       // TODO: cerrar rs, ps y con en finally/try-with-resources.
       return cl;
   }
   
}

// Ref: SRP - este DAO solo gestiona persistencia de Cliente; reglas de negocio van en servicio.
