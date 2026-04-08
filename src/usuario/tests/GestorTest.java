package usuario.tests;

import org.junit.jupiter.api.*;

import exceptions.InvalidArgumentException;
import sistema.*;
import usuario.*;

import java.lang.reflect.Field;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GestorTest {

	private Gestor gestor;
	private Empleado empleado;

	@BeforeEach
	void setUp() throws InvalidArgumentException {
		gestor = new Gestor("gestor1", "pass123");
		empleado = new Empleado("empleado1", "pass456");
		Sistema.getInstancia();
	}

	@AfterEach
	void resetSistema() throws Exception {
		Field f = Sistema.class.getDeclaredField("instancia");
		f.setAccessible(true);
		f.set(null, null);
	}

	// Constructor
	@Test
	void constructorNombreNulo() {
		assertThrows(InvalidArgumentException.class, () -> new Gestor(null, "pass"));
	}

	@Test
	void constructorContrasenaNulan() {
		assertThrows(InvalidArgumentException.class, () -> new Gestor("gestor", null));
	}

	@Test
	void constructorValidoTienePermisoProductos() {
		assertTrue(gestor.tienePermiso(Permiso.PRODUCTOS));
	}

	// tienePermiso
	@Test
	void tienePermisoPermisoNoAsignado() {
		assertFalse(gestor.tienePermiso(Permiso.PEDIDOS));
	}

	// anadirPermisos
	@Test
	void anadirPermisosPermisoNuevo() {
		assertTrue(gestor.anadirPermisos(empleado, Permiso.PEDIDOS));
		assertTrue(empleado.tienePermiso(Permiso.PEDIDOS));
	}

	@Test
	void anadirPermisosPermisoYaExistente() {
		empleado.addPermiso(Permiso.PEDIDOS);
		assertFalse(gestor.anadirPermisos(empleado, Permiso.PEDIDOS));
	}

	@Test
	void anadirPermisosVariosPermisos() {
		boolean resultado = gestor.anadirPermisos(empleado, Permiso.PEDIDOS, Permiso.INTERCAMBIOS);
		assertTrue(resultado);
		assertTrue(empleado.tienePermiso(Permiso.PEDIDOS));
		assertTrue(empleado.tienePermiso(Permiso.INTERCAMBIOS));
	}

	@Test
	void anadirPermisosSinPermisos() {
		assertTrue(gestor.anadirPermisos(empleado));
	}

	// quitarPermisos
	@Test
	void quitarPermisosPermisoExistente() {
		empleado.addPermiso(Permiso.PEDIDOS);
		assertTrue(gestor.quitarPermisos(empleado, Permiso.PEDIDOS));
		assertFalse(empleado.tienePermiso(Permiso.PEDIDOS));
	}

	@Test
	void quitarPermisosPermisoNoExistente() {
		assertFalse(gestor.quitarPermisos(empleado, Permiso.PEDIDOS));
	}

	@Test
	void quitarPermisosVariosPermisos() {
		empleado.addPermiso(Permiso.PEDIDOS);
		empleado.addPermiso(Permiso.INTERCAMBIOS);
		boolean resultado = gestor.quitarPermisos(empleado, Permiso.PEDIDOS, Permiso.INTERCAMBIOS);
		assertTrue(resultado);
		assertFalse(empleado.tienePermiso(Permiso.PEDIDOS));
		assertFalse(empleado.tienePermiso(Permiso.INTERCAMBIOS));
	}

	@Test
	void quitarPermisosSinPermisos() {
		assertTrue(gestor.quitarPermisos(empleado));
	}

	// modificarTiempoCaducidadCarrito
	@Test
	void modificarTiempoCaducidadCarritoDuracionValida() {
		Duration nuevo = Duration.ofHours(5);
		assertTrue(gestor.modificarTiempoCaducidadCarrito(nuevo));
		assertEquals(nuevo, Sistema.getInstancia().getTiempoCaducaCarrito());
	}

	// modificarTiempoCaducidadOferta
	@Test
	void modificarTiempoCaducidadOfertaDuracionValida() {
		Duration nuevo = Duration.ofHours(10);
		assertTrue(gestor.modificarTiempoCaducidadOferta(nuevo));
		assertEquals(nuevo, Sistema.getInstancia().getTiempoCaducaOferta());
	}

	// establecerParametros
	@Test
	void establecerParametrosActivarParametroNoPresente() {
		Sistema.getInstancia().gestionarParametro(ParametroSistema.CATEGORIA, false);
		assertTrue(gestor.establecerParametros(true, ParametroSistema.CATEGORIA));
		assertTrue(Sistema.getInstancia().getParametros().contains(ParametroSistema.CATEGORIA));
	}

	@Test
	void establecerParametrosActivarParametroYaPresente() {
		assertFalse(gestor.establecerParametros(true, ParametroSistema.CATEGORIA));
	}

	@Test
	void establecerParametrosDesactivarParametroConMasDeUno() {
		assertTrue(gestor.establecerParametros(false, ParametroSistema.CATEGORIA));
		assertFalse(Sistema.getInstancia().getParametros().contains(ParametroSistema.CATEGORIA));
	}

	@Test
	void establecerParametrosDesactivarCuandoSoloHayUno() {
		ParametroSistema[] todos = ParametroSistema.values();
		for (int i = 1; i < todos.length; i++) {
			Sistema.getInstancia().gestionarParametro(todos[i], false);
		}
		assertFalse(gestor.establecerParametros(false, todos[0]));
	}

	@Test
	void establecerParametrosSinParametros() {
		assertTrue(gestor.establecerParametros(true));
	}

	@Test
	void establecerParametrosVariosParametros() {
		Sistema.getInstancia().gestionarParametro(ParametroSistema.CATEGORIA, false);
		Sistema.getInstancia().gestionarParametro(ParametroSistema.UDS_COMPRADAS, false);
		assertTrue(gestor.establecerParametros(true, ParametroSistema.CATEGORIA, ParametroSistema.UDS_COMPRADAS));
	}
}