package venta.productos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.*;
import venta.productos.caracteristicas.*;
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

	// --- setCaracteristicas Comic ---

	@Test
	void testSetCaracteristicasComicValido() throws Exception {
		Comic comic = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		comic.setCaracteristicas(new CaracteristicasComic(LocalDate.of(2021, 6, 15), "Bob Kane", 200, "DC"));
		assertTrue(comic.getCaracteristicas().contains("Bob Kane"));
		assertTrue(comic.getCaracteristicas().contains("DC"));
		assertTrue(comic.getCaracteristicas().contains("200"));
	}

	@Test
	void testSetCaracteristicasComicTipoIncorrecto() throws Exception {
		Comic comic = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		assertThrows(InvalidArgumentException.class,
				() -> comic.setCaracteristicas(new CaracteristicasFigura("20x12 cm", "DC", "Plástico")));
	}

	@Test
	void testSetCaracteristicasComicFechaNull() throws Exception {
		Comic comic = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		assertThrows(InvalidArgumentException.class,
				() -> comic.setCaracteristicas(new CaracteristicasComic(null, "Bob Kane", 200, "DC")));
	}

	@Test
	void testSetCaracteristicasComicAutorNull() throws Exception {
		Comic comic = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		assertThrows(InvalidArgumentException.class,
				() -> comic.setCaracteristicas(new CaracteristicasComic(LocalDate.of(2021, 6, 15), null, 200, "DC")));
	}

	@Test
	void testSetCaracteristicasComicPaginasNegativas() throws Exception {
		Comic comic = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		assertThrows(InvalidArgumentException.class, () -> comic
				.setCaracteristicas(new CaracteristicasComic(LocalDate.of(2021, 6, 15), "Bob Kane", -1, "DC")));
	}

	@Test
	void testSetCaracteristicasComicEditorialNull() throws Exception {
		Comic comic = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		assertThrows(InvalidArgumentException.class, () -> comic
				.setCaracteristicas(new CaracteristicasComic(LocalDate.of(2021, 6, 15), "Bob Kane", 200, null)));
	}

	// --- setCaracteristicas Figura ---

	@Test
	void testSetCaracteristicasFiguraValido() throws Exception {
		Figura figura = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		figura.setCaracteristicas(new CaracteristicasFigura("30x20 cm", "Marvel", "Metal"));
		assertTrue(figura.getCaracteristicas().contains("Marvel"));
		assertTrue(figura.getCaracteristicas().contains("Metal"));
		assertTrue(figura.getCaracteristicas().contains("30x20 cm"));
	}

	@Test
	void testSetCaracteristicasFiguraTipoIncorrecto() throws Exception {
		Figura figura = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		assertThrows(InvalidArgumentException.class,
				() -> figura.setCaracteristicas(new CaracteristicasJuego(4, "8+", TipoJuego.TABLERO)));
	}

	@Test
	void testSetCaracteristicasFiguraDimensionesNull() throws Exception {
		Figura figura = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		assertThrows(InvalidArgumentException.class,
				() -> figura.setCaracteristicas(new CaracteristicasFigura(null, "Marvel", "Metal")));
	}

	@Test
	void testSetCaracteristicasFiguraMarcaNull() throws Exception {
		Figura figura = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		assertThrows(InvalidArgumentException.class,
				() -> figura.setCaracteristicas(new CaracteristicasFigura("30x20 cm", null, "Metal")));
	}

	@Test
	void testSetCaracteristicasFiguraMaterialNull() throws Exception {
		Figura figura = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		assertThrows(InvalidArgumentException.class,
				() -> figura.setCaracteristicas(new CaracteristicasFigura("30x20 cm", "Marvel", null)));
	}

	// --- setCaracteristicas Juego ---

	@Test
	void testSetCaracteristicasJuegoValido() throws Exception {
		Juego juego = new Juego("Monopoly", "desc", 25.0, null, 6, "8+", TipoJuego.TABLERO);
		juego.setCaracteristicas(new CaracteristicasJuego(4, "12+", TipoJuego.CARTAS));
		assertTrue(juego.getCaracteristicas().contains("CARTAS"));
		assertTrue(juego.getCaracteristicas().contains("12+"));
		assertTrue(juego.getCaracteristicas().contains("4"));
	}

	@Test
	void testSetCaracteristicasJuegoTipoIncorrecto() throws Exception {
		Juego juego = new Juego("Monopoly", "desc", 25.0, null, 6, "8+", TipoJuego.TABLERO);
		assertThrows(InvalidArgumentException.class, () -> juego
				.setCaracteristicas(new CaracteristicasComic(LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel")));
	}

	@Test
	void testSetCaracteristicasJuegoNumJugadoresNegativo() throws Exception {
		Juego juego = new Juego("Monopoly", "desc", 25.0, null, 6, "8+", TipoJuego.TABLERO);
		assertThrows(InvalidArgumentException.class,
				() -> juego.setCaracteristicas(new CaracteristicasJuego(-1, "12+", TipoJuego.CARTAS)));
	}

	@Test
	void testSetCaracteristicasJuegoRangoNull() throws Exception {
		Juego juego = new Juego("Monopoly", "desc", 25.0, null, 6, "8+", TipoJuego.TABLERO);
		assertThrows(InvalidArgumentException.class,
				() -> juego.setCaracteristicas(new CaracteristicasJuego(4, null, TipoJuego.CARTAS)));
	}

	@Test
	void testSetCaracteristicasJuegoTipoNull() throws Exception {
		Juego juego = new Juego("Monopoly", "desc", 25.0, null, 6, "8+", TipoJuego.TABLERO);
		assertThrows(InvalidArgumentException.class,
				() -> juego.setCaracteristicas(new CaracteristicasJuego(4, "12+", null)));
	}

	// --- setCaracteristicas Pack ---

	@Test
	void testSetCaracteristicasPackValido() throws Exception {
		Producto p1 = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		Producto p2 = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		Producto p3 = new Juego("Monopoly", "desc", 25.0, null, 6, "8+", TipoJuego.TABLERO);
		Stock[] stocksIniciales = { new Stock(p1, 1), new Stock(p2, 1) };
		Pack pack = new Pack(stocksIniciales, "Pack Hero", "desc", 20.0, null);
		Stock[] stocksNuevos = { new Stock(p2, 1), new Stock(p3, 1) };
		pack.setCaracteristicas(new CaracteristicasPack(stocksNuevos));
		assertTrue(pack.getCaracteristicas().contains("Batman"));
		assertTrue(pack.getCaracteristicas().contains("Monopoly"));
		assertFalse(pack.getCaracteristicas().contains("Spiderman"));
	}

	@Test
	void testSetCaracteristicasPackTipoIncorrecto() throws Exception {
		Producto p1 = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		Producto p2 = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		Stock[] stocks = { new Stock(p1, 1), new Stock(p2, 1) };
		Pack pack = new Pack(stocks, "Pack Hero", "desc", 20.0, null);
		assertThrows(InvalidArgumentException.class,
				() -> pack.setCaracteristicas(new CaracteristicasFigura("20x12 cm", "DC", "Plástico")));
	}

	@Test
	void testSetCaracteristicasPackDemasiadoPequeno() throws Exception {
		Producto p1 = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		Producto p2 = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		Stock[] stocksIniciales = { new Stock(p1, 1), new Stock(p2, 1) };
		Pack pack = new Pack(stocksIniciales, "Pack Hero", "desc", 20.0, null);
		Stock[] stocksNuevos = { new Stock(p1, 1) };
		assertThrows(InvalidArgumentException.class,
				() -> pack.setCaracteristicas(new CaracteristicasPack(stocksNuevos)));
	}

	@Test
	void testSetCaracteristicasPackNull() throws Exception {
		Producto p1 = new Comic("Spiderman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
		Producto p2 = new Figura("Batman", "desc", 12.0, null, "20x12 cm", "DC", "Plástico");
		Stock[] stocks = { new Stock(p1, 1), new Stock(p2, 1) };
		Pack pack = new Pack(stocks, "Pack Hero", "desc", 20.0, null);
		assertThrows(InvalidArgumentException.class, () -> pack.setCaracteristicas(null));
	}
}