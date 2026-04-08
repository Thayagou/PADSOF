package sistema;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Clase que representa el Asignador de ids de la tienda
 */
public class AsignadorId implements Serializable {
	private static final long serialVersionUID = 1L;
	/**Instancia de AsignadorId*/
    private static AsignadorId instancia;
    /**Contador de Ids*/
    private long contadorIds;
    
    /**
     * Constructor de la clase
     */
    private AsignadorId() {
        this.contadorIds = 0;
    }
    
    /**
     * Restaura la instancia del archivo de guardado de la tienda
     * @param ois
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static void asignarInstancia(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    	AsignadorId.instancia = (AsignadorId) ois.readObject();
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
