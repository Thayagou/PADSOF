package venta.descuentos;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;

public interface Descontable {
	public boolean anadirDescuento(Descuento descuento) throws InvalidArgumentException, DoubleDiscountException;
	public void quitarDescuento();
	public boolean tieneDescuento();
}
