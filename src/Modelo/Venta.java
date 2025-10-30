package Modelo;

/**
 * Representa una venta registrada en el sistema.
 * <p>Incluye el identificador de la venta, el id del cliente, el nombre del
 * cliente (campo auxiliar), el vendedor que atendió, el total y la fecha.</p>
 *
 * <p><b>Notas (solo comentarios, sin cambiar código):</b></p>
 * <ul>
 *   <li>El campo {@code nombre_cli} es redundante si ya existe relación con la
 *       entidad Cliente; se mantiene para mostrar rápidamente en reportes/UI. <!-- TODO --></li>
 *   <li>{@code fecha} está modelada como {@code String}; para operaciones de
 *       fecha/hora conviene usar {@code java.time.LocalDate/LocalDateTime}. <!-- TODO --></li>
 *   <li>Validar que {@code total} sea &ge; 0 antes de persistir. <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class Venta {
    // Identificador único de la venta (clave primaria en BD).
    private int id;
    // Id del cliente asociado a la venta (FK hacia clientes).
    private int cliente;
    // Nombre del cliente (campo auxiliar para mostrar en listados).
    private String nombre_cli;
    // Nombre del vendedor que registró la venta.
    private String vendedor;
    // Importe total de la venta.
    private double total;
    // Fecha de la venta (formato como String; ver nota en Javadoc).
    private String fecha;
    
    /**
     * Constructor por defecto.
     * <p>Útil para frameworks (ORM/serialización) o para setear propiedades luego.</p>
     */
    public Venta(){
        
    }

    /**
     * Constructor completo para inicializar todos los campos.
     *
     * @param id          identificador de la venta
     * @param cliente     id del cliente (FK)
     * @param nombre_cli  nombre del cliente (auxiliar para UI/reportes)
     * @param vendedor    nombre del vendedor
     * @param total       total de la venta
     * @param fecha       fecha de emisión (como String)
     */
    public Venta(int id, int cliente, String nombre_cli, String vendedor, double total, String fecha) {
        this.id = id;
        this.cliente = cliente;
        this.nombre_cli = nombre_cli;
        this.vendedor = vendedor;
        this.total = total;
        this.fecha = fecha;
    }

    /**
     * Obtiene el identificador de la venta.
     * @return id de la venta
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la venta.
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el id del cliente asociado.
     * @return id del cliente
     */
    public int getCliente() {
        return cliente;
    }

    /**
     * Establece el id del cliente asociado.
     * @param cliente id del cliente (FK)
     */
    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el nombre del cliente (auxiliar).
     * @return nombre del cliente
     */
    public String getNombre_cli() {
        return nombre_cli;
    }

    /**
     * Establece el nombre del cliente (auxiliar).
     * @param nombre_cli nombre del cliente
     */
    public void setNombre_cli(String nombre_cli) {
        this.nombre_cli = nombre_cli;
    }

    /**
     * Obtiene el nombre del vendedor.
     * @return vendedor
     */
    public String getVendedor() {
        return vendedor;
    }

    /**
     * Establece el nombre del vendedor.
     * @param vendedor nombre del vendedor
     */
    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * Obtiene el total de la venta.
     * @return total
     */
    public double getTotal() {
        return total;
    }

    /**
     * Establece el total de la venta.
     * @param total importe total (>= 0) <!-- TODO: validar -->
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Obtiene la fecha de la venta (como String).
     * @return fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la venta (como String).
     * @param fecha fecha de emisión
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
