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
	/** Lista de todos los pedidos que se han realizado en la tienda */
	private List<Pedido> pedidos = new ArrayList<>();
	/** Lista de todas las valoraciones que se han solicitado en la tienda */
	private List<Valoracion> valoraciones = new ArrayList<>();
	/** Lista de todos los intercambios que se han realizado en la tienda */
	private List<Intercambio> intercambios = new ArrayList<>();
	/** Mapa que asigna a cada producto con su respectiva estadística */
	private Map<Producto, StatsProducto> statsProductos = new HashMap<>();
	/** Mapa que une asigna a cada cliente con su respectiva estadística */
	private Map<ClienteRegistrado, StatsUsuario> statsClientes = new HashMap<>();
	/** Mapa con las estadísticas mensuales de ventas ordenadas por meses */
	private TreeMap<YearMonth, StatsMensual> ventasMensuales = new TreeMap<>();
	/** Mapa con las estadísticas mensuales de los intercambios ordenadas por meses */
	private TreeMap<YearMonth, StatsMensual> wallapopMensuales = new TreeMap<>();

	/**
	 * Constructor de la clase historial
	 */
	public Historial() { }
	
	@Override
	/**
	 * Guarda un producto en el historial y le asigna estadísticas
	 * @param producto Producto a almacenar
	 */
	public void guardarProducto(Producto producto) {
		if (statsProductos.containsKey(producto) == false) statsProductos.put(producto, new StatsProducto(producto));	
	}
	
	/**
	 * Guarda un usuario en el historial y le asigna estadísticas
	 * @param cliente Cliente de la tienda a almacenar
	 */
	public void guardarUsuario(ClienteRegistrado cliente) {
		if (statsClientes.containsKey(cliente) == false) statsClientes.put(cliente, new StatsUsuario(cliente));
	}
	
	/**
	 * Método para guardar un pedido, actualizando las estadísticas de la tienda, de los productos y del cliente que lo ha comprado
	 * @param pedido Pedido realizado por el usuario
	 * @return true si se guarda correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de que algún parámetro introducido no sea válido
	 * 
	 */
	public boolean guardarPedido(Pedido pedido) throws InvalidArgumentException {
		if (pedido == null) throw new InvalidArgumentException("Pedido inválido introducido", "guardar pedido en las estadísticas");
		StockExterno[] productos = pedido.getItemsPedido();
		Producto p;
		
		Map<Categoria, Double> vectorCompra = new HashMap<>();
		int udsVendidas = 0;
		double pesoUds = Sistema.getInstancia().getPonderacionUdsCompra(), 
				pesoPrecio = Sistema.getInstancia().getPonderacionPrecioCompra();
		
		for (StockExterno stExt: productos) {
			p = stExt.getProducto();
			if (statsProductos.containsKey(p) == false) statsProductos.put(p, new StatsProducto(p));
				
			statsProductos.get(p).actualizarUltima(stExt.getUdsEnStock(), stExt.getPrecioTotal());
			
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
	 * @throws InvalidArgumentException Lanzado en caso de que se introduzcan argumentos inválidos
	 */
	public boolean guardarIntercambio(Intercambio intercambio) throws InvalidArgumentException {	
		if (intercambio == null) throw new InvalidArgumentException("Intercambio inválido introducido", "guardar intercambio en las estadísticas");
		return intercambios.add(intercambio);
	}
	
	/**
	 * Método para guardar la valoración de un artículo, actualizando las estadísticas del cliente que la ha solicitado y las de la tienda
	 * @param pedido Pedido realizado por el usuario
	 * @return true si se guarda correctamente, false en caso contrario
	 * @throws InvalidArgumentException Lanzado en caso de que se introduzcan argumentos inválidos
	 */
	public boolean guardarValoracion(Valoracion valoracion) throws InvalidArgumentException {
		if (valoracion == null) throw new InvalidArgumentException("Valoración inválida introducida", "guardar valoración en las estadísticas");
		double precio = valoracion.getPrecioPagado();
		
		ClienteRegistrado cliente = valoracion.getDuenoArticulo();
		if (statsClientes.get(cliente) == null) statsClientes.put(cliente, new StatsUsuario(cliente));
		StatsUsuario statCliente = statsClientes.get(cliente);
		statCliente.actualizarUltimaValoracion(precio);
		
		YearMonth month = YearMonth.now();
		if (wallapopMensuales.get(month) == null) wallapopMensuales.put(month, new StatsMensual());
		StatsMensual statWallapop = ventasMensuales.get(month);
		statWallapop.incrementar(0, precio);
		
		valoraciones.add(valoracion);
		
		return true;
	}
	
	/**
	 * Obtiene las estadísticas de las ventas de la tienda mes a mes ordenadas 
	 * @param inicio Mes desde el cual se desea conocer las estadísticas
	 * @param fin Mes hasta el cual se desea conocer las estadísticas
	 * @return Lista con las estadísticas entre estos dos meses
	 */
	public List<StatsMensual> getVentasEntreMeses(YearMonth inicio, YearMonth fin) {
		List<StatsMensual> estadisticas = new ArrayList<>(ventasMensuales.subMap(inicio, true, fin, true).values());
		
		return estadisticas;
	}
	
	/**
	 * Obtiene el valor acumulado de las ventas entre los meses establecidos
	 * @param inicio Mes desde el cual se desea conocer las estadísticas
	 * @param fin Mes hasta el cual se desea conocer las estadísticas
	 * @return StatsMensual con el acumulado entre meses
	 * @throws InvalidArgumentException Se lanza en caso de haber problemas con las estadísticas acumuladas
	 */
	public StatsMensual getVentasEntreMesesAcumulado(YearMonth inicio, YearMonth fin) throws InvalidArgumentException {
		List<StatsMensual> lista = getVentasEntreMeses(inicio, fin);
		
		StatsMensual acumulado = new StatsMensual();
		for (StatsMensual stats: lista) {
			acumulado.incrementar(stats.getUnidades(), stats.getRecaudacion());
		}
		
		return acumulado;
	}
	
	/**
	 * Obtiene las estadísticas de los intercambios de la tienda mes a mes ordenadas 
	 * @param inicio Mes desde el cual se desea conocer las estadísticas
	 * @param fin Mes hasta el cual se desea conocer las estadísticas
	 * @return Lista con las estadísticas entre estos dos meses
	 */
	public List<StatsMensual> getIntercambiosEntreMeses(YearMonth inicio, YearMonth fin) {
		List<StatsMensual> estadisticas = new ArrayList<>(wallapopMensuales.subMap(inicio, true, fin, true).values());
		
		return estadisticas;	
	}
	
	/**
	 * Obtiene una lista de los productos ordenada según la recaudación que han obtenido estos entre los meses especificados.
	 * En el primer lugar de la lista se encuentra el par (null, total), que corresponde al total de las ventas en el periodo establecido
	 * @param inicio Mes de inicio del que se desea obtener la lista
	 * @param fin Mes final establecido
	 * @return Lista de pares (Producto, StatsMensual), conteniendo en cada una la recaudación y las unidades vendidas de cada producto respectivamente
	 * @throws InvalidArgumentException En caso de que los meses estén insertados en un orden incorrecto se lanza esta excepción
	 */
	public List<Map.Entry<Producto, StatsMensual>> getProductosMayorRecaudacion(YearMonth inicio, YearMonth fin) throws InvalidArgumentException {
		if (inicio == null || fin == null) throw new InvalidArgumentException("Alguno de los meses introducidos no es válido", "obtener productos con mayor recaudación");
		if (inicio.compareTo(fin) > 0) throw new InvalidArgumentException("El mes de inicio debe ser anterior al del final", "obtener productos con mayor recaudación");
		
		List<Map.Entry<Producto, StatsMensual>> lista = new ArrayList<>();
		
		for (StatsProducto stats: statsProductos.values()) {
			StatsMensual s = stats.getEstadisticasEntreMeses(inicio, fin);
			lista.add(Map.entry(stats.getProducto(), s));
		}
		
		Collections.sort(lista, (a,b)->Double.compare(a.getValue().getRecaudacion(), b.getValue().getRecaudacion()));
				
		return lista;
	}
	
	/**
	 * Obtiene todos aquellos pedidos que no se encuentren en estado RECOGIDO, es decir, que aún no han terminado su tramitación
	 * @return Array de Pedido, con aquellos pendientes
	 */
	public Pedido[] getPedidosPendientes() {
		return pedidos.stream().filter(p->p.getEstado().equals(EstadoPedido.RECOGIDO) == false).toArray(Pedido[]::new);
	}
	
	/**
	 * Obtiene todos aquellas valoraciones que se encuentren en estado PENDIENTE, es decir, que aún no han recibido conclusión
	 * @return Array de Valoracion, con aquellas pendientes
	 */
	public Valoracion[] getValoracionesPendientes() {
		return valoraciones.stream().filter(v->v.getEstadoFisico().equals(EstadoFisicoArticulo.PENDIENTE)).toArray(Valoracion[]::new);
	}
	
	/**
	 * Obtiene todos aquellos intercambios que se encuentren en estado ACEPTADO, es decir, los que no han sido validados por algún empleado
	 * @return Array de Valoracion, con aquellas pendientes
	 */
	public Intercambio[] getIntercambiosPendientes() {
		return intercambios.stream().filter(i->i.getEstado().equals(EstadoIntercambio.ACEPTADO)).toArray(Intercambio[]::new);
	}
	
	/**
	 * Método para actualizar el estado del pedido al siguiento. Los pedidos tienen una serie de estados secuenciales: PAGADO-> EN_PREPARACION-> LISTO-> RECOGIDO, por lo que el método lo avanza en la secuencia
	 * @param empleado Empleado de la tienda que debe tener el permiso de PEDIDOS
	 * @param pedido Pedido cuyo estado se pretende avanzar
	 * @return true si se ha avanzado correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de que alguno de los parámetros introducidos no sea válido
	 * @throws InvalidPermit Se lanza en caso de que el empleado introducido no tenga los correspondientes permisos
	 */
	public boolean avanzarEstadoPedido(Empleado empleado, Pedido pedido) throws InvalidArgumentException, InvalidPermitException {
		if (empleado.tienePermiso(Permiso.PEDIDOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "gestionar pedidos", Permiso.PEDIDOS, empleado);
		
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
	 * @throws InvalidArgumentException Se lanza en caso de que se introduzcan parámetros inválidos
	 * @throws ArticuloSinValoracionException Se lanza en caso de que el artículo que se intenta valorar no tiene valoración
	 * @throws InvalidPermit Se lanza en caso de que el empleado introducido no tenga los correspondientes permisos
	 */
	public boolean valorarArticulo(Empleado empleado, ArticuloSegundaMano articulo, double precioEstimado, EstadoFisicoArticulo estado) throws InvalidPermitException, InvalidArgumentException, ArticuloSinValoracionException {
		if (empleado == null || articulo == null || estado == null) throw new InvalidArgumentException("Algún argumento introducido no es válido", "valorar artículo de segunda mano");
		if (precioEstimado < 0) throw new InvalidArgumentException("El precio estimado no puede ser negativo", "valorar artículo de segunda mano");
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "valorar artículo", Permiso.INTERCAMBIOS, empleado);
		if (articulo.getValoracion() == null) throw new ArticuloSinValoracionException("El artículo introducido no tiene valoracion", "valorar artículo de segunda mano", articulo);
		
		Valoracion valoracion = articulo.getValoracion();
		if (valoracion == null) return false;
		
		valoracion.valorar(empleado, precioEstimado, estado);
		
		return true;
	}
	
	/**
	 * Método para validar un intercambio. Para completarse, el intercambio debe estar aceptado y el empleado debe tener el debido permiso
	 * @param empleado Empleado de la tienda que debe tener el permiso de INTERCAMBIOS
	 * @param intercambio Intercambio cuyo estado debe ser ACEPTADO
	 * @return true si se valida correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de que algún parámetro introducido no sea válido
	 * @throws InvalidPermit Se lanza en caso de que el empleado introducido no tenga los correspondientes permisos
	 */
	public boolean validarIntercambio(Empleado empleado, Intercambio intercambio) throws InvalidPermitException, InvalidArgumentException {
		if (empleado == null || intercambio == null) throw new InvalidArgumentException("Alguno de los parámetros introducidos es inválido", "validar intercambio");
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "validar intercambio", Permiso.INTERCAMBIOS, empleado);
		
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
		
		intercambio.validarIntercambio(empleado);
		
		return true;
	}
	
	/**
	 * Método para obtener estadísticas que devuelve los usuarios ordenados en función de la recaudación de cada usuario
	 * @return una lista de StatsUsuario, que obtiene el usuario y su gasto
	 */
	public List<StatsUsuario> getUsuariosMasActivos() {
		List<StatsUsuario> clientes = new ArrayList<>(statsClientes.values());
		
		Collections.sort(clientes, (a,b)->Double.compare(a.getGastoTotal(), b.getGastoTotal()));
		
		return clientes;		
	}

	@Override
	public String toString() {
		return "Historial [pedidos=" + pedidos + ", valoraciones=" + valoraciones + ", intercambios=" + intercambios
				+ ", statsProductos=" + statsProductos + ", statsClientes=" + statsClientes + ", ventasMensuales="
				+ ventasMensuales + ", wallapopMensuales=" + wallapopMensuales + "]";
	}	
	
	
	
	
	
}

















