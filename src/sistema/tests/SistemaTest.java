package sistema.tests;

import exceptions.InvalidArgumentException;
import sistema.ParametroSistema;
import sistema.Sistema;

import org.junit.jupiter.api.*;
import usuario.Gestor;
import java.lang.reflect.Field;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

class SistemaTest {

	private Sistema sistema;
	private Gestor gestor;

	@BeforeEach
	void reset() throws Exception {
		Field f = Sistema.class.getDeclaredField("instancia");
		f.setAccessible(true);
		f.set(null, null);
		sistema = Sistema.getInstancia();
		gestor = new Gestor("gestor", "123");
	}

	// singleton

	@Test
	void testGetInstanciaNoEsNula() {
		assertNotNull(sistema);
	}

	@Test
	void testGetInstanciaEsSingleton() {
		assertSame(sistema, Sistema.getInstancia());
	}

	// constructor, valores por defecto

	@Test
	void testTiempoCaducaCarritoPorDefecto() {
		assertEquals(Duration.ofDays(3), sistema.getTiempoCaducaCarrito());
	}

	@Test
	void testTiempoCaducaOfertaPorDefecto() {
		assertEquals(Duration.ofDays(3), sistema.getTiempoCaducaOferta());
	}

	@Test
	void testPrecioValoracionPorDefecto() {
		assertEquals(20.0, sistema.getPrecioValoracion());
	}

	@Test
	void testPonderacionesPorDefecto() {
		assertEquals(1.0, sistema.getPonderacionCategoria());
		assertEquals(1.0, sistema.getPonderacionUdsCompra());
		assertEquals(1.0, sistema.getPonderacionPrecioCompra());
		assertEquals(1.0, sistema.getPonderacionValoracionesProducto());
		assertEquals(1.0, sistema.getPonderacionProductoRecomendado());
		assertEquals(1.0, sistema.getPonderacionBusqueda());
	}

	@Test
	void testNumProductosRecomendadosPorDefecto() {
		assertEquals(10, sistema.getNumProductosRecomendados());
	}

	@Test
	void testParametrosPorDefectoContieneTodos() {
		assertEquals(ParametroSistema.values().length, sistema.getParametros().size());
	}

	// setters

	@Test
	void testSetTiempoCaducaCarrito() {
		assertTrue(sistema.setTiempoCaducaCarrito(Duration.ofDays(5)));
		assertEquals(Duration.ofDays(5), sistema.getTiempoCaducaCarrito());
	}

	@Test
	void testSetTiempoCaducaOferta() {
		assertTrue(sistema.setTiempoCaducaOferta(Duration.ofDays(7)));
		assertEquals(Duration.ofDays(7), sistema.getTiempoCaducaOferta());
	}

	@Test
	void testSetPrecioValoracionValido() throws Exception {
		sistema.setPrecioValoracion(gestor, 50.0);
		assertEquals(50.0, sistema.getPrecioValoracion());
	}

	@Test
	void testSetPrecioValoracionNegativo() throws Exception {
		assertThrows(InvalidArgumentException.class, () -> sistema.setPrecioValoracion(gestor, -1.0));
	}

	@Test
	void testSetPrecioValoracionCero() throws Exception {
		sistema.setPrecioValoracion(gestor, 0.0);
		assertEquals(0.0, sistema.getPrecioValoracion());
	}

	// ponderaciones válidas

	@Test
	void testSetPonderacionCategoriaValida() throws InvalidArgumentException {
		sistema.setPonderacionCategoria(gestor, 2.0);
		assertEquals(2.0, sistema.getPonderacionCategoria());
	}

	@Test
	void testSetPonderacionUdsCompraValida() throws InvalidArgumentException {
		sistema.setPonderacionUdsCompra(gestor, 3.0);
		assertEquals(3.0, sistema.getPonderacionUdsCompra());
	}

	@Test
	void testSetPonderacionPrecioCompraValida() throws InvalidArgumentException {
		sistema.setPonderacionPrecioCompra(gestor, 4.0);
		assertEquals(4.0, sistema.getPonderacionPrecioCompra());
	}

	@Test
	void testSetPonderacionValoracionesProductoValida() throws InvalidArgumentException {
		sistema.setPonderacionValoracionesProducto(gestor, 5.0);
		assertEquals(5.0, sistema.getPonderacionValoracionesProducto());
	}

	@Test
	void testSetPonderacionProductoRecomendadoValida() throws InvalidArgumentException {
		sistema.setPonderacionProductoRecomendado(gestor, 6.0);
		assertEquals(6.0, sistema.getPonderacionProductoRecomendado());
	}

	@Test
	void testSetPonderacionBusquedaValida() throws InvalidArgumentException {
		sistema.setPonderacionBusqueda(gestor, 7.0);
		assertEquals(7.0, sistema.getPonderacionBusqueda());
	}

	@Test
	void testSetNumProdsRecomendadosValido() throws InvalidArgumentException {
		sistema.setNumProdsRecomendados(gestor, 20);
		assertEquals(20, sistema.getNumProductosRecomendados());
	}

	// ponderaciones negativas

	@Test
	void testSetPonderacionCategoriaNegativa() {
		assertThrows(InvalidArgumentException.class, () -> sistema.setPonderacionCategoria(gestor, -1.0));
	}

	@Test
	void testSetPonderacionUdsCompraNegativa() {
		assertThrows(InvalidArgumentException.class, () -> sistema.setPonderacionUdsCompra(gestor, -1.0));
	}

	@Test
	void testSetPonderacionPrecioCompraNegativa() {
		assertThrows(InvalidArgumentException.class, () -> sistema.setPonderacionPrecioCompra(gestor, -1.0));
	}

	@Test
	void testSetPonderacionValoracionesProductoNegativa() {
		assertThrows(InvalidArgumentException.class, () -> sistema.setPonderacionValoracionesProducto(gestor, -1.0));
	}

	@Test
	void testSetPonderacionProductoRecomendadoNegativa() {
		assertThrows(InvalidArgumentException.class, () -> sistema.setPonderacionProductoRecomendado(gestor, -1.0));
	}

	@Test
	void testSetPonderacionBusquedaNegativa() {
		assertThrows(InvalidArgumentException.class, () -> sistema.setPonderacionBusqueda(gestor, -1.0));
	}

	@Test
	void testSetNumProdsRecomendadosNegativo() {
		assertThrows(InvalidArgumentException.class, () -> sistema.setNumProdsRecomendados(gestor, -1));
	}

	// gestionarParametro

	@Test
	void testGestionarParametroActivarYaActivo() {
		assertFalse(sistema.gestionarParametro(ParametroSistema.CATEGORIA, true));
	}

	@Test
	void testGestionarParametroDesactivar() {
		assertTrue(sistema.gestionarParametro(ParametroSistema.CATEGORIA, false));
		assertFalse(sistema.getParametros().contains(ParametroSistema.CATEGORIA));
	}

	@Test
	void testGestionarParametroActivarDesactivado() {
		sistema.gestionarParametro(ParametroSistema.CATEGORIA, false);
		assertTrue(sistema.gestionarParametro(ParametroSistema.CATEGORIA, true));
		assertTrue(sistema.getParametros().contains(ParametroSistema.CATEGORIA));
	}

	@Test
	void testGestionarParametroNoDesactivaElUltimo() {
		for (ParametroSistema p : ParametroSistema.values()) {
			sistema.gestionarParametro(p, false);
		}
		assertEquals(1, sistema.getParametros().size());
	}

	// pagoTarjeta

	@Test
	void testPagoTarjetaRetornaTrue() {
		assertTrue(sistema.pagoTarjeta());
	}
}