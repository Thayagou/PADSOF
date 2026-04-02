package estadistica;

import java.io.Serializable;
import java.time.*;
import java.util.*;

import sistema.Sistema;
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
		System.out.println("entraaa");
		this.producto = producto;
		double valor = Sistema.getInstancia().getPonderacionCategoria();
		
		for (Categoria c: producto.getCategorias()) {
			recomendacion.put(c, valor);
		}
		
		producto.setStatsProducto(this);
	}

	public void actualizarVector() {
		double valor = Sistema.getInstancia().getPonderacionCategoria();
		
		for (Categoria c: producto.getCategorias()) {
			recomendacion.putIfAbsent(c, valor);
		}
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
	
	public StatsMensual getEstadisticasEntreMeses(YearMonth inicio, YearMonth fin) {
		StatsMensual stats = new StatsMensual(), 
				clave = new StatsMensual(inicio);
		
		int indexIni = Collections.binarySearch(estadisticas, clave);
		clave.setMes(fin);
		int indexFin = Collections.binarySearch(estadisticas, clave);
		
		if (indexIni < 0) indexIni = -indexIni - 1;
		if (indexFin < 0) indexFin = -indexFin - 1;

		for (int i = indexIni; i < indexFin ;i++) {
			stats.incrementar(estadisticas.get(i).getUnidades(), estadisticas.get(i).getRecaudacion());
		}
		
		return stats;
	}
	
	public Map<Categoria, Double> getVectorRecomendacion() {
		return Collections.unmodifiableMap(recomendacion);
	}
	
	
	/**
	 * Getter del producto asociado
	 * @return el producto asociado
	 */
	public Producto getProducto() {
		return producto;
	}

	public double getNormaVector() {
		return norma;
	}

	@Override
	public String toString() {
		return "StatsProducto [producto=" + producto.getNombre() + ", estadisticas=" + estadisticas + ", recomendacion="
				+ recomendacion + ", norma=" + norma + "]";
	}
	
	
	
	
}
