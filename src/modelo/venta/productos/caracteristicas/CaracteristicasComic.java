package modelo.venta.productos.caracteristicas;

import java.time.*;

import javax.swing.ImageIcon;

import modelo.exceptions.DoubleDiscountException;
import modelo.exceptions.InvalidArgumentException;
import modelo.venta.productos.*;

/**
 * Esta clase representa las caracteristicas de un comic
 */
public class CaracteristicasComic implements CaracteristicasProducto {
	/** Fecha de publicacion del comic*/
	public final LocalDate fechaPublicacion;
	/** Autor del comic*/
	public final String autor;
	/** Número de páginas del comic*/
	public final int numPaginas;
	/** Editorial del comic*/
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

	@Override
	public Producto crearProducto(String nombre, String descripcion, double precio, ImageIcon image, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		return new Comic(nombre, descripcion, precio, image, fechaPublicacion, autor, numPaginas, editorial, categorias);
	}
}
