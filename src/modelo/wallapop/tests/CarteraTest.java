package modelo.wallapop.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Empleado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import modelo.wallapop.Cartera;
import modelo.wallapop.EstadoFisicoArticulo;
import modelo.wallapop.EstadoIntercambio;
import modelo.wallapop.Intercambio;
import modelo.wallapop.Valoracion;

import java.util.*;

class CarteraTest {

	private Tienda tienda;
	private ClienteRegistrado emisor;
	private ClienteRegistrado receptor;
	private Cartera carteraEmisor;
	private Cartera carteraReceptor;
	private Empleado empleado;
	private Categoria cat1;
	private Categoria cat2;
	private ArticuloSegundaMano[] artsEmisor;
	private ArticuloSegundaMano[] artsReceptor;

	@BeforeEach
	void setUp() throws Exception {
		tienda = new Tienda();
		emisor = new ClienteRegistrado("Emisor", "Emisor123", tienda);
		receptor = new ClienteRegistrado("Receptor", "Receptor123", tienda);
		carteraEmisor = emisor.getCartera();
		carteraReceptor = receptor.getCartera();

		empleado = new Empleado("Empleado", "pass");

		cat1 = new Categoria("Juego de mesa");
		cat2 = new Categoria("Cartas");

		artsEmisor = new ArticuloSegundaMano[4];
		artsReceptor = new ArticuloSegundaMano[3];

		for (int i = 0; i < 3; i++) {
			artsEmisor[i] = new ArticuloSegundaMano("Articulo emisor " + i, "Desc emisor " + i, carteraEmisor, "Cosas del receptor", cat2);
			carteraEmisor.addArticulo(artsEmisor[i]);
			Valoracion v1 = new Valoracion(artsEmisor[i]);
			v1.valorar(empleado, (i + 1) * 10, EstadoFisicoArticulo.MUY_BUENO);

			artsReceptor[i] = new ArticuloSegundaMano("Articulo receptor " + i, "Desc receptor " + i, carteraReceptor, "Cosas del emisor", cat1);
			carteraReceptor.addArticulo(artsReceptor[i]);
			Valoracion v2 = new Valoracion(artsReceptor[i]);
			v2.valorar(empleado, (i + 1) * 10, EstadoFisicoArticulo.MUY_BUENO);
		}
		
		artsEmisor[3] = new ArticuloSegundaMano("Articulo emisor 4", "Desc emisor 4", carteraEmisor, "Cosas del receptor", cat2);
	}

	@Test
	void testGetDueno() {
		assertEquals(emisor, carteraEmisor.getDueno());
	}

	@Test
	void testAddArticuloValidoYGetArticulos() throws Exception {
		List<ArticuloSegundaMano> arts = List.of(carteraEmisor.getArticulos());
		for (int i = 0; i < 3; i++) {
			assertTrue(arts.contains(artsEmisor[i]));
		}
	}

	@Test
	void testAddArticuloDuenoInvalido() {
		assertThrows(InvalidArgumentException.class, () -> carteraEmisor.addArticulo(artsReceptor[0]));
	}

	@Test
	void testGetArticulosDisponibles() {
		List<ArticuloSegundaMano> arts = List.of(carteraEmisor.getArticulosDisponibles());
		for (int i = 0; i < 3; i++) {
			assertTrue(arts.contains(artsEmisor[i]));
		}
		assertFalse(arts.contains(artsEmisor[3]));
	}

	@Test
	void testGetArticulosDisponiblesConReservados() throws Exception {
		new Intercambio(new ArticuloSegundaMano[]{artsEmisor[0]}, new ArticuloSegundaMano[]{artsReceptor[0]});
		List<ArticuloSegundaMano> arts = List.of(carteraEmisor.getArticulosDisponibles());
		for (int i = 1; i < 3; i++) {
			assertTrue(arts.contains(artsEmisor[i]));
		}
		assertFalse(arts.contains(artsEmisor[0]));
		assertFalse(arts.contains(artsEmisor[3]));
	}

	@Test
	void testGetArticulosDisponiblesTodosReservados() throws Exception {
		new Intercambio(artsEmisor, new ArticuloSegundaMano[]{artsReceptor[0]});
		List<ArticuloSegundaMano> arts = List.of(carteraEmisor.getArticulosDisponibles());
		for (int i = 0; i < 3; i++) {
			assertFalse(arts.contains(artsEmisor[i]));
		}
		
		assertEquals(0, carteraEmisor.getArticulosDisponibles().length);
	}

	@Test
	void testAddIntercambioAmbos() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		
		List<Intercambio> pendientesEm = List.of(carteraEmisor.getIntercambiosPendientes());
		assertTrue(pendientesEm.contains(intercambio));
		
