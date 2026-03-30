package estadistica;

import java.util.*;

import exceptions.*;
import sistema.Sistema;

import java.io.Serializable;
import java.time.YearMonth;
import venta.productos.*;
import venta.pedidos.EstadoPedido;
import venta.pedidos.Pedido;
import wallapop.*;
import usuario.*;

/**
 * Clase Historial, encargada de guardar y manejar las estadísticas de usuarios y productos
 * @author Tiago Oselka
 */
public class Historial implements Serializable, ObservadorProducto {
	private static final long serialVersionUID = 1L;
	private List<Pedido> pedidos = new ArrayList<>();
	private List<Valoracion> valoraciones = new ArrayList<>();
	private List<Intercambio> intercambios = new ArrayList<>();
	private Map<Producto, StatsProducto> statsProductos = new HashMap<>();
	private Map<ClienteRegistrado, StatsUsuario> statsClientes = new HashMap<>();
	private Map<YearMonth, StatsMensual> ventasMensuales = new HashMap<>();
	private Map<YearMonth, StatsMensual> wallapopMensuales = new HashMap<>();

	/**
	 * Constructor de la clase historial
	 */
	public Historial() { }
	
	@Override
	public void guardarProducto(Producto p) {
		statsProductos.putIfAbsent(p, new StatsProducto(p));		
	}
	
	public void guardarUsuario(ClienteRegistrado cliente) {
		if (statsClientes.containsKey(cliente) == false) statsClientes.put(cliente, new StatsUsuario(cliente));
	}

	@Override
	public void actualizarVector(Producto p) {
		StatsProducto stats = statsProductos.get(p);
		
		
	}
	
	/**
	 * Método para guardar un pedido, actualizando las estadísticas de la tienda, de los productos y del cliente que lo ha comprado
	 * @param pedido Pedido realizado por el usuario
	 * @return true si se guarda correctamente, false en caso contrario
	 */
	public boolean guardarPedido(Pedido pedido) {
		StockExterno[] productos = pedido.getItemsPedido();
		Producto p;
		StatsProducto statProducto;
		
		Map<Categoria, Double> vectorCompra = new HashMap<>();
		int udsVendidas = 0;
		double pesoUds = Sistema.getInstancia().getPonderacionUdsCompra(), 
				pesoPrecio = Sistema.getInstancia().getPonderacionPrecioCompra();
		
		for (StockExterno stExt: productos) {
			p = stExt.getProducto();
			statsProductos.computeIfAbsent(p, prod->new StatsProducto(prod)).
				actualizarUltima(stExt.getUdsEnStock(), stExt.getPrecioTotal());
			
			udsVendidas +=stExt.getUdsEnStock();

			for (Categoria c: p.getCategorias()) {
				vectorCompra.merge(c, pesoUds * stExt.getUdsEnStock() + pesoPrecio * stExt.getPrecioTotal(), (a,b)->Double.sum(a, b));
			}			
		}
				
		double precio = pedido.getPrecioTotal();
		
		ClienteRegistrado cliente = pedido.getCliente();
		if (statsClientes.containsKey(cliente) == false) statsClientes.put(cliente, new StatsUsuario(cliente));
		StatsUsuario statCliente = statsClientes.get(cliente);
		
		statCliente.actualizarCompra(vectorCompra, udsVendidas, precio);
		
		YearMonth month = YearMonth.now();
		if (ventasMensuales.get(month) == null) ventasMensuales.put(month, new StatsMensual());
		StatsMensual statVenta = ventasMensuales.get(month);
		statVenta.incrementar(udsVendidas, precio);
			
		System.out.println(cliente);
		pedidos.add(pedido);
		
		return true;
	}
	
	/**
	 * Método para guardar un intercambio cuando se crea nuevo. No actualiza las estadísticas al tratarse inicialmente de una oferta solamente
	 * @param intercambio Intercambio propuesto
	 * @return true si se añade correctamente, false en caso contario
	 */
	public boolean guardarIntercambio(Intercambio intercambio) {				
		return intercambios.add(intercambio);
	}
	
	/**
	 * Método para guardar la valoración de un artículo, actualizando las estadísticas del cliente que la ha solicitado y las de la tienda
	 * @param pedido Pedido realizado por el usuario
	 * @return true si se guarda correctamente, false en caso contrario
	 */
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
	
	/**
	 * Obtiene todos aquellos pedidos que no se encuentren en estado RECOGIDO, es decir, que aún no han terminado su tramitación
	 * @return Array de Pedido, con aquellos pendientes
	 */
	public Pedido[] getPedidosPendientes() {
		List<Pedido> pedidosPendientes = new ArrayList<>();
		
		for (Pedido pedido: pedidos) {
			if (pedido.getEstado().equals(EstadoPedido.RECOGIDO) == false)
				pedidosPendientes.add(pedido);
		}
		
		return pedidosPendientes.toArray(new Pedido[0]);
	}
	
