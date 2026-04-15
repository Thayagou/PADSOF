package modelo.venta.productos;

import java.io.Serializable;

import modelo.exceptions.*;
import modelo.sistema.AsignadorId;

/**
 * Clase básica Stock
 * 
 * @author Juan Ibáñez
 */
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;
	/**Id del stock*/
	private final long id;
	/**Producto del stock*/
	private Producto producto;
	/**Unidades en stock*/
	private int udsEnStock;
	
	/**
	 * Creador de la clase Stock
	 * @param p Producto del stock
	 * @param uds Unidades en stock del producto
	 * @throws InvalidArgumentException Se lanza si los argumentos del nuevo Stock son inválidos
	 */
	public Stock(Producto p, int uds) throws InvalidArgumentException {
		if(p == null) throw new InvalidArgumentException("El producto no puede ser null", "crear stock");
		if(uds < 0) throw new InvalidArgumentException("El número de unidades no puede ser negativo", "crear stock");
		this.id = AsignadorId.getInstancia().siguienteId();
		this.producto = p;
		this.udsEnStock = uds;
	}
	
	/**
	 * Getter de la id del stock
	 * @return id del stock
	 */
	public long getId() {
		return id;
	}

	/**
	 * Getter de las unidades del stock
	 * @return Unidades del producto en el stock
	 */
	public int getUdsEnStock() {
		return udsEnStock;
	}

	/**
	 * Setter de las unidades del stock
	 * @param udsEnStock Unidades del producto en el stock
	 * @throws InvalidArgumentException Se lanza si los argumentos del método son inválidos
	 */
	public void setUdsEnStock(int udsEnStock) throws InvalidArgumentException {
		if(udsEnStock < 0) throw new InvalidArgumentException("No se pueden establecer unidades negativas", "modificar unidades en stock");
		this.udsEnStock = udsEnStock;
	}
	
	/**
	 * Método para reducir el stock en una unidad
	 */
	public void reducirStock() {
		if(this.udsEnStock > 0)
			this.udsEnStock--;
	}
	
	/**
	 * Método para reducir el stock en n unidades
	 * @param unidades Número de unidades a reducir
	 * @throws InvalidArgumentException Se lanza si los argumentos del método son inválidos
	 */
	public void reducirStock(int unidades) throws InvalidArgumentException {
		if(unidades < 0) throw new InvalidArgumentException("No se pueden reducir unidades negativas", "reducir stock");
		if((udsEnStock -= unidades) < 0)
			udsEnStock = 0;
	}
	
	/**
	 * Método para incrementar el stock en una unidad
	 */
	public void incrementarStock() {
		this.udsEnStock++;
	}
	
	/**
	 * Método para incrementar el stock en n unidades
	 * @param unidades Número de unidades a incrementar
	 * @throws InvalidArgumentException Se lanza si los argumentos del método son inválidos
	 */
	public void incrementarStock(int unidades) throws InvalidArgumentException {
		if(unidades < 0) throw new InvalidArgumentException("No se pueden incrementar unidades negativas", "incrementar stock");
		this.udsEnStock += unidades;
	}

	/**
	 * Getter del producto del stock
	 * @return Producto del cual el stock cuenta unidades
	 */
	public Producto getProducto() {
		return producto;
	}
	
	/**
	 * Método para comprobar si el stock tiene unidades disponibles
	 * @return true si las tiene, false si no
	 */
	public boolean disponible() {
		return udsEnStock > 0;
	}
	
	/**
	 * Método para comprobar si el stock tiene n unidades disponibles
	 * @param uds Número de unidades que debe tener al menos
	 * @return true si tiene n o más unidades, false si no
	 */
	public boolean disponible(int uds) {
		return udsEnStock >= uds;
	}
	
	/**
	 * Método para obtener el código hash de un stock, usando la id
	 */
	@Override
	public int hashCode() {
	    return Long.hashCode(id);
	}
	
	/**
	 * Método para determinar si dos stocks son iguales, usando la id
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Stock st = (Stock) o;
	    return id == st.id;
	}
	
	/**
	 * Método para imprimir un stock
	 */
	@Override
	public String toString() {
		return String.format("Stock{%s Numero de unidades=%d}", producto, udsEnStock);
	}
}
