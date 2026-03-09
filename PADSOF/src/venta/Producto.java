package venta;
import java.util.*;
import javax.swing.ImageIcon;

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
	
	public boolean perteneceACategoria(Categoria categoria) {
		return categorias.contains(categoria);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public ImageIcon getImagen() {
		return imagen;
	}

	public void setImagen(ImageIcon imagen) {
		this.imagen = imagen;
	}

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
			if(c.tieneDescuento()) {
				if(descuento == null) {
					descuento = c.getDescuento();
				} else {
					ret = false;
					continue;
				}
			}
			if(this.categorias.add(c))
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

	public long getId() {
		return id;
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
	
	public Descuento getDescuento() {
		return descuento;
	}

	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}
	
	public void quitarDescuento() {
		this.setDescuento(null);
	}
	
	public boolean tieneDescuento() {
		return this.descuento != null;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
	
	public Resena[] getResenas() {
		return resenas.toArray(new Resena[0]);
	}
	
	public void anadirResena(Resena resena) {
		resenas.add(resena);
	}

	public abstract String getCaracteristicas();
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Producto producto = (Producto) o;
	    return id == producto.id;
	}

	@Override
	public int hashCode() {
	    return Long.hashCode(id);
	}
	
	@Override
	public String toString() {
	    return String.format("Producto{id=%d, nombre='%s', precio=%.2f, eliminado=%s, caracteristicas=%s reseñas=", 
	                         id, nombre, precio, eliminado, getCaracteristicas()) + resenas + ", categorias="+
	                         categorias + ", descuento=["+descuento+"]}";
	}
}
