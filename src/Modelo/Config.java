package Modelo;

/**
 * Configuración general de la farmacia/negocio para mostrar en comprobantes,
 * encabezados, pie de página, etc.
 *
 * <p>Es un POJO simple con los datos: RUC, nombre comercial o razón social,
 * teléfono, dirección y un mensaje opcional (por ejemplo, “¡Gracias por su compra!”).</p>
 *
 * <p><b>Buenas prácticas (solo comentarios, sin cambiar código):</b></p>
 * <ul>
 *   <li>Validar el formato de <code>ruc</code> (longitud y dígitos). <!-- TODO --></li>
 *   <li>Normalizar <code>telefono</code> (solo dígitos, longitud). <!-- TODO --></li>
 *   <li>Considerar inmutabilidad o builder si se carga desde BD una sola vez. <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class Config {
    // Identificador de la fila de configuración (clave en BD).
    private int id;
    // Número de RUC de la empresa (11 dígitos en Perú).
    private String ruc;
    // Nombre comercial o razón social.
    private String nombre;
    // Teléfono de contacto.
    private String telefono;
    // Dirección fiscal o de atención.
    private String direccion;
    // Mensaje opcional para mostrar (p. ej., en tickets).
    private String mensaje;
    
    /**
     * Constructor por defecto.
     * <p>Útil para frameworks (ORM/serialización) o para setear propiedades luego.</p>
     */
    public Config(){
        
    }

    /**
     * Constructor completo para inicializar todos los campos.
     *
     * @param id         identificador único
     * @param ruc        número de RUC de la empresa
     * @param nombre     nombre comercial o razón social
     * @param telefono   teléfono de contacto
     * @param direccion  dirección fiscal o de atención
     * @param mensaje    mensaje opcional para mostrar en comprobantes
     */
    public Config(int id, String ruc, String nombre, String telefono, String direccion, String mensaje) {
        this.id = id;
        this.ruc = ruc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el identificador de la configuración.
     * @return id de la fila de configuración
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la configuración.
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el RUC de la empresa.
     * @return RUC (como cadena)
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * Establece el RUC de la empresa.
     * <!-- TODO: validar longitud (11) y que contenga solo dígitos -->
     * @param ruc número de RUC
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    /**
     * Obtiene el nombre comercial o razón social.
     * @return nombre de la empresa
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre comercial o razón social.
     * @param nombre nombre de la empresa
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el teléfono de contacto.
     * @return teléfono como cadena
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono de contacto.
     * <!-- TODO: normalizar y validar longitud/caracteres -->
     * @param telefono número telefónico
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección de la empresa.
     * @return dirección
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la empresa.
     * @param direccion dirección fiscal o de atención
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el mensaje opcional para comprobantes o UI.
     * @return mensaje a mostrar
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje opcional para comprobantes o UI.
     * @param mensaje texto del mensaje (agradecimiento, aviso, etc.)
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
