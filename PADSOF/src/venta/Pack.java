package venta;

import java.util.*;

import javax.swing.ImageIcon;

public class Pack extends Producto {
	private Set<Stock> productos = new HashSet<Stock>();
	
	public Pack(Set<Stock> stocks, String nombre, String desc, double precio, ImageIcon imagen, Categoria...categorias) {
		super(nombre, desc, precio, imagen, categorias);
		for(Stock s : stocks) {
			productos.add(s);
		}
	}
	
	public Set<Stock> getProductos() {
		return productos;
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
