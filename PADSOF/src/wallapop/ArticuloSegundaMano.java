package wallapop;

import java.util.*;
import java.awt.Image;
import venta.Categoria;

public class ArticuloSegundaMano {
	int id;
	String nombre;
	String descripcion;
	Cartera dueno;
	Image image;
	List<Categoria> categorias = new ArrayList<>();
	String interesadoEn;
	boolean disponible;
	Valoracion valoracion;
	
	public ArticuloSegundaMano(String nombre, String desc, Cartera dueno, List<Categoria> categorias, String interesadoEn) {
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
	
	@Override
	public String toString() {
		return nombre + 
				"\nDescripcion: " + descripcion + 
				"\nPropietario: " + dueno.getNombreDueno() +
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

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public String getInteresadoEn() {
		return interesadoEn;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setValoracion(Valoracion valoracion) {
		this.valoracion = valoracion;
	}
	
	
}