		List<Intercambio> pendientesRec = List.of(carteraReceptor.getIntercambiosPendientes());
		assertTrue(pendientesRec.contains(intercambio));
	}

	@Test
	void testAddIntercambioOtroLanzaExcepcion() throws Exception {
		ClienteRegistrado otro = new ClienteRegistrado("otro", "otro123", tienda);
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});

		assertThrows(InvalidArgumentException.class, () -> otro.getCartera().addIntercambio(intercambio));
	}

	@Test
	void testGetIntercambiosPendientes() throws Exception {
		Intercambio i = new Intercambio(new ArticuloSegundaMano[]{artsEmisor[0]}, new ArticuloSegundaMano[]{artsReceptor[0]});
		List<Intercambio> intercambios = List.of(carteraEmisor.getIntercambiosPendientes());
		assertTrue(intercambios.contains(i));
	}

	@Test
	void testGetIntercambiosPendientesTrasAceptar() throws Exception {
		Intercambio i = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		carteraReceptor.aceptarIntercambio(i);
		List<Intercambio> intercambios = List.of(carteraEmisor.getIntercambiosPendientes());
		assertFalse(intercambios.contains(i));
	}

	@Test
	void testAceptarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertTrue(carteraReceptor.aceptarIntercambio(intercambio));
		assertEquals(EstadoIntercambio.ACEPTADO, intercambio.getEstado());
	}

	@Test
	void testAceptarEmisorLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertThrows(InvalidArgumentException.class, () -> carteraEmisor.aceptarIntercambio(intercambio));
	}

	@Test
	void testRechazarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertTrue(carteraReceptor.rechazarIntercambio(intercambio));
		assertEquals(EstadoIntercambio.RECHAZADO, intercambio.getEstado());
	}

	@Test
	void testRechazarSiendoEmisorLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertThrows(InvalidArgumentException.class, () -> carteraEmisor.rechazarIntercambio(intercambio));
	}

	@Test
	void testRechazarSiendoOtroLanzaExcepcion() throws Exception {
		ClienteRegistrado otro = new ClienteRegistrado("otro", "otro123", tienda);
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertThrows(InvalidArgumentException.class, () -> otro.getCartera().rechazarIntercambio(intercambio));
	}

	@Test
	void testCancelarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertTrue(carteraEmisor.cancelarIntercambio(intercambio));
		assertEquals(EstadoIntercambio.CANCELADO, intercambio.getEstado());
	}

	@Test
	void testCancelarSiendoReceptorLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertThrows(InvalidArgumentException.class, () -> carteraReceptor.cancelarIntercambio(intercambio));
	}

	@Test
	void testCancelarSiendoOtroLanzaExcepcion() throws Exception {
		ClienteRegistrado otro = new ClienteRegistrado("otro", "otro123", tienda);
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});
		assertThrows(InvalidArgumentException.class, () -> otro.getCartera().cancelarIntercambio(intercambio));
	}

	@Test
	void testInvalidarIntercambiosConArticulo() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});

		List<Intercambio> intercambiosInvalidados = List.of( carteraReceptor.invalidarIntercambiosConArticulos(new ArticuloSegundaMano[]{artsReceptor[0]}));
		assertEquals(EstadoIntercambio.INVALIDADO, intercambio.getEstado());
		assertTrue(intercambiosInvalidados.contains(intercambio));
	}

	@Test
	void testInvalidarIntercambiosConArticuloNoPerteneciente() throws Exception {
		Intercambio intercambio = new Intercambio(
				new ArticuloSegundaMano[]{artsEmisor[0]},
				new ArticuloSegundaMano[]{artsReceptor[0]});

		List<Intercambio> intercambiosInvalidados = List.of( carteraReceptor.invalidarIntercambiosConArticulos(new ArticuloSegundaMano[]{artsReceptor[1]}));
		assertNotEquals(EstadoIntercambio.INVALIDADO, intercambio.getEstado());
		assertFalse(intercambiosInvalidados.contains(intercambio));
	}

	@Test
	void testInvalidarVariosIntercambios() throws Exception {
		Intercambio i1 = new Intercambio(new ArticuloSegundaMano[]{artsEmisor[0]}, new ArticuloSegundaMano[]{artsReceptor[0]});
		Intercambio i2 = new Intercambio(new ArticuloSegundaMano[]{artsEmisor[1]}, new ArticuloSegundaMano[]{artsReceptor[1]});
		carteraReceptor.invalidarIntercambiosConArticulos(new ArticuloSegundaMano[]{artsReceptor[0], artsReceptor[1]});

		assertEquals(EstadoIntercambio.INVALIDADO, i1.getEstado());
		assertEquals(EstadoIntercambio.INVALIDADO, i2.getEstado());
	}
}