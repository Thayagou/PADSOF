package venta.productos;
import java.util.*;
import javax.swing.ImageIcon;
import venta.descuentos.*;

import sistema.AsignadorId;

public abstract class Producto {
	private final long id;
	private String nombre;
	private String descripcion;
	private double precio;
	private ImageIcon imagen;
	private List<Resena> resenas = new ArrayList<Resena>();
	private Set<Categoria> categorias = new HashSet<Categoria>();
	private Descuento descuento;
	private boolean eliminado;
	
	/**
	 * Creador de producto
	 * @param nombre Nombre del producto
	 * @param desc Descripción del producto
	 * @param precio Precio del producto
	 * @param imagen Imagen del producto
	 * @param categorias Listado de las categorías a las que pertenece el producto
	 */
	public Producto(String nombre, String desc, double precio, ImageIcon imagen, Categoria...categorias ) {
		this.id = AsignadorId.getInstancia().siguienteId();
		this.nombre = nombre;
		this.descripcion = desc;
		this.precio = precio;
		this.imagen = imagen;
		this.descuento = null;
		this.eliminado = false;
		
		if(!anadirCategorias(categorias)) {
			throw new IllegalArgumentException("El producto no puede tener multiples descuentos por categoria");
		}
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
	 * Metodo para añadir categorias al producto
	 * @param categorias Categorias que se van a añadir al producto
	 * @return true si se añadieron todas las categorias, false si alguna no pudo añadirse.
	 */
	public boolean anadirCategorias(Categoria...categorias) {
		boolean ret = true;
		for(Categoria c : categorias) {
			if(this.categorias.contains(c)) {
				ret = false;
				continue;
			}
			
			if(c.tieneDescuento()) {
				if(descuento == null) {
					descuento = c.getDescuento();
				} else {
					ret = false;
					continue;
				}
			}
			this.categorias.add(c);
			c.anadirProducto(this);
		}
		return ret;
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
			if(this.categorias.remove(c))
				c.quitarProducto(this);
		}
	}
	
	/**
	 * Método para cambiar todas las categorias de golpe
	 * @param categorias Nuevas categorias para el producto
	 * @return true si se añadieron todas las categorias, false si alguna no pudo añadirse.
	 */
	public boolean setCategorias(Categoria...categorias) {
		this.quitarCategorias(this.getCategorias());
		return this.anadirCategorias(categorias);
	}

	/**
	 * Metodo para obtener la puntuacion media del producto
	 * @return Puntuacion media de las reseñas del producto
	 */
	public double getPuntuacionMedia() {
		if(resenas.isEmpty()) return 0.0;
		
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
		return descuento;
	}

	/**
	 * Método para añadir un descuento al producto
	 * @param descuento Nuevo descuento del producto
	 * @return true si se pudo añadir el descuento, false si no se pudo
	 */
	public boolean anadirDescuento(Descuento descuento) {
		if(this.descuento == null) {
			this.descuento = descuento;
			return true;
		}
		return false;
	}
	
	/**
	 * Método para quitar el descuento del producto
	 */
	public void quitarDescuento() {
		this.descuento = null;
	}
	
	/**
	 * Método para comprobar si el producto tiene descuento
	 * @return true si sí lo tiene, false si no
	 */
	public boolean tieneDescuento() {
		return this.descuento != null;
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
