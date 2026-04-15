package modelo.sistema.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.sistema.AsignadorId;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class AsignadorIdTest {

	@BeforeEach
	void resetSingleton() throws Exception {
		Field f = AsignadorId.class.getDeclaredField("instancia");
		f.setAccessible(true);
		f.set(null, null);
	}

	@Test
	void testGetInstanciaNoEsNula() {
		assertNotNull(AsignadorId.getInstancia());
	}

	@Test
	void testGetInstanciaEsSingleton() {
		AsignadorId a = AsignadorId.getInstancia();
		AsignadorId b = AsignadorId.getInstancia();
		assertSame(a, b);
	}

	@Test
	void testPrimerIdEsUno() {
		assertEquals(1L, AsignadorId.getInstancia().siguienteId());
	}

	@Test
	void testIdSeIncrementa() {
		AsignadorId asignador = AsignadorId.getInstancia();
		long primero = asignador.siguienteId();
		long segundo = asignador.siguienteId();
		assertEquals(primero + 1, segundo);
	}

	@Test
	void testVariasLlamadasIncrementan() {
		AsignadorId asignador = AsignadorId.getInstancia();
		for (int i = 1; i <= 5; i++) {
			assertEquals((long) i, asignador.siguienteId());
		}
	}
}