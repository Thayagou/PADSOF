/**
 * Este paquete contiene los tests de las clases incluídas en el paquete de usuario
 */
package usuario.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.*;
import sistema.Sistema;
import usuario.*;
import venta.productos.*;
import wallapop.ArticuloSegundaMano;

/**
 * Clase con los tests de los métodos de la clase clienteRegistrado
 * 
 * @author Claudia Saiz
 */
class ClienteRegistradoTest {
	
	
	private ClienteRegistrado crearCliente() {
		return new ClienteRegistrado("Cliente1", "pass", );
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
	void setUp() {
		Sistema.getInstancia().setTiempoCaducaCarrito(Duration.ofHours(1));
	}

	// constructor

	@Test
	void testConstructorDatosCorrectos() {
		ClienteRegistrado c = crearCliente();
		assertAll(() -> assertEquals("Cliente1", c.getNombre()), () -> assertEquals("pass", c.getContrasena()));
	}

	@Test
	void testConstructorNombreNull() {
		assertThrows(NullPointerException.class, () -> new ClienteRegistrado(null, "pass"));
	}

	@Test
	void testConstructorContrasenaNull() {
		assertThrows(NullPointerException.class, () -> new ClienteRegistrado("Cliente1", null));
	}

	@Test
	void testConstructorReferenciasCorrectas() {
		ClienteRegistrado c = crearCliente();
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
		ClienteRegistrado c = crearCliente();
		c.cambiarContrasena("pass", "nuevaPass", "nuevaPass");
		assertEquals("nuevaPass", c.getContrasena());
	}

	@Test
	void testCambiarContrasenaAntiguaIncorrecta() {
		ClienteRegistrado c = crearCliente();
		assertAll(
				() -> assertThrows(InvalidArgumentException.class,
						() -> c.cambiarContrasena("incorrecta", "nuevaPass", "nuevaPass")),
				() -> assertThrows(InvalidArgumentException.class,
						() -> c.cambiarContrasena("pass1234", "nuevaPass", "otraDistinta")));
	}

	// Notificaciones

	@Test
	void testEnviarNotificacionConInteres() {
		ClienteRegistrado c = crearCliente();
		boolean resultado = c.enviarNotificacion("Tu pedido está en camino", TipoNotificacion.PEDIDO);
		assertAll(() -> assertTrue(resultado), () -> assertEquals(1, c.getNotificaciones().length));
	}

	@Test
	void testEnviarNotificacionSinInteres() {
		ClienteRegistrado c = crearCliente();
		c.quitarInteres(TipoNotificacion.PEDIDO);
		boolean resultado = c.enviarNotificacion("Oferta disponible", TipoNotificacion.INTERCAMBIO);
		assertAll(() -> assertFalse(resultado), () -> assertEquals(0, c.getNotificaciones().length));
	}

	@Test
	void testEnviarVariasNotificaciones() {
		ClienteRegistrado c = crearCliente();
		c.enviarNotificacion("Notificación 1", TipoNotificacion.PEDIDO);
		c.enviarNotificacion("Notificación 2", TipoNotificacion.PEDIDO);
		assertEquals(2, c.getNotificaciones().length);
	}

	// Intereses

	@Test
	void testAnadirInteres() {
		ClienteRegistrado c = crearCliente();
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
		ClienteRegistrado c = crearCliente();
		boolean resultado = c.anadirInteres(TipoNotificacion.PEDIDO);
		assertFalse(resultado);
	}

	@Test
	void testQuitarInteres() {
		ClienteRegistrado c = crearCliente();
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
		ClienteRegistrado c = crearCliente();
		boolean resultado = c.quitarInteres(TipoNotificacion.INTERCAMBIO);
		assertFalse(resultado);
	}

	// anadirResena

	@Test
	void testAnadirResenaValida() throws Exception {
		ClienteRegistrado c = crearCliente();
		Comic comic = crearComic("Superman", 10.0);
		boolean resultado = c.anadirResena(4, "Muy bueno", comic);
		assertTrue(resultado);
	}

	@Test
	void testAnadirResenaFalla() throws Exception {
		ClienteRegistrado c = crearCliente();
		assertThrows(InvalidArgumentException.class, () -> c.anadirResena(3, "Comentario", null));
		Comic comic = crearComic("Superman", 10.0);
		assertThrows(InvalidArgumentException.class, () -> c.anadirResena(3, null, comic));
		assertThrows(InvalidArgumentException.class, () -> c.anadirResena(-1, "Mal", comic));
		assertThrows(InvalidArgumentException.class, () -> c.anadirResena(6, "Demasiado", comic));
	}

	// tienePermiso

	@Test
	void testTienePermiso() {
		ClienteRegistrado c = crearCliente();
		for (Permiso p : Permiso.values()) {
			assertFalse(c.tienePermiso(p));
		}
	}

	// getCarrito / getCartera / getPedidos

	@Test
	void testGetCarritoNoNull() {
		assertNotNull(crearCliente().getCarrito());
	}

	@Test
	void testGetCarteraNoNull() {
		assertNotNull(crearCliente().getCartera());
	}
	
	@Test
	void testGetPedidosNoNull() {
		assertNotNull(crearCliente().getPedidos());
	}
	
	// verCartera
	
	@Test
	void testVerCarteraVacia() {
		ClienteRegistrado c = crearCliente();
		assertEquals(0, c.verCartera());
	}
	
	@Test
	void testVerCarteraConArticulos() throws Exception {
		ClienteRegistrado c = crearCliente();
		c.getCartera().addArticulo(crearArticulo(c));
		assertEquals(1, c.verCartera().length);
	}
	
	// carritoAPedido
	
	@Test
	void testCarritoAPedido() throws Exception {
		ClienteRegistrado c = crearCliente();
		c.getCarrito().anadirProducto(crearComic("Superman", 10.0));
		c.carritoAPedido();
		assertEquals(1, c.getPedidos().length);
	}
	
	@Test
	void testCarritoVacioAPedido() throws Exception {
		ClienteRegistrado c = crearCliente();
		c.carritoAPedido();
		assertAll(() -> assertThrows(InvalidArgumentException.class, () -> c.getPedidos()), () -> assertEquals(0, c.getPedidos().length));
	}

	// toString

	@Test
	void testToStringNoNull() {
		assertNotNull(crearCliente().toString());
	}
}
