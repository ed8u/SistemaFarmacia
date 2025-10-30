package Modelo;

/**
 * Representa un producto del inventario de la farmacia.
 * <p>Incluye identificador, código, nombre, proveedor (id y nombre),
 * stock disponible y precio unitario.</p>
 *
 * <p><b>Notas (solo comentarios, sin cambiar código):</b></p>
 * <ul>
 *   <li>Validar que <code>stock</code> sea &ge; 0 y <code>precio</code> sea &ge; 0 antes de persistir. <!-- TODO --></li>
 *   <li>En una refactorización futura, considerar nombres más expresivos:
 *       <code>proveedor</code> &rarr; <code>proveedorId</code> y <code>proveedorPro</code> &rarr; <code>proveedorNombre</code>. <!-- TODO --></li>
 *   <li>Si el campo <code>codigo</code> es clave única (SKU/Barcode), reforzar con índice UNIQUE en BD. <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class Productos {
    // Identificador único del producto (clave primaria en BD).
    private int id;
    // Código interno o de barras del producto (SKU/EAN/UPC).
    private String codigo;
    // Nombre comercial del producto.
    private String nombre;
    // Identificador del proveedor (relación con tabla proveedores).
    private int proveedor;
    // Nombre del proveedor (redundante para mostrar en UI/reportes).
    private String proveedorPro;
    // Stock actual disponible del producto.
    private int stock;
    // Precio unitario del producto.
    private double precio;
    
    /**
     * Constructor por defecto.
     * <p>Útil para frameworks (ORM/serialización) o para setear propiedades luego.</p>
     */
    public Productos(){
        
    }

    /**
     * Constructor completo para inicializar todos los campos.
     *
     * @param id            identificador del producto
     * @param codigo        código (SKU/EAN/UPC)
     * @param nombre        nombre del producto
     * @param proveedor     id del proveedor
     * @param proveedorPro  nombre del proveedor
     * @param stock         stock disponible
     * @param precio        precio unitario
     */
    public Productos(int id, String codigo, String nombre, int proveedor, String proveedorPro, int stock, double precio) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.proveedorPro = proveedorPro;
        this.stock = stock;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador del producto.
     * @return id del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el código del producto (SKU/EAN/UPC).
     * @return código del producto
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del producto (SKU/EAN/UPC).
     * @param codigo código a asignar <!-- TODO: validar unicidad/formato -->
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre del producto.
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el id del proveedor.
     * @return id del proveedor
     */
    public int getProveedor() {
        return proveedor;
    }

    /**
     * Establece el id del proveedor.
     * @param proveedor identificador del proveedor
     */
    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Obtiene el nombre del proveedor (campo auxiliar para mostrar).
     * @return nombre del proveedor
     */
    public String getProveedorPro() {
        return proveedorPro;
    }

    /**
     * Establece el nombre del proveedor (campo auxiliar para mostrar).
     * @param proveedorPro nombre del proveedor
     */
    public void setProveedorPro(String proveedorPro) {
        this.proveedorPro = proveedorPro;
    }

    /**
     * Obtiene el stock disponible del producto.
     * @return stock actual
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock disponible del producto.
     * @param stock cantidad en inventario <!-- TODO: validar que sea &ge; 0 -->
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Obtiene el precio unitario del producto.
     * @return precio unitario
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio unitario del producto.
     * @param precio precio a asignar <!-- TODO: validar que sea &ge; 0 -->
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

   
}
