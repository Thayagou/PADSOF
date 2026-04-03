package wallapop;

import java.io.Serializable;
import java.time.*;

import exceptions.InvalidArgumentException;
import usuario.*;
import sistema.AsignadorId;
import sistema.Sistema;

/**
 * Clase que representa una valoración de un artículo de segunda mano realizada por un empleado de la tienda
 */
public class Valoracion implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Identificador único de la valoración en la tienda */
	private final long id;
	/** Artículo de segunda mano al que hace referencia la valoración */
	private ArticuloSegundaMano articuloValorado;
	/** Fecha en la que se solicitó la valoración */
	private LocalDateTime fechaSolicitud;
	/** Precio pagado por el propietario del artículo por la valoración */
	private double precioPagado;
	/** Estado físico del artículo. PENDIENTE si aún no ha sido valorado */
	private EstadoFisicoArticulo estadoArticulo;
	/** Empleado que realiza la valoración del artículo */
	private Empleado empleado;
	/** Fecha en la que el empleado realiza la valoración */
	private LocalDateTime fechaValoracion;
	/** Precio estimado que le asigna el empleado al artículo */
	private double precioEstimado;
	
	/**
	 * Contructor de una valoración 
	 * @param articulo Artículo de segunda mano del cual se ha solicitado una valoración
	 */
	public Valoracion(ArticuloSegundaMano articulo) {
		id = AsignadorId.getInstancia().siguienteId();
		this.fechaSolicitud = LocalDateTime.now();
		this.articuloValorado = articulo;
		this.precioPagado = Sistema.getInstancia().getPrecioValoracion();
		this.estadoArticulo = EstadoFisicoArticulo.PENDIENTE;
	}
	
	/**
	 * Método que le parmite a un empleado valorar un artículo de segunda mano
	 * @param empleado Empleado que realiza la valoración
	 * @param precioEstimado Precio estimado de la valoración
	 * @param estado Estado físico en el que se encuentta el artículo
	 * @throws InvalidArgumentException  Se lanza en caso de que alguno de los parámetros introducidos no sea válido
	 */
	public void valorar(Empleado empleado, double precioEstimado, EstadoFisicoArticulo estado) throws InvalidArgumentException {
		if (empleado == null || estado == null || precioEstimado < 0) throw new InvalidArgumentException("Alguno de los parámetros introducidos no es válido", "validar intercambio");
		
		this.empleado = empleado;
		this.precioEstimado = precioEstimado;
		fechaValoracion = LocalDateTime.now();
		estadoArticulo = estado;
		
		articuloValorado.disponibilizar();
	}

	/**
	 * Getter del dueño del artículo valorado
	 * @return el cliente registrado propietario del artículo
	 */
	public ClienteRegistrado getDuenoArticulo() {
		return articuloValorado.getPropietario();
	}

	/**
	 * Getter de la fecha de solicitud de valoración
	 * @return la fecha y hora en la que se realizó la solicitud
	 */
	public LocalDateTime getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * Getter del artículo asociado a la valoración
	 * @return el artículo de segunda mano asociado a la valoración
	 */
	public ArticuloSegundaMano getArticulo() {
		return articuloValorado;
	}

	/**
	 * Getter del precio pagado por la valoración del artículo
	 * @return el precio pagado
	 */
	public double getPrecioPagado() {
		return precioPagado;
	}

	/**
	 * Getter del empleado que realizó la valoración
	 * @return el empleado asignado, o null si aún no se ha realizado la valoración
	 */
	public Empleado getEmpleado() {
		if (fechaValoracion == null) return null;

		return empleado;
	}

	/**
	 * Getter de la fecha en la que se realizó la valoración
	 * @return la fecha y hora de la valoración
	 */
	public LocalDateTime getFechaValoracion() {
		return fechaValoracion;
	}

	/**
	 * Getter del precio estimado del artículo tras la valoración
	 * @return el precio estimado, o -1 si aún no se ha realizado la valoración
	 */
	public double getPrecioEstimado() {
		if (fechaValoracion == null) return -1;

		return precioEstimado;
	}
	
	/**
	 * Getter del estado físico del artículo
	 * @return su estado
	 */
	public EstadoFisicoArticulo getEstadoFisico() {
		return estadoArticulo;
	}
	
	@Override
	public String toString() {
		return "Articulo valorado: " + articuloValorado +
				"Fecha solicitud: " + fechaSolicitud +
				"Precio pagado: " + precioPagado +
				"Estado del articulo: " + estadoArticulo +
				((estadoArticulo == EstadoFisicoArticulo.PENDIENTE) ? "Pendiente de valorar\n" : 
					("\nFecha valoracion: "+ fechaValoracion + "\nEstado del articulo: " + "\nPrecio estimado: "+ estadoArticulo +"\nEmpleado: "+ empleado));
			
	}
	
	public String toStringSinArticulo() {
		return "Fecha solicitud: " + fechaSolicitud +
				"Precio pagado: " + precioPagado +
				"Estado del articulo: " + estadoArticulo +
				((estadoArticulo == EstadoFisicoArticulo.PENDIENTE) ? "Pendiente de valorar\n" : 
					("\nFecha valoracion: "+ fechaValoracion + "\nEstado del articulo: " + "\nPrecio estimado: "+ estadoArticulo +"\nEmpleado: "+ empleado));
		
	}	
}
