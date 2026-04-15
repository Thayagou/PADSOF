package modelo.estadistica.tests;

/**
 * Clase que ejecuta todos los tests del paquete estadistica
 * 
 * @author Tiago Oselka
 */
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de tests de productos.
 */
@SelectClasses({ StatsMensualTest.class, 
	StatsUsuarioTest.class, 
	StatsProductoTest.class, 
	HistorialTest.class})

@Suite
public class AllEstadisticaTest {
	/**
	 * Constructor privado para evitar instanciación.
	 */
	private AllEstadisticaTest() {
	}
}
