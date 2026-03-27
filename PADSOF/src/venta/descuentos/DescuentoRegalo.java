package venta.descuentos;

import java.time.LocalDateTime;
import exceptions.*;

import venta.productos.Producto;

/**
 * Clase básica de los descuentos por regalo
 * 
 * @author Juan Ibáñez
 */
public class DescuentoRegalo extends Descuento {
	private Producto regalo;
	
	/**
	 * Creador de la clase DescuentoRegalo
	 * @param valorMin Valor minimo para poder aplicar el descuento (de unidades o volumen de compra)
	 * @param inicio Fecha y hora de inicio del descuento
	 * @param fin Fecha y hora de finalizacion del descuento
	 * @param condicion Tipo de condicion del descuento
	 * @param regalo Producto que se añade al carrito si se aplica el descuento
	 */
	public DescuentoRegalo(double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, Producto regalo) throws InvalidArgumentException {
		super(valorMin, inicio, fin, condicion);
		
		if(regalo == null) throw new InvalidArgumentException("El regalo no puede ser null");
		this.regalo = regalo;
	}

	/**
	 * Método para obtener el regalo que se añade al carrito
	 */
	@Override
	public Producto getRegalo(int numUds, double volumen) throws InvalidArgumentException {
		if(numUds < 0 || volumen < 0) throw new InvalidArgumentException("No se pueden pasar valores negativos al descuento");
		
		if(this.cumpleCondiciones(numUds, volumen)) {
			return regalo;
		} else {
			return null;
		}
	}
	
	/**
	 * Método para imprimir un descuento de regalo
	 */
	@Override
	public String toString() {
		return super.toString()+"{Regalo="+regalo.getNombre()+"}";
	}
}
