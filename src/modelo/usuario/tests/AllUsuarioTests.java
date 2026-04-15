package modelo.usuario.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de tests que ejecuta todos los tests del paquete usuario
 * @author Claudia Saiz
 */
@Suite
@SelectClasses({ CarritoTest.class, ClienteRegistradoTest.class, EmpleadoTest.class, GestorTest.class,
		NotificacionTest.class })

/**
 * Clase que ejecuta todos los tests del paquete usuario
 * 
 * @author Claudia Saiz
 */
public class AllUsuarioTests {
	/**
	 * Constructor privado para evitar instanciación.
	 */
	private AllUsuarioTests() { };
}
