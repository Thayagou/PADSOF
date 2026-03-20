package venta.productos.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@SelectClasses({
    ProductoTest.class,
    ProductoSubTest.class,
    CategoriaTest.class,
    StockTest.class,
    StockExternoTest.class,
    ResenaTest.class
})
@Suite
public class AllProductosTest {
}