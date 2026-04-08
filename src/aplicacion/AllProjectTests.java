package aplicacion;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import estadistica.tests.AllEstadisticaTest;
import sistema.tests.AllSistemaTest;
import venta.descuentos.tests.AllDescuentosTest;
import venta.pedidos.tests.PedidoTest;
import venta.productos.tests.AllProductosTest;
import wallapop.tests.AllWallapopTest;
import usuario.tests.AllUsuarioTests;

/**
 * Suite de tests que ejecuta todos los tests del proyecto
 * 
 * @author Tiago Oselka
 */
@Suite
@SelectClasses({ AllSistemaTest.class, AllEstadisticaTest.class, AllDescuentosTest.class, PedidoTest.class,
		AllProductosTest.class, AllUsuarioTests.class, AllWallapopTest.class })
public class AllProjectTests {
	/**
	 * Constructor privado para evitar instanciación.
	 */
	private AllProjectTests() {
	}
}