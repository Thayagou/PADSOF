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
	Producto producto;
	List<StatsMensual> estadisticas = new ArrayList<>();
	double sumaRecomendacion;
	Map<Categoria, Double> recomendacion = new HashMap<>();
	
	
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
	 * A partir del vector de preferencias de un usuario hace un producto escalar para obtener el valor de semejanza entre el producto y el usuario
	 * @param user Vector de preferencias de un usuario
	 * @param sumaUsuario Norma del vector
	 * @return valor de compatibilidad resultante
	 */
	public double getCompatibilidad(Vector<Double> user, double sumaUsuario) {
		int i;
		double total = 0;
		
		for (i = 0; i < recomendacion.size(); i++) {
			total += user.get(i)* recomendacion.get(i);
		}
		
		total /= sumaUsuario*sumaRecomendacion;
		
		return total;
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
	
	
}
