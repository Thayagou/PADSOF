package venta;

import java.time.*;

import javax.swing.ImageIcon;

public class Comic extends Producto {
	private LocalDate fechaPublicacion;
	private String autor;
	private int numPaginas;
	private String editorial;
	
	public Comic(String nombre, String desc, double precio, ImageIcon imagen, LocalDate fecha, String autor, int pags, String editorial, Categoria...categorias) {
		super(nombre, desc, precio, imagen, categorias);
		this.fechaPublicacion = fecha;
		this.autor = autor;
		this.numPaginas = pags;
		this.editorial = editorial;
	}
	
	public String getCaracteristicas() {
		return String.format("(fecha de publicacion=%s, Autor=%s, Numero de paginas=%d, Editorial=%s)",
				fechaPublicacion, autor, numPaginas, editorial);
	}
}
