package estadistica;

import java.io.Serializable;
import java.time.*;

import exceptions.InvalidArgumentException;
import sistema.Reloj;

/**
 * Clase StatsMensual que nos permite almacenar las unidades y recaudación. Unidades es un término intensionalmente ambiguo para poder almacenar distintos parámetros
 */
public class StatsMensual implements Serializable, Comparable<StatsMensual> {
	private static final long serialVersionUID = 1L;
	/** Mes que representa las estadísticas */
	private YearMonth mes;
	/** unidades vendidasd ese mes*/
	private int unidades = 0;
	/** recaudacion total ese mes*/
	private double recaudacion = 0;
	
	/**
	 * Constructor de la clase. Se inicializan a 0 las unidades y la recaudación y se asigna el mes actual a la instancia
	 */
	public StatsMensual() {
		this.mes = Reloj.mesNow();
	}
	
	/**
	 * Constructor de la clase. Se inicializan a 0 las unidades y la recaudación y se asigna el mes introducido a la instancia
	 * @param mes Mes de la estadística
	 */
	public StatsMensual(YearMonth mes) {
		this.mes = mes;
	}
	
	/**
	 * Actualiza la información almacenada dentro de la instancia
	 * @param uds Unidades a incrementar
	 * @param precio Recaudación a incrementar
	 * @return true si se actualiza correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de que alguna entrada sea negativa
	 */
	public boolean incrementar(int uds, double precio) throws InvalidArgumentException {
		if (uds < 0 || precio < 0) throw new InvalidArgumentException("Las unidades y precio deben ser positivos", "incrementar estadísticas mensuales");
		
		unidades += uds;
		recaudacion += precio;
		
		return true;
	}
	
	/**
	 * Getter del mes
	 * @return el mes
	 */
	public YearMonth getMes() {
		return mes;
	}
	
	/**
	 * Setter del mes
	 * @param mes Mes a establecer
	 */
	public void setMes(YearMonth mes) {
		this.mes = mes;
	}
	
	/**
	 * Getter de las unidades
	 * @return las unidades
	 */
	public int getUnidades() {
		return unidades;
	}
	
	/**
	 * Getter de la recaudación
	 * @return la recaudación
	 */
	public double getRecaudacion() {
		return recaudacion;
	}
	
	@Override
	public String toString() {
		return "Estadisticas mensual del " + mes + 
				"\nUnidades: " + unidades + 
				"\nRecaudacion=" + recaudacion;
	}

	/**
	 * Implementación del comparador de la implementación de la interfaz comparator
	 */
	@Override
	public int compareTo(StatsMensual stats) {
		return this.mes.compareTo(stats.mes);
	}	
	
}
