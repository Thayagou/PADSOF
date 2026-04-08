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
import sistema.*;

/**
 * Esta clase representa un cliente registrado en la tienda
 */
public class ClienteRegistrado extends Usuario implements Serializable, CarritoCaducadoObserver {
	private static final long serialVersionUID = 1L;
	/**Carrito del cliente*/
	private Carrito carrito;
	/**Cartera del cliente*/
	private Cartera cartera;
	/**Lista de notificaciones del cliente*/
	private List<Notificacion> notificaciones;
	/**Set de intereses del cliente*/
	private Set<TipoNotificacion> intereses;
	/**Lista de pedidos realizados del cliente*/
	private List<Pedido> misCompras;
	/**Estadísticas personales del cliente*/
	private StatsUsuario estadisticas;
	
	/**
	 * Constructor de la clase
	 * @param nombre Nombre del usuario
	 * @param contrasena Contraseña del usuario
	 * @param tienda observador tienda
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public ClienteRegistrado(String nombre, String contrasena, CarritoCaducadoObserver tienda) throws InvalidArgumentException {
		super(nombre, contrasena);
		if (tienda == null) throw new InvalidArgumentException("Null arguments for creating new user", "crear cliente");
		this.carrito = new Carrito(this, tienda);
		this.cartera = new Cartera(this);
		this.notificaciones = new LinkedList<>();
		this.intereses = new HashSet<>();
		intereses.add(TipoNotificacion.PEDIDO);
		this.misCompras = new LinkedList<>();
	}
	
	@Override
	public void carritoCaducado(Carrito carrito) {
		this.enviarNotificacion("Su carrito ha sido cancelado por inactividad", TipoNotificacion.CADUCIDAD);
	}
	
	/**
	 * Cambia la contraseña por una nueva con confirmacion
	 * @param contrasenaAntigua Contraseña antigua
	 * @param contrasena Nueva contraseña
	 * @param confirmarContrasena Confirmacion de la nueva contraseña
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public void cambiarContrasena(String contrasenaAntigua, String contrasena, String confirmarContrasena) throws InvalidArgumentException {
		if (contrasenaAntigua.equals(this.contrasena) == false) throw new InvalidArgumentException("La contraseña original introducida es incorrecta", "cambiar contraseña");
		if(contrasena.equals(confirmarContrasena)) {
			this.contrasena = contrasena;
		} else throw new InvalidArgumentException("La confirmación de contraseña no es correcta", "cambiar contraseña");
	}

	/**
	 * Devuelve el carrito del cliente
	 * @return Carrito del cliente
	 */
	public Carrito getCarrito() {
		return this.carrito;
	}
	
	/**
	 * Devuelve la cartera del cliente
	 * @return Cartera del cliente
	 */
	public Cartera getCartera() {
		return this.cartera;
	}
	
	/**
	 * Devuelve los articulos que el cliente tiene en su cartera
	 * @return array de ArticuloSegundaMano que tiene en la cartera
	 */
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
	 * @param estadisticas Estadísticas asociadas al usuario
	 */
	public void setEstadisticas(StatsUsuario estadisticas) {
		this.estadisticas = estadisticas;
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
	public void actualizarVectorInteresesPorBusqueda(Categoria...categorias) throws InvalidArgumentException {
		estadisticas.actualizarVectorInteresesBusqueda(categorias);
	}

	/**
	 * Envía una notificación si el clinte esta interesado
	 * @param mensaje Mensaje de la notificacion
	 * @param tipo Tipo de notificacion
	 * @return true si se ha enviado, false en caso contrario
	 */
	public boolean enviarNotificacion(String mensaje, TipoNotificacion tipo) {
		if(!intereses.contains(tipo)) return false;
		Notificacion notificacion = new Notificacion(mensaje, tipo);
		notificaciones.add(notificacion);
		return true;
	}

	/**
	 * Devuelve el array de intereses del cliente
	 * @return array de TipoNotificacion con los intereses del cliente
	 */
	public TipoNotificacion[] getIntereses() {
		return this.intereses.toArray(new TipoNotificacion[0]);
	}
	
	/**
	 * Añade un interes al cliente
	 * @param interes Interes añadido
	 * @return true si se añadio correctamente, false en caso contrario
	 */
	public boolean anadirInteres(TipoNotificacion interes) {
		return this.intereses.add(interes);
	}
	
	/**
	 * Quita un interes del cliente
	 * @param interes Interes quitado
	 * @return true si se quito correctamente, false en caso contrario
	 */
	public boolean quitarInteres(TipoNotificacion interes) {
		return intereses.remove(interes);
	}
	
	/**
	 * Devuelve las compras que ha realizado el cliente
	 * @return array de Pedido con las compras realizadas
	 */
	public Pedido[] getCompras() {
		return misCompras.toArray(new Pedido[0]);
	}
	
	/**
	 * Añade una reseña a un producto comprado
	 * @param estrellas Estrellas que le da
	 * @param comentario Comentario sobre el producto
	 * @param producto Producto que esta reseñando
	 * @return true si la reseña se hizo correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public boolean anadirResena(int estrellas, String comentario, Producto producto) throws InvalidArgumentException {
		if(comentario == null || producto == null) throw new InvalidArgumentException("No se pueden pasar argumentos null", "añadir reseña a producto");
		if(estrellas < 0 || estrellas > 5) throw new InvalidArgumentException("La puntuacion debe ser un valor entre 0 y 5", "añadir reseña a producto");
		
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