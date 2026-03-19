package venta.descuentos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.*;

import venta.descuentos.*;
import venta.productos.*;

class DescuentoTest {

    private Descuento descPorc;
    private Descuento descReg;

    @BeforeEach
    void setUp() throws Exception {
    	Producto p = new Comic("Spiderman", "Comic de Spiderman", 10.0, null, LocalDate.of(2020, 1, 1), "Stan Lee", 100, "Marvel");
        descPorc = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10);
        descReg = new DescuentoRegalo(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, p);
    }

    // --- Constructor ---

    @Test
    void testConstructorValido() {
        assertNotNull(descPorc);
        assertEquals(CondicionDescuento.SIN_CONDICION, descPorc.getCondicion());
        assertEquals(0, descPorc.getValorMin());
        assertEquals(LocalDateTime.MIN, descPorc.getInicio());
        assertEquals(LocalDateTime.MAX, descPorc.getFin());
        assertFalse(descPorc.isCaducado());
        assertTrue(descPorc.isVigente());
    }

    @Test
    void testConstructorValorMinNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new DescuentoPorcentaje(-1, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10));
    }

    @Test
    void testConstructorInicioNull() {
        assertThrows(IllegalArgumentException.class, () -> new DescuentoPorcentaje(0, null, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10));
    }

    @Test
    void testConstructorFinNull() {
        assertThrows(IllegalArgumentException.class, () -> new DescuentoPorcentaje(0, LocalDateTime.MIN, null, CondicionDescuento.SIN_CONDICION, 10));
    }

    @Test
    void testConstructorCondicionNull() {
        assertThrows(IllegalArgumentException.class, () -> new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, null, 10));
    }

    // --- getId ---

    @Test
    void testGetIdUnico() {
        Descuento otro = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10);
        assertNotEquals(descPorc.getId(), otro.getId());
    }

    // --- isVigente ---

    @Test
    void testIsVigenteActivo() {
        assertTrue(descPorc.isVigente());
    }

    @Test
    void testIsVigenteNoEmpezado() {
        Descuento noEmpezado = new DescuentoPorcentaje(0, LocalDateTime.now().plusHours(1), LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10);
        assertFalse(noEmpezado.isVigente());
    }

    @Test
    void testIsVigenteFinalizado() {
        descPorc.finalizarDescuento();
        assertFalse(descPorc.isVigente());
    }

    @Test
    void testIsVigenteCaducado() {
        Descuento caducado = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MIN, CondicionDescuento.SIN_CONDICION, 10);
        assertFalse(caducado.isVigente());
    }

    // --- isCaducado ---

    @Test
    void testIsCaducadoFalse() {
        assertFalse(descPorc.isCaducado());
    }

    @Test
    void testIsCaducadoTrue() {
        Descuento caducado = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MIN, CondicionDescuento.SIN_CONDICION, 10);
        assertTrue(caducado.isCaducado());
    }

    @Test
    void testIsCaducadoMarcaComoFinalizado() {
        Descuento caducado = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MIN, CondicionDescuento.SIN_CONDICION, 10);
        caducado.isCaducado();
        assertFalse(caducado.isVigente());
    }

    // --- finalizarDescuento ---

    @Test
    void testFinalizarDescuento() {
        descPorc.finalizarDescuento();
        assertFalse(descPorc.isVigente());
    }

    // --- cumpleCondiciones ---

    @Test
    void testCumpleCondicionesSinCondicion() {
        assertTrue(descPorc.cumpleCondiciones(1, 10.0));
    }

    @Test
    void testCumpleCondicionesNoVigente() {
        descPorc.finalizarDescuento();
        assertFalse(descPorc.cumpleCondiciones(1, 10.0));
    }

    @Test
    void testCumpleCondicionesCantidadSuficiente() {
        Descuento dCantidad = new DescuentoPorcentaje(3, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.CANTIDAD, 10);
        assertTrue(dCantidad.cumpleCondiciones(3, 0));
    }

    @Test
    void testCumpleCondicionesCantidadInsuficiente() {
        Descuento dCantidad = new DescuentoPorcentaje(3, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.CANTIDAD, 10);
        assertFalse(dCantidad.cumpleCondiciones(2, 0));
    }

    @Test
    void testCumpleCondicionesVolumenSuficiente() {
        Descuento dVolumen = new DescuentoPorcentaje(50, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.VOLUMEN, 10);
        assertTrue(dVolumen.cumpleCondiciones(1, 50.0));
    }

    @Test
    void testCumpleCondicionesVolumenInsuficiente() {
        Descuento dVolumen = new DescuentoPorcentaje(50, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.VOLUMEN, 10);
        assertFalse(dVolumen.cumpleCondiciones(1, 49.0));
    }

    // --- getPrecioDescontado (base, sin override) ---
    
    @Test
    void testGetPrecioDescontadoBaseDevuelvePrecio() {
        // La implementación base de Descuento devuelve el precio sin modificar
        assertEquals(10.0, descReg.getPrecioDescontado(1, 0, 10.0));
    }

    @Test
    void testGetPrecioDescontadoNumUdsNegativo() {
        assertThrows(IllegalArgumentException.class, () -> descPorc.getPrecioDescontado(-1, 0, 10.0));
    }

    @Test
    void testGetPrecioDescontadoVolumenNegativo() {
        assertThrows(IllegalArgumentException.class, () -> descPorc.getPrecioDescontado(1, -1, 10.0));
    }

    @Test
    void testGetPrecioDescontadoPrecioNegativo() {
        assertThrows(IllegalArgumentException.class, () -> descPorc.getPrecioDescontado(1, 0, -1.0));
    }

    // --- getRegalo (base, sin override) ---

    @Test
    void testGetRegaloBaseDevuelveNull() {
        assertNull(descPorc.getRegalo(1, 10.0));
    }

    @Test
    void testGetRegaloNumUdsNegativo() {
        assertThrows(IllegalArgumentException.class, () -> descPorc.getRegalo(-1, 10.0));
    }

    @Test
    void testGetRegaloVolumenNegativo() {
        assertThrows(IllegalArgumentException.class, () -> descPorc.getRegalo(1, -1.0));
    }

    // --- equals ---

    @Test
    void testEqualsMismoObjeto() {
        assertEquals(descPorc, descPorc);
    }

    @Test
    void testEqualsDistintoDescuento() {
        Descuento otro = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10);
        assertNotEquals(descPorc, otro);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(descPorc, null);
    }
}