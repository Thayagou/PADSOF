package sistema;

import java.time.*;

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
	 */
	default boolean isCaducado() {
		return !Reloj.now().isBefore(getFechaCaducidad());
	}

	@Override
	default int compareTo(Caducable otro) {
		return this.getFechaCaducidad().compareTo(otro.getFechaCaducidad());
	}
}