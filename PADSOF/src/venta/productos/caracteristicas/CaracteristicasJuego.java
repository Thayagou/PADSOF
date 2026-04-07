package venta.productos.caracteristicas;

import javax.swing.ImageIcon;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;
import venta.productos.*;

/**
 * Esta clase representa las caracteristicas de un juego
 */
public class CaracteristicasJuego implements CaracteristicasProducto {
	public final int numJugadores;
	public final String rangoEdad;
	public final TipoJuego tipo;
	
	/**
	 * Conctructor de la clase
	 * @param numJugadores Número de jugadores del juego
	 * @param rango Rango de edad del juego
	 * @param tipo Tipo de juego
	 */
	public CaracteristicasJuego(int numJugadores, String rango, TipoJuego tipo) {
		this.numJugadores = numJugadores;
		this.rangoEdad = rango;
		this.tipo = tipo;
	}
	
	/**
	 * Sobreescribe el método que crea un producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripcion del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param categorias Categorias del producto
	 * @return Producto creado
	 * @throws InvalidArgumentException Se lanza cuando algun argumento no es válido
	 * @throws DoubleDiscountException Se lanza cuando hay conflicto de descuentos
	 */
	@Override
	public Producto crearProducto(String nombre, String descripcion, double precio, ImageIcon image, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		return new Juego(nombre, descripcion, precio, image, numJugadores, rangoEdad, tipo, categorias);
	}
}
