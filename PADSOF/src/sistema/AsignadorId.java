package sistema;

import java.io.Serializable;

/**
 * Clase que representa el Asignador de ids de la tienda
 */
public class AsignadorId implements Serializable {
	private static final long serialVersionUID = 1L;
    private static AsignadorId instancia;
    private long contadorIds;
    
    /**
     * Constructor de la clase
     */
    private AsignadorId() {
        this.contadorIds = 0;
    }
    
    /**
     * Devuelve la instancia de asignador Id
     * @return AsignadorId, su instancia
     */
    public static AsignadorId getInstancia() {
        if (instancia == null) {
            instancia = new AsignadorId();
        }
        return instancia;
    }
    
    /**
     * Devuelve el siguiente Id disponible
     * @return el siguiente Id
     */
    public long siguienteId() {
        return ++contadorIds;
    }
}
