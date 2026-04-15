package modelo.wallapop;

import java.io.Serializable;
import java.util.*;

import modelo.exceptions.InvalidArgumentException;
import modelo.usuario.ClienteRegistrado;

/**
 * Esta clase representa la cartera de artículos de segunda mano de un cliente
 */
public class Cartera implements Serializable{
	private static final long serialVersionUID = 1L;
	/** Cliente al que pertenece la cartera */
	private ClienteRegistrado dueno;
	/** Artículos de segunda mano que el cliente ha añadido a la cartera */
	private List<ArticuloSegundaMano> articulos = new ArrayList<>();
	/** Intercambios que el cliente propietario ha realizado o que se le han ofrecido */
	private List<Intercambio> intercambios = new ArrayList<>();
	
	/**
	 * Constructor de cartera
	 * @param dueno Cliente al que pertenece la cartera
	 */
	public Cartera(ClienteRegistrado dueno) {
		this.dueno = dueno;
	}
	
	/**
	 * Añada un artículo que ha subido el cliente a la cartera
	 * @param articulo Artículo de segunda mano que se añade
	 * @return true si se ha añadido correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de que el artículo pasado no le pertenezca a este cliente
	 */
	public boolean addArticulo(ArticuloSegundaMano articulo) throws InvalidArgumentException {
		if (articulo.getDueno().equals(this) == false) throw new InvalidArgumentException("No puedes añadir este artículo a tu cartera ya que no te pertenece", "añadir artículo a la cartera");
		return articulos.add(articulo);
	}
	
	/**
	 * Añada un intercambio que ha subido el cliente a la cartera
	 * @param intercambio Nuevo intercambio añadido
	 * @return true si se ha añadido correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de que el cliente no participe en el intercambio pasado
	 */
	public boolean addIntercambio(Intercambio intercambio) throws InvalidArgumentException {
		if (intercambio.getEmisor().equals(this) == false && intercambio.getReceptor().equals(this) == false) throw new InvalidArgumentException("No se pudo añadir el intercambio a la cartera ya que el cliente no participa en él", "añadir intercambio a la cartera");
		return intercambios.add(intercambio);
	}
	
	/**
	 * Permite que el cliente al que se le ha hecho una oferta de intercambio la acepte
	 * @param intercambio Intercambio a aceptar
	 * @return true si se acepta correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de que el dueño de la cartera no sea el que recibe el intercambio
	 */
	public boolean aceptarIntercambio(Intercambio intercambio) throws InvalidArgumentException {	
		if (!intercambio.getReceptor().equals(this)) throw new InvalidArgumentException("No puedes aceptar este intercambio porque no eres el receptor", "aceptar intercambio");
		return intercambio.aceptarIntercambio();
	}
	
	/**
	 * Permite que el cliente al que se le ha hecho una oferta de intercambio la rechace
	 * @param intercambio Intercambio a rechazar
	 * @return true si se acepta correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de que el dueño de la cartera no sea el que recibe el intercambio
	 */
	public boolean rechazarIntercambio(Intercambio intercambio) throws InvalidArgumentException {
		if (!intercambio.getReceptor().equals(this)) throw new InvalidArgumentException("No puedes rechazar este intercambio porque no eres el receptor", "rechazar intercambio");
		return intercambio.rechazarIntercambio();
	}
	
	/**
	 * Permite que el cliente que ha hecho una oferta de intercambio la cancele
	 * @param intercambio Intercambio a aceptar
	 * @return true si se acepta correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de que el dueño de la cartera no sea el que recibe el intercambio
	 */
	public boolean cancelarIntercambio(Intercambio intercambio) throws InvalidArgumentException {
		if (!intercambio.getEmisor().equals(this)) throw new InvalidArgumentException("No puedes cancelar este intercambio porque no eres el emisor", "cancelar intercambio");
		return intercambio.cancelarIntercambio();
	}
	
	/**
	 * Invalida los intercambios con artículos que hayan sido intercambiados ya
	 * @param articulos Articulos intercambiados
	 * @return Array con intercambios invalidados
	 */
	public Intercambio[] invalidarIntercambiosConArticulos(ArticuloSegundaMano[] articulos) {
		List<Intercambio> invalidados = new ArrayList<>();
		
		for (Intercambio i: intercambios) {
			if (i.invalidarSiSolicitaArticulos(articulos)) {
				invalidados.add(i);
			}
		}
		
		return invalidados.toArray(new Intercambio[0]);
	}
	
	/**
	 * Getter del dueño de la cartera
	 * @return El cliente al que pertenece
	 */
	public ClienteRegistrado getDueno() {
		return dueno;
	}
	
	/**
	 * Getter de los artículos de segunda mano que se encuentren disponibles
	 * @return array de ArticuloSegundaMano con los disponibles
	 */
	public ArticuloSegundaMano[] getArticulosDisponibles() {
		return articulos.stream().filter(art -> art.isDisponible()).toArray(ArticuloSegundaMano[]::new);
	}
	
	/**
	 * Getter de todos los artículos de segunda mano de la cartera
	 * @return Array con dichos artículos
	 */
	public ArticuloSegundaMano[] getArticulos() {
		return articulos.toArray(new ArticuloSegundaMano[0]);
	}

	/**
	 * Getter de los intercambios que se encuentren en estado OFERTADO, es decir, que se encuentren pendientes de respuesta
	 * @return Array de Intercambio con dichos intercambios
	 */
	public Intercambio[] getIntercambiosPendientes() {
		return intercambios.stream().filter(i->i.getEstado().equals(EstadoIntercambio.OFERTADO)).toArray(Intercambio[]::new);
	}
	
	@Override
	public String toString() {
		return 
				"\nArticulos de segunda mano: " + articulos +
				"\nIntercambios: " + intercambios;
	}
}