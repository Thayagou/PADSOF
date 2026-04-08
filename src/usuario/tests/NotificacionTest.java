package usuario.tests;

import org.junit.jupiter.api.*;
import sistema.Reloj;
import usuario.Notificacion;
import usuario.TipoNotificacion;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionTest {

	@BeforeEach
	void setUp() {
		Reloj.pausarReloj();
	}

	@AfterEach
	void tearDown() {
		Reloj.volverATiempoReal();
	}

	@Test
	void constructorInicializaCamposCorrectamente() {
		LocalDate ahora = Reloj.localDateNow();
		Notificacion n = new Notificacion("mensaje", TipoNotificacion.PEDIDO);

		assertEquals(ahora, n.getFecha());
		assertEquals("mensaje", n.getContenido());
		assertEquals(TipoNotificacion.PEDIDO, n.getTipo());
		assertFalse(n.isLeida());
		assertFalse(n.isBorrada());
	}

	@Test
	void marcarLeidaCambiaEstadoALeida() {
		Notificacion n = new Notificacion("msg", TipoNotificacion.PEDIDO);
		n.marcarLeida();
		assertTrue(n.isLeida());
	}

	@Test
	void borrarCambiaEstadoABorrada() {
		Notificacion n = new Notificacion("msg", TipoNotificacion.PEDIDO);
		n.borrar();
		assertTrue(n.isBorrada());
	}

	@Test
	void toStringContienetipoContenidoYFecha() {
		Notificacion n = new Notificacion("msg", TipoNotificacion.PEDIDO);
		String result = n.toString();
		assertTrue(result.contains("PEDIDO"));
		assertTrue(result.contains("msg"));
		assertTrue(result.contains(Reloj.localDateNow().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
	}
}