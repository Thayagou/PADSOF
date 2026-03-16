package estadistica;

import java.util.*;
import java.time.YearMonth;
import venta.productos.*;
import venta.pedidos.EstadoPedido;
import venta.pedidos.Pedido;
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
		StatsProducto statProducto;
		int udsVendidas = 0;
		
		for (StockExterno stExt: productos) {
			p = stExt.getProducto();
			if (statsProductos.containsKey(p) == false) statsProductos.put(p, new StatsProducto(p));
			
			statProducto = statsProductos.get(p);
			statProducto.actualizarUltima(stExt.getUdsEnStock(), stExt.getPrecioFinal());
			udsVendidas +=stExt.getUdsEnStock();
		}
		
		double precio = pedido.getPrecioTotal();
		
		ClienteRegistrado cliente = pedido.getCliente();
		if (statsClientes.get(cliente) == null) statsClientes.put(cliente, new StatsUsuario(cliente));
		StatsUsuario statCliente = statsClientes.get(cliente);
		statCliente.actualizarUltimaVenta(udsVendidas, precio);
		
		YearMonth month = YearMonth.now();
		if (ventasMensuales.get(month) == null) ventasMensuales.put(month, new StatsMensual());
		StatsMensual statVenta = ventasMensuales.get(month);
		statVenta.incrementar(udsVendidas, precio);
			
		
		pedidos.add(pedido);
		
		return true;
	}
	
	public boolean guardarIntercambio(Intercambio intercambio) {				
		return intercambios.add(intercambio);
	}
	
	public boolean guardarValoracion(Valoracion valoracion) {
		double precio = valoracion.getPrecioPagado();
		
		ClienteRegistrado cliente = valoracion.getDuenoArticulo();
		if (statsClientes.get(cliente) == null) statsClientes.put(cliente, new StatsUsuario(cliente));
		StatsUsuario statCliente = statsClientes.get(cliente);
		statCliente.actualizarUltimaValoracion(precio);
		
		YearMonth month = YearMonth.now();
		if (wallapopMensuales.get(month) == null) wallapopMensuales.put(month, new StatsMensual());
		StatsMensual statWallapop = ventasMensuales.get(month);
		statWallapop.incrementar(0, precio);
		
		return true;
	}
	
	public Pedido[] getPedidosPendientes() {
		List<Pedido> pedidosPendientes = new ArrayList<>();
		
		for (Pedido pedido: pedidos) {
			if (pedido.getEstado().equals(EstadoPedido.PAGADO) == false)
				pedidosPendientes.add(pedido);
		}
		
		return pedidosPendientes.toArray(new Pedido[0]);
	}
	
	public Valoracion[] getValoracionesPendientes() {
		List<Valoracion> valoracionesPendientes = new ArrayList<>();
		
		for (Valoracion valoracion: valoraciones) {
			if (valoracion.getEstadoFisico().equals(EstadoFisicoArticulo.PENDIENTE))
				valoracionesPendientes.add(valoracion);
		}
		
		return valoracionesPendientes.toArray(new Valoracion[0]);
	}
	
	public Intercambio[] getIntercambiosPendientes() {
		List<Intercambio> intercambiosPendientes = new ArrayList<>();
		
		for (Intercambio intercambio: intercambios) {
			if (intercambio.getEstado().equals(EstadoIntercambio.ACEPTADO))
				intercambiosPendientes.add(intercambio);
		}
		
		return intercambiosPendientes.toArray(new Intercambio[0]);
	}
	
	public ClienteRegistrado[] getClientesMasActivos(int nClientes) {
		return null;
	}
	
	public boolean modificarEstadoPedido(Empleado empleado, Pedido pedido) {
		if (empleado.tienePermiso(Permiso.PEDIDOS) == false) return false;
		
		pedido.setEstadoPedido(pedido.getEstado().getSiguienteEstado());
		return true;
	}
	
	public boolean valorarArticulo(Empleado empleado, ArticuloSegundaMano articulo, double precioEstimado, EstadoFisicoArticulo estado) {
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) return false;
		
		Valoracion valoracion = articulo.getValoracion();
		if (valoracion == null) return false;
		
		return valoracion.valorar(empleado, precioEstimado, estado);
	}
	
	public boolean validarIntercambio(Empleado empleado, Intercambio intercambio) {
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) return false;
		
		ClienteRegistrado emisor = intercambio.getEmisor().getDueno(), receptor = intercambio.getReceptor().getDueno();;
		
		if (statsClientes.get(emisor) == null) statsClientes.put(emisor, new StatsUsuario(emisor));
		StatsUsuario statEmisor = statsClientes.get(emisor);
		int nOfrecidos = intercambio.getSolicitados().length;
		statEmisor.actualizarUltimoIntercambio(nOfrecidos);
		
		if (statsClientes.get(receptor) == null) statsClientes.put(receptor, new StatsUsuario(receptor));
		StatsUsuario statReceptor = statsClientes.get(receptor);
		int nSolicitados = intercambio.getSolicitados().length;
		statReceptor.actualizarUltimoIntercambio(nSolicitados);
		
		return intercambio.validarIntercambio(empleado);
	}
}

















