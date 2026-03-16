package sistema;

import java.util.*;

import estadistica.Historial;
import usuario.*;
import venta.productos.*;
import venta.pedidos.*;

/**
 * Clase tienda que recoge todas las funcionalidades de la aplicación
 * 
 * Autores: Juan Ibáñez, Tiago Oselka, Claudia Sainz
 */
public class Tienda {
	private Almacen almacen;
	private Historial historial;
	private Map<String, Usuario> usuarios;
	
	/**
	 * Creador de la tienda
	 */
	public Tienda() {
		almacen = new Almacen();
		historial = new Historial();
		usuarios = new HashMap<>();
		usuarios.put("GESTOR", new Gestor("GESTOR", "GESTOR123"));
	}
	
	/**
	 * Getter del almacen de la tienda
	 * @return Almacen de la tienda
	 */
	public Almacen getAlmacen() {
		return almacen;
	}
	
	/**
	 * Getter del historial de la tienda
	 * @return Historial de la tienda
	 */
	public Historial getHistorial() {
		return historial;
	}
	
	/**
	 * Método para obtener el gestor de la tienda
	 * @return Gestor de la tienda
	 */
	public Gestor getGestor() {
		return (Gestor)usuarios.get("GESTOR");
	}
	
	/**
	 * Método para obtener un usuario con el nombre
	 * @param nombre Nombre del usuario
	 * @return Usuario obtenido con el nombre
	 */
	public Usuario getUsuario(String nombre) {
		return usuarios.get(nombre);
	}
	
	/**
	 * Método para obtener un empleado con su nombre
	 * @param nombre Nombre del empleado
	 * @return Empleado obtenido con ese nombre
	 */
	public Empleado getEmpleado(String nombre) {
		Usuario usr = usuarios.get(nombre);
		if(usr instanceof Empleado) {
			return (Empleado)usr;
		}
		return null;
	}
	
	/**
	 * Método para obtener un cliente con su nombre
	 * @param nombre Nombre del cliente
	 * @return Cliente obtenido con ese nombre
	 */
	public ClienteRegistrado getCliente(String nombre) {
		Usuario usr = usuarios.get(nombre);
		if(usr instanceof ClienteRegistrado) {
			return (ClienteRegistrado)usr;
		}
		return null;
	}
	
	/**
	 * Método para registrarse en la tienda como cliente
	 * @param nombre Nombre de usuario del cliente
	 * @param contrasena Contraseña de la cuenta
	 * @param confirmarContrasena Confirmacion de la contraseña
	 * @return Usuario que se creó
	 */
	public Usuario registrarse(String nombre, String contrasena, String confirmarContrasena) {
		if(!contrasena.equals(confirmarContrasena))
			return null;
		if(usuarios.containsKey(nombre))
			return null;
		usuarios.put(nombre, new ClienteRegistrado(nombre, contrasena));
		return usuarios.get(nombre);
	}
	
	/**
	 * Método para iniciar sesión en la aplicación
	 * @param nombre Nombre de usuario
	 * @param contrasena Contraseña de la cuenta
	 * @return Usuario que está registrado con ese nombre de usuario
	 */
	public Usuario iniciarSesion(String nombre, String contrasena) {
		if(!usuarios.containsKey(nombre)) return null;
		if(usuarios.get(nombre).getContrasena().equals(contrasena))
			return usuarios.get(nombre);
		return null;
	}
	
	/**
	 * Método para dar de alta a un empleado nuevo o existente
	 * @param nombre Nombre de usuario del empleado
	 * @param contrasena Contraseña del usuario si se crea uno nuevo
	 * @param permisos Permisos del empleado que se da de alta
	 * @return true si se pudo dar de alta, false si ya existía y está dado de alta
	 */
	public boolean darDeAltaEmpleado(String nombre, String contrasena, Permiso...permisos) {
		Empleado emp = getEmpleado(nombre);
		if(emp != null) {
			if(emp.estaDeAlta()) return false;
			emp.darDeAlta();
			emp.setPermisos(permisos);
			return true;
		}
		usuarios.put(nombre, new Empleado(nombre, contrasena, permisos));
		return true;
	}
	
