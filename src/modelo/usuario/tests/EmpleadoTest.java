/**
 * Este paquete contiene los tests de las clases incluídas en el paquete de usuario
 */
package modelo.usuario.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.exceptions.InvalidArgumentException;
import modelo.usuario.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase con los tests de los métodos de la clase empleado
 * 
 * @author Claudia Saiz
 */
class EmpleadoTest {

	private Empleado empleado;

	@BeforeEach
	void setUp() throws IllegalArgumentException, InvalidArgumentException {
		empleado = new Empleado("Ana", "1234", Permiso.INTERCAMBIOS, Permiso.PEDIDOS);
	}
	
	// constructor

	@Test
	void constructorSinPermisosEsValido() {
		assertDoesNotThrow(() -> new Empleado("Luis", "pass"));
	}

	@Test
	void constructorConTresPermisosEsValido() {
		assertDoesNotThrow(
				() -> new Empleado("Luis", "pass", Permiso.INTERCAMBIOS, Permiso.PEDIDOS, Permiso.PRODUCTOS));
	}

	@Test
	void constructorConMasDeTresPermisosLanzaExcepcion() {
		assertThrows(IllegalArgumentException.class, () -> new Empleado("Luis", "pass", Permiso.INTERCAMBIOS,
				Permiso.PEDIDOS, Permiso.PRODUCTOS, Permiso.INTERCAMBIOS));
	}
	
	// darDeAlta/Baja

	@Test
	void empleadoNuevoEstaDeAlta() {
		assertTrue(empleado.estaDeAlta());
	}

	@Test
	void darDeBajaDesactivaAlta() {
		empleado.darDeBaja();
		assertFalse(empleado.estaDeAlta());
	}

	@Test
	void darDeAltaReactivaEmpleado() {
		empleado.darDeBaja();
		empleado.darDeAlta();
		assertTrue(empleado.estaDeAlta());
	}

	// setPermisos
	
	@Test
	void setPermisosReemplazaPermisosPrevios() {
		empleado.setPermisos(new Permiso[] { Permiso.PRODUCTOS });
		Set<Permiso> permisos = empleado.getPermisos();
		assertEquals(1, permisos.size());
		assertTrue(permisos.contains(Permiso.PRODUCTOS));
	}
	
	// addPermiso

	@Test
	void addPermisoAgregaPermisoNuevo() {
		assertTrue(empleado.addPermiso(Permiso.PRODUCTOS));
	}

	@Test
	void addPermisoDuplicado() {
		assertFalse(empleado.addPermiso(Permiso.INTERCAMBIOS));
	}
	
	// quitarPermiso

	@Test
	void quitarPermisoExistente() {
		assertTrue(empleado.quitarPermiso(Permiso.PEDIDOS));
	}

	@Test
	void quitarPermisoNoExistente() {
		assertFalse(empleado.quitarPermiso(Permiso.PRODUCTOS));
	}
	
	// tienePermiso

	@Test
	void tienePermisoSiLoTiene() {
		assertTrue(empleado.tienePermiso(Permiso.INTERCAMBIOS));
	}

	@Test
	void tienePermisoSiNoLoTiene() {
		assertFalse(empleado.tienePermiso(Permiso.PRODUCTOS));
	}

	// getNotificaciones
	
	@Test
	void getNotificacionesInicialmenteVacia() {
		assertTrue(empleado.getNotificaciones().isEmpty());
	}
	
	// enviarNotificaciones

	@Test
	void enviarNotificacionIntercambioConPermiso() {
		assertTrue(empleado.enviarNotificacion("msg", TipoNotificacion.INTERCAMBIO));
	}

	@Test
	void enviarNotificacionValoracionConPermiso() {
		assertTrue(empleado.enviarNotificacion("msg", TipoNotificacion.VALORACION));
	}

	@Test
	void enviarNotificacionIntercambioSinPermiso() throws IllegalArgumentException, InvalidArgumentException {
		Empleado sinPermiso = new Empleado("Bob", "pass");
		assertFalse(sinPermiso.enviarNotificacion("msg", TipoNotificacion.INTERCAMBIO));
	}

	@Test
	void enviarNotificacionValoracionSinPermiso() throws IllegalArgumentException, InvalidArgumentException {
		Empleado sinPermiso = new Empleado("Bob", "pass");
		assertFalse(sinPermiso.enviarNotificacion("msg", TipoNotificacion.VALORACION));
	}

	@Test
	void enviarNotificacionPedidoConPermiso() {
		assertTrue(empleado.enviarNotificacion("msg", TipoNotificacion.PEDIDO));
	}

	@Test
	void enviarNotificacionPedidoSinPermiso() throws IllegalArgumentException, InvalidArgumentException {
		Empleado sinPermiso = new Empleado("Bob", "pass");
		assertFalse(sinPermiso.enviarNotificacion("msg", TipoNotificacion.PEDIDO));
	}

	@Test
	void enviarNotificacionCaducidadConPermiso() throws IllegalArgumentException, InvalidArgumentException {
		Empleado conProductos = new Empleado("Bob", "pass", Permiso.PRODUCTOS);
		assertTrue(conProductos.enviarNotificacion("msg", TipoNotificacion.CADUCIDAD));
	}

	@Test
	void enviarNotificacionProductoAgotadoConPermiso() throws IllegalArgumentException, InvalidArgumentException {
		Empleado conProductos = new Empleado("Bob", "pass", Permiso.PRODUCTOS);
		assertTrue(conProductos.enviarNotificacion("msg", TipoNotificacion.PRODUCTO_AGOTADO));
	}

	@Test
	void enviarNotificacionCaducidadSinPermiso() {
		assertFalse(empleado.enviarNotificacion("msg", TipoNotificacion.CADUCIDAD));
	}

	@Test
	void enviarNotificacionProductoAgotadoSinPermiso() {
		assertFalse(empleado.enviarNotificacion("msg", TipoNotificacion.PRODUCTO_AGOTADO));
	}

	@Test
	void notificacionAgregadaTrasTEnvioExitoso() {
		empleado.enviarNotificacion("msg", TipoNotificacion.INTERCAMBIO);
		List<Notificacion> notifs = empleado.getNotificaciones();
		assertEquals(1, notifs.size());
	}
	
	// toString

	@Test
	void toStringContienePermisos() {
		String str = empleado.toString();
		assertTrue(str.contains("Permisos:"));
	}
}