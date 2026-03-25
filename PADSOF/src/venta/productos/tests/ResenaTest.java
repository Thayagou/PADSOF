package venta.productos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.*;

import usuario.*;
import venta.productos.*;
import exceptions.*;

/**
 * Clase con los tests de la clase Resena
 * 
 * @author Juan Ibáñez
 */
class ResenaTest {

    private ClienteRegistrado usuario;
    private Resena resena;

    @BeforeEach
    void setUp() throws Exception {
        usuario = new ClienteRegistrado("Cliente1", "pass");
        resena = new Resena(4.0, "Muy bueno", usuario);
    }

    // --- Constructor ---

    @Test
    void testConstructorValido() {
        assertNotNull(resena);
        assertEquals(4.0, resena.getPuntuacion());
        assertEquals("Muy bueno", resena.getComentario());
        assertEquals(usuario, resena.getUsuario());
        assertEquals(LocalDate.now(), resena.getFecha());
    }

    @Test
    void testConstructorComentarioNull() {
        assertThrows(InvalidArgumentException.class, () -> new Resena(4.0, null, usuario));
    }

    @Test
    void testConstructorUsuarioNull() {
        assertThrows(InvalidArgumentException.class, () -> new Resena(4.0, "Muy bueno", null));
    }

    @Test
    void testConstructorPuntuacionNegativa() {
        assertThrows(InvalidArgumentException.class, () -> new Resena(-1.0, "Muy bueno", usuario));
    }

    @Test
    void testConstructorPuntuacionMayorDeCinco() {
        assertThrows(InvalidArgumentException.class, () -> new Resena(5.1, "Muy bueno", usuario));
    }

    @Test
    void testConstructorPuntuacionCero() {
        assertDoesNotThrow(() -> new Resena(0.0, "Muy malo", usuario));
    }

    @Test
    void testConstructorPuntuacionCinco() {
        assertDoesNotThrow(() -> new Resena(5.0, "Perfecto", usuario));
    }

    // --- Getters ---

    @Test
    void testGetPuntuacion() {
        assertEquals(4.0, resena.getPuntuacion());
    }

    @Test
    void testGetComentario() {
        assertEquals("Muy bueno", resena.getComentario());
    }

    @Test
    void testGetUsuario() {
        assertEquals(usuario, resena.getUsuario());
    }

    @Test
    void testGetFechaEsHoy() {
        assertEquals(LocalDate.now(), resena.getFecha());
    }
}