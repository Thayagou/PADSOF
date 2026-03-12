package estadistica;

import java.time.*;
import java.util.*;

import venta.productos.Producto;

public class StatsProducto {
	Producto producto;
	List<StatsMensual> estadisticas = new ArrayList<>();
	
	public StatsProducto(Producto producto) {
		this.producto = producto;
	}
	
	public boolean actualizarUltima(int udsVendidas, double precio) {
		if (udsVendidas < 0 || precio < 0) return false;
		
		if (estadisticas.getLast().getMes().equals(YearMonth.now()) == false) {
			estadisticas.add(new StatsMensual());
		}
		
		estadisticas.getLast().incrementar(udsVendidas, precio);
		
		return true;
	}
	
	
}
