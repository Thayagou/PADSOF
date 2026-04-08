package sistema.tests;

import org.junit.jupiter.api.*;
import exceptions.*;
import sistema.*;
import usuario.*;
import venta.productos.*;
import venta.productos.caracteristicas.*;
import wallapop.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private Categoria cat() throws Exception {
        return tienda.getAlmacen().getCategoria("Cat");
    }

    private Producto addComic(String nombre) throws Exception {
        tienda.getAlmacen().anadirComic(em1, 5, nombre, "d", 10.0, null,
                LocalDate.now(), "Autor", 100, "Ed", cat());
        return tienda.getAlmacen().getStock(nombre).getProducto();
    }

    private ClienteRegistrado registrar(String nombre) throws Exception {
        return tienda.registrarse(nombre, "pass", "pass");
    }

    // ---- Constructor ----

    @Test
    void constructor_creaAlmacenYGestor() {
        assertNotNull(tienda.getAlmacen());
        assertNotNull(tienda.getGestor());
    }

    // ---- getters basicos ----

    @Test
    void getHistorial_noEsNull() {
        assertNotNull(tienda.getHistorial());
    }

    @Test
    void getEmpleado_noExistente_devuelveNull() {
        assertNull(tienda.getEmpleado("noExiste"));
    }

    @Test
    void getCliente_noExistente_devuelveNull() {
        assertNull(tienda.getCliente("noExiste"));
    }

    @Test
    void getClientes_inicialmente_vacio() {
        assertEquals(0, tienda.getClientes().length);
    }

    @Test
    void getEmpleados_inicialmente_vacio() {
        assertEquals(0, tienda.getEmpleados().length);
    }

    // ---- registrarse ----

    @Test
    void registrarse_datosValidos_devuelveCliente() throws Exception {
        ClienteRegistrado c = tienda.registrarse("cli1", "pass", "pass");
        assertNotNull(c);
        assertEquals("cli1", c.getNombre());
    }

    @Test
    void registrarse_nombreNull_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.registrarse(null, "pass", "pass"));
    }

    @Test
    void registrarse_contrasenaNull_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.registrarse("cli1", null, "pass"));
    }

    @Test
    void registrarse_confirmarNull_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.registrarse("cli1", "pass", null));
    }

    @Test
    void registrarse_nombreDuplicadoCliente_lanzaExcepcion() throws Exception {
        tienda.registrarse("cli1", "pass", "pass");
        assertThrows(NotValidUserException.class,
                () -> tienda.registrarse("cli1", "pass", "pass"));
    }

    @Test
    void registrarse_nombreIgualQueGestor_lanzaExcepcion() {
        assertThrows(NotValidUserException.class,
                () -> tienda.registrarse("gestor", "pass", "pass"));
    }

    @Test
    void registrarse_contrasenasNoCoinciden_lanzaExcepcion() {
        assertThrows(NotValidUserException.class,
                () -> tienda.registrarse("cli1", "pass", "otro"));
    }

    // ---- iniciarSesion ----

    @Test
    void iniciarSesion_gestor_devuelveGestor() throws Exception {
        Usuario u = tienda.iniciarSesion("gestor", "g123");
        assertSame(tienda.getGestor(), u);
    }

    @Test
    void iniciarSesion_gestorContrasenaIncorrecta_lanzaExcepcion() {
        assertThrows(NotValidUserException.class,
                () -> tienda.iniciarSesion("gestor", "mala"));
    }

    @Test
    void iniciarSesion_clienteValido_devuelveCliente() throws Exception {
        tienda.registrarse("cli1", "pass", "pass");
        assertNotNull(tienda.iniciarSesion("cli1", "pass"));
    }

    @Test
    void iniciarSesion_clienteContrasenaIncorrecta_lanzaExcepcion() throws Exception {
        tienda.registrarse("cli1", "pass", "pass");
        assertThrows(NotValidUserException.class,
                () -> tienda.iniciarSesion("cli1", "mala"));
    }

    @Test
    void iniciarSesion_empleadoValido_devuelveEmpleado() throws Exception {
        tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
        assertNotNull(tienda.iniciarSesion("emp1", "pass"));
    }

    @Test
    void iniciarSesion_empleadoContrasenaIncorrecta_lanzaExcepcion() throws Exception {
        tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
        assertThrows(NotValidUserException.class,
                () -> tienda.iniciarSesion("emp1", "mala"));
    }

    @Test
    void iniciarSesion_empleadoDadoDeBaja_lanzaExcepcion() throws Exception {
        tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
        tienda.darDeBajaEmpleado("emp1");
        assertThrows(NotValidUserException.class,
                () -> tienda.iniciarSesion("emp1", "pass"));
    }

    @Test
    void iniciarSesion_usuarioNoExistente_lanzaExcepcion() {
        assertThrows(NotValidUserException.class,
                () -> tienda.iniciarSesion("noExiste", "pass"));
    }

    // ---- darDeAltaEmpleado ----

    @Test
    void darDeAltaEmpleado_nuevo_devuelveTrue() throws Exception {
        assertTrue(tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS));
    }

    @Test
    void darDeAltaEmpleado_nombreDuplicadoConCliente_devuelveFalse() throws Exception {
        tienda.registrarse("cli1", "pass", "pass");
        assertFalse(tienda.darDeAltaEmpleado("cli1", "pass", Permiso.PEDIDOS));
    }

    @Test
    void darDeAltaEmpleado_yaExisteYDadoDeAlta_devuelveFalse() throws Exception {
        tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
        assertFalse(tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS));
    }

    @Test
    void darDeAltaEmpleado_existeYDadoDeBaja_devuelveTrue() throws Exception {
        tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
        tienda.darDeBajaEmpleado("emp1");
        assertTrue(tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS));
    }

    @Test
    void getEmpleados_soloDevuelveDeAlta() throws Exception {
        tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
        tienda.darDeAltaEmpleado("emp2", "pass", Permiso.PEDIDOS);
        tienda.darDeBajaEmpleado("emp1");
        assertEquals(1, tienda.getEmpleados().length);
    }

    // ---- darDeBajaEmpleado ----

    @Test
    void darDeBajaEmpleado_existente_devuelveTrue() throws Exception {
        tienda.darDeAltaEmpleado("emp1", "pass", Permiso.PEDIDOS);
        assertTrue(tienda.darDeBajaEmpleado("emp1"));
    }

    @Test
    void darDeBajaEmpleado_noExistente_devuelveFalse() {
        assertFalse(tienda.darDeBajaEmpleado("noExiste"));
    }

    // ---- carritoCaducado ----

    @Test
    void carritoCaducado_devuelveStockAlmacen() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        Producto p = addComic("C1");
        tienda.anadirACarritoDe(cli, p);
        int antes = tienda.getAlmacen().getUnidades(p);
        tienda.carritoCaducado(cli.getCarrito());
        int despues = tienda.getAlmacen().getUnidades(p);
        assertEquals(antes + 1, despues);
    }

    // ---- anadirACarritoDe ----

    @Test
    void anadirACarritoDe_valido_reducStock() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        Producto p = addComic("C1");
        int antes = tienda.getAlmacen().getUnidades(p);
        tienda.anadirACarritoDe(cli, p);
        assertEquals(antes - 1, tienda.getAlmacen().getUnidades(p));
    }

    @Test
    void anadirACarritoDe_clienteNull_lanzaExcepcion() throws Exception {
        Producto p = addComic("C1");
        assertThrows(InvalidArgumentException.class,
                () -> tienda.anadirACarritoDe(null, p));
    }

    @Test
    void anadirACarritoDe_productoNull_lanzaExcepcion() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        assertThrows(InvalidArgumentException.class,
                () -> tienda.anadirACarritoDe(cli, null));
    }

    @Test
    void anadirACarritoDe_sinStock_lanzaExcepcion() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        tienda.getAlmacen().anadirComic(em1, 0, "C0", "d", 10.0, null,
                LocalDate.now(), "A", 10, "E", cat());
        Producto p = tienda.getAlmacen().getStock("C0").getProducto();
        assertThrows(ProductoNoDisponibleException.class,
                () -> tienda.anadirACarritoDe(cli, p));
    }

    // ---- quitarDeCarritoDe ----

    @Test
    void quitarDeCarritoDe_valido_incrementaStock() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        Producto p = addComic("C1");
        tienda.anadirACarritoDe(cli, p);
        int antes = tienda.getAlmacen().getUnidades(p);
        tienda.quitarDeCarritoDe(cli, p);
        assertEquals(antes + 1, tienda.getAlmacen().getUnidades(p));
    }

    @Test
    void quitarDeCarritoDe_clienteNull_lanzaExcepcion() throws Exception {
        Producto p = addComic("C1");
        assertThrows(InvalidArgumentException.class,
                () -> tienda.quitarDeCarritoDe(null, p));
    }

    @Test
    void quitarDeCarritoDe_productoNull_lanzaExcepcion() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        assertThrows(InvalidArgumentException.class,
                () -> tienda.quitarDeCarritoDe(cli, null));
    }

    // ---- cancelarCarritoDe ----

    @Test
    void cancelarCarritoDe_valido_devuelveTrue() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        Producto p = addComic("C1");
        tienda.anadirACarritoDe(cli, p);
        assertTrue(tienda.cancelarCarritoDe(cli));
    }

    @Test
    void cancelarCarritoDe_clienteNull_devuelveFalse() throws Exception {
        assertFalse(tienda.cancelarCarritoDe(null));
    }

    // ---- gestionarParametroDeSistema (double) ----

    @Test
    void gestionarParametroDoble_CATEGORIA_actualiza() throws Exception {
        assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(
                tienda.getGestor(), ParametroSistema.CATEGORIA, 2.0));
    }

    @Test
    void gestionarParametroDoble_UDS_COMPRADAS_actualiza() throws Exception {
        assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(
                tienda.getGestor(), ParametroSistema.UDS_COMPRADAS, 1.5));
    }

    @Test
    void gestionarParametroDoble_PRECIO_COMPRA_actualiza() throws Exception {
        assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(
                tienda.getGestor(), ParametroSistema.PRECIO_COMPRA, 1.0));
    }

    @Test
    void gestionarParametroDoble_VALORACIONES_PRODUCTO_actualiza() throws Exception {
        assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(
                tienda.getGestor(), ParametroSistema.VALORACIONES_PRODUCTO, 1.0));
    }

    @Test
    void gestionarParametroDoble_PRODUCTO_RECOMENDADO_actualiza() throws Exception {
        assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(
                tienda.getGestor(), ParametroSistema.PRODUCTO_RECOMENDADO, 1.0));
    }

    @Test
    void gestionarParametroDoble_BUSQUEDA_actualiza() throws Exception {
        assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(
                tienda.getGestor(), ParametroSistema.BUSQUEDA, 1.0));
    }

    @Test
    void gestionarParametroDoble_gestorNull_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.gestionarParametroDeSistema(null, ParametroSistema.CATEGORIA, 1.0));
    }

    @Test
    void gestionarParametroDoble_parametroNull_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), null, 1.0));
    }

    @Test
    void gestionarParametroDoble_valorNegativo_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.CATEGORIA, -1.0));
    }

    @Test
    void gestionarParametroDoble_parametroInvalido_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.DURACION_CARRITO, 1.0));
    }

    // ---- gestionarParametroDeSistema (Duration) ----

    @Test
    void gestionarParametroDuracion_DURACION_CARRITO_actualiza() throws Exception {
        assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(
                tienda.getGestor(), ParametroSistema.DURACION_CARRITO, java.time.Duration.ofHours(1)));
    }

    @Test
    void gestionarParametroDuracion_DURACION_OFERTA_actualiza() throws Exception {
        assertDoesNotThrow(() -> tienda.gestionarParametroDeSistema(
                tienda.getGestor(), ParametroSistema.DURACION_OFERTA, java.time.Duration.ofHours(2)));
    }

    @Test
    void gestionarParametroDuracion_gestorNull_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.gestionarParametroDeSistema(null, ParametroSistema.DURACION_CARRITO, java.time.Duration.ofHours(1)));
    }

    @Test
    void gestionarParametroDuracion_parametroNull_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), null, java.time.Duration.ofHours(1)));
    }

    @Test
    void gestionarParametroDuracion_duracionNull_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.DURACION_CARRITO, (java.time.Duration) null));
    }

    @Test
    void gestionarParametroDuracion_parametroInvalido_lanzaExcepcion() {
        assertThrows(InvalidArgumentException.class,
                () -> tienda.gestionarParametroDeSistema(tienda.getGestor(), ParametroSistema.CATEGORIA, java.time.Duration.ofHours(1)));
    }

    // ---- intercambios ----

    private ArticuloSegundaMano crearArticuloDisponible(ClienteRegistrado cli, String nombre) throws Exception {
        ArticuloSegundaMano art = new ArticuloSegundaMano(nombre, "d", cli.getCartera(), "x");
        tienda.getAlmacen().anadirArticuloSegundaMano(art);
        art.disponibilizar();
        return art;
    }

    @Test
    void hacerOfertaIntercambio_valido_devuelveTrue() throws Exception {
        ClienteRegistrado cli1 = registrar("cli1");
        ClienteRegistrado cli2 = registrar("cli2");
        cli2.anadirInteres(TipoNotificacion.INTERCAMBIO);
        ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
        ArticuloSegundaMano art2 = crearArticuloDisponible(cli2, "Art2");
        assertTrue(tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[]{art1}, new ArticuloSegundaMano[]{art2}));
    }

    @Test
    void aceptarIntercambio_valido_devuelveTrue() throws Exception {
        ClienteRegistrado cli1 = registrar("cli1");
        ClienteRegistrado cli2 = registrar("cli2");
        cli1.anadirInteres(TipoNotificacion.INTERCAMBIO);
        cli2.anadirInteres(TipoNotificacion.INTERCAMBIO);
        ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
        ArticuloSegundaMano art2 = crearArticuloDisponible(cli2, "Art2");
        tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[]{art1}, new ArticuloSegundaMano[]{art2});
        Intercambio intercambio = cli2.getCartera().getIntercambiosPendientes()[0];
        assertTrue(tienda.aceptarIntercambio(cli2, intercambio));
    }

    @Test
    void rechazarIntercambio_valido_devuelveTrue() throws Exception {
        ClienteRegistrado cli1 = registrar("cli1");
        ClienteRegistrado cli2 = registrar("cli2");
        cli1.anadirInteres(TipoNotificacion.INTERCAMBIO);
        ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
        ArticuloSegundaMano art2 = crearArticuloDisponible(cli2, "Art2");
        tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[]{art1}, new ArticuloSegundaMano[]{art2});
        Intercambio intercambio = cli2.getCartera().getIntercambiosPendientes()[0];
        assertTrue(tienda.rechazarIntercambio(cli2, intercambio));
    }

    @Test
    void cancelarIntercambio_valido_devuelveTrue() throws Exception {
        ClienteRegistrado cli1 = registrar("cli1");
        ClienteRegistrado cli2 = registrar("cli2");
        cli2.anadirInteres(TipoNotificacion.INTERCAMBIO);
        ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
        ArticuloSegundaMano art2 = crearArticuloDisponible(cli2, "Art2");
        tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[]{art1}, new ArticuloSegundaMano[]{art2});
        Intercambio intercambio = cli1.getCartera().getIntercambiosPendientes()[0];
        assertTrue(tienda.cancelarIntercambio(cli1, intercambio));
    }

    @Test
    void hacerOfertaIntercambio_receptorNull_devuelveFalse() throws Exception {
        ClienteRegistrado cli1 = registrar("cli1");
        ArticuloSegundaMano art1 = crearArticuloDisponible(cli1, "Art1");
        // Art2 sin dueño real: cartera con cliente null no es posible en la API,
        // usamos un artículo cuyo propietario no está en la tienda
        ClienteRegistrado cli2 = new ClienteRegistrado("cli2ext", "p", c -> {});
        ArticuloSegundaMano art2 = new ArticuloSegundaMano("Art2", "d", cli2.getCartera(), "x");
        art2.disponibilizar();
        // forzamos que getDueno().getDueno() devuelva null usando reflexión
        Field f = Cartera.class.getDeclaredField("dueno");
        f.setAccessible(true);
        f.set(cli2.getCartera(), null);
        assertFalse(tienda.hacerOfertaIntercambio(cli1, new ArticuloSegundaMano[]{art1}, new ArticuloSegundaMano[]{art2}));
    }

    // ---- anadirArticulo ----

    @Test
    void anadirArticulo_valido_devuelveTrue() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        assertTrue(tienda.anadirArticulo("Art1", "desc", cli.getCartera(), new Categoria[]{}, "nada"));
    }

    // ---- solicitarValoracion ----

    @Test
    void solicitarValoracion_clienteNull_devuelveFalse() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        ArticuloSegundaMano art = crearArticuloDisponible(cli, "Art1");
        assertFalse(tienda.solicitarValoracion(null, art, "1234567890123456"));
    }

    @Test
    void solicitarValoracion_articuloNull_devuelveFalse() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        assertFalse(tienda.solicitarValoracion(cli, null, "1234567890123456"));
    }

    @Test
    void solicitarValoracion_tarjetaNull_devuelveFalse() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        ArticuloSegundaMano art = crearArticuloDisponible(cli, "Art1");
        assertFalse(tienda.solicitarValoracion(cli, art, null));
    }

    @Test
    void solicitarValoracion_articuloYaValorado_lanzaExcepcion() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        ArticuloSegundaMano art = crearArticuloDisponible(cli, "Art1");
        tienda.solicitarValoracion(cli, art, "1234567890123456");
        assertThrows(InvalidArgumentException.class,
                () -> tienda.solicitarValoracion(cli, art, "1234567890123456"));
    }

    // ---- pagarCarritoDe ----

    @Test
    void pagarCarritoDe_clienteNull_devuelveFalse() throws Exception {
        assertFalse(tienda.pagarCarritoDe(null, "1234567890123456"));
    }

    @Test
    void pagarCarritoDe_tarjetaInvalida_devuelveFalse() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        Producto p = addComic("C1");
        tienda.anadirACarritoDe(cli, p);
        assertFalse(tienda.pagarCarritoDe(cli, "0000000000000000"));
    }

    @Test
    void pagarCarritoDe_tarjetaValida_devuelveTrue() throws Exception {
        ClienteRegistrado cli = registrar("cli1");
        Producto p = addComic("C1");
        tienda.anadirACarritoDe(cli, p);
        assertTrue(tienda.pagarCarritoDe(cli, "1234567890123456"));
    }

    // ---- guardarTienda / cargarTienda ----

    @Test
    void guardarYCargarTienda_recuperaTienda() throws Exception {
        String archivo = "/tmp/tiendaTest.ser";
        tienda.registrarse("cli1", "pass", "pass");
        tienda.guardarTienda(archivo);
        Tienda cargada = Tienda.cargarTienda(archivo);
        assertNotNull(cargada);
        assertNotNull(cargada.getCliente("cli1"));
    }
}