package usuario;

import java.util.*;

import wallapop.ArticuloSegundaMano;
import wallapop.Cartera;

public class ClienteRegistrado extends Cliente {
	private Carrito carrito;
	private Cartera cartera;
	private List<Notificacion> notificaciones;
	private Set<TipoNotificacion> intereses;
	
	public ClienteRegistrado(String nombre, String contrasena) {
		super(nombre, contrasena);
		this.carrito = new Carrito();
		this.cartera = new Cartera(this);
		this.notificaciones = new LinkedList<>();
		this.intereses = new HashSet<>();
	}
	
	public boolean cambiarContrasena(String contrasena, String confirmarContrasena) {
		if(contrasena.equals(confirmarContrasena)) {
			this.contrasena = contrasena;
			return true;
		}
		return false;
	}
	
	public Carrito getCarrito() {
		return this.carrito;
	}
	
	public Cartera getCartera() {
		return this.cartera;
	}
	
	public List<ArticuloSegundaMano> verCartera(){
		List<ArticuloSegundaMano> articulos = new LinkedList<>();
		for(ArticuloSegundaMano ar : this.cartera.getArticulos()) {
			articulos.add(ar);
		}
		return articulos;
	}

	public List<Notificacion> getNotificaciones() {
		return this.notificaciones;
	}
	
	public List<Notificacion> getNotificacionesDeInteres() {
		List<Notificacion> deInteres = new LinkedList<>();
		for(Notificacion n : notificaciones) {
			if(this.intereses.contains(n.getTipo())) {
				deInteres.add(n);
			}
		}
		return deInteres;
	}
	
	public boolean addNotificacion(Notificacion notificacion) {
		notificaciones.add(notificacion);
		return true;
	}

	public Set<TipoNotificacion> getIntereses() {
		return this.intereses;
	}
	
	public boolean gestionarInteres(TipoNotificacion interes, boolean activa) {
		if(activa) {
			return this.intereses.add(interes);
		} else {
			return intereses.remove(interes);
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nCarrito: " + carrito
				+ "\nCartera: " + cartera
				+ "\nIntereses: " + intereses
				+ "\nNotificaciones: " + notificaciones;
	}
}
