package venta.productos;

import java.io.Serializable;

import javax.swing.ImageIcon;

import exceptions.*;

/**
 * Clase que define el subtipo de Producto, Juego
 * 
 * @author Juan Ibáñez
 */
public class Juego extends Producto implements Serializable {
	private static final long serialVersionUID = 1L;
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
	public Juego(String nombre, String desc, double precio, ImageIcon imagen, int numJug, String rango, TipoJuego tipo, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		super(nombre, desc, precio, imagen, categorias);
		
		if(rango == null || tipo == null) throw new InvalidArgumentException("No se pueden dejar características vacías");
		if(numJug < 0) throw new InvalidArgumentException("El número de jugadores no puede ser negativo");
		
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
