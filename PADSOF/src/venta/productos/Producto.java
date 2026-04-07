package venta.productos;
import java.io.Serializable;
import java.util.*;
import javax.swing.ImageIcon;
import venta.descuentos.*;
import exceptions.*;
import estadistica.*;
import venta.productos.caracteristicas.*;

import sistema.AsignadorId;

/**
 * Clase básica Producto
 * 
 * @author Juan Ibáñez
 */
public abstract class Producto implements Serializable, Descontable {
	private static final long serialVersionUID = 1L;
	/**Id del producto*/
	private final long id;
	/**Nombre del producto*/
	private String nombre;
	/**Descripción del producto*/
	private String descripcion;
	/**Precio del producto*/
	private double precio;
	/**Imagen del producto*/
	private ImageIcon imagen;
	/**Reseñas que se han hecho al producto*/
	private List<Resena> resenas = new ArrayList<Resena>();
	/**Categorías a las que pertenece el producto*/
	private Set<Categoria> categorias = new HashSet<Categoria>();
	/**Descuento del producto si tiene*/
	private Descuento descuento;
	/**Indica si está eliminado el producto*/
	private boolean eliminado;
	/**Estadísticas del producto*/
	private StatsProducto estadisticas;
	
	/**
	 * Constructor de producto
	 * @param nombre Nombre del producto
	 * @param desc Descripción del producto
	 * @param precio Precio del producto
	 * @param imagen Imagen del producto
	 * @param categorias Listado de las categorías a las que pertenece el producto
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 * @throws DoubleDiscountException Se lanza si hay conflixto de descuentos
	 */
	public Producto(String nombre, String desc, double precio, ImageIcon imagen, Categoria...categorias ) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(nombre == null || desc == null || categorias == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos", "crear producto");
		if(precio < 0) throw new InvalidArgumentException("El precio del producto no puede ser negativo", "crear producto");
		this.id = AsignadorId.getInstancia().siguienteId();
		this.nombre = nombre;
		this.descripcion = desc;
		this.precio = precio;
		this.imagen = imagen;
		this.descuento = null;
		this.eliminado = false;
		
		anadirCategorias(categorias);
	}
	
	/**
	 * Getter de la id del producto
	 * @return Id del producto
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Método para comprobar si el producto pertenece a una categoría.
	 * @param categoria Categoría que se comprueba
	 * @return true si pertenece a la categoría, false si no.
	 */
	public boolean perteneceACategoria(Categoria categoria) {
		return categorias.contains(categoria);
	}

	/**
	 * Getter del nombre del producto
	 * @return Nombre del producto
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setter del nombre del producto
	 * @param nombre Nuevo nombre del producto
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Getter de la descripción del producto
	 * @return Descripción del producto
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Setter de la descripción del producto
	 * @param descripcion Nueva descripción del producto
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Getter del precio del producto
	 * @return Precio del producto
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * Setter del precio del producto
	 * @param precio Nuevo precio del producto
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * Getter de la imagen del producto
	 * @return Imagen del producto
	 */
	public ImageIcon getImagen() {
		return imagen;
	}

