package estadistica;

import java.io.Serializable;
import java.time.*;
import java.util.*;

import exceptions.InvalidArgumentException;
import sistema.Reloj;
import sistema.Sistema;
import venta.productos.*;

/**
 * Clase StatsProducto nos permite almacenar información relevante de los productos de cada mes, junto a su vector de intereses
 */

public class StatsProducto implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Producto */
	private Producto producto;
	/** Lista de estadísticas */
	private List<StatsMensual> estadisticas = new ArrayList<>();
	/** Vector para recomendar en función de categorías */
	private Map<Categoria, Double> recomendacion = new HashMap<>();
	/** Resultado de la operación de la norma */
	private double norma = 0;	
	
	/**
	 * Constructor de la clase StatsProducto
	 * @param producto Producto respecto al cual se calculan las estadísticas
	 */
	public StatsProducto(Producto producto) {
		this.producto = producto;
		double valor = Sistema.getInstancia().getPonderacionCategoria();
		
		for (Categoria c: producto.getCategorias()) {
			recomendacion.put(c, valor);
		}
		
		producto.setStatsProducto(this);
	}

	/**
	 * Actualiza el vector de intereses del producto
	 */
	public void actualizarVector() {
		double valor = Sistema.getInstancia().getPonderacionCategoria();
		
		for (Categoria c: producto.getCategorias()) {
			recomendacion.putIfAbsent(c, valor);
		}
	}
	
	/**
	 * Actualiza la estadística del producto del último mes, incrementando las unidades vendidas y la recaudación
	 * @param udsVendidas Unidades vendidas
	 * @param precio Precio total pagado por ellas
	 * @return true si se actualiza correctamente
	 * @throws InvalidArgumentException Se lanza en caso de que alguna entrada sea negativa
	 */
	public boolean actualizarUltima(int udsVendidas, double precio) throws InvalidArgumentException {
		if (udsVendidas < 0 || precio < 0) throw new InvalidArgumentException("Las unidades y valor gastado deben ser positivos", "actualizar estadísticas del producto");
		
		if (estadisticas.isEmpty() || estadisticas.getLast().getMes().equals(Reloj.mesNow()) == false) {
			estadisticas.add(new StatsMensual());
		}
		
		estadisticas.getLast().incrementar(udsVendidas, precio);
		
		return true;
	}
	
	/**
	 * Obtiene un StatsMensual con el cúmulo de estadísticas entre los meses de inicio y fin
	 * @param inicio Mes desde el cual se empiezan a sumar las estadísticas
	 * @param fin Mes hasta el cual se suman las estadística
	 * @return Estadística acumulada
	 * @throws InvalidArgumentException Se lanza en caso de que haya algún error con las estadísticas acumuladas
	 */
	public StatsMensual getEstadisticasEntreMeses(YearMonth inicio, YearMonth fin) throws InvalidArgumentException {
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
	
	/**
	 * Getter del vector de recomendación del producto
	 * @return el vector en forma de Map
	 */
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

	/**
	 * Getter de la norma del vector asociado a la recomendación
	 * @return la norma
	 */
	public double getNormaVector() {
		return norma;
	}

	@Override
	public String toString() {
		return "StatsProducto [producto=" + producto.getNombre() + ", estadisticas=" + estadisticas + ", recomendacion="
				+ recomendacion + ", norma=" + norma + "]";
	}
	
	
	
	
}
