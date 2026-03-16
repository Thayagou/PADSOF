package wallapop;

import java.util.*;
import usuario.ClienteRegistrado;

public class Cartera {
	private ClienteRegistrado dueno;
	private List<ArticuloSegundaMano> articulos = new ArrayList<>();
	private List<Intercambio> intercambios = new ArrayList<>();
	
	public Cartera(ClienteRegistrado dueno) {
		this.dueno = dueno;
	}
	
	
	public List<ArticuloSegundaMano> getArticulosDisponibles() {
		List<ArticuloSegundaMano> articulosDisp = new ArrayList<>();
		
		for(ArticuloSegundaMano art: articulos) {
			if (art.isDisponible()) articulosDisp.add(art);
		}
		
		return articulosDisp;
	}
	
	@Override
	public String toString() {
		return "Propietario: "+ dueno + 
				"\n" + articulos +
				"\n" + intercambios;
	}
	
	public List<ArticuloSegundaMano> getArticulos() {
		return articulos;
	}
	
	public String getNombreDueno() {
		return dueno.getNombre();
	}
	
	public boolean addArticulo(ArticuloSegundaMano articulo) {
		return articulos.add(articulo);
	}
	
	public boolean addIntercambio(Intercambio intercambio) {
		return intercambios.add(intercambio);
	}
	
	public boolean aceptarIntercambio(Intercambio i) {	
		return i.aceptarIntercambio(this);
	}
	
	public boolean rechazarIntercambio(Intercambio i) {	
		return i.rechazarIntercambio(this);
	}
	
	public boolean cancelarIntercambio(Intercambio i) {	
		return i.cancelarIntercambio(this);
	}
}



