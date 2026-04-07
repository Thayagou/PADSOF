package sistema;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * Reloj interno de la tienda que nos permite realizar mejor los tests
 */
public class Reloj {
	/** Reloj que llevará el tiempo interno, inicializado a tiempo real */
	static private Clock clock = Clock.systemDefaultZone();
	/** Boolean que representa si el reloj se encuentra o no fijo */
	static private boolean fijo = false;
	/** Boolean que representa si el reloj está sincronizado con el tiempo real */
	static private boolean tiempoReal = true;
	
	/**
	 * Equivalente a LocalDateTime.now para nuestro reloj interno
	 * @return instante actual formateado a LocalDateTime
	 */
	public static LocalDateTime now() {
		return LocalDateTime.now(clock);
	}
	
	/**
	 * Equivalente a YearMonth.now para nuestro reloj interno
	 * @return instante actual formateado a YearMonth
	 */
	public static YearMonth mesNow() {
		return YearMonth.now(clock);
	}
	
	/**
	 * Equivalente a LocalDate.now para nuestro reloj interno
	 * @return instante actual formateado a LocalDate
	 */
	public static LocalDate localDateNow() {
		return LocalDate.now(clock);
	}
	
	/**
	 * Avanza el reloj interno de la tienda en un mes
	 */
	public static void avanzarMes() {
		clock = Clock.offset(clock, Duration.ofDays(mesNow().lengthOfMonth()));
		tiempoReal = false;
	}
	
	/**
	 * Pausa el reloj en el instante en el que se encontraba
	 */
	public static void pausarReloj() {
		if (fijo) return;
		clock = Clock.fixed(clock.instant(), clock.getZone());
		fijo = true;
		tiempoReal = false;
	}
	
	/**
	 * Continua el reloj a partir del instante en que quedó pausado
	 */
	public static void continuarReloj() {
		if (fijo == false) return;
		Instant pausa = clock.instant();
		Instant curr = Clock.systemDefaultZone().instant();
		Duration desfase = Duration.between(curr, pausa);
		clock = Clock.offset(clock, desfase);
		fijo = false;
	}
	
	/**
	 * Reseteamos el clock para que siga ahora el tiempo real con Clock.systemDefaultZone()
	 */
	public static void volverATiempoReal() {
		if (tiempoReal) return;
		clock = Clock.systemDefaultZone();
		fijo = false;
		tiempoReal = true;
	}
	
	/**
	 * Obtenemos si el reloj está fijo
	 * @return valor de fijo
	 */
	public static boolean isFijo() {
		return fijo;
	}
	
	/**
	 * Obtenemos si el reloj está siguiendo el tiempo real
	 * @return valor de tiempo real
	 */
	public static boolean isTiempoReal() {
		return tiempoReal;
	}
}
