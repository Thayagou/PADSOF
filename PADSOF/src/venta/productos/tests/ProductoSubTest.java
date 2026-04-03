package venta.productos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.*;

import exceptions.*;
import venta.productos.*;

/**
 * Clase con los tests de los métodos de las subclases de Producto
 * 
 * @author Juan Ibáñez
 */
class ProductoSubTest {

	private Categoria cat1;

	@BeforeEach
	void setUp() throws Exception {
		cat1 = new Categoria("SuperHeroes");
	}

	// --- Comic ---

	@Test
	void testComicValido() throws Exception {
		Comic comic = new Comic("Spiderman", "Comic de Spiderman", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee",
				100, "Marvel", cat1);
		assertNotNull(comic);
		assertEquals("Spiderman", comic.getNombre());
		assertTrue(comic.perteneceACategoria(cat1));
		assertTrue(comic.getCaracteristicas().contains("Stan Lee"));
		assertTrue(comic.getCaracteristicas().contains("Marvel"));
	}

	@Test
	void testComicFechaNull() {
		assertThrows(InvalidArgumentException.class,
				() -> new Comic("Spiderman", "desc", 10.0, null, null, "Stan Lee", 100, "Marvel"));
	}

	@Test
	void testComicAutorNull() {
		assertThrows(InvalidArgumentException.class,
				() -> new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), null, 100, "Marvel"));
	}

	@Test
	void testComicPaginasNegativas() {
		assertThrows(InvalidArgumentException.class,
				() -> new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", -1, "Marvel"));
	}

	@Test
	void testComicEditorialNull() {
		assertThrows(InvalidArgumentException.class,
				() -> new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, null));
	}

	// --- Figura ---

	@Test
	void testFiguraValida() throws Exception {
		Figura figura = new Figura("Batman", "Figura de Batman", 12.0, null, "20x12 cm", "DC", "Plástico", cat1);
		assertNotNull(figura);
		assertEquals("Batman", figura.getNombre());
		assertTrue(figura.perteneceACategoria(cat1));
		assertTrue(figura.getCaracteristicas().contains("DC"));
		assertTrue(figura.getCaracteristicas().contains("Plástico"));
	}

	@Test
	void testFiguraDimensionesNull() {
		assertThrows(InvalidArgumentException.class,
				() -> new Figura("Batman", "desc", 12.0, null, null, "DC", "Plástico"));
	}

	@Test
	void testFiguraMarcaNull() {
		assertThrows(InvalidArgumentException.class,
				() -> new Figura("Batman", "desc", 12.0, null, "20x12 cm", null, "Plástico"));
	}

	@Test
	void testFiguraMaterialNull() {
		assertThrows(InvalidArgumentException.class,
				() -> new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", null));
	}

	// --- Juego ---

	@Test
	void testJuegoValido() throws Exception {
		Juego juego = new Juego("Monopoly", "Juego de mesa", 25.0, null, 6, "8+", TipoJuego.TABLERO, cat1);
		assertNotNull(juego);
		assertEquals("Monopoly", juego.getNombre());
		assertTrue(juego.perteneceACategoria(cat1));
		assertTrue(juego.getCaracteristicas().contains("TABLERO"));
		assertTrue(juego.getCaracteristicas().contains("8+"));
	}

	@Test
	void testJuegoNumJugadoresNegativo() {
		assertThrows(InvalidArgumentException.class,
				() -> new Juego("Monopoly", "desc", 25.0, null, -1, "8+", TipoJuego.TABLERO));
	}

	@Test
	void testJuegoRangoNull() {
		assertThrows(InvalidArgumentException.class,
				() -> new Juego("Monopoly", "desc", 25.0, null, 6, null, TipoJuego.TABLERO));
	}

	@Test
	void testJuegoTipoNull() {
		assertThrows(InvalidArgumentException.class, () -> new Juego("Monopoly", "desc", 25.0, null, 6, "8+", null));
	}

	// --- Pack ---

	@Test
	void testPackValido() throws Exception {
		Producto p1 = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		Producto p2 = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		Stock[] stocks = { new Stock(p1, 1), new Stock(p2, 1) };
		Pack pack = new Pack(stocks, "Pack Hero", "Pack de superheroes", 20.0, null, cat1);
		assertNotNull(pack);
		assertEquals("Pack Hero", pack.getNombre());
		assertEquals(2, pack.getProductos().length);
		assertTrue(pack.getCaracteristicas().contains("Spiderman"));
		assertTrue(pack.getCaracteristicas().contains("Batman"));
	}

	@Test
	void testPackStocksNull() {
		assertThrows(InvalidArgumentException.class, () -> new Pack(null, "Pack", "desc", 20.0, null));
	}

	@Test
	void testPackStockNullEnArray() throws Exception {
		Producto p1 = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		Stock[] stocks = { new Stock(p1, 1), null };
		assertThrows(InvalidArgumentException.class, () -> new Pack(stocks, "Pack", "desc", 20.0, null));
	}

	@Test
	void testPackDemasiadoPequeno() throws Exception {
		Producto p1 = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		Stock[] stocks = { new Stock(p1, 1) };
		assertThrows(InvalidArgumentException.class, () -> new Pack(stocks, "Pack", "desc", 20.0, null));
	}

	@Test
	void testPackVacio() {
		assertThrows(InvalidArgumentException.class, () -> new Pack(new Stock[0], "Pack", "desc", 20.0, null));
	}

	@Test
	void testPackUnProductoConVariasUnidades() throws Exception {
		// Un pack con un solo producto pero con 2 o más unidades es válido
		Producto p1 = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		Stock[] stocks = { new Stock(p1, 2) };
		assertDoesNotThrow(() -> new Pack(stocks, "Pack Doble", "desc", 15.0, null));
	}
}