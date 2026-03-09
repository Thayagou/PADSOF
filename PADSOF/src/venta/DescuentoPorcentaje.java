package venta;

import java.time.LocalDateTime;

public class DescuentoPorcentaje extends Descuento {
	private double porcentaje;
	
	public DescuentoPorcentaje(double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double porcentaje) {
		super(valorMin, inicio, fin, condicion);
		this.porcentaje = porcentaje;
	}
	
	@Override
	public double getPrecioDescontado(int numUds, double volumen, double precio) {
		if(this.cumpleCondiciones(numUds, volumen)) {
			return precio-(precio*(porcentaje/100));
		} else {
			return precio;
		}
	}
	
	@Override
	public String toString() {
		return super.toString()+"{Porcentaje="+porcentaje+"}";
	}
}
