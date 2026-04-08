package usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import sistema.Reloj;

/**
 * Esta clase representa una notificación recibida por usuarios
 */
public class Notificacion implements Serializable {
	private static final long serialVersionUID = 1L;
	/**Fecha en la que se ha enviado la notificación*/
	private LocalDate fecha;
	/**Contenido de la notificación*/
	private String contenido;
	/**Indica si ha sido leída*/
	private boolean leida;
	/**Indica si ha sido borrada*/
	private boolean borrada;
	/**Tipo de Notificación*/
	private TipoNotificacion tipo;
	
	/**
	 * Constructor de la clase
	 * @param contenido Contenido de la notificación
	 * @param tipo Tipo de Notificación
	 */
	public Notificacion(String contenido, TipoNotificacion tipo) {
		this.fecha = Reloj.localDateNow();
		this.contenido = contenido;
		this.leida = false;
		this.borrada = false;
		this.tipo = tipo;
	}
	
	/**
	 * Devuelve la fecha en la que se envió la notificación
	 * @return fecha de envío
	 */
	public LocalDate getFecha() {
		return this.fecha;
	}
	
	/**
	 * Devuelve el contenido de la notificación
	 * @return contenido de la notificación
	 */
	public String getContenido() {
		return this.contenido;
	}

	/**
	 * Devuelve si se ha leído la notificación ya
	 * @return true si se ha leído, false en caso contrario
	 */
	public boolean isLeida() {
		return leida;
	}

	/**
	 * Marca una notificación como leída
	 */
	public void marcarLeida() {
		this.leida = true;
	}

	/**
	 * Devuelve si se ha borrado la notificación
	 * @return true si se ha borrado, false en caso contrario
	 */
	public boolean isBorrada() {
		return borrada;
	}

	/**
	 * Borra la notificación
	 */
	public void borrar() {
		this.borrada = true;
	}
	
	/**
	 * Devuelve el tipo de notificación de la notificación
	 * @return tipo de notificación
	 */
	public TipoNotificacion getTipo() {
		return this.tipo;
	}
	
	@Override
	public String toString() {
		return this.tipo.name() +": "+ contenido + " Fecha: "+ fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}
