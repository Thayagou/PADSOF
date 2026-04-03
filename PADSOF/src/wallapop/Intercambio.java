package wallapop;

import java.io.Serializable;
import java.time.*;
import java.util.*;

import exceptions.InvalidArgumentException;
import sistema.AsignadorId;
import usuario.Empleado;

public class Intercambio implements Serializable {
	private static final long serialVersionUID = 1L;
	private final long id;
	private Cartera emisor;
	private List<ArticuloSegundaMano> ofrecidos = new ArrayList<>();
	private Cartera receptor;
	private List<ArticuloSegundaMano> solicitados = new ArrayList<>();;
	private LocalDateTime fechaCaducaOferta;
	private EstadoIntercambio estado;
	private LocalDateTime fechaRespuesta;
	private Empleado empleado;
	private LocalDateTime fechaConfirmacion;
	
	public Intercambio (ArticuloSegundaMano[] ofrecidos, ArticuloSegundaMano[] solicitados) throws IllegalArgumentException {
		Cartera emisor, receptor;
		if (ofrecidos.length < 1 || solicitados.length < 1) {
			throw new IllegalArgumentException("Se debe solicitar y pedir al menos un artículo");
		}
		
		emisor = ofrecidos[0].getDueno();
		for (ArticuloSegundaMano art: ofrecidos) {
			if (!emisor.equals(art.getDueno())) {
				throw new IllegalArgumentException("Los artículos ofrecidos deben ser del mismo dueno");
			}
		}
		
		receptor = solicitados[0].getDueno();
		for (ArticuloSegundaMano art: solicitados) {
			if (!emisor.equals(art.getDueno())) {
				throw new IllegalArgumentException("Los artículos solicitados deben ser del mismo dueno");
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
	
	public boolean validarIntercambio(Empleado empleado) {
		this.empleado = empleado;
		this.fechaConfirmacion = LocalDateTime.now();
		this.estado = EstadoIntercambio.CONFIRMADO;
		return true;
	}
	
	public Cartera getEmisor() {
		return emisor;
	}

	public ArticuloSegundaMano[] getOfrecidos() {
		return ofrecidos.toArray(new ArticuloSegundaMano[0]);
	}

	public Cartera getReceptor() {
		return receptor;
	}

	public ArticuloSegundaMano[] getSolicitados() {
		return solicitados.toArray(new ArticuloSegundaMano[0]);
	}

	public LocalDateTime getFechaCaducaOferta() {
		return fechaCaducaOferta;
	}

	public EstadoIntercambio getEstado() {
		return estado;
	}
	
	public boolean setEstado(EstadoIntercambio e) {
		estado = e;
		return true;
	}
	
	public boolean aceptarIntercambio () throws InvalidArgumentException {
		if (estado.equals(EstadoIntercambio.OFERTADO) == false) throw new InvalidArgumentException("Este intercambio no esta en estado ofertado", "aceptar intercambio");
		
		estado = EstadoIntercambio.ACEPTADO;
		for (ArticuloSegundaMano art: solicitados) art.reservar();
		return true;
	}
	
	public boolean rechazarIntercambio () throws InvalidArgumentException {
		if (estado.equals(EstadoIntercambio.OFERTADO) == false) throw new InvalidArgumentException("Este intercambio no esta en estado ofertado", "aceptar intercambio");
		
		this.liberarOfertado();
		estado = EstadoIntercambio.RECHAZADO;
		return true;
	}
	
	
	public boolean cancelarIntercambio() throws InvalidArgumentException {
		if (estado.equals(EstadoIntercambio.OFERTADO) == false) throw new InvalidArgumentException("Este intercambio no esta en estado ofertado", "aceptar intercambio");
		
		this.liberarOfertado();
		estado = EstadoIntercambio.CANCELADO;
		return true;
	}
	
	public boolean caducarIntercambio() {
		this.liberarOfertado();
		estado = EstadoIntercambio.CADUCADO;
		
		return true;
	}
	
	private boolean liberarOfertado() {
		if (estado.equals(EstadoIntercambio.OFERTADO) == false) return false;
		for (ArticuloSegundaMano art: ofrecidos) art.disponibilizar();
		
		return true;
	}

	public LocalDateTime getFechaRespuesta() {
		return fechaRespuesta;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

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
