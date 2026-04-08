package estadistica.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import estadistica.StatsMensual;
import estadistica.StatsProducto;
import exceptions.InvalidArgumentException;
import sistema.Reloj;
import sistema.Tienda;
import venta.productos.Categoria;
import venta.productos.Juego;
import venta.productos.Producto;
import venta.productos.TipoJuego;

import java.time.YearMonth;
import java.util.Map;

class StatsProductoTest {
	private static Tienda tienda;
	private static Producto producto;
	private static Producto productoCats;
	private static StatsProducto stats;
	private static StatsProducto statsCats;
	private static Categoria cat1;
	private static Categoria cat2;

	@BeforeEach
	void setUp() throws Exception {
		tienda = new Tienda();
		cat1 = new Categoria("Juegos");
		cat2 = new Categoria("Libros");
		producto = new Juego("Monopoly", "Juego de dinero", 10.0, null, 6, "6+", TipoJuego.TABLERO);
		productoCats = new Juego("Monopoly", "Juego de dinero", 10.0, null, 6, "6+", TipoJuego.TABLERO, cat1, cat2);
		stats = new StatsProducto(producto);
		statsCats = new StatsProducto(productoCats);
	}

	@Test
	void testConstructor() {
		StatsProducto s = new StatsProducto(producto);
		assertNotNull(s);
		assertEquals(producto, s.getProducto());
		assertEquals(0.0, s.getNormaVector());
	}

	@Test
	void testConstructorInicializaVectorConCategorias() {
		Map<Categoria, Double> vector = statsCats.getVectorRecomendacion();
		assertTrue(vector.containsKey(cat1));
		assertTrue(vector.containsKey(cat2));
	}

	@Test
	void testActualizarUltima() throws Exception {
		assertTrue(stats.actualizarUltima(5, 50.0));
		assertTrue(stats.actualizarUltima(10, 100.0));
		assertTrue(stats.actualizarUltima(15, 150.0));
		StatsMensual acum = stats.getEstadisticasEntreMeses(Reloj.mesNow(), Reloj.mesNow());
		
		assertEquals(30, acum.getUnidades());
		assertEquals(300.0, acum.getRecaudacion());
	}

	@Test
	void testActualizarMalLanzaExcepcion() {
		assertThrows(InvalidArgumentException.class, () -> stats.actualizarUltima(-1, 10.0));
		assertThrows(InvalidArgumentException.class, () -> stats.actualizarUltima(1, -10.0));
	}

	@Test
	void testGetEstadisticasEntreMeses() throws Exception {
		assertTrue(stats.actualizarUltima(5, 50.0));
		Reloj.avanzarMes();
		assertTrue(stats.actualizarUltima(10, 100.0));
		Reloj.avanzarMes();
		assertTrue(stats.actualizarUltima(15, 150.0));
		StatsMensual acum = stats.getEstadisticasEntreMeses(Reloj.mesNow().minusMonths(3), Reloj.mesNow());

		assertEquals(30, acum.getUnidades());
		assertEquals(300.0, acum.getRecaudacion());
	}

	@Test
	void testGetEstadisticasEntreMesesFueraDeRango() throws Exception {
		stats.actualizarUltima(4, 40.0);
		
		StatsMensual resultado = stats.getEstadisticasEntreMeses(Reloj.mesNow().minusMonths(10), Reloj.mesNow().minusMonths(5));
		
		assertEquals(0, resultado.getUnidades());
		assertEquals(0.0, resultado.getRecaudacion());
	}

	@Test
	void testActualizarVector() throws Exception {
		//Internamente lo actualiza
		assertFalse(stats.getVectorRecomendacion().containsKey(cat1));
		assertFalse(stats.getVectorRecomendacion().containsKey(cat2));
		producto.anadirCategorias(new Categoria[] {cat1, cat2});
		assertTrue(stats.getVectorRecomendacion().containsKey(cat1));
		assertTrue(stats.getVectorRecomendacion().containsKey(cat2));
	}

	@Test
	void testToStringNombre() {
		assertTrue(stats.toString().contains("Monopoly"));
	}
}