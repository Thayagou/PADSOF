package modelo.sistema.tests;

import org.junit.jupiter.api.*;

import modelo.sistema.Caducable;
import modelo.sistema.GestorCaducidad;
import modelo.sistema.Reloj;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

class GestorCaducidadTest {

	private GestorCaducidad gestor;

	@BeforeEach
	void resetSingleton() throws Exception {
		Field f = GestorCaducidad.class.getDeclaredField("instancia");
		f.setAccessible(true);
		f.set(null, null);
		gestor = GestorCaducidad.getInstancia();
	}

	@AfterEach
	void detener() {
		gestor.detener();
	}

	// helper CaducableYaCaducado
	static class CaducableYaCaducado implements Caducable {
		boolean caducado = false;

		@Override
		public LocalDateTime getFechaCaducidad() {
			return Reloj.now().minusSeconds(1);
		}

		@Override
		public void caducar() {
			caducado = true;
		}
	}

	// helper CaducableNoCaducado
	static class CaducableNoCaducado implements Caducable {
		boolean caducado = false;

		@Override
		public LocalDateTime getFechaCaducidad() {
			return Reloj.now().plusDays(1);
		}

		@Override
		public void caducar() {
			caducado = true;
		}
	}

	@Test
	void testGetInstanciaNoEsNula() {
		assertNotNull(gestor);
	}

	@Test
	void testGetInstanciaEsSingleton() {
		assertSame(gestor, GestorCaducidad.getInstancia());
	}

	@Test
	void testRegistrarYDesregistrar() {
		CaducableNoCaducado c = new CaducableNoCaducado();
		assertDoesNotThrow(() -> gestor.registrar(c));
		assertDoesNotThrow(() -> gestor.desregistrar(c));
	}

	@Test
	void testComprobarCaducaElementoCaducado() throws Exception {
		CaducableYaCaducado c = new CaducableYaCaducado();
		gestor.registrar(c);
		invocarComprobar();
		assertTrue(c.caducado);
	}

	@Test
	void testComprobarNoCaducaElementoVigente() throws Exception {
		CaducableNoCaducado c = new CaducableNoCaducado();
		gestor.registrar(c);
		invocarComprobar();
		assertFalse(c.caducado);
	}

	@Test
	void testComprobarColaVacia() throws Exception {
		assertDoesNotThrow(() -> invocarComprobar());
	}

	@Test
	void testComprobarCaducaMultiplesElementos() throws Exception {
		CaducableYaCaducado c1 = new CaducableYaCaducado();
		CaducableYaCaducado c2 = new CaducableYaCaducado();
		gestor.registrar(c1);
		gestor.registrar(c2);
		invocarComprobar();
		assertTrue(c1.caducado);
		assertTrue(c2.caducado);
	}

	@Test
	void testComprobarSoloCaducaLosExpirados() throws Exception {
		CaducableYaCaducado caducado = new CaducableYaCaducado();
		CaducableNoCaducado vigente = new CaducableNoCaducado();
		gestor.registrar(caducado);
		gestor.registrar(vigente);
		invocarComprobar();
		assertTrue(caducado.caducado);
		assertFalse(vigente.caducado);
	}

	@Test
	void testDesregistrarElementoNoRegistrado() {
		CaducableNoCaducado c = new CaducableNoCaducado();
		assertDoesNotThrow(() -> gestor.desregistrar(c));
	}

	@Test
	void testIniciarYDetener() throws InterruptedException {
		assertDoesNotThrow(() -> gestor.iniciar(1, TimeUnit.SECONDS));
		assertDoesNotThrow(() -> gestor.detener());
	}

	@Test
	void testIniciarEjecutaComprobar() throws InterruptedException {
		CaducableYaCaducado c = new CaducableYaCaducado();
		gestor.registrar(c);
		gestor.iniciar(100, TimeUnit.MILLISECONDS);
		Thread.sleep(300);
		assertTrue(c.caducado);
	}

	// helper, invoca el método privado comprobar(), lo hace accesible (public) y lo llama sobre la instancia gestor
	private void invocarComprobar() throws Exception {
		var metodo = GestorCaducidad.class.getDeclaredMethod("comprobar");
		metodo.setAccessible(true);
		metodo.invoke(gestor);
	}
}