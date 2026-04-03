package wallapop;

import java.io.Serializable;
import java.util.*;

import exceptions.InvalidArgumentException;
import usuario.ClienteRegistrado;

public class Cartera implements Serializable{
	private static final long serialVersionUID = 1L;
	private ClienteRegistrado dueno;
	private List<ArticuloSegundaMano> articulos = new ArrayList<>();
	private List<Intercambio> intercambios = new ArrayList<>();
	
	public Cartera(ClienteRegistrado dueno) {
		this.dueno = dueno;
	}
	
	
	public ArticuloSegundaMano[] getArticulosDisponibles() {
		List<ArticuloSegundaMano> articulosDisp = new ArrayList<>();
		
		for(ArticuloSegundaMano art: articulos) {
			if (art.isDisponible()) articulosDisp.add(art);
		}
		
		return articulosDisp.toArray(new ArticuloSegundaMano[0]);
	}
	
	@Override
	public String toString() {
		return 
				"\nArticulos de segunda mano: " + articulos +
				"\nIntercambios: " + intercambios;
	}
	
	public ArticuloSegundaMano[] getArticulos() {
		return articulos.toArray(new ArticuloSegundaMano[0]);
	}
	
	public ClienteRegistrado getDueno() {
		return dueno;
	}
	
	public Intercambio[] getIntercambiosPendientes() {
		return intercambios.stream().filter(i->i.getEstado().equals(EstadoIntercambio.OFERTADO)).toArray(Intercambio[]::new);
	}
	
	public boolean addArticulo(ArticuloSegundaMano articulo) {
		return articulos.add(articulo);
	}
	
	public boolean addIntercambio(Intercambio intercambio) {
		return intercambios.add(intercambio);
	}
	
	public boolean aceptarIntercambio(Intercambio intercambio) throws InvalidArgumentException {	
		if (!intercambio.getReceptor().equals(this)) throw new InvalidArgumentException("No puedes aceptar este intercambio porque no eres el receptor", "aceptar intercambio");
		return intercambio.aceptarIntercambio();
	}
	
	public boolean rechazarIntercambio(Intercambio intercambio) throws InvalidArgumentException {
		if (!intercambio.getReceptor().equals(this)) throw new InvalidArgumentException("No puedes rechazar este intercambio porque no eres el receptor", "rechazar intercambio");
		return intercambio.rechazarIntercambio();
	}
	
	public boolean cancelarIntercambio(Intercambio intercambio) throws InvalidArgumentException {

		if (!intercambio.getEmisor().equals(this)) throw new InvalidArgumentException("No puedes cancelar este intercambio porque no eres el emisor", "cancelar intercambio");
		return intercambio.cancelarIntercambio();
	}
}



