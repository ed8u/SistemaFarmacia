package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar productos, proveedores y datos de configuración.
 * <p>Maneja operaciones CRUD y consultas específicas usando JDBC contra las tablas
 * <code>productos</code>, <code>proveedor</code> y <code>config</code>.</p>
 *
 * <p><b>Notas (solo comentarios, sin cambiar lógica):</b></p>
 * <ul>
 *   <li>Se recomienda cerrar recursos (ResultSet, PreparedStatement, Connection)
 *       con try-with-resources para evitar fugas. <!-- TODO --></li>
 *   <li>Algunos métodos usan {@code con} sin inicializar con {@code cn.getConnection()};
 *       esto puede provocar NullPointerException si no se abrió antes. <!-- TODO --></li>
 *   <li>Agregar índices/UNIQUE en BD para campos como <code>codigo</code> en productos. <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class ProductosDao {
    Connection con;            // Conexión activa
    Conexion cn = new Conexion(); // Proveedor de conexiones
    PreparedStatement ps;      // Sentencia preparada
    ResultSet rs;              // Resultado de consultas
    
    /**
     * Registra un producto en la base de datos.
     *
     * @param pro objeto Productos con código, nombre, proveedor, stock y precio
     * @return true si el INSERT fue exitoso, false si ocurrió un error
     */
    public boolean RegistrarProductos(Productos pro){
        String sql = "INSERT INTO productos (codigo, nombre, proveedor, stock, precio) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();         // obtiene conexión
            ps = con.prepareStatement(sql);   // prepara sentencia
            // Asigna parámetros a los placeholders (?)
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setInt(3, pro.getProveedor());
            ps.setInt(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
            ps.execute();                     // ejecuta INSERT
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
        // TODO: cerrar ps y con (try-with-resources o finally).
    }
    
    /**
     * Lista todos los productos con datos del proveedor (JOIN).
     *
     * @return lista de productos; si falla, puede retornar lista vacía
     */
    public List ListarProductos(){
       List<Productos> Listapro = new ArrayList(); // TODO: tipar también la firma: List<Productos>
       String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN productos p ON pr.id = p.proveedor ORDER BY p.id DESC";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {
               // Mapeo de fila a objeto Productos
               Productos pro = new Productos();
               pro.setId(rs.getInt("id"));
               pro.setCodigo(rs.getString("codigo"));
               pro.setNombre(rs.getString("nombre"));
               pro.setProveedor(rs.getInt("id_proveedor"));
               pro.setProveedorPro(rs.getString("nombre_proveedor"));
               pro.setStock(rs.getInt("stock"));
               pro.setPrecio(rs.getDouble("precio"));
               Listapro.add(pro);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       // TODO: cerrar rs, ps y con.
       return Listapro;
   }
    
    /**
     * Elimina un producto por su ID.
     *
     * @param id identificador del producto a eliminar
     * @return true si el DELETE fue exitoso; false si ocurrió un error
     */
    public boolean EliminarProductos(int id){
       String sql = "DELETE FROM productos WHERE id = ?";
       try {
           // Nota: aquí no se invoca cn.getConnection(); si 'con' es null, fallará.
           // TODO: agregar con = cn.getConnection(); para consistencia (sin cambiar ahora).
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           // Cierra la conexión (ps permanece abierto). // TODO: cerrar ps también.
           try {
               con.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
    
    /**
     * Modifica los datos de un producto existente.
     *
     * @param pro producto con los nuevos datos (incluye id para el WHERE)
     * @return true si el UPDATE fue exitoso; false si ocurrió un error
     */
    public boolean ModificarProductos(Productos pro){
       String sql = "UPDATE productos SET codigo=?, nombre=?, proveedor=?, stock=?, precio=? WHERE id=?";
       try {
           // Nota: igual que en eliminar, aquí no se abre la conexión explícitamente.
           // TODO: agregar con = cn.getConnection(); (sin modificar ahora).
           ps = con.prepareStatement(sql);
           ps.setString(1, pro.getCodigo());
           ps.setString(2, pro.getNombre());
           ps.setInt(3, pro.getProveedor());
           ps.setInt(4, pro.getStock());
           ps.setDouble(5, pro.getPrecio());
           ps.setInt(6, pro.getId());
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
     * Busca un producto por su código.
     *
     * @param cod código del producto (SKU/EAN/UPC)
     * @return objeto Productos con los campos encontrados; si no existe, retorna
     *         un objeto con valores por defecto
     */
    public Productos BuscarPro(String cod){
        Productos producto = new Productos();
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next()) {
                // Se mapean solo algunos campos necesarios para ventas/búsqueda rápida
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                // Nota: no se setean proveedor/codigo aquí, solo lo esencial para la operación.
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        // TODO: cerrar rs, ps y con.
        return producto;
    }

    /**
     * Busca un producto por su ID, incluyendo datos del proveedor (JOIN).
     *
     * @param id identificador del producto
     * @return objeto Productos con datos completos si existe; caso contrario, objeto por defecto
     */
    public Productos BuscarId(int id){
        Productos pro = new Productos();
        String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN productos p ON p.proveedor = pr.id WHERE p.id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pro.setId(rs.getInt("id"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setNombre(rs.getString("nombre"));
                pro.setProveedor(rs.getInt("proveedor")); // id del proveedor según alias p.*
                pro.setProveedorPro(rs.getString("nombre_proveedor"));
                pro.setStock(rs.getInt("stock"));
                pro.setPrecio(rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        // TODO: cerrar rs, ps y con.
        return pro;
    }

    /**
     * Busca un proveedor por nombre exacto.
     *
     * @param nombre nombre del proveedor
     * @return objeto Proveedor con el id si se encontró; caso contrario, objeto por defecto
     */
    public Proveedor BuscarProveedor(String nombre){
        Proveedor pr = new Proveedor();
        String sql = "SELECT * FROM proveedor WHERE nombre = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next()) {
                pr.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        // TODO: cerrar rs, ps y con.
        return pr;
    }

    /**
     * Obtiene los datos generales de configuración (tabla config).
     *
     * @return objeto Config con la fila encontrada (asume una única fila)
     */
    public Config BuscarDatos(){
        Config conf = new Config();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setRuc(rs.getString("ruc"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setMensaje(rs.getString("mensaje"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        // TODO: cerrar rs, ps y con.
        return conf;
    }
    
    /**
     * Modifica los datos generales de configuración.
     *
     * @param conf objeto Config con los nuevos valores (incluye id para WHERE)
     * @return true si el UPDATE fue exitoso; false si ocurrió un error
     */
    public boolean ModificarDatos(Config conf){
       String sql = "UPDATE config SET ruc=?, nombre=?, telefono=?, direccion=?, mensaje=? WHERE id=?";
       try {
           // Nota: aquí no se inicializa 'con'. // TODO: con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setString(1, conf.getRuc());
           ps.setString(2, conf.getNombre());
           ps.setString(3, conf.getTelefono());
           ps.setString(4, conf.getDireccion());
           ps.setString(5, conf.getMensaje());
           ps.setInt(6, conf.getId());
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
