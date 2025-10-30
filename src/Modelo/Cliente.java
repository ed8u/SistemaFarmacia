package Modelo;

/**
 * Representa a un cliente dentro del sistema.
 * <p>Agrupa los datos básicos del cliente
 * (id, dni, nombre, teléfono y dirección) para ser usado en las
 * distintas capas de la aplicación (DAO/servicio/vista).</p>
 */
public class Cliente {
    // Identificador único del cliente (normalmente la clave primaria en BD)
    private int id;
    // Documento de identidad (p. ej. DNI). Considera validar longitud/formato.
    private String dni;
    // Nombre completo o razón social del cliente.
    private String nombre;
    // Teléfono de contacto. Considera normalizar y validar caracteres numéricos.
    private String telefono;
    // Dirección del cliente (domicilio o fiscal).
    private String direccion;

    /**
     * Constructor por defecto.
     * <p>Requerido por algunas librerías (serialización/ORM) y útil para
     * instanciar el objeto y luego setear sus propiedades.</p>
     */
    public Cliente() {
    }

    /**
     * Constructor completo para inicializar todas las propiedades.
     *
     * @param id         identificador único del cliente
     * @param dni        documento de identidad (DNI/RUC/CE según el caso de uso)
     * @param nombre     nombre completo o razón social
     * @param telefono   teléfono de contacto
     * @param direccion  dirección del cliente
     */
    public Cliente(int id, String dni, String nombre, String telefono, String direccion) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /**
     * Devuelve el identificador único del cliente.
     *
     * @return id del cliente
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del cliente.
     *
     * @param id identificador único (proveniente de la BD o generado por la app)
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el documento de identidad del cliente.
     *
     * @return DNI (u otro tipo de documento) del cliente
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el documento de identidad del cliente.
     * <p>Sugerencia: validar longitud y formato según normativa local antes de setear.</p>
     *
     * @param dni documento de identidad del cliente
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Devuelve el nombre del cliente.
     *
     * @return nombre completo o razón social
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * <p>Sugerencia: normalizar (trim/capitalización) si es necesario.</p>
     *
     * @param nombre nombre completo o razón social
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el teléfono del cliente.
     *
     * @return número telefónico de contacto
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     * <p>Sugerencia: validar que solo contenga dígitos y la longitud esperada.</p>
     *
     * @param telefono número telefónico de contacto
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve la dirección del cliente.
     *
     * @return dirección (domicilio o fiscal)
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del cliente.
     *
     * @param direccion dirección (domicilio o fiscal)
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
