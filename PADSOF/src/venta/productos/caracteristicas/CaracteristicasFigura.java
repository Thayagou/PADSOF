package venta.productos.caracteristicas;

import javax.swing.ImageIcon;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;
import venta.productos.*;

/**
 * Esta clase representa las caracteristicas de una figura
 */
public class CaracteristicasFigura implements CaracteristicasProducto {
	public final String dimensiones;
	public final String marca;
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
		return new Figura(nombre, descripcion, precio, image, dimensiones, marca, material, categorias);
	}
}
