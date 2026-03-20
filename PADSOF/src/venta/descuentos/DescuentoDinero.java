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
	public DescuentoDinero(double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double dinero) throws IllegalArgumentException {
		super(valorMin, inicio, fin, condicion);
		
		if(dinero < 0) throw new IllegalArgumentException();
		this.dinero = dinero;
	}
	
	/**
	 * Método para obtener el precio descontado.
	 * Resta al precio original el dinero de descuento el numero de unidades,
	 * repartiendo el descuento entre cada unidad de producto para las estadísticas.
	 */
	@Override
	public double getPrecioDescontado(int numUds, double volumen, double precio) throws IllegalArgumentException {
		if(numUds < 0 || volumen < 0 || precio < 0) throw new IllegalArgumentException();
		
		if(this.cumpleCondiciones(numUds, volumen)) {
			if((precio - dinero/numUds) < 0) return 0;
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
