package venta.descuentos.tests;

/**
 * Clase que ejecuta todos los tests del paquete descuentos
 * 
 * @author Juan Ibáñez
 */
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de tests de descuentos
 */
@SelectClasses({ DescuentoTest.class, DescuentoSubTest.class })
@Suite
public class AllDescuentosTest {
	
}