package venta;

import java.util.*;

public class Categoria {
	private String nombre;
	private boolean eliminada;
	private Descuento descuento;
	private Set<Producto> productos = new HashSet<Producto>();

	public Categoria(String nombre) {
		this.nombre = nombre;
		this.eliminada = false;
		this.descuento = null;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isEliminada() {
		return eliminada;
	}

	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
	}

	public Descuento getDescuento() {
		return descuento;
	}

	public boolean setDescuento(Descuento descuento) {
		if(descuento == null) return false;
		
		for(Producto p : productos) {
			if(p.tieneDescuento()) return false;
		}
		this.descuento = descuento;
		for(Producto p : productos) {
			p.setDescuento(descuento);
		}
		return true;
	}
	
	public void quitarDescuento() {
		for(Producto p : productos) {
			p.quitarDescuento();
		}
		this.descuento = null;
	}
	
	public boolean tieneDescuento() {
		return descuento != null;
	}
	
	public Producto[] getProductos() {
		return productos.toArray(new Producto[0]);
	}
	
	public boolean anadirProducto(Producto p) {
		if(!p.anadirCategorias(this)) return false;
		productos.add(p);
		return true;
	}
	
	public void quitarProducto(Producto p) {
		p.quitarCategorias(this);
		productos.remove(p);
	}

	/**
	 * Metodo para imprimir una categoria.
	 */
	@Override
	public String toString() {
		return nombre;
	}
}
