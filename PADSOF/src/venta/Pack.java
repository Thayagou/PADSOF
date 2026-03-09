package venta;

import java.util.*;

import javax.swing.ImageIcon;

public class Pack extends Producto {
	private Set<Stock> productos = new HashSet<Stock>();
	
	public Pack(Set<Stock> stocks, String nombre, String desc, double precio, ImageIcon imagen, Categoria...categorias) {
		super(nombre, desc, precio, imagen, categorias);
		productos.addAll(stocks);
	}
	
	public Stock[] getProductos() {
		return productos.toArray(new Stock[0]);
	}

	@Override
	public String getCaracteristicas() {
		if (productos.isEmpty()) {
			return "Pack vacío";
		}

		StringBuilder s = new StringBuilder("Contenido del pack: ");

	    boolean primero = true;
	    for (Stock stock : productos) {
	    	if (!primero) {
	    		s.append(", ");
	    	}
	    	s.append(stock.getProducto().getNombre()).append(" x").append(stock.getUdsEnStock());
	    	primero = false;
	    }

	    return s.toString();
	}

}
