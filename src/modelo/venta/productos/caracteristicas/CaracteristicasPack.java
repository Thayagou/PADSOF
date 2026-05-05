package modelo.venta.productos.caracteristicas;

import java.util.List;

import modelo.exceptions.DoubleDiscountException;
import modelo.exceptions.InvalidArgumentException;
import modelo.venta.productos.*;

/**
 * Esta clase representa las caracteristicas de un pack
 */
public class CaracteristicasPack implements CaracteristicasProducto {
	/** Productos que forman el pack*/
	public final Stock[] stocks;
	
	/**
	 * Constructor de la clase
	 * @param stocks Productos que forman el pack
	 */
	public CaracteristicasPack(Stock...stocks) {
		this.stocks = stocks;
	}
	
	@Override
	public String[] getNombresCaracteristicas() {
		return List.of("Productos").toArray(new String[0]);
	}
	
	/**
	 * Sobreescribe el método que crea un producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripcion del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param categorias Categorias del producto
	 * @return Producto creado
	 * @throws InvalidArgumentException Se lanza cuando algun argumento no es válido
	 * @throws DoubleDiscountException Se lanza cuando hay conflicto de descuentos
	 */
	@Override
	public Producto crearProducto(String nombre, String descripcion, double precio, String image, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		return new Pack(stocks, nombre, descripcion, precio, image, categorias);
	}
}
