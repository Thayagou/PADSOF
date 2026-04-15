package modelo.venta.descuentos;

import java.io.Serializable;
import java.time.LocalDateTime;

import modelo.exceptions.*;

/**
 * Clase básica de los descuentos por porcentaje de precio
 * 
 * @author Juan Ibáñez
 */
public class DescuentoPorcentaje extends Descuento implements Serializable {
	private static final long serialVersionUID = 1L;
	/**Porcentage de dinero que se descuenta*/
	private double porcentaje;
	
	/**
	 * Creador de la clase DineroPorcentaje
	 * @param valorMin Valor minimo para poder aplicar el descuento (de unidades o volumen de compra)
	 * @param inicio Fecha y hora de inicio del descuento
	 * @param fin Fecha y hora de finalizacion del descuento
	 * @param condicion Tipo de condicion del descuento
	 * @param porcentaje Porcentaje de dinero que se descuenta si se aplica el descuento
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public DescuentoPorcentaje(double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double porcentaje) throws InvalidArgumentException {
		super(valorMin, inicio, fin, condicion);
		
		if(porcentaje < 0 || porcentaje > 100) throw new InvalidArgumentException("El porcentaje debe ser un valor entre 0 y 100", "crear un descuento de tipo porcentaje");
		this.porcentaje = porcentaje;
	}
	
	/**
	 * Método para obtener el precio descontado.
	 * Le resta al precio original el porcentaje del descuento
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	@Override
	public double getPrecioDescontado(int numUds, double volumen, double precio) throws InvalidArgumentException {
		if(numUds < 0 || volumen < 0 || precio < 0) throw new InvalidArgumentException("No se pueden pasar valores negativos al descuento", "calcular el precio descontado");
		
		if(this.cumpleCondiciones(numUds, volumen)) {
			return precio-(precio*(porcentaje/100));
		} else {
			return precio;
		}
	}
	
	/**
	 * Método para imprimir un descuento de porcentaje
	 */
	@Override
	public String toString() {
		return super.toString()+"{Porcentaje="+porcentaje+"}";
	}
}
