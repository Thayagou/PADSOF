package usuario;

import java.time.LocalDate;

public class Notificacion {
	private LocalDate fecha;
	private String contenido;
	private boolean leida;
	private boolean borrada;
	private TipoNotificacion tipo;
	
	public Notificacion(String contenido, TipoNotificacion tipo) {
		this.fecha = LocalDate.now();
		this.contenido = contenido;
		this.leida = false;
		this.borrada = false;
		this.tipo = tipo;
	}
	
	public LocalDate getFecha() {
		return this.fecha;
	}
	
	public String getContenido() {
		return this.contenido;
	}

	public boolean isLeida() {
		return leida;
	}

	public void marcarLeida() {
		this.leida = true;
	}

	public boolean isBorrada() {
		return borrada;
	}

	public void borrar() {
		this.borrada = false;
	}
	
	public TipoNotificacion getTipo() {
		return this.tipo;
	}
		
	@Override
	public String toString() {
		return this.tipo + contenido + fecha;
	}
}
