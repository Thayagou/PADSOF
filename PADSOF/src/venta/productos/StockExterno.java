package venta.productos;

import java.io.Serializable;
import exceptions.*;

/**
 * Clase que define un StockExterno de un carrito o pedido
 * 
 * @author Juan Ibáñez
 */
public class StockExterno extends Stock implements Serializable {
	private static final long serialVersionUID = 1L;
	private double precioUnitarioFinal;	/*Precio unitario del producto del stock, no el precio total del stock*/
	
	/**
	 * Creador de un StockExterno
	 * @param p Producto del stock
	 * @param uds Número de unidades en el stock
	 * @param precio Precio final unitario del producto en el contexto de un carrito o pedido
	 * @throws InvalidArgumentException Se lanza si los argumentos del método son inválidos
	 */
	public StockExterno(Producto p, int uds, double precio) throws InvalidArgumentException {
		super(p, uds);
		if(precio < 0) throw new InvalidArgumentException("No se puede establecer un precio negativo");
		this.precioUnitarioFinal = precio;
	}
	
	/**
	 * Creador de un StockExterno sin especificar precio final aun
	 * @param p Producto del stock
	 * @param uds Número de unidades en el stock
	 * @throws InvalidArgumentException Se lanza si los argumentos del método son inválidos
	 */
	public StockExterno(Producto p, int uds) throws InvalidArgumentException {
		super(p, uds);
		this.precioUnitarioFinal = getProducto().getPrecio();
	}

	/**
	 * Getter del precio final del producto
	 * @return Precio final de venta de un producto en el contexto de un carrito o pedido
	 */
	public double getPrecioUnitarioFinal() {
		return precioUnitarioFinal;
	}

	/**
	 * Setter del precio final del producto
	 * @param precioFinal Precio final de venta de un producto en el contexto de un carrito o pedido
	 * @throws InvalidArgumentException Se lanza si los argumentos del método son inválidos
	 */
	public void setPrecioUnitarioFinal(double precioFinal) throws InvalidArgumentException{
		if(precioFinal < 0) throw new InvalidArgumentException("No se puede establecer un precio negativo");
		this.precioUnitarioFinal = precioFinal;
	}
	
	/**
	 * Método para obtener el precio total del stock
	 * @return Precio total del stock entre todas las unidades
	 */
	public double getPrecioTotal() {
		return getPrecioUnitarioFinal()*getUdsEnStock();
	}
	
	@Override
	public String toString() {
		return getProducto().getNombre() + " x " + getUdsEnStock() + ". Precio por unidad: " + precioUnitarioFinal + "€. Precio acumulado: " + getPrecioTotal() + "€.";
	}
}
