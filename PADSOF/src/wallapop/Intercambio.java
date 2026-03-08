package wallapop;

import java.time.*;
import java.util.*;
import usuario.Empleado;

public class Intercambio {
	private int id;
	private Cartera emisor;
	private List<ArticuloSegundaMano> ofrecidos = new ArrayList<>();
	private Cartera receptor;
	private List<ArticuloSegundaMano> solicitados = new ArrayList<>();;
	private LocalDateTime fechaCaducaOferta;
	private EstadoIntercambio estado;
	private LocalDateTime fechaRespuesta;
	private Empleado empleado;
	private LocalDateTime fechaConfirmacion;
	
	public Intercambio (List<ArticuloSegundaMano> ofrecidos, List<ArticuloSegundaMano> solicitados) {
		Cartera emisor, receptor;
		if (ofrecidos.size() < 1 || solicitados.size() < 1) {
			throw new IllegalArgumentException("Se debe solicitar y pedir al menos un artÍculo");
		}
		
		emisor = ofrecidos.getFirst().getDueno();
		/*for (ArticuloSegundaMano art: ofrecidos) {
			if (!emisor.equals(art.getDueno())) {
				throw new IllegalArgumentException("Los artÍculos ofrecidos deben ser del mismo dueno");
			}
		}*/
		
		receptor = solicitados.getFirst().getDueno();
		/*for (ArticuloSegundaMano art: solicitados) {
			if (!emisor.equals(art.getDueno())) {
				throw new IllegalArgumentException("Los artÍculos solicitados deben ser del mismo dueno");
			}
		}*/
		
		this.emisor = emisor;
		this.ofrecidos.addAll(ofrecidos);
		this.receptor = receptor;
		this.solicitados.addAll(solicitados);
		estado = EstadoIntercambio.OFERTADO;
		
		/*emisor.addIntercambio(this);
		receptor.addIntercambio(this);*/
	}
	
	public boolean validarIntercambio(Empleado empleado) {
		this.empleado = empleado;
		this.fechaConfirmacion = LocalDateTime.now();
		this.estado = EstadoIntercambio.CONFIRMADO;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "\nCliente emisor: " + emisor.getNombreDueno() + 
				"\nArticulos ofrecidos: " + ofrecidos + 
				"\nCliente receptor: " + receptor.getNombreDueno() +
				"\nArticulso solicitados: " + solicitados +
				"\nEstado del intercambio: " + estado +
				((estado == EstadoIntercambio.OFERTADO) ? ("Fecha de caducidad de la oferta:" + fechaCaducaOferta):"") +
				((estado == EstadoIntercambio.CONFIRMADO)? ("Empleado: "+ empleado+ "\nFecha de confirmacion: "+ fechaConfirmacion) : "");
				
	}

	public Cartera getEmisor() {
		return emisor;
	}

	public List<ArticuloSegundaMano> getOfrecidos() {
		return ofrecidos;
	}

	public Cartera getReceptor() {
		return receptor;
	}

	public List<ArticuloSegundaMano> getSolicitados() {
		return solicitados;
	}

	public LocalDateTime getFechaCaducaOferta() {
		return fechaCaducaOferta;
	}

	public EstadoIntercambio getEstado() {
		return estado;
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

}
