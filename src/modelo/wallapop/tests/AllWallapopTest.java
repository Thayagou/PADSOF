package modelo.wallapop.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de tests de productos.
 */
@SelectClasses({ ValoracionTest.class, 
	ArticuloSegundaManoTest.class, 
	CarteraTest.class, 
	IntercambioTest.class})

@Suite
/**
 * Clase que ejecuta todos los tests del paquete wallapop
 * 
 * @author Tiago Oselka
 */
public class AllWallapopTest {
	/**
	 * Constructor privado para evitar instanciación.
	 */
	private AllWallapopTest() { }
}
