package venta;

import java.time.LocalDateTime;

public class DescuentoDinero extends Descuento {
	private double dinero;
	
	public DescuentoDinero(double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double dinero) {
		super(valorMin, inicio, fin, condicion);
		this.dinero = dinero;
	}
	
	@Override
	public double getPrecioDescontado(int numUds, double volumen, double precio) {
		if(this.cumpleCondiciones(numUds, volumen)) {
			return dinero;
		} else {
			return 0.0;
		}
	}
	
	@Override
	public String toString() {
		return super.toString()+"{Dinero="+dinero+"}";
	}
}
