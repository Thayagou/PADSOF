package sistema;

import java.io.Serializable;
import java.util.*;

import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;
import estadistica.Historial;
import usuario.*;
import venta.productos.*;
import wallapop.ArticuloSegundaMano;
import wallapop.Intercambio;
import wallapop.Valoracion;
import venta.pedidos.*;
import exceptions.*;

/**
 * Clase tienda que recoge todas las funcionalidades de la aplicación
 * 
 * @author Juan Ibáñez, Tiago Oselka, Claudia Saiz
 */
public class Tienda implements Serializable {
	private static final long serialVersionUID = 1L;
	private Historial historial = new Historial();
	private Almacen almacen;
	private Map<String, ClienteRegistrado> clientes = new HashMap<>();
	private Map<String, Empleado> empleados = new HashMap<>();
	private Gestor gestor = new Gestor("gestor", "g123");
	
	/**
	 * Constructor de la tienda
	 */
	public Tienda() { 
		almacen = new Almacen(historial);
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
		return gestor;
	}
	
	/**
	 * Método para obtener un empleado con su nombre
	 * @param nombre Nombre del empleado
	 * @return Empleado obtenido con ese nombre
	 */
	public Empleado getEmpleado(String nombre) {
		return empleados.get(nombre);
	}
	
	/**
	 * Devuelve los empleados de la tienda
	 * @return array de los empleados de las tiendas
	 */
	public Empleado[] getEmpleados() {
		return empleados.values().stream().filter(e->e.getDeAlta()==true).toArray(Empleado[]::new);
	}
	
	/**
	 * Método para obtener un cliente con su nombre
	 * @param nombre Nombre del cliente
	 * @return Cliente obtenido con ese nombre
	 */
	public ClienteRegistrado getCliente(String nombre) {
		return clientes.get(nombre);
	}
	
	/**
	 * Devuelve los clientes registrados de la tienda
	 * @return array de los clientes registrados de la tienda
	 */
	public ClienteRegistrado[] getClientes() {
		return clientes.values().toArray(new ClienteRegistrado[0]);
	}
	
	/**
	 * Método para registrarse en la tienda como cliente
	 * @param nombre Nombre de usuario del cliente
	 * @param contrasena Contraseña de la cuenta
	 * @param confirmarContrasena Confirmacion de la contraseña
	 * @return Usuario que se creó
	 */
	public Usuario registrarse(String nombre, String contrasena, String confirmarContrasena) throws InvalidArgumentException, NotValidUserException {
		if(nombre == null || contrasena == null || confirmarContrasena == null) throw new InvalidArgumentException("No se pueden dejar argumentos vacíos");
		if(!comprobarUnicidadNombre(nombre)) throw new NotValidUserException("Ya existe un usuario con ese nombre", "registrarse", nombre);
		if(!contrasena.equals(confirmarContrasena)) throw new NotValidUserException("Ha fallado la comprobación de contraseña", "registrarse", nombre);
		
		ClienteRegistrado cliente = new ClienteRegistrado(nombre, contrasena);
		clientes.put(nombre, cliente);
		historial.guardarUsuario(cliente);
		return clientes.get(nombre);
	}
	
