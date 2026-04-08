package sistema.tests;

/**
 * Clase que ejecuta todos los tests del paquete sistema
 * 
 * @author Claudia Saiz
 */
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de tests del paquete sistema
 */
@Suite
@SelectClasses({ AlmacenTest.class, AsignadorIdTest.class, GestorCaducidadTest.class, RelojTest.class,
		SistemaTest.class, TiendaTest.class })
class AllSistemaTest {

}
