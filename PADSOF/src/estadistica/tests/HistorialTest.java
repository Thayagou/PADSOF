package estadistica.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.InvalidArgumentException;
import exceptions.InvalidPermitException;
import exceptions.ArticuloSinValoracionException;
import estadistica.Historial;
import estadistica.StatsMensual;
import estadistica.StatsUsuario;
import sistema.Tienda;
import sistema.Reloj;
import usuario.ClienteRegistrado;
import usuario.Empleado;
import usuario.Permiso;
import venta.productos.Categoria;
import venta.pedidos.Pedido;
import wallapop.ArticuloSegundaMano;
import wallapop.Cartera;
import wallapop.EstadoFisicoArticulo;
import wallapop.EstadoIntercambio;
import wallapop.Intercambio;
import wallapop.Valoracion;

import java.time.YearMonth;
import java.util.*;

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

		empleadoConPermiso = new Empleado("EmpleadoPermiso", "pass", new Permiso[] {Permiso.INTERCAMBIOS, Permiso.PEDIDOS});

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


	@Test
	void testGuardarUsuario() {
		historial.guardarUsuario(emisor);
		List<StatsUsuario> activos = historial.getUsuariosMasActivos();

		assertEquals(emisor, activos.get(0));
	}

	@Test
	void testGuardarIntercambio() throws Exception {
		assertEquals(0, historial.getIntercambiosPendientes().length);
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertTrue(historial.guardarIntercambio(intercambio));
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
		assertThrows(InvalidArgumentException.class, () -> historial.guardarIntercambio(null));
		assertThrows(InvalidArgumentException.class, () -> historial.guardarValoracion(null));
	}

	@Test
	void testGuardarValoracionGastoCliente() throws Exception {
		historial.guardarUsuario(emisor);
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		Valoracion v = new Valoracion(art);
		historial.guardarValoracion(v);

		StatsUsuario stats = historial.getUsuariosMasActivos().stream()
				.filter(s -> s.getCliente().equals(emisor)).findFirst().orElse(null);
		assertNotNull(stats);
		assertTrue(stats.getGastoTotal() > 0);
	}

	// ─── getValoracionesPendientes ────────────────────────────────────────────────

	@Test
	void testGetValoracionesPendientesVacio() {
		assertEquals(0, historial.getValoracionesPendientes().length);
	}

	@Test
	void testGetValoracionesPendientesConUna() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		Valoracion v = new Valoracion(art); // pendiente — no valorada
		historial.guardarValoracion(v);

		List<Valoracion> pendientes = List.of(historial.getValoracionesPendientes());
		assertTrue(pendientes.contains(v));
	}

	@Test
	void testGetValoracionesPendientesNoIncluyeValoradas() throws Exception {
		// artsEmisor[0] ya fue valorado en setUp → no debe aparecer como pendiente
		Valoracion vValorada = artsEmisor[0].getValoracion();
		historial.guardarValoracion(vValorada);

		List<Valoracion> pendientes = List.of(historial.getValoracionesPendientes());
		assertFalse(pendientes.contains(vValorada));
	}

	// ─── getIntercambiosPendientes ────────────────────────────────────────────────

	@Test
	void testGetIntercambiosPendientesVacio() {
		assertEquals(0, historial.getIntercambiosPendientes().length);
	}

	@Test
	void testGetIntercambiosPendientesSoloAceptados() throws Exception {
		Intercambio i1 = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		historial.guardarIntercambio(i1);
		carteraReceptor.aceptarIntercambio(i1); // ahora está ACEPTADO

		Intercambio i2 = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[1]},
				new ArticuloSegundaMano[]{artsReceptor[1]});
		historial.guardarIntercambio(i2); // sigue OFERTADO

		List<Intercambio> pendientes = List.of(historial.getIntercambiosPendientes());
		assertTrue(pendientes.contains(i1));
		assertFalse(pendientes.contains(i2));
	}

	// ─── validarIntercambio ───────────────────────────────────────────────────────

	@Test
	void testValidarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		historial.guardarIntercambio(intercambio);
		carteraReceptor.aceptarIntercambio(intercambio);

		assertTrue(historial.validarIntercambio(empleadoConPermiso, intercambio));
		assertEquals(EstadoIntercambio.CONFIRMADO, intercambio.getEstado());
	}

	@Test
	void testValidarIntercambioActualizaEstadisticas() throws Exception {
		historial.guardarUsuario(emisor);
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		historial.guardarIntercambio(intercambio);
		carteraReceptor.aceptarIntercambio(intercambio);
		historial.validarIntercambio(empleadoConPermiso, intercambio);

		StatsUsuario stats = historial.getUsuariosMasActivos().stream()
				.filter(s -> s.getCliente().equals(emisor)).findFirst().orElse(null);
		assertNotNull(stats);
		assertTrue(stats.getUdsIntercambiadas() > 0);
	}

	@Test
	void testValidarIntercambioSinPermisoLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		historial.guardarIntercambio(intercambio);
		carteraReceptor.aceptarIntercambio(intercambio);

		assertThrows(InvalidPermitException.class, () ->
				historial.validarIntercambio(empleadoSinPermiso, intercambio));
	}

	@Test
	void testValidarIntercambioNullLanzaExcepcion() {
		assertThrows(InvalidArgumentException.class, () ->
				historial.validarIntercambio(empleadoConPermiso, null));
	}

	@Test
	void testValidarIntercambioEmpleadoNullLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertThrows(InvalidArgumentException.class, () ->
				historial.validarIntercambio(null, intercambio));
	}

	@Test
	void testValidarIntercambioNoAceptadoDevuelveFalse() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		historial.guardarIntercambio(intercambio); // sigue OFERTADO

		assertFalse(historial.validarIntercambio(empleadoConPermiso, intercambio));
	}

	// ─── valorarArticulo ──────────────────────────────────────────────────────────

	@Test
	void testValorarArticulo() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		art.anadirValoracion(new Valoracion(art));

		assertTrue(historial.valorarArticulo(empleadoConPermiso, art, 50.0, EstadoFisicoArticulo.BUENO));
		assertEquals(EstadoFisicoArticulo.BUENO, art.getValoracion().getEstadoFisico());
	}

	@Test
	void testValorarArticuloSinValoracionLanzaExcepcion() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("SinVal", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		// sin valoracion asignada
		assertThrows(ArticuloSinValoracionException.class, () ->
				historial.valorarArticulo(empleadoConPermiso, art, 50.0, EstadoFisicoArticulo.BUENO));
	}

	@Test
	void testValorarArticuloSinPermisoLanzaExcepcion() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		art.anadirValoracion(new Valoracion(art));

		assertThrows(InvalidPermitException.class, () ->
				historial.valorarArticulo(empleadoSinPermiso, art, 50.0, EstadoFisicoArticulo.BUENO));
	}

	@Test
	void testValorarArticuloPrecioNegativoLanzaExcepcion() throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Nuevo", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		art.anadirValoracion(new Valoracion(art));

		assertThrows(InvalidArgumentException.class, () ->
				historial.valorarArticulo(empleadoConPermiso, art, -1.0, EstadoFisicoArticulo.BUENO));
	}

	@Test
	void testValorarArticuloNullLanzaExcepcion() {
		assertThrows(InvalidArgumentException.class, () ->
				historial.valorarArticulo(empleadoConPermiso, null, 50.0, EstadoFisicoArticulo.BUENO));
	}

	// ─── getVentasEntreMeses / getVentasEntreMesesAcumulado ───────────────────────

	@Test
	void testGetVentasEntreMesesVacio() {
		YearMonth inicio = Reloj.mesNow().minusMonths(1);
		YearMonth fin = Reloj.mesNow();
		List<StatsMensual> ventas = historial.getVentasEntreMeses(inicio, fin);
		assertTrue(ventas.isEmpty());
	}

	@Test
	void testGetVentasEntreMesesAcumuladoVacio() throws Exception {
		YearMonth inicio = Reloj.mesNow().minusMonths(1);
		YearMonth fin = Reloj.mesNow();
		StatsMensual acumulado = historial.getVentasEntreMesesAcumulado(inicio, fin);
		assertEquals(0, acumulado.getUnidades());
		assertEquals(0.0, acumulado.getRecaudacion());
	}

	// ─── getIntercambiosEntreMeses ────────────────────────────────────────────────

	@Test
	void testGetIntercambiosEntreMesesVacio() {
		YearMonth inicio = Reloj.mesNow().minusMonths(1);
		YearMonth fin = Reloj.mesNow();
		List<StatsMensual> intercambios = historial.getIntercambiosEntreMeses(inicio, fin);
		assertTrue(intercambios.isEmpty());
	}

	// ─── getProductosMayorRecaudacion ─────────────────────────────────────────────

	@Test
	void testGetProductosMayorRecaudacionMesesInvalidosLanzaExcepcion() {
		YearMonth inicio = Reloj.mesNow();
		YearMonth fin = Reloj.mesNow().minusMonths(1); // fin antes que inicio
		assertThrows(InvalidArgumentException.class, () ->
				historial.getProductosMayorRecaudacion(inicio, fin));
	}

	@Test
	void testGetProductosMayorRecaudacionNullLanzaExcepcion() {
		assertThrows(InvalidArgumentException.class, () ->
				historial.getProductosMayorRecaudacion(null, Reloj.mesNow()));
		assertThrows(InvalidArgumentException.class, () ->
				historial.getProductosMayorRecaudacion(Reloj.mesNow(), null));
	}

	@Test
	void testGetProductosMayorRecaudacionVacio() throws Exception {
		YearMonth inicio = Reloj.mesNow().minusMonths(1);
		YearMonth fin = Reloj.mesNow();
		List<Map.Entry<?, StatsMensual>> lista = historial.getProductosMayorRecaudacion(inicio, fin);
		assertTrue(lista.isEmpty());
	}

	// ─── getUsuariosMasActivos ────────────────────────────────────────────────────

	@Test
	void testGetUsuariosMasActivosVacio() {
		assertTrue(historial.getUsuariosMasActivos().isEmpty());
	}

	@Test
	void testGetUsuariosMasActivosOrdenados() throws Exception {
		historial.guardarUsuario(emisor);
		historial.guardarUsuario(receptor);

		ArticuloSegundaMano art = new ArticuloSegundaMano("Art", "Desc", carteraEmisor, "Algo", cat1);
		carteraEmisor.addArticulo(art);
		Valoracion v = new Valoracion(art);
		historial.guardarValoracion(v); // aumenta gasto del emisor

		List<StatsUsuario> activos = historial.getUsuariosMasActivos();
		// el de menor gasto primero (orden ascendente)
		assertTrue(activos.get(0).getGastoTotal() <= activos.get(activos.size() - 1).getGastoTotal());
	}

	// ─── toString ─────────────────────────────────────────────────────────────────

	@Test
	void testToStringNoNull() {
		assertNotNull(historial.toString());
	}

	@Test
	void testToStringContieneHistorial() {
		assertTrue(historial.toString().contains("Historial"));
	}
}