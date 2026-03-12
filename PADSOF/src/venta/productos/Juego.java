package venta.productos;

import javax.swing.ImageIcon;

public class Juego extends Producto {
	private int numJugadores;
	private String rangoEdad;
	private TipoJuego tipo;
	
	/**
	 * Creador de la clase Juego
	 * @param nombre Nombre del juego
	 * @param desc Descripción del juego
	 * @param precio Precio del juego
	 * @param imagen Imagen del juego
	 * @param numJug Número de jugadores del juego
	 * @param rango Rango de edad recomendade del juego
	 * @param tipo Tipo de juego
	 * @param categorias Array de categorías del juego
	 */
	public Juego(String nombre, String desc, double precio, ImageIcon imagen, int numJug, String rango, TipoJuego tipo, Categoria...categorias) {
		super(nombre, desc, precio, imagen, categorias);
		this.numJugadores = numJug;
		this.rangoEdad = rango;
		this.tipo = tipo;
	}
	
	/**
	 * Método para imprimir las caracteristicas del juego
	 */
	@Override
	public String getCaracteristicas() {
		return String.format("(Tipo de juego=%s, Numero de Jugadores=%d, Rango de edad=%s)", tipo, numJugadores, rangoEdad);
	}
}
