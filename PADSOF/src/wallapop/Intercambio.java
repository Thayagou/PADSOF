package wallapop;

import java.io.Serializable;
import java.time.*;
import java.util.*;

import exceptions.InvalidArgumentException;
import sistema.AsignadorId;
import usuario.Empleado;

/**
 * Clase que representa un intercambio ofertado de un cliente a otro que contiene productos solicitados y ofertados
 */
public class Intercambio implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Identificador único del intercambio en la tienda */
	private final long id;
	/** Cartera del cliente que realiza la oferta de intercambio */
	private Cartera emisor;
	/** Lista de los artículos de segunda mano ofrecidos por el cliente emisor */
	private List<ArticuloSegundaMano> ofrecidos = new ArrayList<>();
	/** Cartera del cliente que recibe la oferta de intercambio */
	private Cartera receptor;
	/** Lista de los artículos de segunda mano solicitados al cliente receptor */
	private List<ArticuloSegundaMano> solicitados = new ArrayList<>();
	/** Fecha en la que caduca la oferta de intercambio */
	private LocalDateTime fechaCaducaOferta;
	/** Estado en el que se encuentra el intercambio */
	private EstadoIntercambio estado;
	/** Fecha en la que se responde a la oferta de intercambio */
	private LocalDateTime fechaRespuesta;
	/** Empleado que confirma la realización física del intercambio */
	private Empleado empleado;
	/** Fecha en la que se confirma la realización física del intercambio */
	private LocalDateTime fechaConfirmacion;
	
	/**
	 * Constructor del Intercambio. Se obtienen los clientes que lo ofertan y reciben a través de los artículos ofrecidos y solicitados respectivamente 
	 * @param ofrecidos Artículos de segunda manos ofrecidos por el emisor
	 * @param solicitados Artículos de segunda manos solicitados por el emisor
	 * @throws InvalidArgumentException Se lanza en caso de que alguno de los 
	 */
	public Intercambio (ArticuloSegundaMano[] ofrecidos, ArticuloSegundaMano[] solicitados) throws InvalidArgumentException {
		Cartera emisor, receptor;
		if (ofrecidos.length < 1 || solicitados.length < 1) {
			throw new InvalidArgumentException("Se debe solicitar y pedir al menos un artículo", "crear intercambio");
		}
		
		emisor = ofrecidos[0].getDueno();
		for (ArticuloSegundaMano art: ofrecidos) {
			if (!emisor.equals(art.getDueno())) {
				throw new InvalidArgumentException("Los artículos ofrecidos deben ser del mismo dueno", "crear intercambio");
			}
		}
		
		receptor = solicitados[0].getDueno();
		for (ArticuloSegundaMano art: solicitados) {
			if (!emisor.equals(art.getDueno())) {
				throw new InvalidArgumentException("Los artículos solicitados deben ser del mismo dueno", "crear intercambio");
			}
		}
		id = AsignadorId.getInstancia().siguienteId();
		
		this.emisor = emisor;
		for(ArticuloSegundaMano art : ofrecidos) {
			this.ofrecidos.add(art);
			art.reservar();
		}
		for(ArticuloSegundaMano art : solicitados) this.solicitados.add(art);
		estado = EstadoIntercambio.OFERTADO;
		
		emisor.addIntercambio(this);
		receptor.addIntercambio(this);
	}
	
	/**
	 * Se acepta el intercambio solamente si se encontraba en estado OFERTADO y se reservan los artículos solicitados
	 * @return true en caso de que se acepte correctamente
	 * @throws InvalidArgumentException Se lanza en caso de que el intercambio no se encuentre en estado OFERTADO
	 */
	public boolean aceptarIntercambio () throws InvalidArgumentException {
		if (estado.equals(EstadoIntercambio.OFERTADO) == false) throw new InvalidArgumentException("Este intercambio no esta en estado ofertado", "aceptar intercambio");
		
		estado = EstadoIntercambio.ACEPTADO;
		fechaRespuesta = LocalDateTime.now();
		
		for (ArticuloSegundaMano art: solicitados) art.reservar();
		return true;
	}
	
	/**
	 * Se rechaza el intercambio solamente si se encontraba en estado OFERTADO y se liberan los artículos ofrecidos
	 * @return true en caso de que se rechace correctamente
	 * @throws InvalidArgumentException Se lanza en caso de que el intercambio no se encuentre en estado OFERTADO
	 */
	public boolean rechazarIntercambio () throws InvalidArgumentException {
		if (estado.equals(EstadoIntercambio.OFERTADO) == false) throw new InvalidArgumentException("Este intercambio no esta en estado ofertado", "rechazar intercambio");
		
		this.liberarOfertado();
		estado = EstadoIntercambio.RECHAZADO;

		fechaRespuesta = LocalDateTime.now();
		return true;
	}
	
	/**
	 * Se cancela el intercambio solamente si se encontraba en estado OFERTADO y se liberan los artículos ofrecidos
	 * @return true en caso de que se cancele correctamente
	 * @throws InvalidArgumentException Se lanza en caso de que el intercambio no se encuentre en estado OFERTADO
	 */
	public boolean cancelarIntercambio() throws InvalidArgumentException {
		if (estado.equals(EstadoIntercambio.OFERTADO) == false) throw new InvalidArgumentException("Este intercambio no esta en estado ofertado", "cancelar intercambio");
		
		this.liberarOfertado();
		estado = EstadoIntercambio.CANCELADO;
		
		fechaRespuesta = LocalDateTime.now();
		return true;
	}
	
	/**
	 * Se rechaza el intercambio solamente si se encontraba en estado OFERTADO y se liberan los artículos ofrecidos
	 * @return true en caso de que se caduque correctamente
	 * @throws InvalidArgumentException Se lanza en caso de que el intercambio no se encuentre en estado OFERTADO
	 */
	public boolean caducarIntercambio() throws InvalidArgumentException {
		if (estado.equals(EstadoIntercambio.OFERTADO) == false) throw new InvalidArgumentException("Este intercambio no esta en estado ofertado", "caducar intercambio");
		
		this.liberarOfertado();
		estado = EstadoIntercambio.CADUCADO;

		fechaRespuesta = LocalDateTime.now();
		return true;
	}
	
	/**
	 * Libera los artículos ofertados en caso de que se rechace/ cancele/ caduque el intercambio, solamente si el intercambio se encuentra en estado OFERTADO
	 * @throws InvalidArgumentException Se lanza en caso de que el intercambio no se encuentre en estado OFERTADO
	 */
	private void liberarOfertado() throws InvalidArgumentException {
		if (estado.equals(EstadoIntercambio.OFERTADO) == false) throw new InvalidArgumentException("Este intercambio no esta en estado ofertado", "liberar artículos del intercambio");

		for (ArticuloSegundaMano art: ofrecidos) art.disponibilizar();
	}
	
	/**
	 * Permite que un intercambio valide un intercambio
	 * @param empleado Empleado que lo valida
	 * @return true 
	 * @throws InvalidArgumentException Se lanza en caso de que el empleado introducido sea null
	 */
	public void validarIntercambio(Empleado empleado) throws InvalidArgumentException {
		if (empleado == null) throw new InvalidArgumentException("Empleado inválido introducido", "validar intercambio");
		this.empleado = empleado;
		this.fechaConfirmacion = LocalDateTime.now();
		this.estado = EstadoIntercambio.CONFIRMADO;
	}
	
	/**
	 * Getter de la cartera del emisor
	 * @return cartera del emisor
	 */
	public Cartera getEmisor() {
		return emisor;
	}

	/**
	 * Getter de la cartera del receptor
	 * @return cartera del emisor
	 */
	public Cartera getReceptor() {
		return receptor;
	}
	
	/**
	 * Getter de los artículos de segunda mano ofrecidos en el intercambio
	 * @return array con los artículos
	 */
	public ArticuloSegundaMano[] getOfrecidos() {
		return ofrecidos.toArray(new ArticuloSegundaMano[0]);
	}

	/**
	 * Getter de los artículos de segunda mano solicitados en el intercambio
	 * @return array con los artículos
	 */
	public ArticuloSegundaMano[] getSolicitados() {
		return solicitados.toArray(new ArticuloSegundaMano[0]);
	}

	/**
	 * Getter de la fecha en la que se caduca la oferta de intercambio
	 * @return dicha fecha
	 */
	public LocalDateTime getFechaCaducaOferta() {
		return fechaCaducaOferta;
	}

	/**
	 * Getter del estado del intercambio
	 * @return el estado actual
	 */
	public EstadoIntercambio getEstado() {
		return estado;
	}
	
	/**
	 * Setter del estado del intercambio;
	 * @param e Estado a establecer
	 */
	public void setEstado(EstadoIntercambio e) {
		estado = e;
	}

	/**
	 * Getter de la fecha de respuesta del intercambio
	 * @return fecha de repuesta
	 */
	public LocalDateTime getFechaRespuesta() {
		return fechaRespuesta;
	}

	/**
	 * Getter del empleado que ha validado el intercambio
	 * @return el empleado
	 */
	public Empleado getEmpleado() {
		return empleado;
	}

	/**
	 * Getter de la fecha de confirmación de la realización física del intercambio
	 * @return fecha de confirmación
	 */
	public LocalDateTime getFechaConfirmacion() {
		return fechaConfirmacion;
	}

	@Override
	public String toString() {
		return "\nId: " + id +
				"\nCliente emisor: " + emisor.getDueno().getNombre() + 
				"\nArticulos ofrecidos: " + ofrecidos + 
				"\nCliente receptor: " + receptor.getDueno().getNombre() +
				"\nArticulso solicitados: " + solicitados +
				"\nEstado del intercambio: " + estado +
				((estado == EstadoIntercambio.OFERTADO) ? ("Fecha de caducidad de la oferta:" + fechaCaducaOferta):"") +
				((estado == EstadoIntercambio.CONFIRMADO)? ("Empleado: "+ empleado+ "\nFecha de confirmacion: "+ fechaConfirmacion) : "");
				
	}

}
