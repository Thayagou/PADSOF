package sistema;

import java.time.*;

/**
 * Interfaz Caducable para los objetos que caducan en la tienda
 */
public interface Caducable extends Comparable<Caducable> {

	/**
	 * Método para obtener la fecha de caducidad de un caducable
	 * @return Fecha de caducidad
	 */
	LocalDateTime getFechaCaducidad();

	/**
	 * Método que define la acción que realiza un objeto al caducarse
	 */
	void caducar();

	/*
	 * Método para comprobar si un caducable está caducado
	 * @return true si ha caducado, false si no
	 */
	default boolean isCaducado() {
		return !Reloj.now().isBefore(getFechaCaducidad());
	}

	/**
	 * Método para comparar dos caducables
	 * @return el valor de la comparación
	 */
	@Override
	default int compareTo(Caducable otro) {
		return this.getFechaCaducidad().compareTo(otro.getFechaCaducidad());
	}
}