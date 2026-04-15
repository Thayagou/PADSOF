package modelo.venta.productos.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.*;

import modelo.exceptions.*;
import modelo.sistema.CarritoCaducadoObserver;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import modelo.venta.descuentos.*;
import modelo.venta.productos.*;

/**
 * Clase con los tests de los métodos de la clase Producto
 * 
 * @author Juan Ibáñez
 */
class ProductoTest {
	private Producto producto;
	private Categoria cat1;
	private Categoria cat2;

	@BeforeEach
	void setUp() throws Exception {
		cat1 = new Categoria("SuperHeroes");
		cat2 = new Categoria("Infantil");
		producto = new Comic("Spiderman", "Comic de Spiderman", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100,
				"Marvel", cat1);
	}

	@Test
	void testProducto() {
		assertNotNull(producto);
		assertEquals("Spiderman", producto.getNombre());
		assertEquals("Comic de Spiderman", producto.getDescripcion());
		assertEquals(10.0, producto.getPrecio());
		assertFalse(producto.isEliminado());
		assertTrue(producto.perteneceACategoria(cat1));
	}

	@Test
	void testConstructorNombreNull() {
		assertThrows(InvalidArgumentException.class,
				() -> new Comic(null, "desc", 10.0, null, LocalDate.of(2020, 1, 1), "autor", 10, "editorial"));
	}

	@Test
	void testConstructorDescNull() {
		assertThrows(InvalidArgumentException.class,
				() -> new Comic("nombre", null, 10.0, null, LocalDate.of(2020, 1, 1), "autor", 10, "editorial"));
	}

	@Test
	void testConstructorPrecioNegativo() {
		assertThrows(InvalidArgumentException.class,
				() -> new Comic("nombre", "desc", -1.0, null, LocalDate.of(2020, 1, 1), "autor", 10, "editorial"));
	}

	@Test
	void testGetIdUnico() throws Exception {
		Producto otro = new Comic("Batman", "Comic de Batman", 12.0, null, LocalDate.of(2021, 1, 1), "Bob Kane", 80,
				"DC");
		assertNotEquals(producto.getId(), otro.getId());
	}

	@Test
	void testSetNombre() {
		producto.setNombre("SuperMan");
		assertEquals("SuperMan", producto.getNombre());
	}

	@Test
	void testSetDescripcion() {
		producto.setDescripcion("Nueva desc");
		assertEquals("Nueva desc", producto.getDescripcion());
	}

	@Test
	void testSetPrecio() {
		producto.setPrecio(20.0);
		assertEquals(20.0, producto.getPrecio());
	}

	@Test
	void testPuedeAnadirCategoriasCierto() {
		assertTrue(producto.puedeAnadirCategorias(cat2));
	}

	@Test
	void testPuedeAnadirCategoriaFalse() {
		assertFalse(producto.puedeAnadirCategorias(cat1));
	}

	@Test
	void testPuedeAnadirCategoriaNull() {
		assertFalse(producto.puedeAnadirCategorias((Categoria) null));
	}

	@Test
	void testAnadirCategoriasValida() throws Exception {
		producto.anadirCategorias(cat2);
		assertTrue(producto.perteneceACategoria(cat2));
	}

	@Test
	void testAnadirCategoriaNull() {
		assertThrows(InvalidArgumentException.class, () -> producto.anadirCategorias((Categoria[]) null));
	}

	@Test
	void testAnadirCategoriaRepetida() {
		assertThrows(DoubleDiscountException.class, () -> producto.anadirCategorias(cat1));
	}

	@Test
	void testQuitarCategoria() throws Exception {
		producto.quitarCategorias(cat1);
		assertFalse(producto.perteneceACategoria(cat1));
	}

