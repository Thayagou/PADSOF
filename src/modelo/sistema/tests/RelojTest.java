package modelo.sistema.tests;

import org.junit.jupiter.api.*;

import modelo.sistema.Reloj;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class RelojTest {

	@BeforeEach
	void resetReloj() {
		Reloj.volverATiempoReal();
	}

	@Test
	void testNowNoEsNull() {
		assertNotNull(Reloj.now());
	}

	@Test
	void testMesNowNoEsNull() {
		assertNotNull(Reloj.mesNow());
	}

	@Test
	void testLocalDateNowNoEsNull() {
		assertNotNull(Reloj.localDateNow());
	}

	@Test
	void testIsTiempoRealInicialmente() {
		assertTrue(Reloj.isTiempoReal());
	}

	@Test
	void testIsFijoInicialmenteFalse() {
		assertFalse(Reloj.isFijo());
	}

	@Test
	void testAvanzarMesAvanzaElTiempo() {
		LocalDateTime antes = Reloj.now();
		Reloj.avanzarMes();
		LocalDateTime despues = Reloj.now();
		assertTrue(despues.isAfter(antes));
	}

	@Test
	void testAvanzarMesDesactivaTiempoReal() {
		Reloj.avanzarMes();
		assertFalse(Reloj.isTiempoReal());
	}

	@Test
	void testPausarRelojFijaElTiempo() throws InterruptedException {
		Reloj.pausarReloj();
		LocalDateTime t1 = Reloj.now();
		Thread.sleep(50);
		LocalDateTime t2 = Reloj.now();
		assertEquals(t1, t2);
	}

	@Test
	void testPausarRelojSetFijo() {
		Reloj.pausarReloj();
		assertTrue(Reloj.isFijo());
	}

	@Test
	void testPausarRelojDobleVez() {
		Reloj.pausarReloj();
		Reloj.pausarReloj();
		assertTrue(Reloj.isFijo());
	}

	@Test
	void testContinuarRelojSinPausar() {
		Reloj.continuarReloj();
		assertFalse(Reloj.isFijo());
	}

	@Test
	void testContinuarRelojSetFijo() {
		Reloj.pausarReloj();
		Reloj.continuarReloj();
		assertFalse(Reloj.isFijo());
	}

	@Test
	void testContinuarRelojAvanzaElTiempo() throws Exception {
		Reloj.pausarReloj();
		LocalDateTime pausado = Reloj.now();
		Thread.sleep(50);
		Reloj.continuarReloj();
		Thread.sleep(50);
		LocalDateTime despues = Reloj.now();
		assertTrue(despues.isBefore(pausado));
	}

	@Test
	void testVolverATiempoRealDesdeAvanzado() {
		Reloj.avanzarMes();
		Reloj.volverATiempoReal();
		assertTrue(Reloj.isTiempoReal());
		assertFalse(Reloj.isFijo());
	}

	@Test
	void testVolverATiempoRealSiYaEsTiempoReal() {
		Reloj.volverATiempoReal();
		assertTrue(Reloj.isTiempoReal());
	}

	@Test
	void testVolverATiempoRealDesdePausado() {
		Reloj.pausarReloj();
		Reloj.volverATiempoReal();
		assertTrue(Reloj.isTiempoReal());
		assertFalse(Reloj.isFijo());
	}

	@Test
	void testNowEsCoherenteConTiempoReal() {
		LocalDateTime antes = LocalDateTime.now();
		LocalDateTime reloj = Reloj.now();
		LocalDateTime despues = LocalDateTime.now();
		assertFalse(reloj.isBefore(antes.minusSeconds(1)));
		assertFalse(reloj.isAfter(despues.plusSeconds(1)));
	}

	@Test
	void testMesNowEsCoherenteConTiempoReal() {
		assertEquals(YearMonth.now(), Reloj.mesNow());
	}

	@Test
	void testLocalDateNowEsCoherenteConTiempoReal() {
		assertEquals(LocalDate.now(), Reloj.localDateNow());
	}
}
