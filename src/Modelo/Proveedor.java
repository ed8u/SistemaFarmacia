package Modelo;

/**
 * Representa a un proveedor del sistema (datos para compras, catálogos y reportes).
 * <p>Incluye RUC, nombre, teléfono y dirección.</p>
 *
 * <p><b>Notas (solo comentarios, sin cambiar código):</b></p>
 * <ul>
 *   <li>Validar que <code>ruc</code> tenga 11 dígitos y solo números. <!-- TODO --></li>
 *   <li>Normalizar <code>telefono</code> y <code>direccion</code> (trim, longitud). <!-- TODO --></li>
 *   <li>Considerar índices/UNIQUE por <code>ruc</code> en la base de datos. <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class Proveedor {
    // Identificador único del proveedor (clave primaria en BD).
    private int id;
    // RUC del proveedor (Perú: 11 dígitos).
    private String ruc;
    // Razón social o nombre comercial del proveedor.
    private String nombre;
    // Teléfono de contacto del proveedor.
    private String telefono;
    // Dirección del proveedor.
    private String direccion;
    
    /**
     * Constructor por defecto.
     * <p>Útil para frameworks (ORM/serialización) o para setear propiedades luego.</p>
     */
    public Proveedor(){
        
    }

    /**
     * Constructor completo para inicializar todos los campos.
     *
     * @param id         identificador del proveedor
     * @param ruc        RUC del proveedor
     * @param nombre     razón social o nombre comercial
     * @param telefono   teléfono de contacto
     * @param direccion  dirección del proveedor
     */
    public Proveedor(int id, String ruc, String nombre, String telefono, String direccion) {
        this.id = id;
        this.ruc = ruc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /**
     * Obtiene el identificador del proveedor.
     * @return id del proveedor
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del proveedor.
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el RUC del proveedor.
     * @return RUC
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * Establece el RUC del proveedor.
     * <!-- TODO: validar longitud (11) y que contenga solo dígitos -->
     * @param ruc RUC del proveedor
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    /**
     * Obtiene el nombre o razón social del proveedor.
     * @return nombre del proveedor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre o razón social del proveedor.
     * @param nombre nombre del proveedor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el teléfono del proveedor.
     * @return teléfono de contacto
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del proveedor.
     * <!-- TODO: normalizar y validar longitud/caracteres -->
     * @param telefono teléfono de contacto
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección del proveedor.
     * @return dirección
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del proveedor.
     * @param direccion dirección
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
