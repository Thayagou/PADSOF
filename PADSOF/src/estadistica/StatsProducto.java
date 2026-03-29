package estadistica;

import java.io.Serializable;
import java.time.*;
import java.util.*;

import venta.productos.*;

/**
 * Clase StatsProducto nos permite almacenar información relevante de los productos de cada mes, junto a su vector de intereses
 */

public class StatsProducto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Producto producto;
	private List<StatsMensual> estadisticas = new ArrayList<>();
	private Map<Categoria, Double> recomendacion = new HashMap<>();
	private double norma = 0;	
	
	/**
	 * Constructor de la clase StatsProducto
	 * @param producto Producto respecto al cual se calculan las estadísticas
	 */
	public StatsProducto(Producto producto) {
		this.producto = producto;
	}
	
	public boolean actualizarUltima(int udsVendidas, double precio) {
		if (udsVendidas < 0 || precio < 0) return false;
		
		if (estadisticas.isEmpty() || estadisticas.getLast().getMes().equals(YearMonth.now()) == false) {
			estadisticas.add(new StatsMensual());
		}
		
		estadisticas.getLast().incrementar(udsVendidas, precio);
		
		return true;
	}
	
	/**
	 * Obtiene la estadística del último mes
	 * @return Devuelve la del mes actual y si no existe, devuelve null. 
	 */
	public StatsMensual getUltima() {
		if (estadisticas.getLast().getMes().equals(YearMonth.now()) == false) return null;
		
		return estadisticas.getLast();
	}
	
	public Map<Categoria, Double> getVectorRecomendacion() {
		return Collections.unmodifiableMap(recomendacion);
	}
	
	public double getNormaVector() {
		return norma;
	}
	
	
}
