package wallapop;

import java.util.*;

import sistema.AsignadorId;
import venta.productos.Categoria;
import usuario.ClienteRegistrado;

import java.awt.Image;
import java.io.Serializable;

/**
 * Clase que representa un artículo de segunda mano publicado por los clientes con el fin de intercambiarlos
 */
public class ArticuloSegundaMano implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Identificador único del artículo en la tienda */
	private final long id;
	/** Nombre del artículo */
	private String nombre;
	/** Descripción del articulo */
	private String descripcion;
	/** Cartera del cliente propietario */
	private Cartera dueno;
	/** Imagen publicada por el propietario */
	private Image image;
	/** Lista de categorías a las que pertenece el artículo */
	private List<Categoria> categorias = new ArrayList<>();
	/** Descripción de artículos por los que el propietario querría intercambiar este artículo */
	private String interesadoEn;
	/** Indica si el artículo se encuentra o no disponible para los demás */
	private boolean disponible;
	/** Valoración que solicita el propietario y que es completada por algún empleado */
	private Valoracion valoracion;
	
	/**
	 * Constructor de un artículo de segunda mano
	 * @param nombre Nombre del artículo
	 * @param desc Descripción del artículo
	 * @param dueno Cartera a la que pertenece este artículo
	 * @param categorias Categorías a las que pertenece el artículo
	 * @param interesadoEn Describe por lo que quiere intercambiar el dueño este artículo
	 */
	public ArticuloSegundaMano(String nombre, String desc, Cartera dueno, Categoria[] categorias, String interesadoEn) {
		id = AsignadorId.getInstancia().siguienteId();
		this.nombre = nombre;
		this.descripcion = desc;
		this.dueno = dueno;
		
		for (Categoria c: categorias) {
			this.categorias.add(c);
		}
		
		this.interesadoEn = interesadoEn;
		this.disponible = false;
	}	

	/**
	 * Getter del nombre del artículo
	 * @return su nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Getter del propietario de la cartera
	 * @return el cliente propietario
	 */
	public ClienteRegistrado getPropietario() {
		return dueno.getDueno();
	}
	
	/**
	 * Getter de la valoración del artículo
	 * @return la valoración
	 */
	public Valoracion getValoracion() {
		return valoracion;
	}

	/**
	 * Getter de la descripción del artículo
	 * @return su descripción
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Getter del dueño del artículo
	 * @return la cartera del dueño
	 */
	public Cartera getDueno() {
		return dueno;
	}

	/**
	 * Getter de la imagen del artículo
	 * @return su imagen
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Getter de las categorías del artículo
	 * @return array con las categorías asociadas
	 */
	public Categoria[] getCategorias() {
		return categorias.toArray(new Categoria[0]);
	}

	/**
	 * Getter del artículo en el que el dueño está interesado a cambio
	 * @return descripción del artículo deseado
	 */
	public String getInteresadoEn() {
		return interesadoEn;
	}

	/**
	 * Indica si el artículo está disponible para intercambio
	 * @return true si está disponible, false en caso contrario
	 */
	public boolean isDisponible() {
		return disponible;
	}

	/**
	 * Marca el artículo como no disponible, reservándolo
	 */
	public void reservar() {
		disponible = false;
	}

	/**
	 * Marca el artículo como disponible para intercambio
	 */
	public void disponibilizar() {
		disponible = true;
	}

	/**
	 * Setter de la valoración
	 * @param valoracion
	 */
	public void anadirValoracion(Valoracion valoracion) {
		if (this.valoracion != null) return;
		this.valoracion = valoracion;
	}
	
	@Override
	public String toString() {
		return nombre + 
				"\nDescripcion: " + descripcion + 
				"\nPropietario: " + dueno.getDueno().getNombre() +
				"\nCategorias:" + categorias + 
				"\nInteresado en: " + interesadoEn +
				"\nDisponible: " + disponible + 
				"\nValoracion: " + (valoracion == null ? "Pendiente de solicitud" : valoracion.toStringSinArticulo());
	}
}

