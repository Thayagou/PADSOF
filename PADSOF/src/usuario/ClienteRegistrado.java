package usuario;

import java.util.*;

import venta.pedidos.Pedido;
import venta.productos.Producto;
import venta.productos.Resena;
import wallapop.ArticuloSegundaMano;
import wallapop.Cartera;

public class ClienteRegistrado extends Cliente {
	private Carrito carrito;
	private Cartera cartera;
	private List<Notificacion> notificaciones;
	private Set<TipoNotificacion> intereses;
	private List<Pedido> misCompras;
	
	public ClienteRegistrado(String nombre, String contrasena) {
		super(nombre, contrasena);
		this.carrito = new Carrito();
		this.cartera = new Cartera(this);
		this.notificaciones = new LinkedList<>();
		this.intereses = new HashSet<>();
		this.misCompras = new LinkedList<>();
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
	
	public ArticuloSegundaMano[] verCartera(){
		List<ArticuloSegundaMano> articulos = new ArrayList<>();
		for(ArticuloSegundaMano ar : this.cartera.getArticulos()) {
			articulos.add(ar);
		}
		return articulos.toArray(new ArticuloSegundaMano[0]);
	}

	public Notificacion[] getNotificaciones() {
		return this.notificaciones.toArray( new Notificacion[0]);
	}
	
	public Notificacion[] getNotificacionesDeInteres() {
		List<Notificacion> deInteres = new LinkedList<>();
		for(Notificacion n : notificaciones) {
			if(this.intereses.contains(n.getTipo())) {
				deInteres.add(n);
			}
		}
		return deInteres.toArray( new Notificacion[0]);
	}
	
	public boolean addNotificacion(Notificacion notificacion) {
		notificaciones.add(notificacion);
		return true;
	}

	public TipoNotificacion[] getIntereses() {
		return this.intereses.toArray(new TipoNotificacion[0]);
	}
	
	public boolean anadirInteres(TipoNotificacion interes) {
		return this.intereses.add(interes);
	}
	
	public boolean quitarInteres(TipoNotificacion interes) {
		return intereses.remove(interes);
	}
	
	public Pedido[] getCompras() {
		return misCompras.toArray(new Pedido[0]);
	}
	
	public boolean anadirResena(int estrellas, String comentario, Producto producto) {
		if(estrellas < 0 || estrellas > 5)
			return false;
		
		producto.anadirResena(new Resena(estrellas, comentario, this));
		return true;
	}
	
	/**
	 * Método para crear un pedido a partir del carrito
	 * @return Pedido con el contenido del carrito
	 */
	public Pedido carritoAPedido() {
		carrito.calcularCarrito();
		Pedido pedido = new Pedido(this, carrito.getContenido());
		misCompras.add(pedido);
		return pedido;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nCarrito: " + carrito
				+ "\nCartera: " + cartera
				+ "\nIntereses: " + intereses
				+ "\nNotificaciones: " + notificaciones;
	}
}
