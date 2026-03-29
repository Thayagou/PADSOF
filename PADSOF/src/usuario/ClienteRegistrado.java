package usuario;

import java.io.Serializable;
import java.util.*;

import exceptions.InvalidArgumentException;
import venta.pedidos.Pedido;
import venta.productos.Producto;
import venta.productos.Resena;
import wallapop.ArticuloSegundaMano;
import wallapop.Cartera;
import exceptions.*;

public class ClienteRegistrado extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private Carrito carrito;
	private Cartera cartera;
	private List<Notificacion> notificaciones;
	private Set<TipoNotificacion> intereses;
	private List<Pedido> misCompras;
	
	public ClienteRegistrado(String nombre, String contrasena) 
			throws IllegalArgumentException {
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

	public boolean enviarNotificacion(String mensaje, TipoNotificacion tipo) {
		if(!intereses.contains(tipo)) return false;
		Notificacion notificacion = new Notificacion(mensaje, tipo);
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
	
	public boolean anadirResena(int estrellas, String comentario, Producto producto) throws InvalidArgumentException {
		if(comentario == null || producto == null) throw new InvalidArgumentException("No se pueden pasar argumentos null");
		if(estrellas < 0 || estrellas > 5) throw new InvalidArgumentException("La puntuacion debe ser un valor entre 0 y 5");
		
		producto.anadirResena(new Resena(estrellas, comentario, this));
		return true;
	}
	
	/**
	 * Método para crear un pedido a partir del carrito
	 * @return Pedido con el contenido del carrito
	 */
	public Pedido carritoAPedido() {
		try {
			carrito.calcularCarrito();
			Pedido pedido = new Pedido(this, carrito.getContenido());
			misCompras.add(pedido);
			return pedido;
		} catch(CustomException e) {
			throw new RuntimeException("Carrito con contenido null", e);
		}
	}
	
	public boolean tienePermiso() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nCarrito: " + carrito
				+ "\nCartera: " + cartera
				+ "\nIntereses: " + intereses
				+ "\nNotificaciones: " + notificaciones;
	}
}
