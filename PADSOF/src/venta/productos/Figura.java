package venta.productos;

import javax.swing.ImageIcon;

import exceptions.IncompatibleCategoriesException;

public class Figura extends Producto {
	private String dimensiones;
	private String marca;
	private String material;
	
	/**
	 * Creador de la clase Figura
	 * @param nombre Nombre de la figura
	 * @param desc Descripcion de la figura
	 * @param precio Precio de la figura
	 * @param imagen Imagen de la figura
	 * @param dim Dimensiones de la figura
	 * @param marca Marca de la figura
	 * @param material Material de la figura
	 * @param categorias Array de categorias de la figura
	 */
	public Figura(String nombre, String desc, double precio, ImageIcon imagen, String dim, String marca, String material, Categoria...categorias) 
			throws IllegalArgumentException, IncompatibleCategoriesException {
		super(nombre, desc, precio, imagen, categorias);
		
		if(dim == null || marca == null || material == null) throw new IllegalArgumentException();
		
		this.dimensiones = dim;
		this.marca = marca;
		this.material = material;
	}
	
	/**
	 * Método para imprimir las caracteristicas de la figura
	 */
	@Override
	public String getCaracteristicas() {
		return String.format("(Dimensiones=%s, Marca=%s, Material=%s)", dimensiones, marca, material);
	}

}
