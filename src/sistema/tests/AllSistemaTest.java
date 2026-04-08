package sistema.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de tests del paquete sistema
 * @author Claudia Saiz
 */
@Suite
@SelectClasses({ AlmacenTest.class, AsignadorIdTest.class, GestorCaducidadTest.class, RelojTest.class,
		SistemaTest.class, TiendaTest.class })

/**
 * Clase que ejecuta todos los tests del paquete sistema
 * 
 * @author Claudia Saiz
 */
public class AllSistemaTest {
	/**
	 * Constructor privado para evitar instanciación.
	 */
	private AllSistemaTest() { }
}
