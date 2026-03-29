package wallapop;

import java.util.*;

import sistema.AsignadorId;
import venta.productos.Categoria;
import usuario.ClienteRegistrado;

import java.awt.Image;
import java.io.Serializable;

public class ArticuloSegundaMano implements Serializable {
	private static final long serialVersionUID = 1L;
	private final long id;
	private String nombre;
	private String descripcion;
	private Cartera dueno;
	private Image image;
	private List<Categoria> categorias = new ArrayList<>();
	private String interesadoEn;
	private boolean disponible;
	private Valoracion valoracion;
	
	public ArticuloSegundaMano(String nombre, String desc, Cartera dueno, List<Categoria> categorias, String interesadoEn) {
		id = AsignadorId.getInstancia().siguienteId();
		this.nombre = nombre;
		this.descripcion = desc;
		this.dueno = dueno;
		if (categorias != null) {
			this.categorias.addAll(categorias);
		}
		this.interesadoEn = interesadoEn;
		this.disponible = false;
	}	

	public String getNombre() {
		return nombre;
	}
	
	public Valoracion getValoracion() {
		return valoracion;
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

	public String getDescripcion() {
		return descripcion;
	}
	
	public Cartera getDueno() {
		return dueno;
	}

	public Image getImage() {
		return image;
	}

	public Categoria[] getCategorias() {
		return categorias.toArray(new Categoria[0]);
	}

	public String getInteresadoEn() {
		return interesadoEn;
	}

	public boolean isDisponible() {
		return disponible;
	}
	
	public void reservar() {
		disponible = false;
	}
	
	public void disponibilizar() {
		disponible = true;
	}

	public void anadirValoracion(Valoracion valoracion) {
		this.valoracion = valoracion;
	}
	
	public ClienteRegistrado getPropietario() {
		return dueno.getDueno();
	}
	
	
}

