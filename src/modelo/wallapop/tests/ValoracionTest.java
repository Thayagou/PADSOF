package modelo.wallapop.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Sistema;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Empleado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import modelo.wallapop.Cartera;
import modelo.wallapop.EstadoFisicoArticulo;
import modelo.wallapop.Valoracion;

class ValoracionTest {

	private Tienda tienda;
	private ClienteRegistrado cliente;
	private Cartera cartera;
	private Empleado empleado;
	private ArticuloSegundaMano articulo;
	private Valoracion valoracion;

	@BeforeEach
	void setUp() throws Exception {
		tienda = new Tienda();
		cliente = new ClienteRegistrado("Cliente", "Cliente123", tienda);
		cartera = cliente.getCartera();
		empleado = new Empleado("Empleado", "pass");

		articulo = new ArticuloSegundaMano("Catan", "Juego de mesa", cartera, "Juegos de cartas", new Categoria("Juegos"));
		cartera.addArticulo(articulo);
		valoracion = new Valoracion(articulo);
	}

	// ─── Constructor ─────────────────────────────────────────────────────────────

	@Test
	void testConstructor() {
		assertNotNull(valoracion);
		assertEquals(articulo, valoracion.getArticulo());
		assertEquals(EstadoFisicoArticulo.PENDIENTE, valoracion.getEstadoFisico());
		assertNotNull(valoracion.getFechaSolicitud());
		assertNull(valoracion.getEmpleado());
		assertNull(valoracion.getFechaValoracion());
		assertEquals(-1, valoracion.getPrecioEstimado());
	}

	@Test
	void testGetDuenoArticulo() {
		assertEquals(cliente, valoracion.getDuenoArticulo());
	}

	@Test
	void testConstructorPrecioPagado() {
		assertEquals(valoracion.getPrecioPagado(), Sistema.getInstancia().getPrecioValoracion());
	}

	@Test
	void testValorar() throws Exception {
		valoracion.valorar(empleado, 50.0, EstadoFisicoArticulo.MUY_BUENO);

		assertEquals(empleado, valoracion.getEmpleado());
		assertEquals(50.0, valoracion.getPrecioEstimado());
		assertEquals(EstadoFisicoArticulo.MUY_BUENO, valoracion.getEstadoFisico());
		assertNotNull(valoracion.getFechaValoracion());
	}

	@Test
	void testValorarDisponibilizaArticulo() throws Exception {
		assertFalse(articulo.isDisponible());
		valoracion.valorar(empleado, 50.0, EstadoFisicoArticulo.MUY_BUENO);
		assertTrue(articulo.isDisponible());
	}

	@Test
	void testValorarLanzaExcepciones() {
		assertThrows(InvalidArgumentException.class, () -> valoracion.valorar(null, 50.0, EstadoFisicoArticulo.MUY_BUENO));
		assertThrows(InvalidArgumentException.class, () -> valoracion.valorar(empleado, 50.0, null));
		assertThrows(InvalidArgumentException.class, () -> valoracion.valorar(empleado, -1.0, EstadoFisicoArticulo.MUY_BUENO));
	
	}

	@Test
	void testGetEmpleadoAntesYDespuesValoracion() throws Exception {
		assertNull(valoracion.getEmpleado());
		valoracion.valorar(empleado, 50.0, EstadoFisicoArticulo.MUY_BUENO);
		assertEquals(empleado, valoracion.getEmpleado());
	}

	@Test
	void testGetPrecioEstimadoAntesYDespuesValoracion() throws Exception {
		assertEquals(-1, valoracion.getPrecioEstimado());
		valoracion.valorar(empleado, 75.0, EstadoFisicoArticulo.MUY_BUENO);
		assertEquals(75.0, valoracion.getPrecioEstimado());
	}

	@Test
	void testGetEstadoFisicoAntesYDespuesValoracion() throws Exception {
		assertEquals(EstadoFisicoArticulo.PENDIENTE, valoracion.getEstadoFisico());
		valoracion.valorar(empleado, 50.0, EstadoFisicoArticulo.MUY_BUENO);
		assertEquals(EstadoFisicoArticulo.MUY_BUENO, valoracion.getEstadoFisico());
	}

	// ─── toString / toStringSinArticulo ──────────────────────────────────────────

	@Test
	void testToStringPendiente() {
		assertTrue(valoracion.toString().contains("Pendiente"));
	}

	@Test
	void testToStringTrasValorar() throws Exception {
		assertTrue(valoracion.toString().contains("Pendiente"));
		valoracion.valorar(empleado, 50.0, EstadoFisicoArticulo.MUY_BUENO);
		assertFalse(valoracion.toString().contains("Pendiente"));
	}

	@Test
	void testToStringSinArticuloPendiente() {
		assertTrue(valoracion.toStringSinArticulo().contains("Pendiente"));
	}

	@Test
	void testToStringSinArticuloTrasValorar() throws Exception {
		valoracion.valorar(empleado, 50.0, EstadoFisicoArticulo.MUY_BUENO);
		assertFalse(valoracion.toStringSinArticulo().contains("Pendiente"));
	}
}
