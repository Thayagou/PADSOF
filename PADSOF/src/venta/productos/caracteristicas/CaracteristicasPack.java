package venta.productos.caracteristicas;

import javax.swing.ImageIcon;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;
import venta.productos.*;

public class CaracteristicasPack implements CaracteristicasProducto {
	public final Stock[] stocks;
	
	public CaracteristicasPack(Stock...stocks) {
		this.stocks = stocks;
	}
	
	@Override
	public Producto crearProducto(String nombre, String descripcion, double precio, ImageIcon image, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		return new Pack(stocks, nombre, descripcion, precio, image, categorias);
	}
}
