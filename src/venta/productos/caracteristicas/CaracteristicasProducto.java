package venta.productos.caracteristicas;

import javax.swing.ImageIcon;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;
import venta.productos.*;

/**
 * Interfaz que implementamos para poder pasar caracteristicas de cualquier tipo de producto
 */
public interface CaracteristicasProducto {
	/**
	 * Método para crear un producto usando las características
	 * @param nombre Nombre del producto
	 * @param descripcion Descripción del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param categorias Array de categorías del producto
	 * @return Producto que se creó
	 * @throws InvalidArgumentException Si alguno de los parámetros introducidos es inváildo
	 * @throws DoubleDiscountException Si las categorías no son compatibles entre sí o con el producto por descuentos
	 */
	public abstract Producto crearProducto(String nombre, String descripcion, double precio, ImageIcon image, Categoria...categorias) throws InvalidArgumentException, DoubleDiscountException;
}
