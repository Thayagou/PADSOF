package sistema.tests;

import org.junit.jupiter.api.*;

import exceptions.*;
import sistema.*;
import usuario.*;
import venta.descuentos.CondicionDescuento;
import venta.descuentos.DescuentoRegalo;
import venta.productos.*;
import wallapop.*;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TiendaTest {

	private Tienda tienda;
	private Empleado em1;

	@BeforeEach
	void setUp() throws Exception {
		resetSingletons();
		Reloj.volverATiempoReal();
		tienda = new Tienda();
		em1 = new Empleado("Empleado1", "pass", Permiso.PRODUCTOS);
		tienda.getAlmacen().anadirCategoria(em1, "Cat");
	}

	@AfterEach
	void tearDown() throws Exception {
		resetSingletons();
		Reloj.volverATiempoReal();
	}

	private void resetSingletons() throws Exception {
		Field f = Sistema.class.getDeclaredField("instancia");
		f.setAccessible(true);
		f.set(null, null);

		Field a = AsignadorId.class.getDeclaredField("instancia");
		a.setAccessible(true);
		a.set(null, null);

		Field g = GestorCaducidad.class.getDeclaredField("instancia");
		g.setAccessible(true);
		g.set(null, null);
	}

	private Categoria getCat() throws Exception {
		return tienda.getAlmacen().getCategoria("Cat");
	}

	private Producto addComic(String nombre) throws Exception {
		tienda.getAlmacen().anadirComic(em1, 5, nombre, "d", 10.0, null, LocalDate.now(), "Autor", 100, "Ed", getCat());
		return tienda.getAlmacen().getStock(nombre).getProducto();
	}

	private ClienteRegistrado registrar(String nombre) throws Exception {
		return tienda.registrarse(nombre, "pass", "pass");
	}

	// Constructor

	@Test
	void constructorCreaAlmacenYGestor() {
		assertNotNull(tienda.getAlmacen());
		assertNotNull(tienda.getGestor());
	}

	// getters

	@Test
	void getHistorialNoEsNull() {
		assertNotNull(tienda.getHistorial());
	}

	@Test
	void getEmpleadoNoExistente() {
		assertNull(tienda.getEmpleado("noExiste"));
	}

	@Test
	void getClienteNoExistente() {
		assertNull(tienda.getCliente("noExiste"));
	}

	@Test
	void getClientesVacio() {
		assertEquals(0, tienda.getClientes().length);
	}

	@Test
	void getEmpleadosVacio() {
		assertEquals(0, tienda.getEmpleados().length);
	}

	// ---- registrarse ----

	@Test
	void registrarseValido() throws Exception {
		ClienteRegistrado c = tienda.registrarse("cli1", "pass", "pass");
		assertNotNull(c);
		assertEquals("cli1", c.getNombre());
	}

	@Test
	void registrarseNombreNull() {
		assertThrows(InvalidArgumentException.class, () -> tienda.registrarse(null, "pass", "pass"));
	}

	@Test
	void registrarseContrasenaNull() {
		assertThrows(InvalidArgumentException.class, () -> tienda.registrarse("cli1", null, "pass"));
	}

	@Test
	void registrarseConfirmarNull() {
		assertThrows(InvalidArgumentException.class, () -> tienda.registrarse("cli1", "pass", null));
	}

	@Test
	void registrarseNombreDuplicadoCliente() throws Exception {
		tienda.registrarse("cli1", "pass", "pass");
		assertThrows(NotValidUserException.class, () -> tienda.registrarse("cli1", "pass", "pass"));
	}

	@Test
	void registrarseNombreIgualQueGestor() {
		assertThrows(NotValidUserException.class, () -> tienda.registrarse("gestor", "pass", "pass"));
	}

	@Test
	void registrarseContrasenasNoCoinciden() {
		assertThrows(NotValidUserException.class, () -> tienda.registrarse("cli1", "pass", "otro"));
	}

	// iniciarSesion

	@Test
	void iniciarSesionGestor() throws Exception {
		Usuario u = tienda.iniciarSesion("gestor", "g123");
		assertSame(tienda.getGestor(), u);
	}

	@Test
	void iniciarSesionGestorContrasenaIncorrecta() {
		assertThrows(NotValidUserException.class, () -> tienda.iniciarSesion("gestor", "mala"));
	}

	@Test
	void iniciarSesionClienteValido() throws Exception {
		tienda.registrarse("cli1", "pass", "pass");
		assertNotNull(tienda.iniciarSesion("cli1", "pass"));
	}

	@Test
	void iniciarSesionClienteContrasenaIncorrecta() throws Exception {
		tienda.registrarse("cli1", "pass", "pass");
		assertThrows(NotValidUserException.class, () -> tienda.iniciarSesion("cli1", "mala"));
	}

	@Test
	void iniciarSesionEmpleadoValido() throws Exception {
		tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
		assertNotNull(tienda.iniciarSesion("emp1", "pass"));
	}

	@Test
	void iniciarSesionEmpleadoContrasenaIncorrecta() throws Exception {
		tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
		assertThrows(NotValidUserException.class, () -> tienda.iniciarSesion("emp1", "mala"));
	}

	@Test
	void iniciarSesionEmpleadoDadoDeBaja() throws Exception {
		tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
		tienda.darDeBajaEmpleado("emp1");
		assertThrows(NotValidUserException.class, () -> tienda.iniciarSesion("emp1", "pass"));
	}

	@Test
	void iniciarSesionUsuarioNoExistente() {
		assertThrows(NotValidUserException.class, () -> tienda.iniciarSesion("noExiste", "pass"));
	}

	// darDeAltaEmpleado

	@Test
	void darDeAltaEmpleadoNuevo() throws Exception {
		assertTrue(tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS));
	}

	@Test
	void darDeAltaEmpleadoNombreDuplicadoConCliente() throws Exception {
		tienda.registrarse("cli1", "pass", "pass");
		assertFalse(tienda.darDeAltaEmpleado("cli1", "pass", Permiso.PEDIDOS));
	}

	@Test
	void darDeAltaEmpleadoYaExisteYDadoDeAlta() throws Exception {
		tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
		assertFalse(tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS));
	}

	@Test
	void darDeAltaEmpleadoExisteYDadoDeBaja() throws Exception {
		tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
		tienda.darDeBajaEmpleado("emp1");
		assertTrue(tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS));
	}

	@Test
	void getEmpleadosSoloDevuelveDeAlta() throws Exception {
		tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
		tienda.darDeAltaEmpleado("emp2", "pass", Permiso.PEDIDOS);
		tienda.darDeBajaEmpleado("emp1");
		assertEquals(1, tienda.getEmpleados().length);
	}

	// darDeBajaEmpleado

	@Test
	void darDeBajaEmpleadoExistente() throws Exception {
		tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
		assertTrue(tienda.darDeBajaEmpleado("emp1"));
	}

	@Test
	void darDeBajaEmpleadoNoExistente() {
		assertFalse(tienda.darDeBajaEmpleado("noExiste"));
	}

	// carritoCaducado

	@Test
	void carritoCaducadoDevuelveStockAlmacen() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		Producto p = addComic("C1");
		tienda.anadirACarritoDe(cli, p);
		int antes = tienda.getAlmacen().getUnidades(p);
		tienda.carritoCaducado(cli.getCarrito());
		int despues = tienda.getAlmacen().getUnidades(p);
		assertEquals(antes + 1, despues);
	}

	// anadirACarritoDe

	@Test
	void anadirACarritoDeValidoReducStock() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		Producto p = addComic("C1");
		int antes = tienda.getAlmacen().getUnidades(p);
		tienda.anadirACarritoDe(cli, p);
		assertEquals(antes - 1, tienda.getAlmacen().getUnidades(p));
	}

	@Test
	void anadirACarritoDeClienteNull() throws Exception {
		Producto p = addComic("C1");
		assertThrows(InvalidArgumentException.class, () -> tienda.anadirACarritoDe(null, p));
	}

	@Test
	void anadirACarritoDeProductoNull() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		assertThrows(InvalidArgumentException.class, () -> tienda.anadirACarritoDe(cli, null));
	}

	@Test
	void anadirACarritoDeSinStock() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		tienda.getAlmacen().anadirComic(em1, 0, "C0", "d", 10.0, null, LocalDate.now(), "A", 10, "E", getCat());
		Producto p = tienda.getAlmacen().getStock("C0").getProducto();
		assertThrows(ProductoNoDisponibleException.class, () -> tienda.anadirACarritoDe(cli, p));
	}

	// quitarDeCarritoDe

	@Test
	void quitarDeCarritoDeValidoIncrementaStock() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		Producto p = addComic("C1");
		tienda.anadirACarritoDe(cli, p);
		int antes = tienda.getAlmacen().getUnidades(p);
		tienda.quitarDeCarritoDe(cli, p);
		assertEquals(antes + 1, tienda.getAlmacen().getUnidades(p));
	}

	@Test
	void quitarDeCarritoDeClienteNull() throws Exception {
		Producto p = addComic("C1");
		assertThrows(InvalidArgumentException.class, () -> tienda.quitarDeCarritoDe(null, p));
	}

	@Test
	void quitarDeCarritoDeProductoNull() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		assertThrows(InvalidArgumentException.class, () -> tienda.quitarDeCarritoDe(cli, null));
	}

	// cancelarCarritoDe

	@Test
	void cancelarCarritoDeValido() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		Producto p = addComic("C1");
		tienda.anadirACarritoDe(cli, p);
		assertTrue(tienda.cancelarCarritoDe(cli));
	}

	@Test
	void cancelarCarritoDeClienteNull() throws Exception {
		assertFalse(tienda.cancelarCarritoDe(null));
	}

	// gestionarParametroDeSistema

	@Test
	void gestionarParametroCATEGORIA() throws Exception {
		assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.CATEGORIA, 2.0));
		assertEquals(2.0, Sistema.getInstancia().getPonderacionCategoria());
	}

	@Test
	void gestionarParametroUDS_COMPRADAS() throws Exception {
		assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.UDS_COMPRADAS, 2.0));
		assertEquals(2.0, Sistema.getInstancia().getPonderacionUdsCompra());
	}

	@Test
	void gestionarParametroPRECIO_COMPRA() throws Exception {
		assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.PRECIO_COMPRA, 2.0));
		assertEquals(2.0, Sistema.getInstancia().getPonderacionPrecioCompra());
	}

	@Test
	void gestionarParametroVALORACIONES_PRODUCTO() throws Exception {
		assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.VALORACIONES_PRODUCTO, 2.0));
		assertEquals(2.0, Sistema.getInstancia().getPonderacionValoracionesProducto());
	}

	@Test
	void gestionarParametroPRODUCTO_RECOMENDADO() throws Exception {
		assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.PRODUCTO_RECOMENDADO, 2.0));
		assertEquals(2.0, Sistema.getInstancia().getPonderacionProductoRecomendado());
	}

	@Test
	void gestionarParametroBUSQUEDA() throws Exception {
		assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.BUSQUEDA, 2.0));
		assertEquals(2.0, Sistema.getInstancia().getPonderacionBusqueda());
	}

	@Test
	void gestionarParametroGestorNull() {
		assertThrows(InvalidArgumentException.class, () -> tienda.gestionarParametroDeSistema(null, ParametroSistema.CATEGORIA, 2.0));
	}

	@Test
	void gestionarParametroParametroNull() {
		assertThrows(InvalidArgumentException.class, () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), null, 1.0));
	}

	@Test
	void gestionarParametroValorNegativo() {
		assertThrows(InvalidArgumentException.class, () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.CATEGORIA, -1.0));
	}

	@Test
	void gestionarParametroParametroInvalido() {
		assertThrows(InvalidArgumentException.class, () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.DURACION_CARRITO, 1.0));
	}

	// gestionarParametroDeSistema

	@Test
	void gestionarParametroDURACION_CARRITO() throws Exception {
		assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.DURACION_CARRITO, Duration.ofHours(1)));
	}

	@Test
	void gestionarParametroDURACION_OFERTA() throws Exception {
		assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.DURACION_OFERTA, Duration.ofHours(2)));
	}

	@Test
	void gestionarParametroDuracionGestorNull() {
		assertThrows(InvalidArgumentException.class, () -> tienda.gestionarParametroDeSistema(null, ParametroSistema.DURACION_CARRITO, Duration.ofHours(1)));
	}

	@Test
	void gestionarParametroDuracionParametroNull() {
		assertThrows(InvalidArgumentException.class, () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), null, Duration.ofHours(1)));
	}

	@Test
	void gestionarParametroDuracionNull() {
		assertThrows(InvalidArgumentException.class, () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.DURACION_CARRITO, (Duration) null));
	}

	@Test
	void gestionarParametroDuracionParametroInvalido() {
		assertThrows(InvalidArgumentException.class, () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.CATEGORIA, Duration.ofHours(1)));
	}

	// intercambios

	private ArticuloSegundaMano crearArticuloDisponible(ClienteRegistrado cli, String nombre) throws Exception {
		ArticuloSegundaMano art = new ArticuloSegundaMano(nombre, "d", cli.getCartera(), "x");
		tienda.getAlmacen().anadirArticuloSegundaMano(art);
		art.disponibilizar();
		return art;
	}

	@Test
	void hacerOfertaIntercambioValido() throws Exception {
		ClienteRegistrado cli1 = registrar("cli1");
		ClienteRegistrado cli2 = registrar("cli2");
		cli2.anadirInteres(TipoNotificacion.INTERCAMBIO);
		ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
		ArticuloSegundaMano art2 = crearArticuloDisponible(cli2, "Art2");
		assertTrue(tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[] { art1 }, new ArticuloSegundaMano[] { art2 }));
	}
	
	@Test
	void hacerOfertaIntercambioInvalidandoIntercambios() throws Exception {
		ClienteRegistrado cli1 = registrar("cli1");
		ClienteRegistrado cli2 = registrar("cli2");
		cli2.anadirInteres(TipoNotificacion.INTERCAMBIO);
		ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
		ArticuloSegundaMano art2 = crearArticuloDisponible(cli2, "Art2");
		tienda.hacerOfertaIntercambio(cli2, new ArticuloSegundaMano[] { art2 }, new ArticuloSegundaMano[] { art1 });
		
		assertTrue(tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[] { art1 }, new ArticuloSegundaMano[] { art2 }));
	}

	@Test
	void aceptarIntercambioValido() throws Exception {
		ClienteRegistrado cli1 = registrar("cli1");
		ClienteRegistrado cli2 = registrar("cli2");
		tienda.darDeAltaEmpleado("Emp1", "pass", Permiso.INTERCAMBIOS);
		cli1.anadirInteres(TipoNotificacion.INTERCAMBIO);
		cli2.anadirInteres(TipoNotificacion.INTERCAMBIO);
		ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
		ArticuloSegundaMano art2 = crearArticuloDisponible(cli2, "Art2");
		tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[] { art1 }, new ArticuloSegundaMano[] { art2 });
		Intercambio intercambio = cli2.getCartera().getIntercambiosPendientes()[0];
		assertTrue(tienda.aceptarIntercambio(cli2, intercambio));
	}
	
	@Test
	void aceptarIntercambioInvalidandoIntercambios() throws Exception {
		ClienteRegistrado cli1 = registrar("cli1");
		ClienteRegistrado cli2 = registrar("cli2");
		tienda.darDeAltaEmpleado("Emp1", "pass", Permiso.INTERCAMBIOS);
		cli1.anadirInteres(TipoNotificacion.INTERCAMBIO);
		cli2.anadirInteres(TipoNotificacion.INTERCAMBIO);
		ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
		ArticuloSegundaMano art2 = crearArticuloDisponible(cli1, "Art2");
		ArticuloSegundaMano art3 = crearArticuloDisponible(cli2, "Art3");
		
		tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[] { art1 }, new ArticuloSegundaMano[] { art3 });
		tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[] { art1, art2 }, new ArticuloSegundaMano[] { art3 });
		Intercambio intercambio = cli2.getCartera().getIntercambiosPendientes()[0];
		
		assertTrue(tienda.aceptarIntercambio(cli2, intercambio));
	}

	@Test
	void rechazarIntercambioValido() throws Exception {
		ClienteRegistrado cli1 = registrar("cli1");
		ClienteRegistrado cli2 = registrar("cli2");
		cli1.anadirInteres(TipoNotificacion.INTERCAMBIO);
		ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
		ArticuloSegundaMano art2 = crearArticuloDisponible(cli2, "Art2");
		tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[] { art1 }, new ArticuloSegundaMano[] { art2 });
		Intercambio intercambio = cli2.getCartera().getIntercambiosPendientes()[0];
		assertTrue(tienda.rechazarIntercambio(cli2, intercambio));
	}

	@Test
	void cancelarIntercambioValido() throws Exception {
		ClienteRegistrado cli1 = registrar("cli1");
		ClienteRegistrado cli2 = registrar("cli2");
		cli2.anadirInteres(TipoNotificacion.INTERCAMBIO);
		ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
		ArticuloSegundaMano art2 = crearArticuloDisponible(cli2, "Art2");
		tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[] { art1 }, new ArticuloSegundaMano[] { art2 });
		Intercambio intercambio = cli1.getCartera().getIntercambiosPendientes()[0];
		assertTrue(tienda.cancelarIntercambio(cli1, intercambio));
	}

	// anadirArticulo

	@Test
	void anadirArticuloValido() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		assertTrue(tienda.anadirArticulo("Art1", "desc", cli.getCartera(), new Categoria[] {}, "nada"));
	}

	// solicitarValoracion
	
	@Test
	void solicitarValoracionValido() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		tienda.darDeAltaEmpleado("Emp1", "pass", Permiso.INTERCAMBIOS);
		ArticuloSegundaMano art = crearArticuloDisponible(cli, "Art1");
		assertTrue(tienda.solicitarValoracion(cli, art, "1234567890123456"));
	}
	
	@Test
	void solicitarValoracionTarjetaInválida() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		tienda.darDeAltaEmpleado("Emp1", "pass", Permiso.INTERCAMBIOS);
		ArticuloSegundaMano art = crearArticuloDisponible(cli, "Art1");
		assertFalse(tienda.solicitarValoracion(cli, art, "abc123"));
	}

	@Test
	void solicitarValoracionClienteNull() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		ArticuloSegundaMano art = crearArticuloDisponible(cli, "Art1");
		assertFalse(tienda.solicitarValoracion(null, art, "1234567890123456"));
	}

	@Test
	void solicitarValoracionArticuloNull() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		assertFalse(tienda.solicitarValoracion(cli, null, "1234567890123456"));
	}

	@Test
	void solicitarValoracionTarjetaNull() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		ArticuloSegundaMano art = crearArticuloDisponible(cli, "Art1");
		assertFalse(tienda.solicitarValoracion(cli, art, null));
	}

	@Test
	void solicitarValoracionArticuloYaValorado() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		ArticuloSegundaMano art = crearArticuloDisponible(cli, "Art1");
		tienda.getHistorial().guardarUsuario(cli);
		tienda.solicitarValoracion(cli, art, "1234567890123456");
		assertThrows(InvalidArgumentException.class, () -> tienda.solicitarValoracion(cli, art, "1234567890123456"));
	}

	// pagarCarritoDe

	@Test
	void pagarCarritoDe_ClienteNull() throws Exception {
		assertFalse(tienda.pagarCarritoDe(null, "1234567890123456"));
	}

	/**
	 * Crea un descuento de tipo regalo
	 * @throws InvalidArgumentException
	 */
	private DescuentoRegalo crearDescuentoRegalo(double precioMin, Producto regalo) throws InvalidArgumentException {
		return new DescuentoRegalo(precioMin, Reloj.now().minusDays(2), Reloj.now().plusDays(2), CondicionDescuento.VOLUMEN, regalo);
	}
	
	@Test
	void pagarCarritoDeTarjetaInvalida() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		tienda.darDeAltaEmpleado("Emp1", "pass", Permiso.PEDIDOS);
		Producto c1 = addComic("C1");
		Producto c2 = addComic("C2");
		tienda.anadirACarritoDe(cli, c1);
		tienda.anadirACarritoDe(cli, c2);
		c1.anadirDescuento(crearDescuentoRegalo(9.0, c1));
		c2.anadirDescuento(crearDescuentoRegalo(12.0, c1));
		
		assertFalse(tienda.pagarCarritoDe(cli, "abc123"));
	}
	
	@Test
	void pagarCarritoDeTarjetaValida() throws Exception {
		ClienteRegistrado cli = registrar("cli1");
		tienda.darDeAltaEmpleado("Emp1", "pass", Permiso.PEDIDOS);
		Producto c1 = addComic("C1");
		Producto c2 = addComic("C2");
		tienda.anadirACarritoDe(cli, c1);
		tienda.anadirACarritoDe(cli, c2);
		c1.anadirDescuento(crearDescuentoRegalo(9.0, c1));
		c2.anadirDescuento(crearDescuentoRegalo(12.0, c1));
		
		assertTrue(tienda.pagarCarritoDe(cli, "1234567890123456"));
	}

	// guardarTienda / cargarTienda

	@Test
	void guardarYCargarTienda() throws Exception {
		String archivo = "tiendaTest.dat";
		tienda.registrarse("cli1", "pass", "pass");
		tienda.guardarTienda(archivo);
		Tienda cargada = Tienda.cargarTienda(archivo);
		assertNotNull(cargada);
		assertNotNull(cargada.getCliente("cli1"));
	}
	
	@Test
	void cargarTiendaArchivoNull() {
		assertNull(Tienda.cargarTienda(null));
	}
	
	@Test
	void guardarTiendaArchivoNull() {
		tienda.guardarTienda(null);
	}
}