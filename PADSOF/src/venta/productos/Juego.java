package venta.productos;

import java.io.Serializable;

import javax.swing.ImageIcon;
import venta.productos.caracteristicas.*;
import exceptions.*;
import venta.productos.caracteristicas.CaracteristicasProducto;

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
	 * @throws InvalidArgumentException Si alguno de los argumentos es inválido
	 * @throws DoubleDiscountException Si hay algún conflicto de descuentos por las categorías del producto
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
	
	@Override
	public void setCaracteristicas(CaracteristicasProducto c) throws InvalidArgumentException {
		if(!(c instanceof CaracteristicasJuego)) throw new InvalidArgumentException("Se esperaba CaracteristicasJuego");
		CaracteristicasJuego a = (CaracteristicasJuego)c;
		if(a.numJugadores < 0 || a.rangoEdad == null || a.tipo == null)
			throw new InvalidArgumentException("Atributos inválidos del juego");
		this.numJugadores = a.numJugadores;
		this.rangoEdad = a.rangoEdad;
		this.tipo = a.tipo;
	}
	
	/**
	 * Método para imprimir las caracteristicas del juego
	 */
	@Override
	public String getCaracteristicas() {
		return String.format("(Tipo de juego=%s, Numero de Jugadores=%d, Rango de edad=%s)", tipo, numJugadores, rangoEdad);
	}
}
