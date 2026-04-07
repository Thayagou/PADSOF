package wallapop.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.InvalidArgumentException;

import java.util.*;
import venta.productos.Categoria;

import usuario.ClienteRegistrado;
import usuario.Empleado;
import wallapop.ArticuloSegundaMano;
import wallapop.Cartera;
import wallapop.EstadoFisicoArticulo;
import wallapop.Valoracion;

/**
 * Clase con los tests de los métodos de la clase ArticuloSegundaMano
 */
class ArticuloSegundaManoTest {

	private ClienteRegistrado cliente;
	private Cartera cartera;
	private Categoria cat1;
	private Categoria cat2;
	private ArticuloSegundaMano articulo;

	@BeforeEach
	void setUp() throws Exception {
		cliente = new ClienteRegistrado("Usuario", "Pass");
		cartera = cliente.getCartera();
		cat1 = new Categoria("Juego de mesa");
		cat2 = new Categoria("Cartas");
		articulo = new ArticuloSegundaMano("Catan", "Juego de mesa muy divertido", cartera, "Juegos de cartas", cat1, cat2);
	}

	// ─── Constructor ────────────────────────────────────────────────────────────

	@Test
	void testConstructor() {
		assertNotNull(articulo);
		assertEquals("Catan", articulo.getNombre());
		assertEquals("Juego de mesa muy divertido", articulo.getDescripcion());
		assertEquals(cartera, articulo.getDueno());
		assertEquals("Juegos de cartas", articulo.getInteresadoEn());
		assertFalse(articulo.isDisponible());
		assertNull(articulo.getValoracion());
	}

	@Test
	void testConstructorSinCategorias() {
		ArticuloSegundaMano art = new ArticuloSegundaMano("Monopoly", "Juego clásico", cartera, "Nada");
		assertNotNull(art);
		assertEquals(0, art.getCategorias().length);
	}

	@Test
	void testIdUnico() {
		ArticuloSegundaMano otro = new ArticuloSegundaMano("Parchís", "Juego de tablero", cartera, "Dados");
		assertNotEquals(articulo.getId(), otro.getId());
	}

	// ─── Getters ─────────────────────────────────────────────────────────────────

	@Test
	void testGetNombre() {
		assertEquals("Catan", articulo.getNombre());
	}

	@Test
	void testGetDescripcion() {
		assertEquals("Juego de mesa muy divertido", articulo.getDescripcion());
	}

	@Test
	void testGetDueno() {
		assertEquals(cartera, articulo.getDueno());
	}

	@Test
	void testGetPropietario() {
		assertEquals(cliente, articulo.getPropietario());
	}

	@Test
	void testGetInteresadoEn() {
		assertEquals("Juegos de cartas", articulo.getInteresadoEn());
	}

	@Test
	void testGetImagenInicialmenteNull() {
		assertNull(articulo.getImage());
	}

	@Test
	void testGetCategorias() {
		List<Categoria> cats = List.of(articulo.getCategorias());

		assertTrue(cats.contains(cat1));
		assertTrue(cats.contains(cat2));
	}

	@Test
	void testGetId() {
		assertTrue(articulo.getId() >= 0);
	}

	// ─── isDisponible ────────────────────────────────────────────────────────────

	@Test
	void testIsDisponibleInicialmenteFalse() {
		assertFalse(articulo.isDisponible());
	}

	// ─── disponibilizar ──────────────────────────────────────────────────────────

	@Test
	void testDisponibilizar() {
		articulo.disponibilizar();
		assertTrue(articulo.isDisponible());
	}

	@Test
	void testDisponibilizarVariasVeces() {
		articulo.disponibilizar();
		articulo.disponibilizar();
		assertTrue(articulo.isDisponible());
	}

	// ─── reservar ────────────────────────────────────────────────────────────────

	@Test
	void testReservar() {
		articulo.disponibilizar();
		articulo.reservar();
		assertFalse(articulo.isDisponible());
	}

	@Test
	void testReservarSinDisponibilizarAntes() {
		// Ya está no disponible por defecto; reservar no debe cambiar nada
		articulo.reservar();
		assertFalse(articulo.isDisponible());
	}

	@Test
	void testDisponibilizarYReservarCiclo() {
		articulo.disponibilizar();
		assertTrue(articulo.isDisponible());
		articulo.reservar();
		assertFalse(articulo.isDisponible());
		articulo.disponibilizar();
		assertTrue(articulo.isDisponible());
	}

	// ─── anadirValoracion ────────────────────────────────────────────────────────

	@Test
	void testGetValoracionInicialmenteNull() {
		assertNull(articulo.getValoracion());
	}

	@Test
	void testAnadirValoracion() {
		Valoracion v = new Valoracion(articulo);
		articulo.anadirValoracion(v);
		assertEquals(v, articulo.getValoracion());
	}
	
	@Test
	void testValorarArticulo() throws Exception {
		Valoracion v = new Valoracion(articulo);
		articulo.anadirValoracion(v);
		Empleado e = new Empleado ("Empleado", "pass");
		v.valorar(e, 20, EstadoFisicoArticulo.MUY_BUENO);
		
		assertEquals(e, v.getEmpleado());
		assertEquals(20, v.getPrecioEstimado());
		assertEquals(EstadoFisicoArticulo.MUY_BUENO, v.getEstadoFisico());
	}

	@Test
	void testAnadirValoracionConValoracion() {
		Valoracion v1 = new Valoracion(articulo);
		Valoracion v2 = new Valoracion(articulo);
		articulo.anadirValoracion(v1);
		articulo.anadirValoracion(v2);
		assertEquals(v1, articulo.getValoracion());
	}
	
	@Test
	void testToStringContieneNombre() {
		assertTrue(articulo.toString().contains("Catan"));
	}

	@Test
	void testToStringValoracionPendiente() {
		assertTrue(articulo.toString().contains("Pendiente de solicitud"));
	}

	@Test
	void testToStringConValoracion() {
		Valoracion v = new Valoracion(articulo);
		articulo.anadirValoracion(v);
		assertFalse(articulo.toString().contains("Pendiente de solicitud"));
	}
}