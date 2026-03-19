package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import venta.descuentos.tests.AllDescuentosTest;
import venta.pedidos.tests.PedidoTest;
import venta.productos.tests.AllProductosTest;

@SelectClasses({
    AllProductosTest.class,
    PedidoTest.class,
    AllDescuentosTest.class
})
@Suite
public class VentaTest {
}