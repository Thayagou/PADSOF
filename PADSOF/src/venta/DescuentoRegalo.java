package venta;

import java.time.LocalDateTime;

public class DescuentoRegalo extends Descuento {
	private Producto regalo;
	
	public DescuentoRegalo(double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, Producto regalo) {
		super(valorMin, inicio, fin, condicion);
		this.regalo = regalo;
	}

	@Override
	public Producto getRegalo(int numUds, double volumen, double precio) {
		if(this.cumpleCondiciones(numUds, volumen)) {
			return regalo;
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return super.toString()+"{Regalo="+regalo.getNombre()+"}";
	}
}
