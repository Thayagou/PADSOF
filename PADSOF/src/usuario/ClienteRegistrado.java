package usuario;

import java.io.Serializable;
import java.util.*;


import exceptions.InvalidArgumentException;
import venta.pedidos.Pedido;
import venta.productos.Producto;
import venta.productos.Resena;
import venta.productos.Categoria;
import wallapop.ArticuloSegundaMano;
import wallapop.Cartera;
import exceptions.*;
import estadistica.StatsUsuario;

public class ClienteRegistrado extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private Carrito carrito;
	private Cartera cartera;
	private List<Notificacion> notificaciones;
	private Set<TipoNotificacion> intereses;
	private List<Pedido> misCompras;
	private StatsUsuario estadisticas;
	
	public ClienteRegistrado(String nombre, String contrasena) 
			throws IllegalArgumentException {
		super(nombre, contrasena);
		this.carrito = new Carrito();
		this.cartera = new Cartera(this);
		this.notificaciones = new LinkedList<>();
		this.intereses = new HashSet<>();
		intereses.add(TipoNotificacion.PEDIDO);
		this.misCompras = new LinkedList<>();
	}
	
	public void cambiarContrasena(String contrasenaAntigua, String contrasena, String confirmarContrasena) throws InvalidArgumentException {
		if (contrasenaAntigua.equals(this.contrasena) == false) throw new InvalidArgumentException("La contraseña original introducida es incorrecta", "cambiar contraseña");
		if(contrasena.equals(confirmarContrasena)) {
			this.contrasena = contrasena;
		} else throw new InvalidArgumentException("La confirmación de contraseña no es correcta", "cambiar contraseña");
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

	/**
	 * Obtiene las notificaciones no eliminadas de un cliente
	 * @return Array de notificaciones filtradas
	 */
	public Notificacion[] getNotificaciones() {
		return this.notificaciones.stream().filter(n->(n.isBorrada()==false)).toArray(Notificacion[]::new);
	}
	
	/**
	 * Setter de la instancia de las estadísticas del usuario
	 * @param estadistcas Estadísticas asociadas al usuario
	 */
	public void setEstadisticas(StatsUsuario estadistcas) {
		this.estadisticas = estadistcas;
	}
	
	/**
	 * Obtiene el vector de categorías de interés del cliente a partir de sus estadísticas
	 * @return Un vector en formato de mapa, que asigna a cada categoría una ponderación de interés
	 */
	public Map<Categoria, Double> getVectorRecomendacion() {
		return estadisticas.getVectorIntereses();
	}
	
	/**
	 * Obtiene la norma del vector de recomendaciones
	 * @return Norma del vector
	 */
	public double getNormaVectorRecomendaciones() {
		return estadisticas.getNorma();
	}
	
	/**
	 * Dado un vector de categorías con valores asociados y su norma, obtiene el producto escalar normalizado con su propio vector de intereses
	 * @param vector Vector de Categorías->ponderación 
	 * @param norma Norma del vector enviado
	 * @return valor resultante del producto escalar
	 */
	public double getCompatibilidad(Map<Categoria, Double> vector, double norma) {
		return estadisticas.getCompatibilidad(vector, norma);
	}
	
	/**
	 * Actualiza el vector de estadísticas del usuario a partir de una búsqueda realizada por este
	 * @param categorias Categorías que se han introducido en la búsqueda
	 * @throws InvalidArgumentException Se lanza desde el método llamado en caso de que alguna categoría introducida no sea válida
	 */
	public void actualizarPorBusqueda(Categoria...categorias) throws InvalidArgumentException {
		estadisticas.actualizarBusqueda(categorias);
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
	
	/**
	 * Obtiene los pedidos realizados por el cliente
	 * @return array de pedidos
	 */
	public Pedido[] getPedidos() {
		return misCompras.toArray(new Pedido[0]);
	}
	
	@Override
	public boolean tienePermiso(Permiso p) {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nCarrito: " + carrito
				+ "\nCartera: " + cartera
				+ "\nPedidos realizados: " + misCompras
				+ "\nIntereses: " + intereses
				+ "\nNotificaciones: " + notificaciones
				+ "\nEstadisticas: " + estadisticas;
	}

}
