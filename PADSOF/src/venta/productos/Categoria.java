package venta.productos;

import java.util.*;
import venta.descuentos.*;

public class Categoria {
	private String nombre;
	private boolean eliminada;
	private Descuento descuento;
	private Set<Producto> productos = new HashSet<Producto>();

	/**
	 * Creador de la clase Categoria
	 * @param nombre Nombre de la categoría
	 */
	public Categoria(String nombre) {
		this.nombre = nombre;
		this.eliminada = false;
		this.descuento = null;
	}
	
	/**
	 * Getter del nombre de la categoría
	 * @return Nombre de la categoría
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setter del nombre de la categoría
	 * @param nombre Nuevo nombre para la categoría
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método para comprobar si la categoría está eliminada
	 * @return true si está eliminada, false si no
	 */
	public boolean isEliminada() {
		return eliminada;
	}

	/**
	 * Método para eliminar la categoría
	 */
	public void eliminar() {
		this.eliminada = true;
	}
	
	/**
	 * Método para restaurar una categoría eliminada
	 */
	public void restaurar() {
		this.eliminada = false;
	}

	/**
	 * Getter del descuento de la categoría
	 * @return Descuento de la categoría
	 */
	public Descuento getDescuento() {
		return descuento;
	}

	/**
	 * Método para añadir un descuento a una categoría
	 * @param descuento Descuento que se añade a la categoría
	 * @return true si se pudo añadir el descuento, false si no
	 */
	public boolean anadirDescuento(Descuento descuento) {
		if(descuento == null || this.tieneDescuento()) return false;
		
		for(Producto p : productos) {
			if(p.tieneDescuento()) return false;
		}
		this.descuento = descuento;
		for(Producto p : productos) {
			p.anadirDescuento(descuento);
		}
		return true;
	}
	
	/**
	 * Método para quitarle el descuento a la categoría
	 */
	public void quitarDescuento() {
		for(Producto p : productos) {
			p.quitarDescuento();
		}
		this.descuento = null;
	}
	
	/**
	 * Método para comprobar si la categoría tiene descuento
	 * Utiliza actualizacion en diferido para quitar el descuento si esta caducado
	 * @return true si tiene descuento, false si no
	 */
	public boolean tieneDescuento() {
		if(this.descuento != null) {
			if(!this.descuento.isCaducado()) {
				return true;
			} else {
				this.quitarDescuento();
				return false;
			}
		} else return false;
	}
	
	/**
	 * Getter de los productos de la categoría
	 * @return Array con los productos de la categoría
	 */
	public Producto[] getProductos() {
		return productos.toArray(new Producto[0]);
	}
	
	/**
	 * Método para añadir un producto a la categoría
	 * @param p Producto que se añade
	 */
	void anadirProducto(Producto p) {
		productos.add(p);
	}
	
	/**
	 * Método para quitar un producto de una categoría
	 * @param p Producto que se quita de la categoría
	 */
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
}
