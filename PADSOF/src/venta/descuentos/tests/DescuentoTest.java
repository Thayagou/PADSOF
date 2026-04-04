package venta.descuentos.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.*;

import venta.descuentos.*;
import venta.productos.*;
import exceptions.*;
import sistema.Reloj;

/**
 * Clase con los tests de los métodos de la clase Descuento
 * 
 * @author Juan Ibáñez
 */
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
    void testConstructorValido() throws Exception {
        assertNotNull(descPorc);
        assertEquals(CondicionDescuento.SIN_CONDICION, descPorc.getCondicion());
        assertEquals(0, descPorc.getValorMin());
        assertEquals(LocalDateTime.MIN, descPorc.getInicio());
        assertEquals(LocalDateTime.MAX, descPorc.getFin());
        assertFalse(descPorc.isCaducado());
        assertTrue(descPorc.isVigente());
    }

    @Test
    void testConstructorValorMinNegativo() throws Exception {
        assertThrows(InvalidArgumentException.class, () -> new DescuentoPorcentaje(-1, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10));
    }

    @Test
    void testConstructorInicioNull() throws Exception {
        assertThrows(InvalidArgumentException.class, () -> new DescuentoPorcentaje(0, null, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10));
    }

    @Test
    void testConstructorFinNull() throws Exception {
        assertThrows(InvalidArgumentException.class, () -> new DescuentoPorcentaje(0, LocalDateTime.MIN, null, CondicionDescuento.SIN_CONDICION, 10));
    }

    @Test
    void testConstructorCondicionNull() throws Exception {
        assertThrows(InvalidArgumentException.class, () -> new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, null, 10));
    }

    // --- getId ---

    @Test
    void testGetIdUnico() throws Exception {
        Descuento otro = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10);
        assertNotEquals(descPorc.getId(), otro.getId());
    }

    // --- isVigente ---

    @Test
    void testIsVigenteActivo() throws Exception {
        assertTrue(descPorc.isVigente());
    }

    @Test
    void testIsVigenteNoEmpezado() throws Exception {
        Descuento noEmpezado = new DescuentoPorcentaje(0, Reloj.now().plusHours(1), LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10);
        assertFalse(noEmpezado.isVigente());
    }

    @Test
    void testIsVigenteFinalizado() throws Exception {
        descPorc.finalizarDescuento();
        assertFalse(descPorc.isVigente());
    }

    @Test
    void testIsVigenteCaducado() throws Exception {
        Descuento caducado = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MIN, CondicionDescuento.SIN_CONDICION, 10);
        assertFalse(caducado.isVigente());
    }

    // --- isCaducado ---

    @Test
    void testIsCaducadoFalse() throws Exception {
        assertFalse(descPorc.isCaducado());
    }

    @Test
    void testIsCaducadoTrue() throws Exception {
        Descuento caducado = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MIN, CondicionDescuento.SIN_CONDICION, 10);
        assertTrue(caducado.isCaducado());
    }

    @Test
    void testIsCaducadoMarcaComoFinalizado() throws Exception {
        Descuento caducado = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MIN, CondicionDescuento.SIN_CONDICION, 10);
        caducado.isCaducado();
        assertFalse(caducado.isVigente());
    }

    // --- finalizarDescuento ---

    @Test
    void testFinalizarDescuento() throws Exception {
        descPorc.finalizarDescuento();
        assertFalse(descPorc.isVigente());
    }

    // --- cumpleCondiciones ---

    @Test
    void testCumpleCondicionesSinCondicion() throws Exception {
        assertTrue(descPorc.cumpleCondiciones(1, 10.0));
    }

    @Test
    void testCumpleCondicionesNoVigente() throws Exception {
        descPorc.finalizarDescuento();
        assertFalse(descPorc.cumpleCondiciones(1, 10.0));
    }

    @Test
    void testCumpleCondicionesCantidadSuficiente() throws Exception {
        Descuento dCantidad = new DescuentoPorcentaje(3, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.CANTIDAD, 10);
        assertTrue(dCantidad.cumpleCondiciones(3, 0));
    }

    @Test
    void testCumpleCondicionesCantidadInsuficiente() throws Exception {
        Descuento dCantidad = new DescuentoPorcentaje(3, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.CANTIDAD, 10);
        assertFalse(dCantidad.cumpleCondiciones(2, 0));
    }

    @Test
    void testCumpleCondicionesVolumenSuficiente() throws Exception {
        Descuento dVolumen = new DescuentoPorcentaje(50, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.VOLUMEN, 10);
        assertTrue(dVolumen.cumpleCondiciones(1, 50.0));
    }

    @Test
    void testCumpleCondicionesVolumenInsuficiente() throws Exception {
        Descuento dVolumen = new DescuentoPorcentaje(50, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.VOLUMEN, 10);
        assertFalse(dVolumen.cumpleCondiciones(1, 49.0));
    }

    // --- getPrecioDescontado (base, sin override) ---
    
    @Test
    void testGetPrecioDescontadoBaseDevuelvePrecio() throws Exception {
        // La implementación base de Descuento devuelve el precio sin modificar
        assertEquals(10.0, descReg.getPrecioDescontado(1, 0, 10.0));
    }

    @Test
    void testGetPrecioDescontadoNumUdsNegativo() throws Exception {
        assertThrows(InvalidArgumentException.class, () -> descPorc.getPrecioDescontado(-1, 0, 10.0));
    }

    @Test
    void testGetPrecioDescontadoVolumenNegativo() throws Exception {
        assertThrows(InvalidArgumentException.class, () -> descPorc.getPrecioDescontado(1, -1, 10.0));
    }

    @Test
    void testGetPrecioDescontadoPrecioNegativo() throws Exception {
        assertThrows(InvalidArgumentException.class, () -> descPorc.getPrecioDescontado(1, 0, -1.0));
    }

    // --- getRegalo (base, sin override) ---

    @Test
    void testGetRegaloBaseDevuelveNull() throws Exception {
        assertNull(descPorc.getRegalo(1, 10.0));
    }

    @Test
    void testGetRegaloNumUdsNegativo() throws Exception {
        assertThrows(InvalidArgumentException.class, () -> descPorc.getRegalo(-1, 10.0));
    }

    @Test
    void testGetRegaloVolumenNegativo() throws Exception {
        assertThrows(InvalidArgumentException.class, () -> descPorc.getRegalo(1, -1.0));
    }

    // --- equals ---

    @Test
    void testEqualsMismoObjeto() throws Exception {
        assertEquals(descPorc, descPorc);
    }

    @Test
    void testEqualsDistintoDescuento() throws Exception {
        Descuento otro = new DescuentoPorcentaje(0, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.SIN_CONDICION, 10);
        assertNotEquals(descPorc, otro);
    }

    @Test
    void testEqualsNull() throws Exception {
        assertNotEquals(descPorc, null);
    }
}