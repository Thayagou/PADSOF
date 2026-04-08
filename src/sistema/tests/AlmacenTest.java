package sistema.tests;

import org.junit.jupiter.api.*;

import estadistica.Historial;
import venta.descuentos.*;
import venta.productos.*;
import venta.productos.caracteristicas.*;
import exceptions.*;
import sistema.*;
import wallapop.*;
import usuario.ClienteRegistrado;
import usuario.Empleado;
import usuario.Permiso;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AlmacenTest {
	private static Empleado empleado;
	private Almacen almacen;
	private Categoria cat;
	private Tienda tienda;

	@BeforeEach
	void setUp() throws Exception {
		empleado = new Empleado("empleado", "pass", Permiso.values());
		resetSingletons();
		tienda = new Tienda();
		almacen = tienda.getAlmacen();
		cat = new Categoria("aventuras");
		almacen.anadirCategoria(empleado, "aventuras");
	}

	@AfterEach
	void tearDown() throws Exception {
		resetSingletons();
		Reloj.volverATiempoReal();
	}

	// helpers
	
	private void resetSingletons() throws Exception {
		Field f = Sistema.class.getDeclaredField("instancia");
		f.setAccessible(true);
		f.set(null, null);

		Field a = AsignadorId.class.getDeclaredField("instancia");
		a.setAccessible(true);
		a.set(null, null);
	}

	// anadirProducto

	@Test
	void anadirComicNuevo() throws Exception {
		almacen.anadirComic(empleado, 5, "Comic1", "desc", 10.0, null, LocalDate.now(), "Autor", 100, "Editorial", cat);
		assertEquals(1, almacen.getInventario().length);
	}

	@Test
	void anadirProductoNombreDuplicado() throws Exception {
		almacen.anadirComic(empleado, 5, "Comic1", "desc", 10.0, null, LocalDate.now(), "Autor", 100, "Editorial", cat);
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirComic(empleado, 3, "Comic1", "desc2", 5.0, null,
				LocalDate.now(), "Autor2", 50, "Editorial2", cat));
	}

	@Test
	void anadirJuegoNuevo() throws Exception {
		almacen.anadirJuego(empleado, 3, "Juego1", "desc", 20.0, null, 4, "8+", TipoJuego.TABLERO, cat);
		assertEquals(1, almacen.getInventario().length);
	}

	@Test
	void anadirFiguraNueva() throws Exception {
		almacen.anadirFigura(empleado, 2, "Figura1", "desc", 15.0, null, "10x10", "Marca", "Plastico", cat);
		assertEquals(1, almacen.getInventario().length);
	}

	@Test
	void anadirPackNuevo() throws Exception {
		Comic c1 = new Comic("C1", "d", 5.0, null, LocalDate.now(), "A", 10, "E", cat);
		Comic c2 = new Comic("C2", "d", 5.0, null, LocalDate.now(), "A", 10, "E", cat);
		Stock[] stocks = { new Stock(c1, 1), new Stock(c2, 1) };
		almacen.anadirPack(empleado, 1, "Pack1", "desc", 8.0, null, stocks, cat);
		assertEquals(1, almacen.getInventario().length);
	}

	// getUnidades

	@Test
	void getUnidadesProductoEnStock() throws Exception {
		almacen.anadirComic(empleado, 5, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertEquals(5, almacen.getUnidades(p));
	}

	@Test
	void getUnidadesProductoNoEnStock() throws Exception {
		Comic p = new Comic("Fuera", "d", 5.0, null, LocalDate.now(), "A", 10, "E");
		assertEquals(-1, almacen.getUnidades(p));
	}

	@Test
	void getUnidadesProductoNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.getUnidades(null));
	}

	// getStock

	@Test
	void getStockProductoExistente() throws Exception {
		almacen.anadirComic(empleado, 5, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertNotNull(almacen.getStock(p));
	}

	@Test
	void getStockProductoNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.getStock((Producto) null));
	}

	@Test
	void getStockNombreExistente() throws Exception {
		almacen.anadirComic(empleado, 5, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		assertNotNull(almacen.getStock("C1"));
	}

	@Test
	void getStockNombreNoExistente() {
		assertThrows(InvalidArgumentException.class, () -> almacen.getStock("NoExiste"));
	}

	// eliminarProducto

	@Test
	void eliminarProductoExistente() throws Exception {
		almacen.anadirComic(empleado, 5, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertTrue(almacen.eliminarProducto(empleado, p));
		assertEquals(0, almacen.getInventario().length);
	}

	@Test
	void eliminarProductoNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.eliminarProducto(empleado, null));
	}

	// modificarProducto

	@Test
	void modificarProductoValido() throws Exception {
		almacen.anadirComic(empleado, 5, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		almacen.modificarProducto(empleado, p, 3, "C1Mod", "nuevaDesc", 12.0, null, new CaracteristicasComic(LocalDate.now(), "B", 20, "Ed2"), cat);
		assertNotNull(almacen.getStock("C1Mod"));
		assertEquals(3, almacen.getStock("C1Mod").getUdsEnStock());
	}

	@Test
	void modificarProductoNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		assertThrows(InvalidArgumentException.class, () -> almacen.modificarProducto(empleado, null, 1, "X", "d", 5.0, null, new CaracteristicasComic(LocalDate.now(), "A", 5, "E"), cat));
	}

	@Test
	void modificarProductoNombreNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.modificarProducto(empleado, p, 1, null, "d", 5.0, null, new CaracteristicasComic(LocalDate.now(), "A", 5, "E"), cat));
	}

	@Test
	void modificarProductoUnidadesNegativas() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.modificarProducto(empleado, p, -1, "C1", "d", 5.0, null, new CaracteristicasComic(LocalDate.now(), "A", 5, "E"), cat));
	}

	@Test
	void modificarProductoPrecioNegativo() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.modificarProducto(empleado, p, 1, "C1", "d", -1.0, null, new CaracteristicasComic(LocalDate.now(), "A", 5, "E"), cat));
	}

	@Test
	void modificarProductoNoEnAlmacen() throws Exception {
		Comic externo = new Comic("Ext", "d", 5.0, null, LocalDate.now(), "A", 10, "E");
		assertThrows(InvalidArgumentException.class, () -> almacen.modificarProducto(empleado, externo, 1, "Ext", "d", 5.0, null, new CaracteristicasComic(LocalDate.now(), "A", 5, "E"), cat));
	}

	@Test
	void modificarProductoCaracteristicasIncompatibles() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.modificarProducto(empleado, p, 1, "C1", "d", 5.0, null, new CaracteristicasJuego(4, "8+", TipoJuego.DADOS), cat));
	}

	// anadirProductosDeFichero

	@Test
	void anadirProductosDeFicheroNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirProductosDeFichero(empleado, null));
	}

	@Test
	void anadirProductosDeFicheroNoExistente() {
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirProductosDeFichero(empleado, "noexiste.csv"));
	}
	
	@Test
	void anadirProductosDeFicheroCategoriasNoExistentes() throws Exception {
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirProductosDeFichero(empleado, "productosTest.csv"));
	}
	
	@Test
	void anadirProductoDeFicheroComicMalFormateado() throws Exception {
		almacen.anadirCategoria(empleado, "Aventuras");
		almacen.anadirCategoria(empleado, "Estrategia");
		almacen.anadirCategoria(empleado, "Accion");
		almacen.anadirCategoria(empleado, "Cine");
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirProductosDeFichero(empleado, "productosTestErrorComic.csv"));
	}
	
	@Test
	void anadirProductoDeFicheroJuegoMalFormateado() throws Exception {
		almacen.anadirCategoria(empleado, "Aventuras");
		almacen.anadirCategoria(empleado, "Estrategia");
		almacen.anadirCategoria(empleado, "Accion");
		almacen.anadirCategoria(empleado, "Cine");
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirProductosDeFichero(empleado, "productosTestErrorJuego.csv"));
	}
	
	@Test
	void anadirProductoDeFicheroFiguraMalFormateado() throws Exception {
		almacen.anadirCategoria(empleado, "Aventuras");
		almacen.anadirCategoria(empleado, "Estrategia");
		almacen.anadirCategoria(empleado, "Accion");
		almacen.anadirCategoria(empleado, "Cine");
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirProductosDeFichero(empleado, "productosTestErrorFigura.csv"));
	}
	
	@Test
	void anadirProductosDeFicheroTipoNoExistente() throws Exception {
		almacen.anadirCategoria(empleado, "Aventuras");
		almacen.anadirCategoria(empleado, "Estrategia");
		almacen.anadirCategoria(empleado, "Accion");
		almacen.anadirCategoria(empleado, "Cine");
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirProductosDeFichero(empleado, "productosTestErrorTipo.csv"));
	}
	
	@Test
	void anadirProductosDeFicheroExistente() throws Exception {
		almacen.anadirCategoria(empleado, "Aventuras");
		almacen.anadirCategoria(empleado, "Estrategia");
		almacen.anadirCategoria(empleado, "Accion");
		almacen.anadirCategoria(empleado, "Cine");
		assertTrue(almacen.anadirProductosDeFichero(empleado, "productosTest.csv"));
	}

	// categorias

	@Test
	void anadirCategoriaNueva() throws Exception {
		assertTrue(almacen.anadirCategoria(empleado, "nueva"));
	}

	@Test
	void anadirCategoriaDuplicada() throws Exception {
		assertFalse(almacen.anadirCategoria(empleado, "aventuras"));
	}

	@Test
	void anadirCategoriaNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirCategoria(empleado, null));
	}

	@Test
	void getCategoriaExistente() throws Exception {
		assertNotNull(almacen.getCategoria("aventuras"));
	}

	@Test
	void getCategoriaNoExistente() {
		assertThrows(InvalidArgumentException.class, () -> almacen.getCategoria("noExiste"));
	}

	@Test
	void getCategoriasDevuelveTodasLasCategorias() throws Exception {
		almacen.anadirCategoria(empleado, "infantil");
		assertEquals(2, almacen.getCategorias().length);
	}

	@Test
	void eliminarCategoriaExistente() throws Exception {
		assertTrue(almacen.eliminarCategoria(empleado, cat));
	}

	@Test
	void eliminarCategoriaNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.eliminarCategoria(empleado, null));
	}

	@Test
	void modificarCategoriaValida() throws Exception {
		assertTrue(almacen.modificarCategoria(empleado, cat, "nuevoNombre"));
		assertNotNull(almacen.getCategoria("nuevoNombre"));
	}

	@Test
	void modificarCategoriaNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.modificarCategoria(empleado, null, "X"));
	}

	@Test
	void modificarCategoriaNombreNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.modificarCategoria(empleado, cat, null));
	}

	@Test
	void modificarCategoriaNoEnAlmacen() throws Exception {
		Categoria externa = new Categoria("Externa");
		assertFalse(almacen.modificarCategoria(empleado, externa, "nuevoNombre"));
	}

	@Test
	void modificarCategoriaNuevoNombreYaExiste() throws Exception {
		almacen.anadirCategoria(empleado, "infantil");
		assertFalse(almacen.modificarCategoria(empleado, cat, "infantil"));
	}

	// getProductosCoincidentes

	@Test
	void getProductosCoincidentesCadenaVacia() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		almacen.anadirComic(empleado, 1, "C2", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		assertEquals(2, almacen.getProductosCoincidentes("").length);
	}

	@Test
	void getProductosCoincidentesNombreParcial() throws Exception {
		almacen.anadirComic(empleado, 1, "ComicA", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		almacen.anadirJuego(empleado, 1, "JuegoB", "d", 5.0, null, 2, "6+", TipoJuego.CARTAS, cat);
		assertEquals(1, almacen.getProductosCoincidentes("Comic").length);
	}

	@Test
	void getProductosCoincidentesProductoEliminado() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		almacen.eliminarProducto(empleado, p);
		assertEquals(0, almacen.getProductosCoincidentes("C1").length);
	}

	// getCategoriasCoincidentes

	@Test
	void getCategoriasCoincidentesCadenaVacia() throws Exception {
		almacen.anadirCategoria(empleado, "infantil");
		assertEquals(2, almacen.getCategoriasCoincidentes("").length);
	}

	@Test
	void getCategoriasCoincidentesNombreParcial() throws Exception {
		almacen.anadirCategoria(empleado, "accion");
		assertEquals(1, almacen.getCategoriasCoincidentes("avent").length);
	}

	@Test
	void getCategoriasCoincidentesCategoriaEliminada() throws Exception {
		almacen.eliminarCategoria(empleado, cat);
		assertEquals(0, almacen.getCategoriasCoincidentes("aventuras").length);
	}

	// anadirProductoACategoria / quitarProductoDeCategoria

	@Test
	void anadirProductoACategoriaValido() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E");
		Producto p = almacen.getInventario()[0].getProducto();
		assertTrue(almacen.anadirProductoACategoria(empleado, p, cat));
	}

	@Test
	void anadirProductoACategoriaProductoNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirProductoACategoria(empleado, null, cat));
	}

	@Test
	void anadirProductoACategoriaNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirProductoACategoria(empleado, p, null));
	}

	@Test
	void quitarProductoDeCategoriaValido() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertTrue(almacen.quitarProductoDeCategoria(empleado, p, cat));
	}

	@Test
	void quitarProductoDeCategoriaProductoNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.quitarProductoDeCategoria(empleado, null, cat));
	}

	@Test
	void quitarProductoDeCategoriaNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.quitarProductoDeCategoria(empleado, p, null));
	}

	// descuentos

	@Test
	void anadirDescuentoDineroValido() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		LocalDateTime inicio = Reloj.now().minusHours(1);
		LocalDateTime fin = Reloj.now().plusDays(1);
		assertTrue(almacen.anadirDescuentoDinero(p, 0, inicio, fin, CondicionDescuento.SIN_CONDICION, 2.0));
	}

	@Test
	void anadirDescuentoDineroYaTieneDescuento() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		LocalDateTime inicio = Reloj.now().minusHours(1);
		LocalDateTime fin = Reloj.now().plusDays(1);
		almacen.anadirDescuentoDinero(p, 0, inicio, fin, CondicionDescuento.SIN_CONDICION, 2.0);
		assertThrows(DoubleDiscountException.class, () -> almacen.anadirDescuentoDinero(p, 0, inicio, fin, CondicionDescuento.SIN_CONDICION, 1.0));
	}

	@Test
	void anadirDescuentoDineroDescontableNull() {
		LocalDateTime inicio = Reloj.now().minusHours(1);
		LocalDateTime fin = Reloj.now().plusDays(1);
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoDinero(null, 0, inicio, fin, CondicionDescuento.SIN_CONDICION, 2.0));
	}

	@Test
	void anadirDescuentoDineroInicioNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoDinero(p, 0, null, Reloj.now().plusDays(1), CondicionDescuento.SIN_CONDICION, 2.0));
	}

	@Test
	void anadirDescuentoDineroFinNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoDinero(p, 0, Reloj.now().minusHours(1), null, CondicionDescuento.SIN_CONDICION, 2.0));
	}

	@Test
	void anadirDescuentoDineroCondicionNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoDinero(p, 0, Reloj.now().minusHours(1), Reloj.now().plusDays(1), null, 2.0));
	}

	@Test
	void anadirDescuentoPorcentajeValido() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		LocalDateTime inicio = Reloj.now().minusHours(1);
		LocalDateTime fin = Reloj.now().plusDays(1);
		assertTrue(almacen.anadirDescuentoPorcentaje(p, 0, inicio, fin, CondicionDescuento.SIN_CONDICION, 10.0));
	}

	@Test
	void anadirDescuentoPorcentajeDescontableNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoPorcentaje(null, 0, Reloj.now().minusHours(1), Reloj.now().plusDays(1), CondicionDescuento.SIN_CONDICION, 10.0));
	}

	@Test
	void anadirDescuentoPorcentajeInicioNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoPorcentaje(p, 0, null, Reloj.now().plusDays(1), CondicionDescuento.SIN_CONDICION, 10.0));
	}

	@Test
	void anadirDescuentoPorcentajeFinNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoPorcentaje(p, 0, Reloj.now().minusHours(1), null, CondicionDescuento.SIN_CONDICION, 10.0));
	}

	@Test
	void anadirDescuentoPorcentajeCondicionNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoPorcentaje(p, 0, Reloj.now().minusHours(1), Reloj.now().plusDays(1), null, 10.0));
	}

	@Test
	void anadirDescuentoRegaloValido() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		almacen.anadirComic(empleado, 1, "Regalo", "d", 5.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getStock("C1").getProducto();
		Producto regalo = almacen.getStock("Regalo").getProducto();
		LocalDateTime inicio = Reloj.now().minusHours(1);
		LocalDateTime fin = Reloj.now().plusDays(1);
		assertTrue(almacen.anadirDescuentoRegalo(p, 0, inicio, fin, CondicionDescuento.SIN_CONDICION, regalo));
	}

	@Test
	void anadirDescuentoRegaloDescontableNull() throws Exception {
		Comic regalo = new Comic("R", "d", 5.0, null, LocalDate.now(), "A", 10, "E");
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoRegalo(null, 0, Reloj.now().minusHours(1), Reloj.now().plusDays(1), CondicionDescuento.SIN_CONDICION, regalo));
	}

	@Test
	void anadirDescuentoRegaloInicioNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		Comic regalo = new Comic("R", "d", 5.0, null, LocalDate.now(), "A", 10, "E");
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoRegalo(p, 0, null, Reloj.now().plusDays(1), CondicionDescuento.SIN_CONDICION, regalo));
	}

	@Test
	void anadirDescuentoRegaloFinNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		Comic regalo = new Comic("R", "d", 5.0, null, LocalDate.now(), "A", 10, "E");
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoRegalo(p, 0, Reloj.now().minusHours(1), null, CondicionDescuento.SIN_CONDICION, regalo));
	}

	@Test
	void anadirDescuentoRegaloCondicionNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		Comic regalo = new Comic("R", "d", 5.0, null, LocalDate.now(), "A", 10, "E");
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirDescuentoRegalo(p, 0, Reloj.now().minusHours(1), Reloj.now().plusDays(1), null, regalo));
	}

	@Test
	void eliminarDescuentosCaducados() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		LocalDateTime inicio = Reloj.now().minusDays(2);
		LocalDateTime fin = Reloj.now().minusDays(1);
		almacen.anadirDescuentoDinero(p, 0, inicio, fin, CondicionDescuento.SIN_CONDICION, 1.0);
		assertTrue(almacen.eliminarDescuentosCaducados());
	}

	// articulos segunda mano

	@Test
	void anadirArticuloSegundaManoValido() throws Exception {
		ClienteRegistrado cliente = new ClienteRegistrado("cli", "pass", tienda);
		Cartera cartera = cliente.getCartera();
		ArticuloSegundaMano art = new ArticuloSegundaMano("Art1", "desc", cartera, "nada");
		assertTrue(almacen.anadirArticuloSegundaMano(art));
	}

	@Test
	void anadirArticuloSegundaManoNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.anadirArticuloSegundaMano(null));
	}

	@Test
	void eliminarArticuloSegundaManoExistente() throws Exception {
		ClienteRegistrado cliente = new ClienteRegistrado("cli", "pass", tienda);
		Cartera cartera = cliente.getCartera();
		ArticuloSegundaMano art = new ArticuloSegundaMano("Art1", "desc", cartera, "nada");
		almacen.anadirArticuloSegundaMano(art);
		assertTrue(almacen.eliminarArticuloSegundaMano(art));
	}

	@Test
	void eliminarArticuloSegundaManoNull() {
		assertThrows(InvalidArgumentException.class, () -> almacen.eliminarArticuloSegundaMano(null));
	}

	@Test
	void getArticulosParaCliente() throws Exception {
		ClienteRegistrado cli1 = new ClienteRegistrado("cli1", "pass", tienda);
		ClienteRegistrado cli2 = new ClienteRegistrado("cli2", "pass", tienda);
		ArticuloSegundaMano art1 = new ArticuloSegundaMano("Art1", "d", cli1.getCartera(), "x");
		ArticuloSegundaMano art2 = new ArticuloSegundaMano("Art2", "d", cli2.getCartera(), "x");
		art1.disponibilizar();
		art2.disponibilizar();
		almacen.anadirArticuloSegundaMano(art1);
		almacen.anadirArticuloSegundaMano(art2);
		ArticuloSegundaMano[] visibles = almacen.getArticulosParaCliente(cli1);
		assertEquals(1, visibles.length);
		assertEquals("Art2", visibles[0].getNombre());
	}

	// getProductosPorFiltros

	@Test
	void getProductosPorFiltrosConCliente() throws Exception {
		ClienteRegistrado cli1 = new ClienteRegistrado("cli1", "pass", tienda);
		Historial historial = new Historial();
		historial.guardarUsuario(cli1);
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Categoria[] cats = { cat };
		Producto[] resultado = almacen.getProductosPorFiltros(cli1, cats, 0, 20, 0);
		assertEquals(1, resultado.length);
	}
	
	@Test
	void getProductosPorFiltrosSinCliente() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Categoria[] cats = { cat };
		Producto[] resultado = almacen.getProductosPorFiltros(cats, 0, 20, 0);
		assertEquals(1, resultado.length);
	}

	@Test
	void getProductosPorFiltrosPrecioMaxMenorQueMin() throws Exception {
		Categoria[] cats = { cat };
		assertThrows(InvalidArgumentException.class, () -> almacen.getProductosPorFiltros(cats, 10, 5, 0));
	}

	@Test
	void getProductosPorFiltrosCategoriasNull() {
		assertThrows(InvalidArgumentException.class,
				() -> almacen.getProductosPorFiltros((Categoria[]) null, 0, 100, 0));
	}

	@Test
	void getProductosPorFiltrosPrecioMinNegativo() {
		assertThrows(InvalidArgumentException.class,
				() -> almacen.getProductosPorFiltros(new Categoria[] { cat }, -1, 100, 0));
	}

	@Test
	void getProductosPorFiltrosPrecioMaxNegativo() {
		assertThrows(InvalidArgumentException.class,
				() -> almacen.getProductosPorFiltros(new Categoria[] { cat }, 0, -1, 0));
	}

	@Test
	void getProductosPorFiltrosEstrellasMinNegativas() {
		assertThrows(InvalidArgumentException.class,
				() -> almacen.getProductosPorFiltros(new Categoria[] { cat }, 0, 100, -1));
	}

	@Test
	void getProductosPorFiltrosEstrellasMinMayorCinco() {
		assertThrows(InvalidArgumentException.class,
				() -> almacen.getProductosPorFiltros(new Categoria[] { cat }, 0, 100, 6));
	}

	@Test
	void getProductosPorFiltrosCategoriaNull() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Categoria[] cats = { null, cat };
		assertEquals(1, almacen.getProductosPorFiltros(cats, 0, 20, 0).length);
	}

	@Test
	void getProductosPorFiltrosProductoEliminado() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto p = almacen.getInventario()[0].getProducto();
		almacen.eliminarProducto(empleado, p);
		Categoria[] cats = { cat };
		assertEquals(0, almacen.getProductosPorFiltros(cats, 0, 20, 0).length);
	}
	
	// getListaRecomendacion
	
	@Test
	void getListaRecomendacion() throws Exception {
		almacen.anadirComic(empleado, 1, "C1", "d", 10.0, null, LocalDate.now(), "A", 10, "E", cat);
		Producto c1 = almacen.getStock("C1").getProducto();
		almacen.anadirCategoria(empleado, "Cat2");
		Categoria cat2 = almacen.getCategoria("Cat2");
		almacen.anadirComic(empleado, 1, "C2", "d", 5.0, null, LocalDate.now(), "A", 10, "E", cat2);
		Producto c2 = almacen.getStock("C2").getProducto();

		ClienteRegistrado cli1 = new ClienteRegistrado("cli1", "pass", tienda);
		
		Historial historial = new Historial();
		historial.guardarUsuario(cli1);
		historial.guardarProducto(c1);
		historial.guardarProducto(c2);
		
		Categoria[] cats = { cat2 };
		almacen.getProductosPorFiltros(cli1, cats, 0, 20, 0);

		Producto[] resultado = almacen.getListaRecomendacion(cli1);
		/*for (Producto p: resultado) {
			if (p == null) break;;
			System.out.println("Vector cliente: " + cli1.getVectorRecomendacion() + " Norma: " + cli1.getNormaVectorRecomendaciones());
			System.out.println("Vector prod: " + p.getVectorRecomendacion() + " Norma: " + p.getNormaVector());
			System.out.println(p + " \nProd escalar: " + cli1.getCompatibilidad(p.getVectorRecomendacion(), p.getNormaVector()));
		}*/
		assertEquals("C2", resultado[0].getNombre());
	}
}