package sistema;

import java.util.*;
import java.util.concurrent.*;

public class GestorCaducidad {
	private final PriorityQueue<Caducable> cola = new PriorityQueue<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private static GestorCaducidad instancia;

	public static GestorCaducidad getInstancia() {
		if (instancia == null)
			instancia = new GestorCaducidad();
		return instancia;
	}

	public synchronized void registrar(Caducable c) {
		cola.add(c);
	}

	public synchronized void desregistrar(Caducable c) {
		cola.remove(c);
	}

	public void iniciar(long intervalo, TimeUnit unidad) {
		scheduler.scheduleAtFixedRate(this::comprobar, 0, intervalo, unidad);
	}

	public void detener() {
		scheduler.shutdown();
	}

	private synchronized void comprobar() {
		while (!cola.isEmpty() && cola.peek().isCaducado()) {
			Caducable c = cola.poll();
			c.caducar();
		}
	}
}