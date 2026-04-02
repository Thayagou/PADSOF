package venta.productos.caracteristicas;

import javax.swing.ImageIcon;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;
import venta.productos.*;

public class CaracteristicasJuego implements CaracteristicasProducto {
	public final int numJugadores;
	public final String rangoEdad;
	public final TipoJuego tipo;
	
	public CaracteristicasJuego(int numJugadores, String rango, TipoJuego tipo) {
		this.numJugadores = numJugadores;
		this.rangoEdad = rango;
		this.tipo = tipo;
	}
	
	@Override
	public Producto crearProducto(String nombre, String descripcion, double precio, ImageIcon image, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		return new Juego(nombre, descripcion, precio, image, numJugadores, rangoEdad, tipo, categorias);
	}
}
