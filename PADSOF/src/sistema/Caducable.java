package sistema;

import java.time.*;

public interface Caducable extends Comparable<Caducable> {

	LocalDateTime getFechaCaducidad();

	void caducar();

	default boolean isCaducado() {
		return !Reloj.now().isBefore(getFechaCaducidad());
	}

	@Override
	default int compareTo(Caducable otro) {
		return this.getFechaCaducidad().compareTo(otro.getFechaCaducidad());
	}
}