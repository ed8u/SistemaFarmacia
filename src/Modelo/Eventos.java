package Modelo;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 * Utilidad para validar la entrada de teclas en componentes Swing.
 * <p>Provee filtros para permitir solo letras, solo números, o números decimales.</p>
 *
 * <p><b>Notas (solo comentarios, sin cambiar código):</b></p>
 * <ul>
 *   <li>{@link java.awt.event.KeyEvent#consume()} descarta la tecla para que no se escriba.</li>
 *   <li>Estos métodos se suelen usar en los manejadores de eventos <code>keyTyped</code> de Swing.</li>
 *   <li>Si necesitas admitir tildes/ñ/guiones, amplía las condiciones. <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class Eventos {

    /**
     * Restringe la entrada a <b>letras</b> (A-Z, a-z), espacio y retroceso.
     * <p>Consume cualquier otra tecla.</p>
     *
     * @param evt evento de teclado capturado (normalmente en keyTyped)
     */
    public void textKeyPress(KeyEvent evt) {
        // declaramos una variable y le asignamos un evento
        char car = evt.getKeyChar();
        // Permite: letras mayúsculas/minúsculas, Backspace y espacio.
        // TODO: si se requieren tildes (á,é,í,ó,ú), ñ o guiones, ampliar la condición.
        if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z')
                && (car != (char) KeyEvent.VK_BACK_SPACE) && (car != (char) KeyEvent.VK_SPACE)) {
            evt.consume(); // descarta la tecla
        }
    }

    /**
     * Restringe la entrada a <b>dígitos</b> (0-9) y retroceso.
     * <p>No permite signos, espacios ni separadores decimales.</p>
     *
     * @param evt evento de teclado capturado (normalmente en keyTyped)
     */
    public void numberKeyPress(KeyEvent evt) {
        // declaramos una variable y le asignamos un evento
        char car = evt.getKeyChar();
        // Permite: dígitos 0-9 y Backspace.
        if ((car < '0' || car > '9') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume(); // descarta la tecla
        }
    }

    /**
     * Restringe la entrada a <b>números decimales</b> con punto (.) como separador
     * y retroceso. Solo permite un punto decimal por campo.
     *
     * @param evt       evento de teclado capturado (normalmente en keyTyped)
     * @param textField campo de texto a validar (para verificar si ya contiene '.')
     */
    public void numberDecimalKeyPress(KeyEvent evt, JTextField textField) {
        // declaramos una variable y le asignamos un evento
        char car = evt.getKeyChar();
        // Caso 1: si ya existe un punto en el texto y lo tecleado no es dígito ni Backspace, consumir
        if ((car < '0' || car > '9') && textField.getText().contains(".") && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        // Caso 2: si no es dígito, ni punto, ni Backspace, consumir
        } else if ((car < '0' || car > '9') && (car != '.') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
        // TODO: considerar admitir coma (,) según configuración regional.
        // TODO: validar formato completo en el foco perdido (parseo a double) para mayor robustez.
    }
}
