package venta.productos;

import exceptions.*;
import java.util.*;

import javax.swing.ImageIcon;

/**
 * Clase que define el subtipo de Producto, Pack
 * 
 * @author Juan Ibáñez
 */
public class Pack extends Producto {
	private Set<Stock> productos = new HashSet<Stock>();
	
	/**
	 * Creador de un pack
	 * @param stocks Array con los stocks de los productos del pack con sus cantidades
	 * @param nombre Nombre del pack
	 * @param desc Descripción del pack
	 * @param precio Precio del pack
	 * @param imagen Imagen del pack
	 * @param categorias Array de categorías del pack
	 */
	public Pack(Stock[] stocks, String nombre, String desc, double precio, ImageIcon imagen, Categoria...categorias) 
			throws DoubleDiscountException, InvalidArgumentException{
		super(nombre, desc, precio, imagen, categorias);
		
		if(stocks == null) throw new InvalidArgumentException("El array de stocks no puede ser null");
		for(Stock st : stocks) {
			if(st == null) throw new InvalidArgumentException("No puede haber un stock null entre los stocks del pack");
		}
		
		if((stocks.length == 1 && stocks[0].getUdsEnStock() < 2) || stocks.length == 0){
			throw new InvalidArgumentException("No se puede crear un pack con menos de dos productos");
		}
		for(Stock s : stocks) productos.add(s);
	}
	
	/**
	 * Getter de los productos del pack
	 * @return Array con los Stocks del pack
	 */
	public Stock[] getProductos() {
		return productos.toArray(new Stock[0]);
	}

	/**
	 * Método para imprimir las características (listado de productos) del pack
	 */
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
