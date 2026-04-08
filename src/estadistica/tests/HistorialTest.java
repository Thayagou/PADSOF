package estadistica.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.InvalidArgumentException;
import exceptions.InvalidPermitException;
import exceptions.ArticuloSinValoracionException;
import exceptions.DoubleDiscountException;
import estadistica.Historial;
import estadistica.StatsMensual;
import estadistica.StatsUsuario;
import sistema.Tienda;
import sistema.Reloj;
import sistema.Sistema;
import usuario.ClienteRegistrado;
import usuario.Empleado;
import usuario.Permiso;
import venta.productos.*;
import venta.pedidos.EstadoPedido;
import venta.pedidos.Pedido;
import wallapop.ArticuloSegundaMano;
import wallapop.Cartera;
import wallapop.EstadoFisicoArticulo;
import wallapop.EstadoIntercambio;
import wallapop.Intercambio;
import wallapop.Valoracion;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.Map.Entry;

class HistorialTest {

	private Tienda tienda;
	private Historial historial;
	private ClienteRegistrado emisor;
	private ClienteRegistrado receptor;
	private Cartera carteraEmisor;
	private Cartera carteraReceptor;
	private Empleado empleadoConPermiso;
	private Empleado empleadoSinPermiso;
	private Categoria cat1;
	private Categoria cat2;
	private ArticuloSegundaMano[] artsEmisor;
	private ArticuloSegundaMano[] artsReceptor;

	@BeforeEach
	void setUp() throws Exception {
		tienda = new Tienda();
		historial = tienda.getHistorial();

		emisor = new ClienteRegistrado("Emisor", "Emisor123", tienda);
		receptor = new ClienteRegistrado("Receptor", "Receptor123", tienda);
		carteraEmisor = emisor.getCartera();
		carteraReceptor = receptor.getCartera();

		empleadoConPermiso = new Empleado("EmpleadoPermiso", "pass",
				new Permiso[] { Permiso.INTERCAMBIOS, Permiso.PEDIDOS });

		empleadoSinPermiso = new Empleado("EmpleadoSinPermiso", "pass");

		cat1 = new Categoria("Juego de mesa");
		cat2 = new Categoria("Cartas");

		artsEmisor = new ArticuloSegundaMano[3];
		artsReceptor = new ArticuloSegundaMano[3];

		for (int i = 0; i < 3; i++) {
			artsEmisor[i] = new ArticuloSegundaMano("Art emisor " + i, "Desc " + i, carteraEmisor, "Algo", cat2);
			carteraEmisor.addArticulo(artsEmisor[i]);
			Valoracion v1 = new Valoracion(artsEmisor[i]);
			v1.valorar(empleadoConPermiso, (i + 1) * 10, EstadoFisicoArticulo.MUY_BUENO);

			artsReceptor[i] = new ArticuloSegundaMano("Art receptor " + i, "Desc " + i, carteraReceptor, "Algo", cat1);
			carteraReceptor.addArticulo(artsReceptor[i]);
			Valoracion v2 = new Valoracion(artsReceptor[i]);
			v2.valorar(empleadoConPermiso, (i + 1) * 10, EstadoFisicoArticulo.MUY_BUENO);
		}
	}

	private Comic crearComic(String nombre, double precio) throws InvalidArgumentException, DoubleDiscountException {
		List<Categoria> categorias = new ArrayList<>();
		categorias.add(new Categoria("aventuras"));
		Comic p = new Comic(nombre, "Descripción de " + nombre, precio, null, LocalDate.of(2020, 1, 1), "Autor Test", 100,
				"Editorial Test", categorias.toArray(new Categoria[0]));
		historial.guardarProducto(p);
		
		return p;
	}

	@Test
	void testGuardarUsuario() {
		historial.guardarUsuario(emisor);
		List<StatsUsuario> activos = historial.getUsuariosMasActivos();

		assertEquals(emisor, activos.get(0).getCliente());
	}

