package Modelo;

/**
 * Modelo simple para representar opciones de selección (por ejemplo, en un JComboBox).
 * <p>Contiene un identificador (<code>id</code>) y una etiqueta visible (<code>nombre</code>).
 * La implementación de {@link #toString()} retorna el <code>nombre</code>, lo que permite
 * que los componentes de UI (p. ej., Swing) muestren correctamente la etiqueta del ítem.</p>
 *
 * <p><b>Uso típico:</b> poblar un {@code JComboBox<Combo>} y mostrar el nombre,
 * manteniendo internamente el id para operaciones posteriores.</p>
 *
 * @author Eduardo Jimenez
 */
public class Combo {
    // Identificador interno del ítem (se usa para lógica de negocio/BD).
    private int id;
    // Texto que se mostrará en la interfaz (etiqueta legible).
    private String nombre;

    /**
     * Constructor que inicializa ambos campos.
     *
     * @param id     identificador único del ítem
     * @param nombre etiqueta visible que se mostrará en la UI
     */
    public Combo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador del ítem.
     *
     * @return id del ítem
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del ítem.
     *
     * @param id identificador a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre (etiqueta visible) del ítem.
     *
     * @return nombre del ítem
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre (etiqueta visible) del ítem.
     *
     * @param nombre texto que se mostrará en la UI
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Devuelve la representación en texto del ítem.
     * <p>Se retorna el <code>nombre</code> para que, al utilizar instancias de esta
     * clase en componentes como {@code JComboBox}, se muestre la etiqueta legible
     * automáticamente.</p>
     *
     * @return el nombre del ítem
     */
    @Override
    public String toString(){
        return this.getNombre();
    }
}
