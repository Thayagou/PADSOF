package venta.productos.caracteristicas;

import javax.swing.ImageIcon;

import exceptions.DoubleDiscountException;
import exceptions.InvalidArgumentException;
import venta.productos.*;

public class CaracteristicasFigura implements CaracteristicasProducto {
	public final String dimensiones;
	public final String marca;
	public final String material;

	public CaracteristicasFigura(String dimensiones, String marca, String material) {
		this.dimensiones = dimensiones;
		this.marca = marca;
		this.material = material;
	}
	
	@Override
	public Producto crearProducto(String nombre, String descripcion, double precio, ImageIcon image, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		return new Figura(nombre, descripcion, precio, image, dimensiones, marca, material, categorias);
	}
}
