package wallapop.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.InvalidArgumentException;
import sistema.GestorCaducidad;
import sistema.Reloj;
import sistema.Sistema;
import sistema.Tienda;
import usuario.ClienteRegistrado;
import usuario.Empleado;
import venta.productos.Categoria;
import wallapop.ArticuloSegundaMano;
import wallapop.Cartera;
import wallapop.EstadoFisicoArticulo;
import wallapop.EstadoIntercambio;
import wallapop.Intercambio;
import wallapop.Valoracion;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Clase con los tests de los métodos de la clase Intercambio
 */
class IntercambioTest {
	private Tienda tienda;
	private ClienteRegistrado emisor;
	private ClienteRegistrado receptor;
	private Cartera carteraEmisor;
	private Cartera carteraReceptor;
	private Empleado empleado;

	private Categoria cat1;
	private Categoria cat2;

	/** Artículos del emisor, disponibles para ofertar */
	private ArticuloSegundaMano[] artsEmisor;
	/** Artículos del receptor, disponibles para solicitar */
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

		artsEmisor = new ArticuloSegundaMano[3];
		artsReceptor = new ArticuloSegundaMano[3];

		for (int i = 0; i < 3; i++) {
			artsEmisor[i] = new ArticuloSegundaMano("Articulo emisor " + i, "Desc emisor " + i, carteraEmisor,
					"Cosas del receptor", cat2);
			carteraEmisor.addArticulo(artsEmisor[i]);
			Valoracion v1 = new Valoracion(artsEmisor[i]);
			v1.valorar(empleado, (i + 1) * 10, EstadoFisicoArticulo.MUY_BUENO);

			artsReceptor[i] = new ArticuloSegundaMano("Articulo receptor " + i, "Desc receptor " + i, carteraReceptor,
					"Cosas del emisor", cat1);
			carteraReceptor.addArticulo(artsReceptor[i]);
			Valoracion v2 = new Valoracion(artsReceptor[i]);
			v2.valorar(empleado, (i + 1) * 10, EstadoFisicoArticulo.MUY_BUENO);
		}
	}

	@Test
	void testConstructor() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });

		assertNotNull(intercambio);
		assertEquals(EstadoIntercambio.OFERTADO, intercambio.getEstado());
		assertEquals(carteraEmisor, intercambio.getEmisor());
		assertEquals(carteraReceptor, intercambio.getReceptor());
		assertNull(intercambio.getFechaRespuesta());
		assertNull(intercambio.getEmpleado());
		assertNull(intercambio.getFechaConfirmacion());
	}
	
	@Test
	void unicidadId() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		Intercambio intercambio2 = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[1] },
				new ArticuloSegundaMano[] { artsReceptor[1] });
		
		assertNotEquals(intercambio.getId(), intercambio2.getId());
	}
	
	@Test
	void caducidadIntercambios() throws Exception {
		Sistema.getInstancia().setTiempoCaducaOferta(Duration.ofSeconds(2));
		GestorCaducidad.getInstancia().iniciar(1, TimeUnit.SECONDS);
		
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		Intercambio intercambio2 = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[1] },
				new ArticuloSegundaMano[] { artsReceptor[1] });
		
		Thread.sleep(4000);
		
		GestorCaducidad.getInstancia().iniciar(1, TimeUnit.SECONDS);
		
		assertEquals(EstadoIntercambio.CADUCADO, intercambio.getEstado());
		assertEquals(EstadoIntercambio.CADUCADO, intercambio2.getEstado());
	}
	
	

	@Test
	void testConstructorVariosArticulos() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0], artsEmisor[1] },
				new ArticuloSegundaMano[] { artsReceptor[0], artsReceptor[1] });

		List<ArticuloSegundaMano> sol = List.of(intercambio.getSolicitados());
		List<ArticuloSegundaMano> ofr = List.of(intercambio.getOfrecidos());
		
		assertTrue(sol.contains(artsReceptor[0]));
		assertTrue(sol.contains(artsReceptor[1]));
		assertTrue(ofr.contains(artsEmisor[0]));
		assertTrue(ofr.contains(artsEmisor[1]));
	}

	@Test
	void testConstructorReservaArticulosOfrecidos() throws Exception {
		assertTrue(artsEmisor[0].isDisponible());
		new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] }, new ArticuloSegundaMano[] { artsReceptor[0] });
		assertFalse(artsEmisor[0].isDisponible());
	}

	@Test
	void testConstructorNoReservaSolicitados() throws Exception {
		assertTrue(artsReceptor[0].isDisponible());
		new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] }, new ArticuloSegundaMano[] { artsReceptor[0] });
		assertTrue(artsReceptor[0].isDisponible());
	}

	@Test
	void testConstructorSinOfrecidosLanzaExcepcion() {
		assertThrows(InvalidArgumentException.class,
				() -> new Intercambio(new ArticuloSegundaMano[] {}, new ArticuloSegundaMano[] { artsReceptor[0] }));
	}

	@Test
	void testConstructorSinSolicitadosLanzaExcepcion() {
		assertThrows(InvalidArgumentException.class,
				() -> new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] }, new ArticuloSegundaMano[] {}));
	}

	@Test
	void testConstructorOfrecidosDeDuenoDistintoLanzaExcepcion() throws Exception {
		assertThrows(InvalidArgumentException.class,
				() -> new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0], artsReceptor[0] },
						new ArticuloSegundaMano[] { artsReceptor[1] }));
	}
	
	@Test
	void testConstructorSolicitadosDeDuenoDistintoLanzaExcepcion() throws Exception {
		assertThrows(InvalidArgumentException.class,
				() -> new Intercambio(new ArticuloSegundaMano[] { artsReceptor[0] },
						new ArticuloSegundaMano[] { artsEmisor[0], artsReceptor[1] }));
	}

	@Test
	void testAceptarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });

		assertTrue(carteraReceptor.aceptarIntercambio(intercambio));
		assertEquals(EstadoIntercambio.ACEPTADO, intercambio.getEstado());
		assertNotNull(intercambio.getFechaRespuesta());
	}

	@Test
	void testAceptarYReservar() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		assertTrue(artsReceptor[0].isDisponible());
		carteraReceptor.aceptarIntercambio(intercambio);
		assertFalse(artsReceptor[0].isDisponible());
	}

	@Test
	void testAceptarIntercambioYaAceptadoExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });

		carteraReceptor.aceptarIntercambio(intercambio);

		assertThrows(InvalidArgumentException.class, () -> carteraReceptor.aceptarIntercambio(intercambio));
	}

	@Test
	void testRechazarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });

		assertTrue(carteraReceptor.rechazarIntercambio(intercambio));
		assertEquals(EstadoIntercambio.RECHAZADO, intercambio.getEstado());
		assertNotNull(intercambio.getFechaRespuesta());
	}

	@Test
	void testRechazarLiberaOfrecidos() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		assertFalse(artsEmisor[0].isDisponible());
		carteraReceptor.rechazarIntercambio(intercambio);
		assertTrue(artsEmisor[0].isDisponible());
	}

	@Test
	void testRechazarIntercambioNoOfertadoLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		carteraReceptor.rechazarIntercambio(intercambio);
		assertThrows(InvalidArgumentException.class, () -> intercambio.rechazarIntercambio());
	}

	@Test
	void testCancelarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });

		assertTrue(carteraEmisor.cancelarIntercambio(intercambio));
		assertEquals(EstadoIntercambio.CANCELADO, intercambio.getEstado());
		assertNotNull(intercambio.getFechaRespuesta());
	}

	@Test
	void testCancelarLiberaOfrecidos() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		assertFalse(artsEmisor[0].isDisponible());

		carteraEmisor.cancelarIntercambio(intercambio);
		assertTrue(artsEmisor[0].isDisponible());
	}

	@Test
	void testCancelarIntercambioNoOfertadoLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });

		assertThrows(InvalidArgumentException.class, () -> carteraEmisor.rechazarIntercambio(intercambio));
	}

	@Test
	void testCancelarIntercambioSiendoReceptor() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });

		assertThrows(InvalidArgumentException.class, ()->carteraReceptor.cancelarIntercambio(intercambio));
	}

	// Tests para comprobar localmente la caducidad
	@Test
	void testCaducarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });

		intercambio.caducar();
		
		assertTrue(artsEmisor[0].isDisponible());
		assertEquals(EstadoIntercambio.CADUCADO, intercambio.getEstado());
		assertNotNull(intercambio.getFechaRespuesta());
	}

	@Test
	void testCaducarLiberaOfrecidos() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		assertFalse(artsEmisor[0].isDisponible());
		intercambio.caducar();
		assertTrue(artsEmisor[0].isDisponible());
	}

	@Test
	void testCaducarIntercambioNoOfertadoLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		intercambio.caducar();
		assertThrows(InvalidArgumentException.class, () -> intercambio.caducar());
	}

	// ─── validarIntercambio
	// ───────────────────────────────────────────────────────

	@Test
	void testValidarIntercambio() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		intercambio.aceptarIntercambio();
		intercambio.validarIntercambio(empleado);

		assertEquals(EstadoIntercambio.CONFIRMADO, intercambio.getEstado());
		assertEquals(empleado, intercambio.getEmpleado());
		assertNotNull(intercambio.getFechaConfirmacion());
	}

	@Test
	void testValidarIntercambioEmpleadoNullLanzaExcepcion() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		intercambio.aceptarIntercambio();
		assertThrows(InvalidArgumentException.class, () -> intercambio.validarIntercambio(null));
	}

	// ─── invalidarSiSolicitaArticulos ────────────────────────────────────────────

	@Test
	void testInvalidarSiSolicitaArticulosCierto() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		// artsReceptor[0] está entre los solicitados → debe invalidarse
		boolean resultado = intercambio.invalidarSiSolicitaArticulos(new ArticuloSegundaMano[] { artsReceptor[0] });

		assertTrue(resultado);
		assertEquals(EstadoIntercambio.INVALIDADO, intercambio.getEstado());
	}

	@Test
	void testInvalidarLiberaOfrecidosCuandoInvalida() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		assertFalse(artsEmisor[0].isDisponible());
		intercambio.invalidarSiSolicitaArticulos(new ArticuloSegundaMano[] { artsReceptor[0] });
		assertTrue(artsEmisor[0].isDisponible());
	}

	@Test
	void testInvalidarSiSolicitaArticulosFalso() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		// artsReceptor[2] NO está entre los solicitados → no se invalida
		boolean resultado = intercambio.invalidarSiSolicitaArticulos(new ArticuloSegundaMano[] { artsReceptor[2] });

		assertFalse(resultado);
		assertEquals(EstadoIntercambio.OFERTADO, intercambio.getEstado());
	}

	@Test
	void testInvalidarNoAfectaIntercambioNoOfertado() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		intercambio.aceptarIntercambio(); // ya no está OFERTADO
		boolean resultado = intercambio.invalidarSiSolicitaArticulos(new ArticuloSegundaMano[] { artsReceptor[0] });

		assertFalse(resultado);
		assertEquals(EstadoIntercambio.ACEPTADO, intercambio.getEstado());
	}

	@Test
	void testSetEstado() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		intercambio.setEstado(EstadoIntercambio.CANCELADO);
		assertEquals(EstadoIntercambio.CANCELADO, intercambio.getEstado());
	}

	@Test
	void testToStringContieneEmisor() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		assertTrue(intercambio.toString().contains("Emisor"));
	}

	@Test
	void testToStringContieneReceptor() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		assertTrue(intercambio.toString().contains("Receptor"));
	}

	@Test
	void testToStringContieneEmpleadoTrasConfirmar() throws Exception {
		Intercambio intercambio = new Intercambio(new ArticuloSegundaMano[] { artsEmisor[0] },
				new ArticuloSegundaMano[] { artsReceptor[0] });
		intercambio.aceptarIntercambio();
		intercambio.validarIntercambio(empleado);
		assertTrue(intercambio.toString().contains("Empleado"));
	}
}
