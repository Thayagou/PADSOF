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
