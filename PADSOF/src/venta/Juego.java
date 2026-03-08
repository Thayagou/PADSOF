package venta;

import javax.swing.ImageIcon;

public class Juego extends Producto {
	private int numJugadores;
	private String rangoEdad;
	private TipoJuego tipo;
	
	public Juego(String nombre, String desc, double precio, ImageIcon imagen, int numJug, String rango, TipoJuego tipo, Categoria...categorias) {
		super(nombre, desc, precio, imagen, categorias);
		this.numJugadores = numJug;
		this.rangoEdad = rango;
		this.tipo = tipo;
	}
	
	@Override
	public String getCaracteristicas() {
		return String.format("(Tipo de juego=%s, Numero de Jugadores=%d, Rango de edad=%s)", tipo, numJugadores, rangoEdad);
	}
}