	/**
	 * Setter de la imagen del producto
	 * @param imagen Nueva imagen del producto
	 */
	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}
	
	/**
	 * Método para obtener las categorías del producto
	 * @return Array de las categorías del producto
	 */
	public Categoria[] getCategorias() {
		return categorias.toArray(new Categoria[0]);
	}
	
	/**
	 * Setter de las estadísticas del producto
	 * @param stats Instancia de StatsProducto con todas sus estadísticas
	 */
	public void setStatsProducto(StatsProducto stats) {
		estadisticas = stats;
	}
	
	/**
	 * Actualiza el vector de estadísticas de un producto tras hacer una llamada a añadir/modificar categorías
	 */
	private void actualizarVector() {
		if(estadisticas != null) estadisticas.actualizarVector();
	}
	
	/**
	 * Obtiene el vector de recomendación de un producto a partir de su estadística
	 * @return Un vector en formato de mapa, que asigna a cada categoría una ponderación de interés
	 */
	public Map<Categoria, Double> getVectorRecomendacion() {
		return estadisticas.getVectorRecomendacion();
	}
	
	/**
	 * Obtiene la norma del vector de recomendaciones
	 * @return Norma del vector
	 */
	public double getNormaVector() {
		return estadisticas.getNormaVector();
	}
	
	/**
	 * Método para comprobar si se pueden añadir las categorías al producto
	 * @param categorias Categorias a comprobar si son compatibles
	 * @return true si todas las categorias se pueden añadir, false si no
	 */
	public boolean puedeAnadirCategorias(Categoria...categorias) {
		Descuento descuento = this.getDescuento();
		for(Categoria c : categorias) {
			if(c == null || this.categorias.contains(c)) {
				return false;
			}
			if(c.tieneDescuento()) {
				if(descuento != null && !descuento.equals(c.getDescuento())) return false;
				descuento = c.getDescuento();
			}
		}
		return true;
	}
	
	/**
	 * Metodo para añadir categorias al producto
	 * @param categorias Categorias que se van a añadir al producto
	 * @return true si se añadieron todas las categorias, false si alguna no pudo añadirse.
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 * @throws DoubleDiscountException Se lanza si hay conflixto de descuentos
	 */
	public boolean anadirCategorias(Categoria...categorias) throws DoubleDiscountException, InvalidArgumentException {
		if(categorias == null) throw new InvalidArgumentException("Array de categorías null");
		for(Categoria c : categorias) if(c == null) throw new InvalidArgumentException("No puede haber categorías null entre las categorías");
		
		if(!puedeAnadirCategorias(categorias)) throw new DoubleDiscountException("Las categorías no son compatibles entre sí o con el producto debido a los descuentos", "añadir categorías", this.getNombre());
		for(Categoria c : categorias) {			
			if(c.tieneDescuento()) {
				if(!this.tieneDescuento()) {
					descuento = c.getDescuento();
				}
			}
			this.categorias.add(c);
			c.anadirProducto(this);
		}
		
		actualizarVector();
		
		return true;
	}
	
	/**
	 * Metodo para quitar categorias de un producto
	 * @param categorias Categorias que se van a quitar del producto
	 */
	public void quitarCategorias(Categoria...categorias) {
		for(Categoria c : categorias) {
			if(c.tieneDescuento()) {
				descuento = null;
			}
			if(this.categorias.remove(c)) {
				try{
					c.quitarProducto(this);
				} catch(Exception e) {};
			}
				
		}
		
		actualizarVector();
	}
	
	/**
	 * Método para cambiar todas las categorias de golpe
	 * @param categorias Nuevas categorias para el producto
	 * @return true scuando se añaden todas las categorias sin lanzar una excepcion
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 * @throws DoubleDiscountException Se lanza si hay conflixto de descuentos
	 */
	public boolean setCategorias(Categoria...categorias) throws InvalidArgumentException, DoubleDiscountException {
		this.quitarCategorias(this.getCategorias());
		this.anadirCategorias(categorias);
		return true;
	}

	/**
	 * Metodo para obtener la puntuacion media del producto
	 * @return Puntuacion media de las reseñas del producto
	 */
	public double getPuntuacionMedia() {
		if(resenas.isEmpty()) return 5.0;
		
		double suma = 0;
		for(Resena r : resenas) {
			suma += r.getPuntuacion();
		}
		return suma/resenas.size();
	}
	
	/**
	 * Getter del descuento del producto
	 * @return Descuento del producto
	 */
	public Descuento getDescuento() {
		if(!tieneDescuento()) return null;
		return descuento;
	}

	/**
	 * Método para añadir un descuento al producto
	 * @param descuento Nuevo descuento del producto
	 * @return true si se pudo añadir el descuento, false si no se pudo
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 * @throws DoubleDiscountException Se lanza si hay conflixto de descuentos
	 */
	public boolean anadirDescuento(Descuento descuento) throws InvalidArgumentException, DoubleDiscountException {
		if(descuento == null) throw new InvalidArgumentException("El descuento no puede ser null");
		if(tieneDescuento()) throw new DoubleDiscountException("El producto ya tiene un descuento", "añadir descuento a producto", getNombre());
		this.descuento = descuento;
		return true;
	}
	
	/**
	 * Método para quitar el descuento del producto
	 */
	public void quitarDescuento() {
		this.descuento = null;
	}
	
	/**
	 * Método para comprobar si el producto tiene descuento
	 * Se usa una actualizacion en diferido para quitar el descuento si esta caducado
	 * @return true si sí lo tiene, false si no
	 */
	public boolean tieneDescuento() {
		if(this.descuento != null) {
			if(!this.descuento.isCaducado()) {
				return true;
			} else {
				this.quitarDescuento();
				return false;
			}
		} else return false;
	}

	/**
	 * Método para comprobar si el producto ha sido eliminado
	 * @return true si está eliminado, false si no
	 */
	public boolean isEliminado() {
		return eliminado;
	}

	/**
	 * Método para eliminar un producto
	 */
	public void eliminar() {
		this.eliminado = true;
	}
	
	/**
	 * Método para restaurar un producto eliminado
	 */
	public void restaurar() {
		this.eliminado = false;
	}
	
	/**
	 * Método para obtener las reseñas de un producto
	 * @return Array con las reseñas del producto
	 */
	public Resena[] getResenas() {
		return resenas.toArray(new Resena[0]);
	}
	
	/**
	 * Método para añadir una reseña al producto
	 * @param resena Reseña a añadir
	 */
	public void anadirResena(Resena resena) {
		resenas.add(resena);
	}

	/**
	 * Método abstracto para obtener las características del producto
	 * @return Descripción de las características, según el tipo
	 */
	public abstract String getCaracteristicas();
	
	/**
	 * Método abstracto para modificar las características de un producto
	 * @param c Objeto que contiene las características del producto
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public abstract void setCaracteristicas(CaracteristicasProducto c) throws InvalidArgumentException;
	
	/**
	 * Método para determinar si dos productos son iguales, usando la id
	 */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Producto producto = (Producto) o;
	    return id == producto.id;
	}

	/**
	 * Método para obtener el código hash de un producto, usando la id
	 */
	@Override
	public int hashCode() {
	    return Long.hashCode(id);
	}
	
	/**
	 * Método para imprimir un producto
	 */
	@Override
	public String toString() {
	    return String.format("Producto: id=%d\n"
	    		+ "  %s\n"
	    		+ "  %s\n"
	    		+ "  precio=%.2f\n"
	    		+ "  caracteristicas=%s\n"
	    		+ "  puntuacionMedia=%f\n"
	    		+ "  reseñas=%s\n"
	    		+ "  categorias=%s\n"
	    		+ "  descuento=[%s]\n", 
	    		id, nombre, descripcion, precio, getCaracteristicas(), getPuntuacionMedia(), resenas, categorias, descuento);
	}
}
