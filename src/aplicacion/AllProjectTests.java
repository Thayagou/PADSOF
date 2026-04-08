package aplicacion;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import estadistica.tests.AllEstadisticaTest;
import sistema.tests.AllSistemaTest;
import venta.descuentos.tests.*;
import venta.pedidos.tests.*;
import venta.productos.tests.AllProductosTest;
import wallapop.tests.AllWallapopTest;
import usuario.tests.*;

/**
 * Suite de tests que ejecuta todos los tests del proyecto
 * @author Tiago Oselka
 */
@Suite
@SelectClasses({ AllSistemaTest.class, AllEstadisticaTest.class, AllDescuentosTest.class, PedidoTest.class,  AllProductosTest.class, AllUsuarioTests.class, AllWallapopTest.class})

/**
 * Clase que ejecuta todos los tests del proyecto
 * 
 * @author Tiago Oselka
 */
class AllProjectTest {
	/**
	 * Constructor privado para evitar instanciación.
	 */
	private AllProjectTest() { };
}