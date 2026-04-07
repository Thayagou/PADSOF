package venta.productos.tests;

/**
 * Clase que ejecuta todos los tests del paquete productos
 * 
 * @author Juan Ibáñez
 */
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de tests de productos.
 */
@SelectClasses({ ProductoTest.class, ProductoSubTest.class, CategoriaTest.class, StockTest.class,
		StockExternoTest.class, ResenaTest.class })
@Suite
public class AllProductosTest {
	
	/**
     * Constructor privado para evitar instanciación.
     */
    private AllProductosTest() {
    }
}