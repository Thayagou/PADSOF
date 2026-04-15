package modelo.estadistica.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.estadistica.StatsMensual;
import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Reloj;

import java.time.YearMonth;

class StatsMensualTest {
	private StatsMensual stats;

	@BeforeEach
	void setUp() {
		stats = new StatsMensual();
	}

	@Test
	void testConstructor() {
		StatsMensual s = new StatsMensual();
		assertNotNull(s);
		assertEquals(0, s.getUnidades());
		assertEquals(0.0, s.getRecaudacion());
		assertEquals(s.getMes(), Reloj.mesNow());
	}

	@Test
	void testConstructorConMes() {
		YearMonth mes = YearMonth.of(2024, 3);
		StatsMensual s = new StatsMensual(mes);
		assertEquals(mes, s.getMes());
		assertEquals(0, s.getUnidades());
		assertEquals(0.0, s.getRecaudacion());
	}

	@Test
	void testIncrementar() throws Exception {
		assertTrue(stats.incrementar(5, 100.0));
		assertEquals(5, stats.getUnidades());
		assertEquals(100.0, stats.getRecaudacion());
	}

	@Test
	void testIncrementarAcumula() throws Exception {
		stats.incrementar(5, 75.0);
		stats.incrementar(10, 25.0);
		assertEquals(15, stats.getUnidades());
		assertEquals(100.0, stats.getRecaudacion());
	}

	@Test
	void testIncrementarMal() {
		assertThrows(InvalidArgumentException.class, () -> stats.incrementar(-1, 10.0));
		assertThrows(InvalidArgumentException.class, () -> stats.incrementar(1, -10.0));
	}

	@Test
	void testGetMes() {
		YearMonth mes = YearMonth.of(2023, 6);
		StatsMensual s = new StatsMensual(mes);
		assertEquals(mes, s.getMes());
	}

	@Test
	void testSetMes() {
		YearMonth mes = YearMonth.of(2025, 1);
		stats.setMes(mes);
		assertEquals(mes, stats.getMes());
	}

	@Test
	void testGetUnidades() throws Exception {
		stats.incrementar(7, 0.0);
		assertEquals(7, stats.getUnidades());
	}

	@Test
	void testGetRecaudacion() throws Exception {
		stats.incrementar(0, 99.99);
		assertEquals(99.99, stats.getRecaudacion());
	}

	@Test
	void testCompareTo() {
		StatsMensual marzo = new StatsMensual(YearMonth.of(2026, 3));
		StatsMensual abril = new StatsMensual(YearMonth.of(2026, 4));
		assertTrue(marzo.compareTo(abril) < 0);
		assertEquals(0, marzo.compareTo(marzo));
	}

	@Test
	void testToStringContieneMes() {
		StatsMensual s = new StatsMensual(YearMonth.of(2024, 4));
		assertTrue(s.toString().contains("2024"));
	}
}