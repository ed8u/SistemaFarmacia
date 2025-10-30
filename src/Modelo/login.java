package Modelo;

/**
 * Modelo de usuario para autenticación y control de acceso.
 * <p>Contiene identificador, nombre, correo, contraseña y rol.</p>
 *
 * <p><b>Notas (solo comentarios, sin cambiar código):</b></p>
 * <ul>
 *   <li>La contraseña (<code>pass</code>) debería almacenarse como <b>hash seguro</b> (BCrypt/Argon2), no en texto plano. <!-- TODO --></li>
 *   <li>Conviene normalizar <code>correo</code> (trim y a minúsculas) antes de persistir/consultar. <!-- TODO --></li>
 *   <li>El nombre de la clase empieza en minúscula (<code>login</code>); por convención Java debería ser <code>Login</code>. Mantengo tal cual. <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class login {
    // Identificador único del usuario (clave primaria en BD).
    private int id;
    // Nombre del usuario (para mostrar en UI o logs).
    private String nombre;
    // Correo electrónico del usuario (usado también para login).
    private String correo;
    // Contraseña en texto plano (ver nota de seguridad arriba).
    private String pass;
    // Rol del usuario (p. ej., "ADMIN", "VENDEDOR").
    private String rol;

    /**
     * Constructor por defecto.
     * <p>Útil para frameworks (ORM/serialización) o para setear propiedades luego.</p>
     */
    public login() {
    }

    /**
     * Constructor completo para inicializar todos los campos.
     *
     * @param id      identificador del usuario
     * @param nombre  nombre del usuario
     * @param correo  correo electrónico (login)
     * @param pass    contraseña (idealmente, hash en lugar de texto plano)
     * @param rol     rol del usuario en el sistema
     */
    public login(int id, String nombre, String correo, String pass, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.pass = pass;
        this.rol = rol;
    }

    /**
     * Obtiene el identificador del usuario.
     * @return id del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico.
     * @return correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico.
     * <!-- TODO: normalizar (trim y toLowerCase) antes de persistir/consultar -->
     * @param correo correo electrónico
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña (actualmente texto plano).
     * @return contraseña
     */
    public String getPass() {
        return pass;
    }

    /**
     * Establece la contraseña.
     * <!-- TODO: almacenar/verificar hash seguro en lugar de texto plano -->
     * @param pass contraseña
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Obtiene el rol del usuario.
     * @return rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol rol (p. ej., ADMIN, VENDEDOR)
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}
