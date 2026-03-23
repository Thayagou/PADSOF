package venta.productos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import venta.productos.*;

/**
 * Clase con los tests de los métodos de la clase StockExterno
 * 
 * @author Juan Ibáñez
 */
class StockExternoTest {

    private Producto producto;
    private StockExterno stockExterno;

    @BeforeEach
    void setUp() throws Exception {
        producto = new Juego("Monopoly", "Juego de mesa", 25.0, null, 6, "8+", TipoJuego.TABLERO);
        stockExterno = new StockExterno(producto, 5, 20.0);
    }

    // --- Constructor con precio ---

    @Test
    void testConstructorConPrecioValido() {
        assertNotNull(stockExterno);
        assertEquals(producto, stockExterno.getProducto());
        assertEquals(5, stockExterno.getUdsEnStock());
        assertEquals(20.0, stockExterno.getPrecioUnitarioFinal());
    }

    @Test
    void testConstructorConPrecioNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new StockExterno(producto, 5, -1.0));
    }

    @Test
    void testConstructorConPrecioCero() {
        assertDoesNotThrow(() -> new StockExterno(producto, 5, 0.0));
    }

    @Test
    void testConstructorProductoNull() {
        assertThrows(IllegalArgumentException.class, () -> new StockExterno(null, 5, 20.0));
    }

    @Test
    void testConstructorUnidadesNegativas() {
        assertThrows(IllegalArgumentException.class, () -> new StockExterno(producto, -1, 20.0));
    }

    // --- Constructor sin precio ---

    @Test
    void testConstructorSinPrecioUsaPrecioProducto() {
        StockExterno se = new StockExterno(producto, 3);
        assertEquals(producto.getPrecio(), se.getPrecioUnitarioFinal());
    }

    @Test
    void testConstructorSinPrecioProductoNull() {
        assertThrows(IllegalArgumentException.class, () -> new StockExterno(null, 5));
    }

    @Test
    void testConstructorSinPrecioUnidadesNegativas() {
        assertThrows(IllegalArgumentException.class, () -> new StockExterno(producto, -1));
    }

    // --- setPrecioUnitarioFinal ---

    @Test
    void testSetPrecioUnitarioFinalValido() {
        stockExterno.setPrecioUnitarioFinal(30.0);
        assertEquals(30.0, stockExterno.getPrecioUnitarioFinal());
    }

    @Test
    void testSetPrecioUnitarioFinalCero() {
        stockExterno.setPrecioUnitarioFinal(0.0);
        assertEquals(0.0, stockExterno.getPrecioUnitarioFinal());
    }

    @Test
    void testSetPrecioUnitarioFinalNegativo() {
        assertThrows(IllegalArgumentException.class, () -> stockExterno.setPrecioUnitarioFinal(-1.0));
    }

    // --- getPrecioTotal ---

    @Test
    void testGetPrecioTotal() {
        assertEquals(100.0, stockExterno.getPrecioTotal());
    }

    @Test
    void testGetPrecioTotalCeroUnidades() {
        StockExterno se = new StockExterno(producto, 0, 20.0);
        assertEquals(0.0, se.getPrecioTotal());
    }

    @Test
    void testGetPrecioTotalTrasModificarPrecio() {
        stockExterno.setPrecioUnitarioFinal(10.0);
        assertEquals(50.0, stockExterno.getPrecioTotal());
    }

    @Test
    void testGetPrecioTotalTrasModificarUnidades() {
        stockExterno.setUdsEnStock(2);
        assertEquals(40.0, stockExterno.getPrecioTotal());
    }

    // --- equals (heredado de Stock) ---

    @Test
    void testEqualsMismoObjeto() {
        assertEquals(stockExterno, stockExterno);
    }

    @Test
    void testEqualsDistintoStockExterno() {
        StockExterno otro = new StockExterno(producto, 5, 20.0);
        assertNotEquals(stockExterno, otro);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(stockExterno, null);
    }
}