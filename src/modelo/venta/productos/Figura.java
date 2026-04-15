package modelo.venta.productos;

import java.io.Serializable;

import javax.swing.ImageIcon;

import modelo.exceptions.*;
import modelo.venta.productos.caracteristicas.*;

/**
 * Clase que define el subtipo de Producto, Figura
 * 
 * @author Juan Ibáñez
 */
public class Figura extends Producto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**Dimensiones de la figura*/
	private String dimensiones;
	/**Marca de la figura*/
	private String marca;
	/**Material de la figura*/
	private String material;
	
	/**
	 * Creador de la clase Figura
	 * @param nombre Nombre de la figura
	 * @param desc Descripcion de la figura
	 * @param precio Precio de la figura
	 * @param imagen Imagen de la figura
	 * @param dim Dimensiones de la figura
	 * @param marca Marca de la figura
	 * @param material Material de la figura
	 * @param categorias Array de categorias de la figura
	 * @throws InvalidArgumentException Si alguno de los argumentos es inválido
	 * @throws DoubleDiscountException Si hay algún conflicto de descuentos por las categorías del producto
	 */
	public Figura(String nombre, String desc, double precio, ImageIcon imagen, String dim, String marca, String material, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		super(nombre, desc, precio, imagen, categorias);
		
		if(dim == null || marca == null || material == null) throw new InvalidArgumentException("No se pueden dejar características vacías", "crear figura");
		
		this.dimensiones = dim;
		this.marca = marca;
		this.material = material;
	}
	
	@Override
	public void setCaracteristicas(CaracteristicasProducto c) throws InvalidArgumentException {
		if(!(c instanceof CaracteristicasFigura)) throw new InvalidArgumentException("Se esperaba CaracteristicasFigura", "modificar carecterísticas figura");
		CaracteristicasFigura a = (CaracteristicasFigura)c;
		if(a.dimensiones == null || a.marca == null || a.material == null)
			throw new InvalidArgumentException("Atributos de figura inválidos", "modificar carecterísticas figura");
		this.dimensiones = a.dimensiones;
		this.marca = a.marca;
		this.material = a.material;
	}
	
	/**
	 * Método para imprimir las caracteristicas de la figura
	 */
	@Override
	public String getCaracteristicas() {
		return String.format("(Dimensiones=%s, Marca=%s, Material=%s)", dimensiones, marca, material);
	}

}