	@Test
	void testGuardarPedido() throws Exception {
		assertEquals(0, historial.getPedidosPendientes().length);

		Pedido pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(crearComic("Batman", 30.0), 3),
				new StockExterno(crearComic("Superman", 50.0), 2) });
		assertTrue(historial.guardarPedido(pedido));
		assertEquals(pedido, historial.getPedidosPendientes()[0]);

		StatsUsuario stats = null;
		for (StatsUsuario sUser : historial.getUsuariosMasActivos()) {
			if (sUser.getCliente().equals(emisor)) {
				stats = sUser;
				break;
			}
		}

		assertEquals(190.0, stats.getGastoTotal());
		assertEquals(5, stats.getUdsCompradas());
	}
	
	@Test
	void testAvanzarEstadoPedido() throws Exception {
		Pedido pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(crearComic("Batman", 30.0), 3),
				new StockExterno(crearComic("Superman", 50.0), 2) });
		assertTrue(historial.guardarPedido(pedido));
		assertEquals(pedido, historial.getPedidosPendientes()[0]);

		assertEquals(EstadoPedido.PAGADO, pedido.getEstado());
		historial.avanzarEstadoPedido(empleadoConPermiso, pedido);
		assertEquals(EstadoPedido.EN_PREPARACION, pedido.getEstado());
	}

	@Test
	void testGuardarIntercambio() throws Exception {
		assertEquals(0, historial.getIntercambiosPendientes().length);
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		assertTrue(historial.guardarIntercambio(intercambio));
		carteraReceptor.aceptarIntercambio(intercambio);
		assertEquals(intercambio, historial.getIntercambiosPendientes()[0]);
	}

	@Test
	void testGuardarValoracion() throws Exception {
		assertEquals(0, historial.getValoracionesPendientes().length);
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		Valoracion v = new Valoracion(art);
		assertTrue(historial.guardarValoracion(v));
	}

	@Test
	void testGuardarConNullLanzaExcepcion() {
		assertThrows(InvalidArgumentException.class, () -> historial.guardarPedido(null));
		assertThrows(InvalidArgumentException.class, () -> historial.guardarIntercambio(null));
		assertThrows(InvalidArgumentException.class, () -> historial.guardarValoracion(null));
	}

	@Test
	void testGuardarValoracionGastoCliente() throws Exception {
		assertEquals(0, historial.getValoracionesPendientes().length);
		historial.guardarUsuario(emisor);
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		Valoracion v = new Valoracion(art);
		historial.guardarValoracion(v);

		StatsUsuario stats = null;
		for (StatsUsuario sUser : historial.getUsuariosMasActivos()) {
			if (sUser.getCliente().equals(emisor)) {
				stats = sUser;
				break;
			}
		}

		assertNotNull(stats);
		assertEquals(stats.getGastoTotal(), Sistema.getInstancia().getPrecioValoracion());
	}

	@Test
	void testGetValoracionesPendientes() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		Valoracion v = new Valoracion(art); // pendiente — no valorada
		historial.guardarValoracion(v);

		List<Valoracion> pendientes = List.of(historial.getValoracionesPendientes());
		assertTrue(pendientes.contains(v));
	}

	@Test
	void testGetValoracionesPendientesFalse() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		Valoracion v = new Valoracion(art); // pendiente — no valorada
		historial.guardarValoracion(v);

		historial.valorarArticulo(empleadoConPermiso, art, 15, EstadoFisicoArticulo.MUY_BUENO);

		List<Valoracion> pendientes = List.of(historial.getValoracionesPendientes());
		assertFalse(pendientes.contains(v));
	}

	@Test
	void testGetIntercambiosPendientesVacio() {
		assertEquals(0, historial.getIntercambiosPendientes().length);
	}

	@Test
	void testGetIntercambiosPendientes() throws Exception {
		Intercambio i1 = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		historial.guardarIntercambio(i1);
		carteraReceptor.aceptarIntercambio(i1);

		Intercambio i2 = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[1] },
				new ArticuloSegundaMano[] { artsReceptor[1] });
		historial.guardarIntercambio(i2);

		List<Intercambio> pendientes = List.of(historial.getIntercambiosPendientes());
		assertTrue(pendientes.contains(i1));
		assertFalse(pendientes.contains(i2));
	}

	@Test
	void testValidarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		historial.guardarIntercambio(intercambio);
		carteraReceptor.aceptarIntercambio(intercambio);
		assertEquals(EstadoIntercambio.ACEPTADO, intercambio.getEstado());
		assertTrue(historial.validarIntercambio(empleadoConPermiso, intercambio));
		assertEquals(EstadoIntercambio.CONFIRMADO, intercambio.getEstado());
	}

	@Test
	void testValidarIntercambioActualizaEstadisticas() throws Exception {
		historial.guardarUsuario(emisor);
		historial.guardarUsuario(receptor);
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		historial.guardarIntercambio(intercambio);
		carteraReceptor.aceptarIntercambio(intercambio);
		historial.validarIntercambio(empleadoConPermiso, intercambio);

		StatsUsuario stats = null;
		for (StatsUsuario sUser : historial.getUsuariosMasActivos()) {
			if (sUser.getCliente().equals(emisor)) {
				stats = sUser;
				break;
			}
		}

		assertNotNull(stats);
		assertTrue(stats.getUdsIntercambiadas() > 0);
	}

	@Test
	void testValidarIntercambioMalLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		historial.guardarIntercambio(intercambio);
		carteraReceptor.aceptarIntercambio(intercambio);

		assertThrows(InvalidPermitException.class, () -> historial.validarIntercambio(empleadoSinPermiso, intercambio));
		assertThrows(InvalidArgumentException.class, () -> historial.validarIntercambio(empleadoConPermiso, null));
		assertThrows(InvalidArgumentException.class, () -> historial.validarIntercambio(null, intercambio));
	}

	@Test
	void testValorarArticulo() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		art.anadirValoracion(new Valoracion(art));

		assertTrue(historial.valorarArticulo(empleadoConPermiso, art, 50.0, EstadoFisicoArticulo.MUY_BUENO));
		assertEquals(EstadoFisicoArticulo.MUY_BUENO, art.getValoracion().getEstadoFisico());
	}

	@Test
	void testValorarArticuloSinValoracionLanzaExcepcion() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Sin valorar", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);

		assertThrows(ArticuloSinValoracionException.class,
				() -> historial.valorarArticulo(empleadoConPermiso, art, 50.0, EstadoFisicoArticulo.MUY_BUENO));
	}

	@Test
	void testValorarArticuloMalLanzaExcepcion() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);

		assertThrows(ArticuloSinValoracionException.class,
				() -> historial.valorarArticulo(empleadoConPermiso, art, 50.0, EstadoFisicoArticulo.MUY_BUENO));

		art.anadirValoracion(new Valoracion(art));

		assertThrows(InvalidPermitException.class,
				() -> historial.valorarArticulo(empleadoSinPermiso, art, 50.0, EstadoFisicoArticulo.MUY_BUENO));
		assertThrows(InvalidArgumentException.class,
				() -> historial.valorarArticulo(empleadoConPermiso, art, -1.0, EstadoFisicoArticulo.MUY_BUENO));
		assertThrows(InvalidArgumentException.class,
				() -> historial.valorarArticulo(empleadoConPermiso, art, 1.0, null));
	}

	@Test
	void testGetVentasEntreMeses() throws Exception {
		YearMonth inicio = Reloj.mesNow();
		YearMonth fin = Reloj.mesNow();
		StatsMensual acumulado = historial.getVentasEntreMesesAcumulado(inicio, fin);
		assertEquals(0, acumulado.getUnidades());
		assertEquals(0.0, acumulado.getRecaudacion());

		Pedido pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(crearComic("Batman", 30.0), 3),
				new StockExterno(crearComic("Superman", 50.0), 2) });
		assertTrue(historial.guardarPedido(pedido));
	}

	@Test
	void testGetVentasEntreMeses2() throws Exception {
		YearMonth inicio = Reloj.mesNow();
		Pedido pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(crearComic("Batman", 30.0), 3),
				new StockExterno(crearComic("Superman", 50.0), 2) });
		assertTrue(historial.guardarPedido(pedido));

		Reloj.avanzarMes();
		pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(crearComic("Batman", 30.0), 3),
				new StockExterno(crearComic("Superman", 50.0), 2) });
		assertTrue(historial.guardarPedido(pedido));

		Reloj.avanzarMes();
		pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(crearComic("Batman", 30.0), 3),
				new StockExterno(crearComic("Superman", 50.0), 2) });
		assertTrue(historial.guardarPedido(pedido));

		YearMonth fin = Reloj.mesNow();

		List<StatsMensual> list = historial.getVentasEntreMeses(inicio, fin);

		for (StatsMensual st : list) {
			assertEquals(5, st.getUnidades());
			assertEquals(190, st.getRecaudacion());
		}

		StatsMensual acumulado = historial.getVentasEntreMesesAcumulado(inicio, fin);
		assertEquals(15, acumulado.getUnidades());
		assertEquals(570, acumulado.getRecaudacion());
	}

	@Test
	void testGetIntercambiosEntreMesesVacio() throws Exception {
		YearMonth inicio = Reloj.mesNow();
		for (int i = 0; i < 3; i++) {
			Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[i] },
					new ArticuloSegundaMano[] { artsReceptor[i] });
			historial.guardarIntercambio(intercambio);
			carteraReceptor.aceptarIntercambio(intercambio);
			assertEquals(EstadoIntercambio.ACEPTADO, intercambio.getEstado());
			assertTrue(historial.validarIntercambio(empleadoConPermiso, intercambio));
			assertEquals(EstadoIntercambio.CONFIRMADO, intercambio.getEstado());
			Reloj.avanzarMes();
		}

		YearMonth fin = Reloj.mesNow();

		List<StatsMensual> list = historial.getIntercambiosEntreMeses(inicio, fin);
		assertEquals(3, list.size());

		for (StatsMensual st : list) {
			assertEquals(2, st.getUnidades());
		}
	}

	@Test
	void testGetProductosMayorRecaudacionMesesInvalidosLanzaExcepcion() {
		YearMonth inicio = Reloj.mesNow();
		YearMonth fin = Reloj.mesNow().minusMonths(1);
		assertThrows(InvalidArgumentException.class, () -> historial.getProductosMayorRecaudacion(inicio, fin));
	}
	
	@Test
	void testGetProductosMayorRecaudacion() throws Exception {
		Comic batman = crearComic("Batman", 30.0), superman = crearComic("Superman", 50.0), ironman = crearComic("IronMan", 1000);
		YearMonth inicio = Reloj.mesNow();
		Pedido pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(batman, 3),
				new StockExterno(superman, 2)});
		assertTrue(historial.guardarPedido(pedido));

		Reloj.avanzarMes();
		pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(batman, 3),
				new StockExterno(superman, 2) });
		assertTrue(historial.guardarPedido(pedido));

		Reloj.avanzarMes();
		pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(batman, 3),
				new StockExterno(superman, 2), new StockExterno(ironman, 1)  });
		assertTrue(historial.guardarPedido(pedido));

		YearMonth fin = Reloj.mesNow();
		
		List<Entry<Producto, StatsMensual>> list = historial.getProductosMayorRecaudacion(inicio, fin);
		
		assertEquals(ironman, list.get(0).getKey());
		assertEquals(1000, list.get(0).getValue().getRecaudacion());
		assertEquals(1, list.get(0).getValue().getUnidades());
		assertEquals(superman, list.get(1).getKey());
		assertEquals(300, list.get(1).getValue().getRecaudacion());
		assertEquals(6, list.get(1).getValue().getUnidades());
		assertEquals(batman, list.get(2).getKey());
		assertEquals(270, list.get(2).getValue().getRecaudacion());
		assertEquals(9, list.get(2).getValue().getUnidades());		
	}

	@Test
	void testGetProductosMayorRecaudacionMalLanzaExcepcion() {
		assertThrows(InvalidArgumentException.class,
				() -> historial.getProductosMayorRecaudacion(null, Reloj.mesNow()));
		assertThrows(InvalidArgumentException.class,
				() -> historial.getProductosMayorRecaudacion(Reloj.mesNow(), null));
		assertThrows(InvalidArgumentException.class,
				() -> historial.getProductosMayorRecaudacion(Reloj.mesNow(), Reloj.mesNow().minusMonths(2)));
	}

	// ─── getUsuariosMasActivos
	// ────────────────────────────────────────────────────

	@Test
	void testGetUsuariosMasActivos() {
		assertTrue(historial.getUsuariosMasActivos().isEmpty());
	}

	@Test
	void testGetUsuariosMasActivosOrdenados() throws Exception {
		historial.guardarUsuario(emisor);
		historial.guardarUsuario(receptor);

		Comic batman = crearComic("Batman", 30.0), superman = crearComic("Superman", 50.0), ironman = crearComic("IronMan", 1000);
		
		Pedido pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(batman, 3),
				new StockExterno(superman, 2)});
		assertTrue(historial.guardarPedido(pedido));

		Reloj.avanzarMes();
		pedido = new Pedido(emisor, new StockExterno[] { new StockExterno(batman, 3),
				new StockExterno(superman, 2) });
		assertTrue(historial.guardarPedido(pedido));

		Reloj.avanzarMes();
		pedido = new Pedido(receptor, new StockExterno[] { new StockExterno(batman, 3),
				new StockExterno(superman, 2), new StockExterno(ironman, 1)  });
		assertTrue(historial.guardarPedido(pedido));
		List<StatsUsuario> activos = historial.getUsuariosMasActivos();

		assertEquals(activos.get(0).getCliente(), receptor);
		assertEquals(1190.0, activos.get(0).getGastoTotal());
		assertEquals(activos.get(1).getCliente(), emisor);
		assertEquals(380.0, activos.get(1).getGastoTotal());
	}

	// ─── toString
	// ─────────────────────────────────────────────────────────────────

	@Test
	void testToStringNoNull() {
		assertNotNull(historial.toString());
	}

	@Test
	void testToStringContieneHistorial() {
		assertTrue(historial.toString().contains("Historial"));
	}
}