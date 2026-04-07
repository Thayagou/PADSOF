package venta.productos;

import java.io.Serializable;
import java.time.*;
import usuario.ClienteRegistrado;
import exceptions.*;
import sistema.Reloj;

/**
 * Clase básica Resena que define una reseña de un usuario
 * 
 * @author Juan Ibáñez
 */
public class Resena implements Serializable {
	private static final long serialVersionUID = 1L;
	private double puntuacion;
	private String comentario;
	private LocalDate fecha;
	private ClienteRegistrado usuario;
	
	/**
	 * Creador de la clase Resena
	 * @param puntuacion Puntuación de 0 a 5
	 * @param comentario Comentario de la reseña
	 * @param usuario Usuario que realiza la reseña
	 */
	public Resena(double puntuacion, String comentario, ClienteRegistrado usuario) throws InvalidArgumentException {
		if(comentario == null || usuario == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		if(puntuacion < 0 || puntuacion > 5) {
			throw new InvalidArgumentException("Reseña con puntuacion inválida");
		}
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.fecha = Reloj.localDateNow();
		this.usuario = usuario;
	}

	/**
	 * Getter de la puntuación de la reseña
	 * @return Puntuación de la reseña
	 */
	public double getPuntuacion() {
		return puntuacion;
	}

	/**
	 * Getter del comentario de la reseña
	 * @return Comentario de la reseña
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * Getter de la fecha de publicación de la reseña
	 * @return Fecha de publicación de la reseña
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Getter del usuario que publicó la reseña
	 * @return Usuario que publicó la reseña
	 */
	public ClienteRegistrado getUsuario() {
		return usuario;
	}
	
	/**
	 * Método para imprimir la reseña
	 */
	@Override
	public String toString() {
		return "("+fecha+": "+usuario.getNombre() + "("+puntuacion+"): "+comentario+")";
	}
}
