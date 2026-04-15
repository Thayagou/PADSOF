package modelo.venta.productos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import modelo.exceptions.*;
import modelo.sistema.CarritoCaducadoObserver;
import modelo.sistema.Reloj;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import modelo.venta.productos.*;

/**
 * Clase con los tests de la clase Resena
 * 
 * @author Juan Ibáñez
 */
class ResenaTest {

	private ClienteRegistrado usuario;
	private Resena resena;

	@BeforeEach
	void setUp() throws Exception {
		CarritoCaducadoObserver tienda = new Tienda();
		usuario = new ClienteRegistrado("Cliente1", "pass", tienda);
		resena = new Resena(4.0, "Muy bueno", usuario);
	}

	// --- Constructor ---

	@Test
	void testConstructorValido() {
		assertNotNull(resena);
		assertEquals(4.0, resena.getPuntuacion());
		assertEquals("Muy bueno", resena.getComentario());
		assertEquals(usuario, resena.getUsuario());
		assertEquals(Reloj.localDateNow(), resena.getFecha());
	}

	@Test
	void testConstructorComentarioNull() {
		assertThrows(InvalidArgumentException.class, () -> new Resena(4.0, null, usuario));
	}

	@Test
	void testConstructorUsuarioNull() {
		assertThrows(InvalidArgumentException.class, () -> new Resena(4.0, "Muy bueno", null));
	}

	@Test
	void testConstructorPuntuacionNegativa() {
		assertThrows(InvalidArgumentException.class, () -> new Resena(-1.0, "Muy bueno", usuario));
	}

	@Test
	void testConstructorPuntuacionMayorDeCinco() {
		assertThrows(InvalidArgumentException.class, () -> new Resena(5.1, "Muy bueno", usuario));
	}

	@Test
	void testConstructorPuntuacionCero() {
		assertDoesNotThrow(() -> new Resena(0.0, "Muy malo", usuario));
	}

	@Test
	void testConstructorPuntuacionCinco() {
		assertDoesNotThrow(() -> new Resena(5.0, "Perfecto", usuario));
	}

	// --- Getters ---

	@Test
	void testGetPuntuacion() {
		assertEquals(4.0, resena.getPuntuacion());
	}

	@Test
	void testGetComentario() {
		assertEquals("Muy bueno", resena.getComentario());
	}

	@Test
	void testGetUsuario() {
		assertEquals(usuario, resena.getUsuario());
	}

	@Test
	void testGetFechaEsHoy() {
		assertEquals(Reloj.localDateNow(), resena.getFecha());
	}
}