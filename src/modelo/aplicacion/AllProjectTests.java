package modelo.aplicacion;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import modelo.estadistica.tests.AllEstadisticaTest;
import modelo.sistema.tests.AllSistemaTest;
import modelo.usuario.tests.AllUsuarioTests;
import modelo.venta.descuentos.tests.AllDescuentosTest;
import modelo.venta.pedidos.tests.PedidoTest;
import modelo.venta.productos.tests.AllProductosTest;
import modelo.wallapop.tests.AllWallapopTest;

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