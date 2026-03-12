package venta.descuentos;

import java.time.LocalDateTime;

public class DescuentoDinero extends Descuento {
	private double dinero;
	
	/**
	 * Creador de la clase DescuentoDinero
	 * @param valorMin Valor minimo para poder aplicar el descuento (de unidades o volumen de compra)
	 * @param inicio Fecha y hora de inicio del descuento
	 * @param fin Fecha y hora de finalizacion del descuento
	 * @param condicion Tipo de condicion del descuento
	 * @param dinero Dinero que se descuenta si se aplica el descuento
	 */
	public DescuentoDinero(double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double dinero) {
		super(valorMin, inicio, fin, condicion);
		this.dinero = dinero;
	}
	
	/**
	 * Método para obtener el precio descontado.
	 * Resta al precio original el dinero de descuento el numero de unidades,
	 * repartiendo el descuento entre cada unidad de producto para las estadísticas.
	 */
	@Override
	public double getPrecioDescontado(int numUds, double volumen, double precio) {
		if(this.cumpleCondiciones(numUds, volumen)) {
			return precio - dinero/numUds;
		} else {
			return precio;
		}
	}
	
	/**
	 * Método para imprimir un descuento por dinero
	 */
	@Override
	public String toString() {
		return super.toString()+"{Dinero="+dinero+"}";
	}
}
