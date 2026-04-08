/**
 * Este paquete contiene los tests de las clases incluídas en el paquete de usuario
 */
package usuario.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.*;
import sistema.Sistema;
import sistema.Tienda;
import usuario.*;
import venta.productos.*;
import wallapop.ArticuloSegundaMano;

/**
 * Clase con los tests de los métodos de la clase clienteRegistrado
 * 
 * @author Claudia Saiz
 */
class ClienteRegistradoTest {
	private static Tienda tienda;
	private static ClienteRegistrado c;
	private static Duration durOrig = Sistema.getInstancia().getTiempoCaducaCarrito();

	@AfterAll
	void end() {
		Sistema.getInstancia().setTiempoCaducaCarrito(durOrig);
	}
	
	private Comic crearComic(String nombre, double precio) throws Exception {
		List<Categoria> categorias = new ArrayList<>();
		categorias.add(new Categoria("aventuras"));
		return new Comic(nombre, "Descripción de " + nombre, precio, null, LocalDate.of(2020, 1, 1), "Autor Test", 100, "Editorial Test", categorias.toArray(new Categoria[0]));
	}
	
	
	private ArticuloSegundaMano crearArticulo(ClienteRegistrado c) throws Exception {
		List<Categoria> categorias = new ArrayList<>();
		categorias.add(new Categoria("aventuras"));
		return new ArticuloSegundaMano("Articulo", "Descripción", c.getCartera(), "Otros articulos", categorias.toArray(new Categoria[0]));
	}

	@BeforeEach
	void setUp() throws Exception{
		c = new ClienteRegistrado("Cliente1", "pass", tienda);
		tienda = new Tienda();
		Sistema.getInstancia().setTiempoCaducaCarrito(Duration.ofHours(1));
	}

	// constructor

	@Test
	void testConstructorDatosCorrectos() {
		assertAll(() -> assertEquals("Cliente1", c.getNombre()), () -> assertEquals("pass", c.getContrasena()));
	}

	@Test
	void testConstructorNombreNull() {
		assertThrows(InvalidArgumentException.class, () -> new ClienteRegistrado(null, "pass", tienda));
	}

	@Test
	void testConstructorContrasenaNull() {
		assertThrows(InvalidArgumentException.class, () -> new ClienteRegistrado("Cliente1", null, tienda));
	}

	@Test
	void testConstructorReferenciasCorrectas() {
		assertNotNull(c.getCarrito());
		assertEquals(0, c.getCarrito().getItems().length);
		boolean tienePedido = false;
		for (TipoNotificacion t : c.getIntereses()) {
			if (t == TipoNotificacion.PEDIDO)
				tienePedido = true;
		}
		assertTrue(tienePedido);
		assertEquals(0, c.getCompras().length);
		assertEquals(0, c.getNotificaciones().length);
	}

	// cambiarContrasena

	@Test
	void testCambiarContrasenaCorrecta() throws InvalidArgumentException {
		c.cambiarContrasena("pass", "nuevaPass", "nuevaPass");
		assertEquals("nuevaPass", c.getContrasena());
	}

	@Test
	void testCambiarContrasenaAntiguaIncorrecta() {
		assertAll(
				() -> assertThrows(InvalidArgumentException.class,
						() -> c.cambiarContrasena("incorrecta", "nuevaPass", "nuevaPass")),
				() -> assertThrows(InvalidArgumentException.class,
						() -> c.cambiarContrasena("pass1234", "nuevaPass", "otraDistinta")));
	}

	// Notificaciones

	@Test
	void testEnviarNotificacionConInteres() {
		boolean resultado = c.enviarNotificacion("Tu pedido está en camino", TipoNotificacion.PEDIDO);
		assertAll(() -> assertTrue(resultado), () -> assertEquals(1, c.getNotificaciones().length));
	}

	@Test
	void testEnviarNotificacionSinInteres() {
		c.quitarInteres(TipoNotificacion.PEDIDO);
		boolean resultado = c.enviarNotificacion("Oferta disponible", TipoNotificacion.INTERCAMBIO);
		assertAll(() -> assertFalse(resultado), () -> assertEquals(0, c.getNotificaciones().length));
	}

	@Test
	void testEnviarVariasNotificaciones() {
		c.enviarNotificacion("Notificación 1", TipoNotificacion.PEDIDO);
		c.enviarNotificacion("Notificación 2", TipoNotificacion.PEDIDO);
		assertEquals(2, c.getNotificaciones().length);
	}

	// Intereses

	@Test
	void testAnadirInteres() {
		boolean resultado = c.anadirInteres(TipoNotificacion.INTERCAMBIO);
		assertTrue(resultado);

		boolean encontrado = false;
		for (TipoNotificacion t : c.getIntereses()) {
			if (t == TipoNotificacion.INTERCAMBIO)
				encontrado = true;
		}
		assertTrue(encontrado);
	}

	@Test
	void testAnadirInteresRepetido() {
		boolean resultado = c.anadirInteres(TipoNotificacion.PEDIDO);
		assertFalse(resultado);
	}

	@Test
	void testQuitarInteres() {
		boolean resultado = c.quitarInteres(TipoNotificacion.PEDIDO);
		assertTrue(resultado);

		boolean encontrado = false;
		for (TipoNotificacion t : c.getIntereses()) {
			if (t == TipoNotificacion.PEDIDO)
				encontrado = true;
		}
		assertFalse(encontrado);
	}

	@Test
	void testQuitarInteresInexistente() {
		boolean resultado = c.quitarInteres(TipoNotificacion.INTERCAMBIO);
		assertFalse(resultado);
	}

	// anadirResena

	@Test
	void testAnadirResenaValida() throws Exception {
		Comic comic = crearComic("Superman", 10.0);
		boolean resultado = c.anadirResena(4, "Muy bueno", comic);
		assertTrue(resultado);
	}

	@Test
	void testAnadirResenaFalla() throws Exception {
		assertThrows(InvalidArgumentException.class, () -> c.anadirResena(3, "Comentario", null));
		Comic comic = crearComic("Superman", 10.0);
		assertThrows(InvalidArgumentException.class, () -> c.anadirResena(3, null, comic));
		assertThrows(InvalidArgumentException.class, () -> c.anadirResena(-1, "Mal", comic));
		assertThrows(InvalidArgumentException.class, () -> c.anadirResena(6, "Demasiado", comic));
	}

	// tienePermiso

	@Test
	void testTienePermiso() {
		for (Permiso p : Permiso.values()) {
			assertFalse(c.tienePermiso(p));
		}
	}

	// getCarrito / getCartera / getPedidos

	@Test
	void testGetCarritoNoNull() {
		assertNotNull(c);
	}

	@Test
	void testGetCarteraNoNull() {
		assertNotNull(c.getCartera());
	}
	
	@Test
	void testGetPedidosNoNull() {
		assertNotNull(c.getPedidos());
	}
	
	// verCartera
	
	@Test
	void testVerCarteraVacia() {
		assertEquals(0, c.verCartera().length);
	}
	
	@Test
	void testVerCarteraConArticulos() throws Exception {
		c.getCartera().addArticulo(crearArticulo(c));
		assertEquals(1, c.verCartera().length);
	}
	
	@Test
	void testCarritoAPedido() throws Exception {
		c.getCarrito().anadirProducto(crearComic("Superman", 10.0));
		c.carritoAPedido();
		assertEquals(1, c.getPedidos().length);
	}


	@Test
	void testToStringNoNull() {
		assertNotNull(c.toString());
	}
}