	/**
	 * Método para iniciar sesión en la aplicación
	 * @param nombre Nombre de usuario
	 * @param contrasena Contraseña de la cuenta
	 * @return Usuario que está registrado con ese nombre de usuario
	 */
	public Usuario iniciarSesion(String nombre, String contrasena) throws InvalidArgumentException, NotValidUserException {
		System.out.println(gestor);
		if(gestor.getNombre().equals(nombre)) {
			if(gestor.getContrasena().equals(contrasena))
				return gestor;
		} else if(clientes.containsKey(nombre)) {
			if(clientes.get(nombre).getContrasena().equals(contrasena))
				return clientes.get(nombre);
		} else if(empleados.containsKey(nombre)) {
			if(empleados.get(nombre).getContrasena().equals(contrasena) && empleados.get(nombre).estaDeAlta())
				return empleados.get(nombre);
		} else {
			throw new NotValidUserException("No se encontró el usuario", "iniciar sesión", nombre);
		}
		throw new NotValidUserException("La contraseña es incorrecta", "iniciar sesión", nombre);
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
		if(!comprobarUnicidadNombre(nombre)) return false;
		empleados.put(nombre, new Empleado(nombre, contrasena, permisos));
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
	 * Método que se utiliza para gestionar los parámetros del sistema de recomendación desde el sistema
	 * @param gestor Instancia del gestor de la tienda
	 * @param parametro Parámetro de recomendación
	 * @param valor Ponderación a establecer del parámetro
	 * @throws InvalidArgumentException Se lanza en caso de error en algún parámetro de entrada
	 */
	public void gestionarParametroDeSistema(Gestor gestor, ParametroRecomendacion parametro, double valor) throws InvalidArgumentException {
		if (gestor == null) throw new InvalidArgumentException("Gestor introducido es null", "gestionar parámetros del sistema");
	    if (parametro == null || valor < 0)throw new InvalidArgumentException("Algún parámetro de recomendación inválido", "gestionar parámetros del sistema");
	    switch (parametro) {
	    case CATEGORIA:
	        	Sistema.getInstancia().setPonderacionCategoria(gestor, valor);
	            break;
	        case UDS_COMPRADAS:
	        	Sistema.getInstancia().setPonderacionUdsCompra(gestor, valor);
	        	break;
	        case PRECIO_COMPRA:
	        	Sistema.getInstancia().setPonderacionPrecioCompra(gestor, valor);
	        	break;
	        case VALORACIONES_PRODUCTO:
	        	Sistema.getInstancia().setPonderacionValoracionesProducto(gestor, valor);
	            break;
	        case PRODUCTO_RECOMENDADO:
	        	Sistema.getInstancia().setPonderacionProductoRecomendado(gestor, valor);
	        	break;
	        case BUSQUEDA:
	        	Sistema.getInstancia().setPonderacionBusqueda(gestor, valor);
	        	break;
	        default:
	        	throw new InvalidArgumentException("Parámetro de recomendación inválido", "gestionar parámetros del sistema");
	    }
	}

	
	/**
	 * Comprueba que no exista un usuario con el nombre que se da
	 * @param nombre Nombre que se quiere comprobar
	 * @return boolean, true si el nombre es único, false si el nombre ya existe
	 */
	private boolean comprobarUnicidadNombre(String nombre) {
		if(gestor.getNombre().equals(nombre)) return false;
		if(clientes.containsKey(nombre)) return false;
		if(empleados.containsKey(nombre)) return false;
		
		return true;
	}
	
	/**
	 * Acepta un intercambio en nombre de un cliente registrado
	 * @param cliente Cliente que acepta el intercambio
	 * @param intercambio Intercambio que es aceptado
	 * @return boolean, true si el intercambio se ha aceptado correctamente, false en caso contrario
	 */
	public boolean aceptarIntercambio(ClienteRegistrado cliente, Intercambio intercambio) {
		cliente.getCartera().aceptarIntercambio(intercambio);
		for(Empleado e : this.getEmpleados()) {
			e.enviarNotificacion("Un nuevo intercambio ha sido aceptado", TipoNotificacion.INTERCAMBIO);
		}
		cliente.enviarNotificacion("Su oferta de intercambio ha sido aceptada", TipoNotificacion.INTERCAMBIO);
		return true;
	}
	
	/**
	 * Rechaza un intercambio en nombre de un cliente registrado
	 * @param cliente Cliente que acepta el intercambio
	 * @param intercambio Intercambio que es rechazado
	 * @return boolean, true si el intercambio se ha rechazado correctamente, false en caso contrario
	 */
	public boolean rechazarIntercambio(ClienteRegistrado cliente, Intercambio intercambio) {
		cliente.getCartera().rechazarIntercambio(intercambio);
		cliente.enviarNotificacion("Su oferta de intercambio ha sido rechazada", TipoNotificacion.INTERCAMBIO);
		return true;
	}
	
	/**
	 * Hace una oferta de intercambio en el que se ofrecen un conjunto de artículos a cambio de otro conjunto de artículos
	 * @param cliente Cliente que hace la oferta de intercambio
	 * @param ofrecidos Artículos que se ofrecen
	 * @param solicitados Artículos que se piden a cambio
	 * @return boolean, true si el intercambio se ha hecho correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de error a la hora de guardar el intercambio
	 */
	public boolean hacerOfertaIntercambio(ClienteRegistrado cliente, ArticuloSegundaMano[] ofrecidos, ArticuloSegundaMano[] solicitados) throws InvalidArgumentException {
		ClienteRegistrado clienteRecibe = solicitados[0].getDueno().getDueno();
		if(clienteRecibe == null) return false;
		
		Intercambio intercambio = new Intercambio(ofrecidos, solicitados);
		historial.guardarIntercambio(intercambio);
		
		cliente.getCartera().addIntercambio(intercambio);
		clienteRecibe.getCartera().addIntercambio(intercambio);
		clienteRecibe.enviarNotificacion("Ha recibido una nueva oferta de intercambio", TipoNotificacion.INTERCAMBIO);
		return true;
	}
	
	/**
	 * Solicita una valoración de un artículo en nombre de un cliente 
	 * @param cliente Cliente que solicita la valoración
	 * @param articulo Artículo del que se pide valoración
	 * @return boolean, true si la solicitud se ha hecho, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de error a la hora de guardar la valoración
	 */
	public boolean solicitarValoracion(ClienteRegistrado cliente, ArticuloSegundaMano articulo) throws InvalidArgumentException {
		if(cliente == null || articulo == null) return false;
		Valoracion valoracion = new Valoracion(articulo);
		articulo.anadirValoracion(valoracion);
		historial.guardarValoracion(valoracion);
		
		for(Empleado e : this.getEmpleados()) {
			e.enviarNotificacion("Se ha hecho una nueva solicitud de valoración de un artículo de segunda mano", TipoNotificacion.VALORACION);
		}
		return true;
	}
	
	/**
	 * Método para añadir un producto a un carrito
	 * @param usrName Nombre del usuario a cuyo carrito se añade el producto
	 * @param producto Producto que se añade al carrito
	 * @return true si se pudo añadir el producto, false si no
	 * @throws InvalidArgumentException
	 */
	public boolean anadirACarritoDe(String usrName, Producto producto) throws InvalidArgumentException {
		if(usrName == null || producto == null) throw new InvalidArgumentException("No se pueden dejar argumentos vacíos");
		
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
	 * @throws InvalidArgumentException
	 */
	public boolean quitarDeCarritoDe(String usrName, Producto producto) throws InvalidArgumentException {
		if(usrName == null || producto == null) throw new InvalidArgumentException("No se pueden dejar argumentos vacíos");
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
	 * @throws InvalidArgumentException
	 */
	public boolean cancelarCarritoDe(String usrName) throws InvalidArgumentException {
		if(usrName == null) throw new InvalidArgumentException("No se puede dejar el nombre de usuario vacío");
		
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
	 * @param numTarjeta Número de tarjeta que se introduce para pagar
	 * @return true si se pudo realizar el pago, false si no
	 */
	public boolean pagarCarritoDe(String usrName, String numTarjeta) throws InvalidArgumentException {
		ClienteRegistrado cliente = getCliente(usrName);
		if(cliente == null) return false;
		Carrito carrito = cliente.getCarrito();
		
		double precio = carrito.calcularCarrito();
		
		for(StockExterno st : carrito.getRegalos()) {
			Stock stTienda = getAlmacen().getStock(st.getProducto());
			if(stTienda.getUdsEnStock() < st.getUdsEnStock()) {
				st.setUdsEnStock(stTienda.getUdsEnStock());
			}
			stTienda.reducirStock(st.getUdsEnStock());
		}

		try {
			TeleChargeAndPaySystem.charge(numTarjeta, "Compra en tienda de comics.", precio);
		} catch (OrderRejectedException e) {
			e.printStackTrace();
			for(StockExterno st : carrito.getRegalos()) {
				getAlmacen().getStock(st.getProducto()).incrementarStock(st.getUdsEnStock());
			}
			return false;
		} 
		
		Pedido pedido = cliente.carritoAPedido();
		carrito.vaciarCarrito();
		
		getHistorial().guardarPedido(pedido);
		System.out.println(cliente);
		long codigoPedido = pedido.getId();
		
		cliente.enviarNotificacion("Tu pedido con código "+codigoPedido+" ya está pagado! Se te notificará cuando esté listo para recoger.", TipoNotificacion.PEDIDO);
		
		for(Empleado e : this.getEmpleados()) {
			e.enviarNotificacion("Se ha realizado un nuevo pedido", TipoNotificacion.PEDIDO);
		}
		
		return true;
	}
}