	/**
	 * Método para dar de baja a un empleado
	 * @param nombre Nombre del empleado a dar de baja
	 * @return true si se pudo dar de baja, false si no existía el empleado
	 */
	public boolean darDeBajaEmpleado(String nombre) {
		Empleado emp = getEmpleado(nombre);
		if(emp == null) return false;
		emp.darDeBaja();
		return true;
	}
	
	/**
	 * Método para añadir un producto a un carrito
	 * @param usrName Nombre del usuario a cuyo carrito se añade el producto
	 * @param producto Producto que se añade al carrito
	 * @return true si se pudo añadir el producto, false si no
	 */
	public boolean anadirACarritoDe(String usrName, Producto producto) {
		ClienteRegistrado cliente = getCliente(usrName);
		Stock st = almacen.getStock(producto);
		if(cliente == null || producto == null || st == null) return false;
		
		if(!st.disponible()) return false;
		
		cliente.getCarrito().anadirProducto(producto);
		st.reducirStock();
		return true;
	}
	
	/**
	 * Método para quitar un producto del carrito de un cliente
	 * @param usrName Nombre del cliente con el carrito
	 * @param producto Producto que se quiere quitar (una unidad)
	 * @return true si se pudo quitar el producto, false si no
	 */
	public boolean quitarDeCarritoDe(String usrName, Producto producto) {
		ClienteRegistrado cliente = getCliente(usrName);
		Stock st = almacen.getStock(producto);
		if(cliente == null || producto == null || st == null) return false;
		
		cliente.getCarrito().quitarProducto(producto);
		st.incrementarStock();
		return true;
	}
	
	/**
	 * Método para cancelar el carrito de un cliente, devolviendo el stock a la tienda
	 * @param usrName Nombre del cliente del que se cancela el carrito
	 * @return true si se pudo cancelar el carrito, false si no existe el cliente
	 */
	public boolean cancelarCarritoDe(String usrName) {
		ClienteRegistrado cliente = getCliente(usrName);
		if(cliente == null) return false;
		
		for(StockExterno st : cliente.getCarrito().getItems()) {
			almacen.getStock(st.getProducto()).incrementarStock(st.getUdsEnStock());
		}
		
		cliente.getCarrito().vaciarCarrito();
		return true;
	}
	
	/**
	 * Método para realizar el pago de un carrito
	 * @param usrName Nombre del usuario cuyo carrito se va a pagar
	 * @return true si se pudo realizar el pago, false si no
	 */
	public boolean pagarCarritoDe(String usrName) {
		ClienteRegistrado cliente = getCliente(usrName);
		if(cliente == null) return false;
		Carrito carrito = cliente.getCarrito();
		
		carrito.calcularCarrito();
		
		for(StockExterno st : carrito.getRegalos()) {
			Stock stTienda = getAlmacen().getStock(st.getProducto());
			if(stTienda.getUdsEnStock() < st.getUdsEnStock()) {
				st.setUdsEnStock(stTienda.getUdsEnStock());
			}
			stTienda.reducirStock(st.getUdsEnStock());
		}
		
		if(!Sistema.getInstancia().pagoTarjeta()) {
			/*Manejo si el pago falló*/
			for(StockExterno st : carrito.getRegalos()) {
				getAlmacen().getStock(st.getProducto()).incrementarStock(st.getUdsEnStock());
			}
			return false;
		}
		
		Pedido pedido = cliente.carritoAPedido();
		carrito.vaciarCarrito();
		
		getHistorial().guardarPedido(pedido);
		long codigoPedido = pedido.getId();
		
		Notificacion notificacion = new Notificacion("Tu pedido con código "+codigoPedido+" ya está pagado! Se te notificará cuando esté listo para recoger.", TipoNotificacion.PEDIDO);
		cliente.addNotificacion(notificacion);
		
		return true;
	}
}
