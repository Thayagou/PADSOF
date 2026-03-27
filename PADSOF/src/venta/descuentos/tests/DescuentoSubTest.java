package venta.descuentos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.*;

import venta.descuentos.*;
import venta.productos.*;
import exceptions.*;

/**
 * Clase con los tests de los métodos de las subclases de Descuento
 * 
 * @author Juan Ibáñez
 */
class DescuentoSubTest {

    private Producto regalo;

    @BeforeEach
    void setUp() throws Exception {
        regalo = new Figura("Batman", "Figura de Batman", 12.0, null, "20x12 cm", "DC", "Plastico");
    }

    // --- DescuentoPorcentaje constructor ---

    @Test
    void testDescuentoPorcentajeValido() {
        assertDoesNotThrow(() -> new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10));
    }

    @Test
    void testDescuentoPorcentajeCero() {
        assertDoesNotThrow(() -> new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 0));
    }

    @Test
    void testDescuentoPorcentajeCien() {
        assertDoesNotThrow(() -> new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 100));
    }

    @Test
    void testDescuentoPorcentajeNegativo() {
        assertThrows(InvalidArgumentException.class, () -> new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, -1));
    }

    @Test
    void testDescuentoPorcentajeMayorDeCien() {
        assertThrows(InvalidArgumentException.class, () -> new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 101));
    }

    // --- DescuentoPorcentaje getPrecioDescontado ---

    @Test
    void testDescuentoPorcentajeAplicado() throws Exception {
        Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10);
        assertEquals(9.0, d.getPrecioDescontado(1, 0, 10.0));
    }

    @Test
    void testDescuentoPorcentajeNoAplicadoPorCondicion() throws Exception {
        Descuento d = new DescuentoPorcentaje(5, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.CANTIDAD, 10);
        assertEquals(10.0, d.getPrecioDescontado(3, 0, 10.0));
    }

    @Test
    void testDescuentoPorcentajeCienPorCien() throws Exception {
        Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 100);
        assertEquals(0.0, d.getPrecioDescontado(1, 0, 10.0));
    }

    @Test
    void testDescuentoPorcentajeNoVigente() throws Exception {
        Descuento d = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10);
        d.finalizarDescuento();
        assertEquals(10.0, d.getPrecioDescontado(1, 0, 10.0));
    }

    // --- DescuentoDinero constructor ---

    @Test
    void testDescuentoDineroValido() {
        assertDoesNotThrow(() -> new DescuentoDinero(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 5.0));
    }

    @Test
    void testDescuentoDineroCero() {
        assertDoesNotThrow(() -> new DescuentoDinero(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 0.0));
    }

    @Test
    void testDescuentoDineroNegativo() {
        assertThrows(InvalidArgumentException.class, () -> new DescuentoDinero(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, -1.0));
    }

    // --- DescuentoDinero getPrecioDescontado ---

    @Test
    void testDescuentoDineroAplicado() throws Exception {
        Descuento d = new DescuentoDinero(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 4.0);
        // descuento de 4€ repartido entre 2 uds = 2€ por unidad → 10 - 2 = 8
        assertEquals(8.0, d.getPrecioDescontado(2, 0, 10.0));
    }

    @Test
    void testDescuentoDineroNoAplicadoPorCondicion() throws Exception {
        Descuento d = new DescuentoDinero(5, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.CANTIDAD, 4.0);
        assertEquals(10.0, d.getPrecioDescontado(3, 0, 10.0));
    }

    @Test
    void testDescuentoDineroNuncaNegativo() throws Exception {
        // El descuento es mayor que el precio, debe devolver 0
        Descuento d = new DescuentoDinero(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 100.0);
        assertEquals(0.0, d.getPrecioDescontado(1, 0, 10.0));
    }

    @Test
    void testDescuentoDineroNoVigente() throws Exception {
        Descuento d = new DescuentoDinero(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 4.0);
        d.finalizarDescuento();
        assertEquals(10.0, d.getPrecioDescontado(1, 0, 10.0));
    }

    // --- DescuentoRegalo constructor ---

    @Test
    void testDescuentoRegaloValido() {
        assertDoesNotThrow(() -> new DescuentoRegalo(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, regalo));
    }

    @Test
    void testDescuentoRegaloNull() {
        assertThrows(InvalidArgumentException.class, () -> new DescuentoRegalo(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, null));
    }

    // --- DescuentoRegalo getRegalo ---

    @Test
    void testDescuentoRegaloDevuelveRegalo() throws Exception {
        Descuento d = new DescuentoRegalo(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, regalo);
        assertEquals(regalo, d.getRegalo(1, 0));
    }

    @Test
    void testDescuentoRegaloNoAplicadoPorCondicion() throws Exception {
        Descuento d = new DescuentoRegalo(5, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.CANTIDAD, regalo);
        assertNull(d.getRegalo(3, 0));
    }

    @Test
    void testDescuentoRegaloNoVigente() throws Exception {
        Descuento d = new DescuentoRegalo(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, regalo);
        d.finalizarDescuento();
        assertNull(d.getRegalo(1, 0));
    }

    @Test
    void testDescuentoRegaloNumUdsNegativo() throws Exception {
        Descuento d = new DescuentoRegalo(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, regalo);
        assertThrows(InvalidArgumentException.class, () -> d.getRegalo(-1, 0));
    }

    @Test
    void testDescuentoRegaloVolumenNegativo() throws Exception {
        Descuento d = new DescuentoRegalo(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, regalo);
        assertThrows(InvalidArgumentException.class, () -> d.getRegalo(1, -1.0));
    }
}