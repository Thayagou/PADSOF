package modelo.venta.productos.caracteristicas;

import java.util.List;

import modelo.exceptions.DoubleDiscountException;
import modelo.exceptions.InvalidArgumentException;
import modelo.venta.productos.*;

/**
 * Esta clase representa las caracteristicas de una figura
 */
public class CaracteristicasFigura implements CaracteristicasProducto {
	/** Dimensiones de la figura*/
	public final String dimensiones;
	/** Marca de la figura*/
	public final String marca;
	/** Material de la figura*/
	public final String material;

	/**
	 * Constructor de la clase
	 * @param dimensiones Dimensiones de la figura
	 * @param marca Marca de la figura
	 * @param material Material de la figura
	 */
	public CaracteristicasFigura(String dimensiones, String marca, String material) {
		this.dimensiones = dimensiones;
		this.marca = marca;
		this.material = material;
	}
	
	@Override
	public String[] getNombresCaracteristicas() {
		return List.of("Dimensiones", "Marca", "Material").toArray(new String[0]);
	}
	
	@Override
	public Producto crearProducto(String nombre, String descripcion, double precio, String image, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		return new Figura(nombre, descripcion, precio, image, dimensiones, marca, material, categorias);
	}
}