	/**
	 * Obtiene todos aquellas valoraciones que se encuentren en estado PENDIENTE, es decir, que aún no han recibido conclusión
	 * @return Array de Valoracion, con aquellas pendientes
	 */
	public Valoracion[] getValoracionesPendientes() {
		List<Valoracion> valoracionesPendientes = new ArrayList<>();
		
		for (Valoracion valoracion: valoraciones) {
			if (valoracion.getEstadoFisico().equals(EstadoFisicoArticulo.PENDIENTE))
				valoracionesPendientes.add(valoracion);
		}
		
		return valoracionesPendientes.toArray(new Valoracion[0]);
	}
	
	/**
	 * Obtiene todos aquellos intercambios que se encuentren en estado ACEPTADO, es decir, los que no han sido validados por algún empleado
	 * @return Array de Valoracion, con aquellas pendientes
	 */
	public Intercambio[] getIntercambiosPendientes() {
		List<Intercambio> intercambiosPendientes = new ArrayList<>();
		
		for (Intercambio intercambio: intercambios) {
			if (intercambio.getEstado().equals(EstadoIntercambio.ACEPTADO))
				intercambiosPendientes.add(intercambio);
		}
		
		return intercambiosPendientes.toArray(new Intercambio[0]);
	}
	
	/**
	 * Método para actualizar el estado del pedido al siguiento. Los pedidos tienen una serie de estados secuenciales: PAGADO-> EN_PREPARACION-> LISTO-> RECOGIDO, por lo que el método lo avanza en la secuencia
	 * @param empleado Empleado de la tienda que debe tener el permiso de PEDIDOS
	 * @param pedido Pedido cuyo estado se pretende avanzar
	 * @return true si se ha avanzado correctamente, false en caso contrario
	 * @throws InvalidArgumentException 
	 * @throws InvalidPermit 
	 */
	public boolean avanzarEstadoPedido(Empleado empleado, Pedido pedido) throws InvalidArgumentException, InvalidPermitException {
		if (empleado.tienePermiso(Permiso.PEDIDOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "gestionar pedidos", "Pedidos");
		
		pedido.nextEstadoPedido(empleado);

		ClienteRegistrado cliente = pedido.getCliente();
		
		cliente.enviarNotificacion("Tu pedido con código "+pedido.getId()+" ha avanzado al estado "+pedido.getEstado()+".", TipoNotificacion.PEDIDO);
		
		return true;
	}
	
	/**
	 * Método para valorar un artículo de la tienda. Para completarse, el artículo 
	 * @param empleado Empleado de la tienda que debe tener el permiso de INTERCAMBIOS
	 * @param articulo Artículo a valorar
	 * @param precioEstimado Valor monetario asignado por el empleado
	 * @param estado Estado del producto asignado por el empleado
	 * @return true si se valora correctamente, false en caso contario
	 * @throws InvalidPermit 
	 */
	public boolean valorarArticulo(Empleado empleado, ArticuloSegundaMano articulo, double precioEstimado, EstadoFisicoArticulo estado) throws InvalidPermitException {
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "valorar artículo", "Intercambios");
		if (articulo.getValoracion() == null) return false;
		
		Valoracion valoracion = articulo.getValoracion();
		if (valoracion == null) return false;
		
		return valoracion.valorar(empleado, precioEstimado, estado);
	}
	
	/**
	 * Método para validar un intercambio. Para completarse, el intercambio debe estar aceptado y el empleado debe tener el debido permiso
	 * @param empleado Empleado de la tienda que debe tener el permiso de INTERCAMBIOS
	 * @param intercambio Intercambio cuyo estado debe ser ACEPTADO
	 * @return true si se valida correctamente, false en caso contrario
	 * @throws InvalidPermit 
	 */
	public boolean validarIntercambio(Empleado empleado, Intercambio intercambio) throws InvalidPermitException {
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "validar intercambio", "Intercambios");
		
		if (intercambio.getEstado().equals(EstadoIntercambio.ACEPTADO) == false) return false;
		
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
	
	/**
	 * Método para obtener estadísticas que devuelve los usuarios ordenados en función de la recaudación de cada usuario
	 * @return un array de StatsUsuario, que obtiene el usuario y su gasto
	 */
	public StatsUsuario[] getUsuariosMasActivos() {
		StatsUsuario[] ordenar = statsClientes.values().toArray(new StatsUsuario[0]);
		
		Arrays.sort(ordenar, (a, b)->Double.compare(a.getGastoTotal(), b.getGastoTotal()));
		
		return ordenar;
	}	
	
}

















