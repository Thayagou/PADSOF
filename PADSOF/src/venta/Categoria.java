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

	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}
	
	public boolean tieneDescuento() {
		return descuento != null;
	}
	
	public Set<Producto> getProductos() {
		return productos;
	}
	
	public void anadirProducto(Producto p) {
		productos.add(p);
	}
	
	public void quitarProducto(Producto p) {
		productos.remove(p);
	}

	/**
	 * Metodo para imprimir una categoria.
	 */
	@Override
	public String toString() {
		return nombre;
	}
	
	/**
	 * Metodo para obtener el hashCode de una categoria.
	 * Se utiliza el nombre de la categoria.
	 */
	@Override
	public int hashCode() {
	    return nombre.hashCode();
	}
	
	/**
	 * Metodo para comparar dos categorias y ver si son iguales.
	 * Se determinan como iguales si tienen el mismo nombre.
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Categoria c = (Categoria) o;
	    return nombre.equals(c.nombre);
	}
}
