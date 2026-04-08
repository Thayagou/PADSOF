package sistema;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Clase que representa el Asignador de ids de la tienda
 */
public class AsignadorId implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Instancia de AsignadorId */
	private static AsignadorId instancia;
	/** Contador de Ids */
	private long contadorIds;

	/**
	 * Constructor de la clase
	 */
	private AsignadorId() {
		this.contadorIds = 0;
	}

	/**
	 * Restaura la instancia singleton de AsignadorId desde el stream
	 * @param ois Stream de entrada con la instancia serializada
	 * @throws ClassNotFoundException si la clase no se encuentra
	 * @throws IOException si ocurre un error de lectura
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
