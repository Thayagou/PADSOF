package estadistica.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import estadistica.StatsUsuario;
import exceptions.InvalidArgumentException;
import sistema.Sistema;
import sistema.Tienda;
import usuario.ClienteRegistrado;
import venta.productos.Categoria;

import java.util.*;

class StatsUsuarioTest {
	private Tienda tienda;
	private ClienteRegistrado cliente;
	private StatsUsuario stats;
	private Categoria cat1;
	private Categoria cat2;

	@BeforeEach
	void setUp() throws Exception {
		tienda = new Tienda();
		cliente = new ClienteRegistrado("Usuario", "Pass123", tienda);
		stats = new StatsUsuario(cliente);
		cat1 = new Categoria("Juegos");
		cat2 = new Categoria("Libros");
	}

	@Test
	void testConstructor() {
		StatsUsuario s = new StatsUsuario(cliente);
		assertNotNull(s);
		assertEquals(cliente, s.getCliente());
		assertEquals(0.0, s.getGastoTotal());
		assertEquals(0L, s.getUdsCompradas());
		assertEquals(0L, s.getUdsIntercambiadas());
		assertEquals(0.0, s.getNorma());
		assertTrue(s.getVectorIntereses().isEmpty());
	}

	@Test
	void testActualizarUltimoIntercambio() throws Exception {
		stats.actualizarUltimoIntercambio(2);
		stats.actualizarUltimoIntercambio(3);
		stats.actualizarUltimoIntercambio(5);
		assertEquals(10, stats.getUdsIntercambiadas());
	}
	
	@Test
	void testActualizarUltimaValoracion() throws Exception {
		stats.actualizarUltimaValoracion(20.0);
		stats.actualizarUltimaValoracion(30.0);
		stats.actualizarUltimaValoracion(50.0);
		assertEquals(100.0, stats.getGastoTotal());
	}

	@Test
	void testActualizarVariosMal() {
		assertThrows(InvalidArgumentException.class, () -> stats.actualizarUltimoIntercambio(-1));
		assertThrows(InvalidArgumentException.class, () -> stats.actualizarUltimaValoracion(-1.0));
		assertThrows(InvalidArgumentException.class, () -> stats.actualizarVectorInteresesBusqueda((Categoria) null));

		assertThrows(InvalidArgumentException.class, () -> stats.actualizarCompra(null, 1, 10.0));

		Map<Categoria, Double> vector = Map.of(cat1, 1.0);
		assertThrows(InvalidArgumentException.class, () -> stats.actualizarCompra(vector, -1, 10.0));
		assertThrows(InvalidArgumentException.class, () -> stats.actualizarCompra(vector, 1, -10.0));

	}



	@Test
	void testActualizarVectorInteresesBusqueda() throws Exception {
		stats.actualizarVectorInteresesBusqueda(cat1);
		assertTrue(stats.getVectorIntereses().containsKey(cat1));
		assertEquals(stats.getVectorIntereses().get(cat1), Sistema.getInstancia().getPonderacionBusqueda());
		assertTrue(stats.getNorma() > 0);
		
		stats.actualizarVectorInteresesBusqueda(cat1);
		assertEquals(stats.getVectorIntereses().get(cat1), 2 * Sistema.getInstancia().getPonderacionBusqueda());
		assertEquals(stats.getNorma(), 2 * Sistema.getInstancia().getPonderacionBusqueda());
	}

	@Test
	void testActualizarCompra() throws Exception {
		Map<Categoria, Double> vector = Map.of(cat1, 1.0);
		stats.actualizarCompra(vector, 2, 100.0);
		assertEquals(100.0, stats.getGastoTotal());
		assertEquals(2L, stats.getUdsCompradas());
		assertTrue(stats.getVectorIntereses().containsKey(cat1));
		assertEquals(stats.getVectorIntereses().get(cat1), Sistema.getInstancia().getPonderacionBusqueda());
		assertTrue(stats.getNorma() > 0);
	}

	@Test
	void testActualizarCompra2() throws Exception {
		Map<Categoria, Double> vector = Map.of(cat1, 1.0);
		stats.actualizarCompra(vector, 2, 50.0);
		stats.actualizarCompra(vector, 3, 50.0);
		assertEquals(100.0, stats.getGastoTotal());
		assertEquals(5L, stats.getUdsCompradas());
	}

	@Test
	void testGetCompatibilidad() throws Exception {
		Map<Categoria, Double> vector = Map.of(cat1, 3.0);
		stats.actualizarCompra(vector, 1, 0.0);

		Map<Categoria, Double> vectorExt = Map.of(cat1, 3.0);
		double normaExt = 3.0;
		double compatibilidad = stats.getCompatibilidad(vectorExt, normaExt);
		assertEquals(1, compatibilidad);
	}

	@Test
	void testGetCompatibilidadPerpendiculares() throws Exception {
		Map<Categoria, Double> vector = Map.of(cat1, 1.0);
		stats.actualizarCompra(vector, 1, 0.0);

		Map<Categoria, Double> vectorExt = Map.of(cat2, 1.0);
		double compatibilidad = stats.getCompatibilidad(vectorExt, 1.0);
		assertEquals(0.0, compatibilidad);
	}

	@Test
	void testToStringContieneNombreCliente() {
		assertTrue(stats.toString().contains("Usuario"));
	}
}
