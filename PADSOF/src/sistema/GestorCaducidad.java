package sistema;

import java.util.*;
import java.util.concurrent.*;

/**
 * Esta clase representa el gestionador de los objetos caducables
 */
public class GestorCaducidad {
	private final PriorityQueue<Caducable> cola = new PriorityQueue<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static GestorCaducidad instancia;

	/**
     * Constructor privado para la instancia singleton
     */
    private GestorCaducidad() {}
	
	/**
	 * Método para obtener la instancia del GestorCaducidad
	 * @return Instancia del GestorCaducidad
	 */
	public static GestorCaducidad getInstancia() {
		if (instancia == null)
			instancia = new GestorCaducidad();
		return instancia;
	}

	/**
	 * Método para registrar un caducable en la cola de caducables
	 * @param c Nuevo caducable a añadir
	 */
	public synchronized void registrar(Caducable c) {
		cola.add(c);
	}

	/**
	 * Método para sacar un caducable de la cola de caducables
	 * @param c Caducable a sacar
	 */
	public synchronized void desregistrar(Caducable c) {
		cola.remove(c);
	}

	/**
	 * Método para inicar el proceso de comprobación del GestorCaducidad
	 * @param intervalo Cantidad de tiempo entre cada comprobación
	 * @param unidad Unidad de tiempo de los intervalos
	 */
	public void iniciar(long intervalo, TimeUnit unidad) {
		scheduler.scheduleAtFixedRate(this::comprobar, 0, intervalo, unidad);
	}

	/**
	 * Método para detener las comprobaciones periódicas del GestorCaducidad
	 */
	public void detener() {
		scheduler.shutdown();
	}

	/**
	 * Método para comprobar si ha caducado el caducable más próximo
	 */
	private synchronized void comprobar() {
		while (!cola.isEmpty() && cola.peek().isCaducado()) {
			Caducable c = cola.poll();
			c.caducar();
		}
	}
}