package venta.productos.caracteristicas;

import javax.swing.ImageIcon;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;
import venta.productos.*;

/**
 * Esta clase representa las caracteristicas de una figura
 */
public class CaracteristicasFigura implements CaracteristicasProducto {
	/** Dimensiones de la figura*/
	public final String dimensiones;
	/** Marca de la figura*/
	public final String marca;
	/** Material de la figura*/
	public final String material;

	/**
	 * Constructor de la clase
	 * @param dimensiones Dimensiones de la figura
	 * @param marca Marca de la figura
	 * @param material Material de la figura
	 */
	public CaracteristicasFigura(String dimensiones, String marca, String material) {
		this.dimensiones = dimensiones;
		this.marca = marca;
		this.material = material;
	}
	
	@Override
	public Producto crearProducto(String nombre, String descripcion, double precio, ImageIcon image, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		return new Figura(nombre, descripcion, precio, image, dimensiones, marca, material, categorias);
	}
}
