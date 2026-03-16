package estadistica;

import java.util.*;
import java.time.YearMonth;
import venta.productos.*;
import venta.pedidos.Pedido;
import wallapop.Intercambio;
import wallapop.*;
import usuario.*;

public class Historial {
	List<Pedido> pedidos = new ArrayList<>();
	List<Valoracion> valoraciones = new ArrayList<>();
	List<Intercambio> intercambios = new ArrayList<>();
	Map<Producto, StatsProducto> statsProductos = new HashMap<>();
	Map<ClienteRegistrado, StatsUsuario> statsClientes = new HashMap<>();
	Map<YearMonth, StatsMensual> ventasMensuales = new HashMap<>();
	Map<YearMonth, StatsMensual> wallapopMensuales = new HashMap<>();

	public Historial() {
		
	}
	
	public boolean guardarPedido(Pedido pedido) {
		StockExterno[] productos = pedido.getItemsPedido();
		Producto p;
		StatsProducto stat;
		
		for (StockExterno stExt: productos) {
			p = stExt.getProducto();
			if (statsProductos.containsKey(p) == false) statsProductos.put(p, new StatsProducto(p));
			
			stat = statsProductos.get(p);
			stat.actualizarUltima(stExt.getUdsEnStock(), stExt.getPrecioFinal());
		}
		
		YearMonth month = YearMonth.now();
		if (ventasMensuales.get(month) == null) ventasMensuales.put(month, new StatsMensual());
		StatsMensual statVenta = ventasMensuales.get(month);
		statVenta.incrementar(1, 1 /*pedido.getPrecioTotal()*/);
		
		pedidos.add(pedido);
		
		return true;
	}
	
	public boolean guardarIntercambio(Intercambio intercambio) {
		return true;
	}
	
	public boolean guardarValoracion(Valoracion valoracion) {
		return true;
	}
	
	public Pedido[] getPedidosPendientes() {
		return null;
	}
	
	public Valoracion[] getValoracionesPendientes() {
		return null;
	}
	
	public Intercambio[] getIntercambiosPendientes() {
		return null;
	}
	
	public ClienteRegistrado[] getClientesMasActivos() {
		return null;
	}
	
	public boolean modificarEstadoPedido(Empleado empleado, Pedido pedido) {
		return true;
	}
	
	public boolean valorarArticulo(Empleado empleado, ArticuloSegundaMano articulo, double precioEstimado, EstadoFisicoArticulo estado) {
		return true;
	}
	
	public boolean validarIntercambio(Empleado empleado, Intercambio intercambio) {
		return true;
	}
}

















