/**
 * Este paquete contiene los tests de las clases incluídas en el paquete de usuario
 */
package usuario.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import exceptions.*;
import sistema.Reloj;
import sistema.Sistema;
import sistema.Tienda;
import usuario.Carrito;
import usuario.ClienteRegistrado;
import venta.descuentos.*;
import venta.productos.*;

import java.time.*;
import java.util.*;

/**
 * Clase con los tests de los métodos de la clase carrito
 * 
 * @author Claudia Saiz
 */
class CarritoTest {
	private static final Duration DURACION_LARGA = Duration.ofHours(1);
	private static final Duration DURACION_CORTA = Duration.ofNanos(1);
	private static ClienteRegistrado cliente;
	
	private static Carrito carrito;
	private static Tienda tienda;

	/**
	 * Crea un producto tipo Comic
	 */
	private Comic crearComic(String nombre, double precio) throws InvalidArgumentException, DoubleDiscountException {
		List<Categoria> categorias = new ArrayList<>();
		categorias.add(new Categoria("aventuras"));
		return new Comic(nombre, "Descripción de " + nombre, precio, null, LocalDate.of(2020, 1, 1), "Autor Test", 100, "Editorial Test", categorias.toArray(new Categoria[0]));
	}

	/**
	 * Crea un descuento de tipo regalo
	 * @throws InvalidArgumentException
	 */
	private DescuentoRegalo crearDescuentoRegalo(double precioMin, Producto regalo) throws InvalidArgumentException {
		return new DescuentoRegalo(precioMin, Reloj.now().minusDays(2), Reloj.now().plusDays(2), CondicionDescuento.VOLUMEN, regalo);
	}

	/**
	 * Crea un descuento de tipo regalo
	 * @throws InvalidArgumentException
	 */
	private DescuentoDinero crearDescuentoDinero(double precioMin, double dinero) throws InvalidArgumentException {
		return new DescuentoDinero(precioMin, Reloj.now().minusDays(2), Reloj.now().plusDays(2), CondicionDescuento.VOLUMEN, dinero);
	}

	/**
	 * Crea un descuento de tipo regalo
	 * @throws InvalidArgumentException
	 */
	private DescuentoPorcentaje crearDescuentoPorcentaje(double precioMin, double porcentaje)
			throws InvalidArgumentException {
		return new DescuentoPorcentaje(precioMin, Reloj.now().minusDays(2), Reloj.now().plusDays(2), CondicionDescuento.VOLUMEN, porcentaje);
	}

	@BeforeEach
	void setUp() throws Exception {
		tienda = new Tienda();
		Sistema.getInstancia().setTiempoCaducaCarrito(DURACION_LARGA);
		cliente = new ClienteRegistrado("cliente", "123", tienda);
		carrito = cliente.getCarrito();
	}


	@Test 
	void testConstructorCarritoVacio() {
		assertAll(() -> assertEquals(0, carrito.getItems().length), () -> assertEquals(0, carrito.getRegalos().length),
				() -> assertEquals(0, carrito.getContenido().length));
	}

	/// anadirProducto

	@Test
	void testAnadirProductoNull() {
		assertThrows(InvalidArgumentException.class, () -> carrito.anadirProducto(null));
	}

	@Test
	void testAnadirProductoNuevo() throws Exception {		
		carrito.anadirProducto(crearComic("Superman", 10.0));

		assertEquals(1, carrito.getItems().length);
	}

	@Test
	void testAnadirMismoProducto() throws Exception {
		Comic c = crearComic("Superman", 10.0);

		carrito.anadirProducto(c);
		carrito.anadirProducto(c);

		StockExterno[] items = carrito.getItems();
		assertAll(() -> assertEquals(1, items.length), () -> assertEquals(2, items[0].getUdsEnStock()));
	}

	@Test
	void testAnadirProductosDiferentes() throws Exception {		
		carrito.anadirProducto(crearComic("Superman", 10.0));
		carrito.anadirProducto(crearComic("Batman", 15.0));

		assertEquals(2, carrito.getItems().length);
	}

	@Test
	void testQuitarProductoInexistente() throws Exception {
		Comic c = crearComic("Superman", 10.0);

		assertDoesNotThrow(() -> carrito.quitarProducto(c));
	}

	@Test
	void testQuitarUnicoProducto() throws Exception {
		Comic c = crearComic("Superman", 10.0);

		carrito.anadirProducto(c);
		carrito.quitarProducto(c);

		assertEquals(0, carrito.getItems().length);
	}

	@Test
	void testQuitarUnaVezProducto() throws Exception {
		Comic c = crearComic("Superman", 10.0);

		carrito.anadirProducto(c);
		carrito.anadirProducto(c);
		carrito.quitarProducto(c);

		StockExterno[] items = carrito.getItems();
		assertAll(() -> assertEquals(1, items.length), () -> assertEquals(1, items[0].getUdsEnStock()));
	}

	@Test
	void testVaciarCarrito() throws Exception {
		carrito.anadirProducto(crearComic("Superman", 10.0));

		carrito.vaciarCarrito();
		assertAll(() -> assertEquals(0, carrito.getItems().length), () -> assertEquals(0, carrito.getRegalos().length),
				() -> assertEquals(0, carrito.getContenido().length));
	}

	@Test
	void testVaciarCarritoYaVacio() {
		assertDoesNotThrow(carrito::vaciarCarrito);
	}

	@Test
	void testGetContenidoVacio() {
		assertEquals(0, carrito.getContenido().length);
	}

