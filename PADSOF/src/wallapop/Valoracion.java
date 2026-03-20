package wallapop;

import java.time.*;

import usuario.*;
import sistema.AsignadorId;
import sistema.Sistema;

public class Valoracion {
	private final long id;
	private ArticuloSegundaMano articuloValorado;
	private LocalDateTime fechaSolicitud;
	private double precioPagado;
	private EstadoFisicoArticulo estadoArticulo;
	private Empleado empleado;
	private LocalDateTime fechaValoracion;
	private double precioEstimado;
	
	public Valoracion(ArticuloSegundaMano articulo) {
		id = AsignadorId.getInstancia().siguienteId();
		this.fechaSolicitud = LocalDateTime.now();
		this.articuloValorado = articulo;
		this.precioPagado = Sistema.getInstancia().getPrecioValoracion();
		this.estadoArticulo = EstadoFisicoArticulo.PENDIENTE;
	}
	
	public boolean valorar(Empleado empleado, double precioEstimado, EstadoFisicoArticulo estado) {
		if (empleado == null || estado == null || precioEstimado < 0) return false;
		
		this.empleado = empleado;
		this.precioEstimado = precioEstimado;
		fechaValoracion = LocalDateTime.now();
		estadoArticulo = estado;
		
		return true;
	}
	
	public ClienteRegistrado getDuenoArticulo() {
		return articuloValorado.getPropietario();
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

	public LocalDateTime getFechaSolicitud() {
		return fechaSolicitud;
	}
	
	public ArticuloSegundaMano getArticulo() {
		return articuloValorado;
	}

	public double getPrecioPagado() {
		return precioPagado;
	}

	public Empleado getEmpleado() {
		if (fechaValoracion == null) return null;
		
		return empleado;
	}

	public LocalDateTime getFechaValoracion() {
		return fechaValoracion;
	}

	public double getPrecioEstimado() {
		if (fechaValoracion == null) return -1;
		
		return precioEstimado;
	}
	
	public EstadoFisicoArticulo getEstadoFisico() {
		return estadoArticulo;
	}
	
	
}
