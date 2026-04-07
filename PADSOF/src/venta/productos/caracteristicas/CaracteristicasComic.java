package venta.productos.caracteristicas;

import java.time.*;

import javax.swing.ImageIcon;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;
import venta.productos.*;

/**
 * Esta clase representa las caracteristicas de un comic
 */
public class CaracteristicasComic implements CaracteristicasProducto {
	public final LocalDate fechaPublicacion;
	public final String autor;
	public final int numPaginas;
	public final String editorial;

	/**
	 * Constructor de la clase
	 * @param fechaPublicacion Fecha de publicacion del comic
	 * @param autor Autor del comic
	 * @param numPaginas Número de páginas del comic
	 * @param editorial Editorial del comic
	 */
	public CaracteristicasComic(LocalDate fechaPublicacion, String autor, int numPaginas, String editorial) {
		this.fechaPublicacion = fechaPublicacion;
		this.autor = autor;
		this.numPaginas = numPaginas;
		this.editorial = editorial;
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
		return new Comic(nombre, descripcion, precio, image, fechaPublicacion, autor, numPaginas, editorial, categorias);
	}
}
