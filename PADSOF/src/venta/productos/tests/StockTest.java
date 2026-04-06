package venta.productos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import venta.productos.*;
import exceptions.*;

/**
 * Clase con los tests de los métodos de la clase Stock
 * 
 * @author Juan Ibáñez
 */
class StockTest {

	private Producto producto;
	private Stock stock;

	@BeforeEach
	void setUp() throws Exception {
		producto = new Juego("Monopoly", "Juego de mesa", 25.0, null, 6, "8+", TipoJuego.TABLERO);
		stock = new Stock(producto, 5);
	}

	// --- Constructor ---

	@Test
	void testConstructorValido() {
		assertNotNull(stock);
		assertEquals(producto, stock.getProducto());
		assertEquals(5, stock.getUdsEnStock());
	}

	@Test
	void testConstructorProductoNull() {
		assertThrows(InvalidArgumentException.class, () -> new Stock(null, 5));
	}

	@Test
	void testConstructorUnidadesNegativas() {
		assertThrows(InvalidArgumentException.class, () -> new Stock(producto, -1));
	}

	@Test
	void testConstructorCeroUnidades() {
		assertDoesNotThrow(() -> new Stock(producto, 0));
	}

	// --- setUdsEnStock ---

	@Test
	void testSetUdsEnStockValido() throws Exception {
		stock.setUdsEnStock(10);
		assertEquals(10, stock.getUdsEnStock());
	}

	@Test
	void testSetUdsEnStockCero() throws Exception {
		stock.setUdsEnStock(0);
		assertEquals(0, stock.getUdsEnStock());
	}

	@Test
	void testSetUdsEnStockNegativo() {
		assertThrows(InvalidArgumentException.class, () -> stock.setUdsEnStock(-1));
	}

	// --- reducirStock (1 unidad) ---

	@Test
	void testReducirStockUnaUnidad() {
		stock.reducirStock();
		assertEquals(4, stock.getUdsEnStock());
	}

	@Test
	void testReducirStockConCeroUnidades() throws Exception {
		stock.setUdsEnStock(0);
		stock.reducirStock();
		assertEquals(0, stock.getUdsEnStock());
	}

	// --- reducirStock (n unidades) ---

	@Test
	void testReducirStockNUnidades() throws Exception {
		stock.reducirStock(3);
		assertEquals(2, stock.getUdsEnStock());
	}

	@Test
	void testReducirStockMasDeLoQueHay() throws Exception {
		stock.reducirStock(10);
		assertEquals(0, stock.getUdsEnStock());
	}

	@Test
	void testReducirStockExactamenteLasQueHay() throws Exception {
		stock.reducirStock(5);
		assertEquals(0, stock.getUdsEnStock());
	}

	@Test
	void testReducirStockNUnidadesNegativas() {
		assertThrows(InvalidArgumentException.class, () -> stock.reducirStock(-1));
	}

	// --- incrementarStock (1 unidad) ---

	@Test
	void testIncrementarStockUnaUnidad() {
		stock.incrementarStock();
		assertEquals(6, stock.getUdsEnStock());
	}

	// --- incrementarStock (n unidades) ---

	@Test
	void testIncrementarStockNUnidades() throws Exception {
		stock.incrementarStock(3);
		assertEquals(8, stock.getUdsEnStock());
	}

	@Test
	void testIncrementarStockCeroUnidades() throws Exception {
		stock.incrementarStock(0);
		assertEquals(5, stock.getUdsEnStock());
	}

	@Test
	void testIncrementarStockNUnidadesNegativas() {
		assertThrows(InvalidArgumentException.class, () -> stock.incrementarStock(-1));
	}

	// --- disponible() ---

	@Test
	void testDisponibleConUnidades() {
		assertTrue(stock.disponible());
	}

	@Test
	void testDisponibleSinUnidades() throws Exception {
		stock.setUdsEnStock(0);
		assertFalse(stock.disponible());
	}

	// --- disponible(n) ---

	@Test
	void testDisponibleNUnidadesSuficientes() {
		assertTrue(stock.disponible(3));
	}

	@Test
	void testDisponibleNUnidadesInsuficientes() {
		assertFalse(stock.disponible(10));
	}

	@Test
	void testDisponibleExactamenteLasQueHay() {
		assertTrue(stock.disponible(5));
	}

	@Test
	void testDisponibleCero() {
		assertTrue(stock.disponible(0));
	}

	// --- equals ---

	@Test
	void testEqualsMismoObjeto() {
		assertEquals(stock, stock);
	}

	@Test
	void testEqualsDistintoStock() throws Exception {
		Stock otro = new Stock(producto, 5);
		assertNotEquals(stock, otro);
	}

	@Test
	void testEqualsNull() {
		assertNotEquals(stock, null);
	}
}