	@Test
	void testGetContenidoLongitud() throws Exception {
		carrito.anadirProducto(crearComic("Superman", 10.0));

		int esperado = carrito.getItems().length + carrito.getRegalos().length;
		assertEquals(esperado, carrito.getContenido().length);
	}

	@Test
	void testGetContenidoNoNull() throws Exception {
		carrito.anadirProducto(crearComic("Superman", 10.0));
		carrito.anadirProducto(crearComic("Batman", 15.0));

		for (StockExterno se : carrito.getContenido()) {
			assertNotNull(se);
		}
	}

	/// calcularCarrito (sin descuentos)

	@Test
	void testCalcularCarritoVacio() {
		assertEquals(0.0, carrito.calcularCarrito());
	}

	@Test
	void testCalcularCarritoUnProducto() throws Exception {
		carrito.anadirProducto(crearComic("Superman", 10.0));

		assertEquals(10.0, carrito.calcularCarrito());
	}

	@Test
	void testCalcularCarritoVariosProductos() throws Exception {
		Comic c1 = crearComic("Superman", 10.0);
		Comic c2 = crearComic("Batman", 15.0);

		carrito.anadirProducto(c1); // 10 €
		carrito.anadirProducto(c2); // 15 €
		carrito.anadirProducto(c1); // 10 €

		// Total: 10 + 15 + 10 = 35
		assertEquals(35.0, carrito.calcularCarrito());
	}

	@Test
	void testCalcularCarritoIgual() throws Exception {
		carrito.anadirProducto(crearComic("Superman", 10.0));

		double primera = carrito.calcularCarrito();
		double segunda = carrito.calcularCarrito();

		assertEquals(primera, segunda);
	}

	@Test
	void testCalcularCarritoActualizaAlAnadir() throws Exception {
		Comic c = crearComic("Superman", 10.0);

		carrito.anadirProducto(c);
		double precio1 = carrito.calcularCarrito(); 

		carrito.anadirProducto(c);
		double precio2 = carrito.calcularCarrito();

		assertEquals(10.0, precio1);
		assertEquals(20.0, precio2);
	}

	@Test
	void testCalcularCarritoActualizaAlQuitar() throws Exception {
		Comic c = crearComic("Superman", 10.0);

		carrito.anadirProducto(c);
		carrito.anadirProducto(c);
		double precio1 = carrito.calcularCarrito();

		carrito.quitarProducto(c);
		double precio2 = carrito.calcularCarrito();

		assertEquals(20.0, precio1);
		assertEquals(10.0, precio2);
	}

	/// Calcular carrito (con descuentos)

	@Test
	void testCalcularCarritoConDescuentoRegalo() throws Exception {
		Comic c1 = crearComic("Superman", 10.0);
		carrito.anadirProducto(c1);
		c1.anadirDescuento(crearDescuentoRegalo(9.0, c1));

		assertAll(() -> assertEquals(10.0, carrito.calcularCarrito()),
				() -> assertEquals(1, carrito.getRegalos().length),
				() -> assertEquals(2, carrito.getContenido().length));
	}

	@Test
	void testCalcularCarritoDobleDescuentoRegalo() throws Exception {
		Comic c1 = crearComic("Superman", 10.0);
		Comic c2 = crearComic("Batman", 15.0);
		carrito.anadirProducto(c1);
		carrito.anadirProducto(c2);
		c1.anadirDescuento(crearDescuentoRegalo(9.0, c1));
		c2.anadirDescuento(crearDescuentoRegalo(12.0, c1));

		assertAll(() -> assertEquals(25.0, carrito.calcularCarrito()),
				() -> assertEquals(1, carrito.getRegalos().length),
				() -> assertEquals(2, carrito.getRegalos()[0].getUdsEnStock()));
	}

	@Test
	void testCalcularCarritoDescuentoDinero() throws Exception {
		Comic c1 = crearComic("Superman", 10.0);
		carrito.anadirProducto(c1);
		c1.anadirDescuento(crearDescuentoDinero(9.0, 5));

		assertAll(() -> assertEquals(5.0, carrito.calcularCarrito()));
	}

	@Test
	void testCalcularCarritoDescuentoPorcentaje() throws Exception {
		Comic c1 = crearComic("Superman", 10.0);
		carrito.anadirProducto(c1);
		c1.anadirDescuento(crearDescuentoPorcentaje(9.0, 20.0));

		assertAll(() -> assertEquals(8.0, carrito.calcularCarrito()));
	}

	@Test
	void testRenovarCaducidadConservaItems() throws Exception {
		carrito.anadirProducto(crearComic("Superman", 10.0));

		Sistema.getInstancia().setTiempoCaducaCarrito(DURACION_CORTA);
		carrito.calcularFechaCaducidad();
		Thread.sleep(5);

		Sistema.getInstancia().setTiempoCaducaCarrito(DURACION_LARGA);
		carrito.calcularFechaCaducidad();
		assertEquals(10.0, carrito.calcularCarrito());
	}

	/// toString

	@Test
	void testToStringNoNull() throws Exception {
		Comic c1 = crearComic("Superman", 10.0);
		carrito.anadirProducto(c1);
		c1.anadirDescuento(crearDescuentoRegalo(9.0, c1));

		String resultado = carrito.toString();
		assertNotNull(resultado);
	}

	@Test
	void testToStringCarritoVacio() {
		String resultado = carrito.toString();
		assertNotNull(resultado);
	}
}