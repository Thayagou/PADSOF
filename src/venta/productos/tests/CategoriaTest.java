package venta.productos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.*;

import exceptions.*;
import venta.descuentos.*;
import venta.productos.*;

/**
 * Clase con los tests de los métodos de la clase Categoria
 * 
 * @author Juan Ibáñez
 */
class CategoriaTest {

	private Categoria categoria;
	private Producto producto1;
	private Producto producto2;

	@BeforeEach
	void setUp() throws Exception {
		categoria = new Categoria("SuperHeroes");
		producto1 = new Juego("Monopoly", "Juego de dinero", 10.0, null, 6, "6+", TipoJuego.TABLERO);
		producto2 = new Figura("Batman", "Figura de Batman", 12.0, null, "20x12 cm", "DC", "Plástico");
	}

	// --- Constructor ---

	@Test
	void testConstructorValido() {
		assertNotNull(categoria);
		assertEquals("SuperHeroes", categoria.getNombre());
		assertFalse(categoria.isEliminada());
		assertFalse(categoria.tieneDescuento());
	}

	@Test
	void testConstructorNombreNull() {
		assertThrows(InvalidArgumentException.class, () -> new Categoria(null));
	}

	// --- setNombre ---

	@Test
	void testSetNombreValido() throws Exception {
		categoria.setNombre("Villanos");
		assertEquals("Villanos", categoria.getNombre());
	}

	@Test
	void testSetNombreNull() {
		assertThrows(InvalidArgumentException.class, () -> categoria.setNombre(null));
	}

	// --- Eliminar / Restaurar ---

	@Test
	void testEliminar() {
		categoria.eliminar();
		assertTrue(categoria.isEliminada());
	}

	@Test
	void testRestaurar() {
		categoria.eliminar();
		categoria.restaurar();
		assertFalse(categoria.isEliminada());
	}

	// --- Productos ---

	@Test
	void testAnadirProducto() throws Exception {
		producto1.anadirCategorias(categoria);
		assertEquals(1, categoria.getProductos().length);
		assertEquals(producto1, categoria.getProductos()[0]);
	}

	@Test
	void testQuitarProducto() throws Exception {
		producto1.anadirCategorias(categoria);
		producto1.quitarCategorias(categoria);
		assertEquals(0, categoria.getProductos().length);
	}

	@Test
	void testGetProductosVacia() {
		assertEquals(0, categoria.getProductos().length);
	}

	@Test
	void testGetProductosVariosProductos() throws Exception {
		producto1.anadirCategorias(categoria);
		producto2.anadirCategorias(categoria);
		assertEquals(2, categoria.getProductos().length);
	}

	// --- Descuentos ---

	@Test
	void testAnadirDescuentoValido() throws Exception {
		Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION,
				10);
		assertTrue(categoria.anadirDescuento(d));
		assertTrue(categoria.tieneDescuento());
		assertEquals(d, categoria.getDescuento());
	}

	@Test
	void testAnadirDescuentoNull() {
		assertThrows(InvalidArgumentException.class, () -> categoria.anadirDescuento(null));
	}

	@Test
	void testAnadirDescuentoDoble() throws Exception {
		Descuento d1 = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX,
				CondicionDescuento.SIN_CONDICION, 10);
		Descuento d2 = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX,
				CondicionDescuento.SIN_CONDICION, 20);
		categoria.anadirDescuento(d1);
		assertThrows(DoubleDiscountException.class, () -> categoria.anadirDescuento(d2));
	}

	@Test
	void testAnadirDescuentoConProductoQueYaTieneDescuento() throws Exception {
		// Si un producto de la categoría ya tiene descuento, no se puede añadir a la
		// categoría
		Descuento dProducto = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX,
				CondicionDescuento.SIN_CONDICION, 10);
		Descuento dCategoria = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX,
				CondicionDescuento.SIN_CONDICION, 20);
		producto1.anadirCategorias(categoria);
		producto1.anadirDescuento(dProducto);
		assertThrows(DoubleDiscountException.class, () -> categoria.anadirDescuento(dCategoria));
	}

	@Test
	void testAnadirDescuentoSePropagaAProductosExistentes() throws Exception {
		// Productos que ya estaban en la categoría reciben el descuento
		Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION,
				10);
		producto1.anadirCategorias(categoria);
		producto2.anadirCategorias(categoria);
		categoria.anadirDescuento(d);
		assertTrue(producto1.tieneDescuento());
		assertTrue(producto2.tieneDescuento());
	}

	@Test
	void testProductoNuevoHeredaDescuentoDeCategoria() throws Exception {
		// Un producto que se añade a una categoría con descuento lo hereda
		Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION,
				10);
		categoria.anadirDescuento(d);
		producto1.anadirCategorias(categoria);
		assertTrue(producto1.tieneDescuento());
		assertEquals(d, producto1.getDescuento());
	}

	@Test
	void testQuitarDescuento() throws Exception {
		Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION,
				10);
		categoria.anadirDescuento(d);
		categoria.quitarDescuento();
		assertFalse(categoria.tieneDescuento());
		assertNull(categoria.getDescuento());
	}

	@Test
	void testQuitarDescuentoSePropagaAProductos() throws Exception {
		// Al quitar el descuento de la categoría, sus productos también lo pierden
		Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION,
				10);
		producto1.anadirCategorias(categoria);
		producto2.anadirCategorias(categoria);
		categoria.anadirDescuento(d);
		categoria.quitarDescuento();
		assertFalse(producto1.tieneDescuento());
		assertFalse(producto2.tieneDescuento());
	}

	@Test
	void testTieneDescuentoCaducado() throws Exception {
		Descuento caducado = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MIN,
				CondicionDescuento.SIN_CONDICION, 10);
		categoria.anadirDescuento(caducado);
		assertFalse(categoria.tieneDescuento());
		assertNull(categoria.getDescuento());
	}

	@Test
	void testDescuentoCaducadoSePropagaAProductos() throws Exception {
		// Al caducar el descuento, los productos también deben perderlo
		Descuento caducado = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MIN,
				CondicionDescuento.SIN_CONDICION, 10);
		producto1.anadirCategorias(categoria);
		categoria.anadirDescuento(caducado);
		assertFalse(categoria.tieneDescuento());
		assertFalse(producto1.tieneDescuento());
	}
}
