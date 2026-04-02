package venta.productos;

import java.io.Serializable;
import java.time.*;

import javax.swing.ImageIcon;

import exceptions.*;
import venta.productos.caracteristicas.*;

/**
 * Clase que define el subtipo de Producto, Comic
 * 
 * @author Juan Ibáñez
 */
public class Comic extends Producto implements Serializable {
	private static final long serialVersionUID = 1L;
	private LocalDate fechaPublicacion;
	private String autor;
	private int numPaginas;
	private String editorial;
	
	/**
	 * Creador de la clase Comic
	 * @param nombre Nombre del comic
	 * @param desc Descripcion del comic
	 * @param precio Precio del comic
	 * @param imagen Imagen del comic
	 * @param fecha Fecha de publicación del comic
	 * @param autor Autor del comic
	 * @param pags Número de páginas del comic
	 * @param editorial Editorial que publica el comic
	 * @param categorias Array de categorías del cómic
	 */
	public Comic(String nombre, String desc, double precio, ImageIcon imagen, LocalDate fecha, String autor, int pags, String editorial, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		super(nombre, desc, precio, imagen, categorias);
		
		if(fecha == null || autor == null || editorial == null) throw new InvalidArgumentException("No se pueden dejar características vacías");
		if(pags < 0) throw new InvalidArgumentException("El número de páginas no puede ser negativo");
		
		this.fechaPublicacion = fecha;
		this.autor = autor;
		this.numPaginas = pags;
		this.editorial = editorial;
	}
	
	@Override
	public void setCaracteristicas(CaracteristicasProducto c) throws InvalidArgumentException {
		if(!(c instanceof CaracteristicasComic)) throw new InvalidArgumentException("Se esperaban CaracteristicasComic");
		CaracteristicasComic carac = (CaracteristicasComic)c;
		
		if(carac.fechaPublicacion == null || carac.autor == null || carac.numPaginas < 0 || carac.editorial == null)
			throw new InvalidArgumentException("Características con atributos inválidos");
		
		this.fechaPublicacion = carac.fechaPublicacion;
		this.autor = carac.autor;
		this.numPaginas = carac.numPaginas;
		this.editorial = carac.editorial;
	}
	
	/**
	 * Método para imprimir las características del comic
	 */
	@Override
	public String getCaracteristicas() {
		return String.format("(fecha de publicacion=%s, Autor=%s, Numero de paginas=%d, Editorial=%s)",
				fechaPublicacion, autor, numPaginas, editorial);
	}
}
