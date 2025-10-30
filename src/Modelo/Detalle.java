package Modelo;

/**
 * Representa el detalle de una venta (línea de ítem) en el sistema.
 * <p>Incluye el identificador del detalle, el id del producto, la cantidad,
 * el precio unitario y el id de la venta a la que pertenece.</p>
 *
 * <p><b>Notas (solo comentarios, sin cambiar código):</b></p>
 * <ul>
 *   <li>Si se requiere el subtotal de la línea, puede calcularse como
 *       {@code cantidad * precio} en la capa de servicio o vista. <!-- TODO --></li>
 *   <li>Validar que {@code cantidad} &gt; 0 y {@code precio} &gt;= 0 antes de persistir. <!-- TODO --></li>
 *   <li>Considerar nombres más expresivos como {@code productoId} e {@code ventaId}
 *       en futuras refactorizaciones (manteniendo compatibilidad). <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class Detalle {
    // Identificador del detalle (clave primaria en BD).
    private int id;
    // Identificador del producto asociado a este detalle.
    private int id_pro;
    // Cantidad vendida del producto.
    private int cantidad;
    // Precio unitario del producto en este detalle.
    private double precio;
    // Identificador de la venta a la que pertenece este detalle.
    private int id_venta;
    
    /**
     * Constructor por defecto.
     * <p>Útil para frameworks (ORM/serialización) o para setear propiedades luego.</p>
     */
    public Detalle(){
        
    }

    /**
     * Constructor completo para inicializar todos los campos.
     *
     * @param id        identificador del detalle
     * @param id_pro    identificador del producto
     * @param cantidad  cantidad vendida
     * @param precio    precio unitario del producto
     * @param id_venta  identificador de la venta a la que pertenece
     */
    public Detalle(int id, int id_pro, int cantidad, double precio, int id_venta) {
        this.id = id;
        this.id_pro = id_pro;
        this.cantidad = cantidad;
        this.precio = precio;
        this.id_venta = id_venta;
    }

    /**
     * Obtiene el identificador del detalle.
     * @return id del detalle
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del detalle.
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del producto asociado.
     * @return id del producto
     */
    public int getId_pro() {
        return id_pro;
    }

    /**
     * Establece el identificador del producto asociado.
     * @param id_pro id del producto
     */
    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    /**
     * Obtiene la cantidad vendida del producto.
     * @return cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad vendida del producto.
     * @param cantidad cantidad (debe ser &gt; 0) <!-- TODO: validar antes de persistir -->
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio unitario del producto en este detalle.
     * @return precio unitario
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio unitario del producto en este detalle.
     * @param precio precio unitario (debe ser &gt;= 0) <!-- TODO: validar antes de persistir -->
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el identificador de la venta a la que pertenece el detalle.
     * @return id de la venta
     */
    public int getId_venta() {
        return id_venta;
    }

    /**
     * Establece el identificador de la venta a la que pertenece el detalle.
     * @param id_venta id de la venta
     */
    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
}

/** Subtotal sugerido: cantidad * precio, calculado en capa de servicio/UI. */
