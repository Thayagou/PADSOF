package venta;

import javax.swing.ImageIcon;

public class Figura extends Producto {
	private String dimensiones;
	private String marca;
	private String material;
	
	public Figura(String nombre, String desc, double precio, ImageIcon imagen, String dim, String marca, String material, Categoria...categorias) {
		super(nombre, desc, precio, imagen, categorias);
		this.dimensiones = dim;
		this.marca = marca;
		this.material = material;
	}
	
	@Override
	public String getCaracteristicas() {
		return String.format("(Dimensiones=%s, Marca=%s, Material=%s)", dimensiones, marca, material);
	}

}