	@Test
	void testAnadirYQuitarCategoriaConDescuento() throws Exception {
		Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION,
				10);
		cat2.anadirDescuento(d);
		producto.anadirCategorias(cat2);
		assertTrue(producto.tieneDescuento());
		assertEquals(producto.getDescuento(), d);
		producto.quitarCategorias(cat2);
		assertFalse(producto.tieneDescuento());
	}

	@Test
	void testAnadirCategoriaConDescuentoRepetido() throws Exception {
		Descuento d1 = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX,
				CondicionDescuento.SIN_CONDICION, 10);
		Descuento d2 = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX,
				CondicionDescuento.SIN_CONDICION, 20);
		cat2.anadirDescuento(d1);
		producto.anadirDescuento(d2);
		assertThrows(DoubleDiscountException.class, () -> producto.anadirCategorias(cat2));
	}

	@Test
	void testSetCategorias() throws Exception {
		producto.setCategorias(cat2);
		assertFalse(producto.perteneceACategoria(cat1));
		assertTrue(producto.perteneceACategoria(cat2));
	}

	@Test
	void testGetCategorias() {
		assertEquals(1, producto.getCategorias().length);
		assertEquals(cat1, producto.getCategorias()[0]);
	}

	@Test
	void testAnadirDescuentoValido() throws Exception {
		Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION,
				10);
		assertTrue(producto.anadirDescuento(d));
		assertTrue(producto.tieneDescuento());
	}

	@Test
	void testAnadirDescuentoDoble() throws Exception {
		Descuento d1 = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX,
				CondicionDescuento.SIN_CONDICION, 10);
		Descuento d2 = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX,
				CondicionDescuento.SIN_CONDICION, 20);
		producto.anadirDescuento(d1);
		assertThrows(DoubleDiscountException.class, () -> producto.anadirDescuento(d2));
	}

	@Test
	void testQuitarDescuento() throws Exception {
		Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION,
				10);
		producto.anadirDescuento(d);
		producto.quitarDescuento();
		assertFalse(producto.tieneDescuento());
		assertNull(producto.getDescuento());
	}

	@Test
	void testTieneDescuentoCaducado() throws Exception {
		Descuento caducado = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MIN,
				CondicionDescuento.SIN_CONDICION, 10);
		producto.anadirDescuento(caducado);
		assertFalse(producto.tieneDescuento());
		assertNull(producto.getDescuento());
	}

	@Test
	void testEliminar() {
		producto.eliminar();
		assertTrue(producto.isEliminado());
	}

	@Test
	void testRestaurar() {
		producto.eliminar();
		producto.restaurar();
		assertFalse(producto.isEliminado());
	}

	@Test
	void testAnadirResenaYPuntuacionMedia() throws Exception {
		CarritoCaducadoObserver tienda = new Tienda();
		ClienteRegistrado u1 = new ClienteRegistrado("Cliente1", "pass", tienda);
		ClienteRegistrado u2 = new ClienteRegistrado("Cliente2", "pass", tienda);
		producto.anadirResena(new Resena(4.0, "Muy bueno", u1));
		producto.anadirResena(new Resena(2.0, "Regular", u2));
		assertEquals(3.0, producto.getPuntuacionMedia());
	}

	@Test
	void testPuntuacionMediaSinResenas() {
		assertEquals(5.0, producto.getPuntuacionMedia());
	}

	@Test
	void testGetResenas() throws Exception {
		CarritoCaducadoObserver tienda = new Tienda();
		ClienteRegistrado u = new ClienteRegistrado("Cliente", "pass", tienda);
		producto.anadirResena(new Resena(5.0, "Genial", u));
		assertEquals(1, producto.getResenas().length);
	}

	@Test
	void testEqualsMismoObjeto() {
		assertEquals(producto, producto);
	}

	@Test
	void testEqualsDistintoProducto() throws Exception {
		Producto otro = new Comic("Batman", "desc", 10.0, null, LocalDate.of(2020, 1, 1), "autor", 10, "editorial");
		assertNotEquals(producto, otro);
	}

	@Test
	void testEqualsNull() {
		assertNotEquals(producto, null);
	}

}
