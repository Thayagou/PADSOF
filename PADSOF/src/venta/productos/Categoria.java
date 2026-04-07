package venta.productos;

import java.io.Serializable;
import java.util.*;

import exceptions.*;
import venta.descuentos.*;

/**
 * Clase básica Categoría
 * 
 * @author Juan Ibáñez
 */
public class Categoria implements Serializable, Descontable {
	private static final long serialVersionUID = 1L;
	/**Nombre de la categoría*/
	private String nombre;
	/**Indica si está eliminada la categoría*/
	private boolean eliminada;
	/**Descuento de la categoría si tiene*/
	private Descuento descuento;
	/**Productos que pertenecen a esa categoría*/
	private Set<Producto> productos = new HashSet<Producto>();

	/**
	 * Creador de la clase Categoria
	 * @param nombre Nombre de la categoría
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public Categoria(String nombre) throws InvalidArgumentException {
		if(nombre == null) throw new InvalidArgumentException("El nombre de la categoría no puede estar vacío");
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
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public void setNombre(String nombre) throws InvalidArgumentException {
		if(nombre == null) throw new InvalidArgumentException("El nombre de la categoría no puede estar vacío");
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
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 * @throws DoubleDiscountException Se lanza si uno de los productos ya tiene descuento
	 */
	public boolean anadirDescuento(Descuento descuento) throws InvalidArgumentException, DoubleDiscountException {
		if(descuento == null) throw new InvalidArgumentException("El descuento no puede ser null");
		if(this.tieneDescuento()) throw new DoubleDiscountException("La categoría ya tiene un descuento", "añadir descuento a categoría", getNombre());
		
		for(Producto p : productos) {
			if(p.tieneDescuento()) throw new DoubleDiscountException("Uno de los productos de la categoría ya tiene descuento", "añadir descuento a categoría", getNombre());
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
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	void anadirProducto(Producto p) throws InvalidArgumentException {
		if(p == null) throw new InvalidArgumentException("El producto no puede ser null");
		productos.add(p);
	}
	
	/**
	 * Método para quitar un producto de una categoría
	 * @param p Producto que se quita de la categoría
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	void quitarProducto(Producto p) throws InvalidArgumentException {
		if(p == null) throw new InvalidArgumentException("No se puede quitar un producto null");
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
