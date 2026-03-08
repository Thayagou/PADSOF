package venta;

import java.time.*;
import usuario.ClienteRegistrado;

public class Resena {
	private double puntuacion;
	private String comentario;
	private LocalDate fecha;
	private ClienteRegistrado usuario;
	
	public Resena(double puntuacion, String comentario, ClienteRegistrado usuario) {
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.fecha = LocalDate.now();
		this.usuario = usuario;
	}

	public double getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public ClienteRegistrado getUsuario() {
		return usuario;
	}
	
	@Override
	public String toString() {
		return "("+fecha+": "+usuario+"("+puntuacion+"): "+comentario+")\n";
	}
}
