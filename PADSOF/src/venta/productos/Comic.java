package venta.productos;

import java.time.*;

import javax.swing.ImageIcon;

import exceptions.*;

/**
 * Clase que define el subtipo de Producto, Comic
 * 
 * @author Juan Ibáñez
 */
public class Comic extends Producto {
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
	
	/**
	 * Método para imprimir las características del comic
	 */
	@Override
	public String getCaracteristicas() {
		return String.format("(fecha de publicacion=%s, Autor=%s, Numero de paginas=%d, Editorial=%s)",
				fechaPublicacion, autor, numPaginas, editorial);
	}
}
