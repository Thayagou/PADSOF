package venta.pedidos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import usuario.*;
import venta.pedidos.*;
import venta.productos.*;
import exceptions.*;
import sistema.CarritoCaducadoObserver;
import sistema.Reloj;
import sistema.Tienda;

/**
 * Clase con los tests de los métodos de la clase Pedido
 * 
 * @author Juan Ibáñez
 */
class PedidoTest {

	private ClienteRegistrado cliente;
	private Empleado empleado;
	private Producto producto1;
	private Producto producto2;
	private StockExterno stock1;
	private StockExterno stock2;
	private Pedido pedido;

	@BeforeEach
	void setUp() throws Exception {
		CarritoCaducadoObserver tienda = new Tienda();
		cliente = new ClienteRegistrado("Cliente1", "pass", tienda);
		empleado = new Empleado("Empleado1", "pass");
		producto1 = new Juego("Monopoly", "Juego de mesa", 25.0, null, 6, "8+", TipoJuego.TABLERO);
		producto2 = new Figura("Batman", "Figura de Batman", 12.0, null, "20x12 cm", "DC", "Plastico");
		stock1 = new StockExterno(producto1, 2, 25.0);
		stock2 = new StockExterno(producto2, 1, 12.0);
		pedido = new Pedido(cliente, stock1, stock2);
	}

	// --- Constructor ---

	@Test
	void testConstructorValido() {
		assertNotNull(pedido);
		assertEquals(cliente, pedido.getCliente());
		assertEquals(EstadoPedido.PAGADO, pedido.getEstado());
		assertNotNull(pedido.getFechaPago());
		assertEquals(2, pedido.getItemsPedido().length);
	}

	@Test
	void testConstructorClienteNull() {
		assertThrows(InvalidArgumentException.class, () -> new Pedido(null, stock1));
	}

	@Test
	void testConstructorStocksNull() {
		assertThrows(InvalidArgumentException.class, () -> new Pedido(cliente, (StockExterno[]) null));
	}

	@Test
	void testConstructorStockNullEnArray() {
		assertThrows(InvalidArgumentException.class, () -> new Pedido(cliente, stock1, null));
	}

	// --- getId ---

	@Test
	void testGetIdUnico() throws Exception {
		Pedido otroPedido = new Pedido(cliente, stock1);
		assertNotEquals(pedido.getId(), otroPedido.getId());
	}

	// --- getPrecioTotal ---

	@Test
	void testGetPrecioTotal() {
		// stock1: 2 uds x 25.0 = 50.0, stock2: 1 ud x 12.0 = 12.0 → total = 62.0
		assertEquals(62.0, pedido.getPrecioTotal());
	}

	@Test
	void testGetPrecioTotalUnSoloStock() throws Exception {
		Pedido p = new Pedido(cliente, stock1);
		assertEquals(50.0, p.getPrecioTotal());
	}

	// --- getItemsPedido ---

	@Test
	void testGetItemsPedidoTamanyo() {
		assertEquals(2, pedido.getItemsPedido().length);
	}

	@Test
	void testGetItemsPedidoSonCopias() {
		// Los items del pedido deben ser copias, no los mismos objetos
		for (StockExterno st : pedido.getItemsPedido()) {
			assertNotSame(stock1, st);
			assertNotSame(stock2, st);
		}
	}

	// --- getFechaPago ---

	@Test
	void testGetFechaPagoEsAhora() {
		assertTrue(pedido.getFechaPago().isBefore(Reloj.now().plusSeconds(1)));
		assertTrue(pedido.getFechaPago().isAfter(Reloj.now().minusSeconds(5)));
	}

	// --- nextEstadoPedido ---

	@Test
	void testNextEstadoPedidoDePageadoAEnPreparacion() throws Exception {
		pedido.nextEstadoPedido(empleado);
		assertEquals(EstadoPedido.EN_PREPARACION, pedido.getEstado());
		assertEquals(empleado, pedido.getEmpPreparacion());
		assertNotNull(pedido.getFechaPreparacion());
	}

	@Test
	void testNextEstadoPedidoDeEnPreparacionAListo() throws Exception {
		pedido.nextEstadoPedido(empleado);
		pedido.nextEstadoPedido(empleado);
		assertEquals(EstadoPedido.LISTO, pedido.getEstado());
		assertEquals(empleado, pedido.getEmpListo());
		assertNotNull(pedido.getFechaListo());
	}

	@Test
	void testNextEstadoPedidoDeListoARecogido() throws Exception {
		pedido.nextEstadoPedido(empleado);
		pedido.nextEstadoPedido(empleado);
		pedido.nextEstadoPedido(empleado);
		assertEquals(EstadoPedido.RECOGIDO, pedido.getEstado());
		assertEquals(empleado, pedido.getEmpRecogida());
		assertNotNull(pedido.getFechaRecogida());
	}

	@Test
	void testNextEstadoPedidoNoAvanzaMasDeRecogido() throws Exception {
		pedido.nextEstadoPedido(empleado);
		pedido.nextEstadoPedido(empleado);
		pedido.nextEstadoPedido(empleado);
		pedido.nextEstadoPedido(empleado); // intento extra
		assertEquals(EstadoPedido.RECOGIDO, pedido.getEstado());
	}

	@Test
	void testNextEstadoPedidoEmpleadoNull() {
		assertThrows(InvalidArgumentException.class, () -> pedido.nextEstadoPedido(null));
	}

	@Test
	void testFechasNullAntesDeAvanzar() {
		assertNull(pedido.getFechaPreparacion());
		assertNull(pedido.getFechaListo());
		assertNull(pedido.getFechaRecogida());
	}

	@Test
	void testEmpleadosNullAntesDeAvanzar() {
		assertNull(pedido.getEmpPreparacion());
		assertNull(pedido.getEmpListo());
		assertNull(pedido.getEmpRecogida());
	}

	// --- EstadoPedido.getSiguienteEstado ---

	@Test
	void testGetSiguienteEstadoDesdePagado() {
		assertEquals(EstadoPedido.EN_PREPARACION, EstadoPedido.PAGADO.getSiguienteEstado());
	}

	@Test
	void testGetSiguienteEstadoDesdeEnPreparacion() {
		assertEquals(EstadoPedido.LISTO, EstadoPedido.EN_PREPARACION.getSiguienteEstado());
	}

	@Test
	void testGetSiguienteEstadoDesdeListo() {
		assertEquals(EstadoPedido.RECOGIDO, EstadoPedido.LISTO.getSiguienteEstado());
	}

	@Test
	void testGetSiguienteEstadoDesdeRecogidoSeQueda() {
		assertEquals(EstadoPedido.RECOGIDO, EstadoPedido.RECOGIDO.getSiguienteEstado());
	}
}