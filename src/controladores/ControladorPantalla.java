package controladores;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * Interfaz que deben implementar todos los controladores que participen
 * en el sistema de navegación con pila de TiendaFrame.
 *
 * Ciclo de vida de una pantalla:
 *   navegarA()    → mostrar()  → [usuario interactúa]
 *   navegarA()    → ocultar()  → [queda en la pila]
 *   volverAtras() → mostrar()  → [reaparece con estado intacto]
 *   limpiarPila() → destruir() → [libera recursos]
 *
 * Solo getVista() es obligatorio. El resto tienen implementación default vacía,
 * por lo que solo hay que sobrescribirlos cuando sean necesarios:
 *
 *   - mostrar()  → sobrescribir si la pantalla debe refrescar datos al volver
 *                  (ej: recargar lista de pedidos por si han cambiado).
 *   - ocultar()  → sobrescribir si hay timers, animaciones o scroll que pausar.
 *   - destruir() → sobrescribir si hay recursos externos que liberar
 *                  (conexiones, listeners globales, etc.).
 *
 * Ejemplo mínimo de controlador migrado:
 *
 *   public class ControlMiPantalla implements ActionListener, ControladorPantalla {
 *       private final MiVista vista = new MiVista();
 *
 *       public ControlMiPantalla(Tienda tienda) {
 *           vista.setControlador(this);
 *           TiendaFrame.getInstance().navegarA(this);
 *       }
 *
 *       {@literal @}Override public JPanel getVista() { return vista; }
 *       {@literal @}Override public void actionPerformed(ActionEvent e) { ... }
 *       // mostrar(), ocultar(), destruir() → no hace falta declararlos
 *   }
 */
public interface ControladorPantalla extends ActionListener{

    /**
     * Devuelve el JPanel asociado a esta pantalla.
     * Siempre debe ser la misma instancia (no crear uno nuevo cada vez).
     * <p><b>Obligatorio</b> — no tiene default porque cada controlador
     * tiene su propia vista.</p>
     */
    JPanel getVista();

    /**
     * Se llama justo ANTES de que la pantalla se haga visible.
     * Por defecto no hace nada.
     */
    default void mostrar() {}

    /**
     * Se llama justo DESPUÉS de que la pantalla deja de ser visible.
     * Por defecto no hace nada.
     */
    default void ocultar() {}

    /**
     * Se llama cuando la pantalla se elimina de la pila de forma definitiva.
     * Por defecto no hace nada.
     */
    default void destruir() {}
}