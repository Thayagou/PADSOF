package sistema;

import java.io.*;
import java.time.Duration;
import java.util.*;

import es.uam.eps.padsof.telecard.*;
import estadistica.Historial;
import usuario.*;
import venta.productos.*;
import wallapop.ArticuloSegundaMano;
import wallapop.Cartera;
import wallapop.Intercambio;
import wallapop.Valoracion;
import venta.pedidos.*;
import exceptions.*;

/**
 * Clase tienda que recoge todas las funcionalidades de la aplicación
 * 
 * @author Juan Ibáñez, Tiago Oselka, Claudia Saiz
 */
public class Tienda implements Serializable, CarritoCaducadoObserver {
	private static final long serialVersionUID = 1L;
	/** Historial de la tienda*/
	private Historial historial = new Historial();
	/**Almacen de la tienda*/
	private Almacen almacen;
	/**Mapa de clientes por nombre*/
	private Map<String, ClienteRegistrado> clientes = new HashMap<>();
	/**Mapa de empleados por nombre*/
	private Map<String, Empleado> empleados = new HashMap<>();
	/**Gestor de la tienda*/
	private Gestor gestor;
	
	/**
	 * Constructor de la tienda
	 * @throws InvalidArgumentException Se lanza si los argumentos de crear el gestor son inválidos
	 */
	public Tienda() throws InvalidArgumentException { 
		almacen = new Almacen(historial);
		gestor = new Gestor("gestor", "g123");
	}
	
	@Override
	public void carritoCaducado(Carrito carrito) {
		for(StockExterno se : carrito.getItems()) {
			try {
				Stock st = almacen.getStock(se.getProducto().getNombre());
				st.incrementarStock(se.getUdsEnStock());
			} catch(InvalidArgumentException e) {
				throw new RuntimeException("Error interno al devolver stock", e);
			}
			
		}
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
	 * Devuelve los empleados de la tienda que estén actualmente dados de alta
	 * @return array de los empleados de las tiendas
	 */
	public Empleado[] getEmpleados() {
		return empleados.values().stream().filter(e->e.estaDeAlta()==true).toArray(Empleado[]::new);
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
	 * Carga los datos de la tienda desde un fichero
	 * @param filename Nombre del fichero donde se guarda la tienda
	 * @return Tienda cargada
	 */
	public static Tienda cargarTienda(String filename) {
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
	        Tienda t = (Tienda) ois.readObject();
	        Sistema.asignarInstancia(ois);
	        AsignadorId.asignarInstancia(ois);
	        Reloj.asignarInstancia(ois);
	        return t;

	    } catch (Exception e) {
	        return null;
	    }
	}
	
	/**
	 * Guarda los datos de la tienda en un fichero
	 * @param filename Nombre del fichero donde se guarda la tienda
	 */
	public void guardarTienda(String filename) {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
	        oos.writeObject(this);
	        oos.writeObject(Sistema.getInstancia());
	        oos.writeObject(AsignadorId.getInstancia());
	        oos.writeObject(Reloj.getInstancia());
	    } catch (Exception e) {
	        return;
	    }
	}
	
	/**
	 * Método para registrarse en la tienda como cliente
	 * @param nombre Nombre de usuario del cliente
	 * @param contrasena Contraseña de la cuenta
	 * @param confirmarContrasena Confirmacion de la contraseña
	 * @return Usuario cliente que se creó
	 * @throws InvalidArgumentException Se lanza si los argumentos de entrada no son válidos
	 * @throws NotValidUserException Se lanza si ya existe el usuario o la contraseña es incorrecta
	 */
	public ClienteRegistrado registrarse(String nombre, String contrasena, String confirmarContrasena) throws InvalidArgumentException, NotValidUserException {
		if(nombre == null || contrasena == null || confirmarContrasena == null) throw new InvalidArgumentException("No se pueden dejar argumentos vacíos", "registrarse");
		if(!comprobarUnicidadNombre(nombre)) throw new NotValidUserException("Ya existe un usuario con ese nombre", "registrarse", nombre);
		if(!contrasena.equals(confirmarContrasena)) throw new NotValidUserException("Ha fallado la comprobación de contraseña", "registrarse", nombre);
		
		ClienteRegistrado cliente = new ClienteRegistrado(nombre, contrasena, this);
		clientes.put(nombre, cliente);
		historial.guardarUsuario(cliente);
		return clientes.get(nombre);
	}
	
	/**
	 * Método para iniciar sesión en la aplicación
	 * @param nombre Nombre de usuario
	 * @param contrasena Contraseña de la cuenta
	 * @return Usuario que está registrado con ese nombre de usuario
	 * @throws InvalidArgumentException Se lanza si los argumentos de entrada no son válidos
	 * @throws NotValidUserException Se lanza si el usuario no existe o la contraseña es incorrecta
	 */
	public Usuario iniciarSesion(String nombre, String contrasena) throws InvalidArgumentException, NotValidUserException {
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
	 * @throws InvalidArgumentException Se lanza si los argumentos de entrada no son válidos
	 */
	public boolean darDeAltaEmpleado(String nombre, String contrasena, Permiso...permisos) throws InvalidArgumentException {
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
	public void gestionarParametroDeSistema(Gestor gestor, ParametroSistema parametro, double valor) throws InvalidArgumentException {
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
	 * Método que se utiliza para gestionar los parámetros de duración del sistema
	 * @param gestor Instancia del gestor de la tienda
	 * @param parametro Parámetro de recomendación
	 * @param duracion Duración a establecer
	 * @throws InvalidArgumentException Se lanza en caso de error en algún parámetro de entrada
	 */
	public void gestionarParametroDeSistema(Gestor gestor, ParametroSistema parametro, Duration duracion) throws InvalidArgumentException {
		if (gestor == null) throw new InvalidArgumentException("Gestor introducido es null", "gestionar parámetros del sistema");
	    if (parametro == null || duracion == null)throw new InvalidArgumentException("Algún parámetro de recomendación inválido", "gestionar parámetros del sistema");
	    switch (parametro) {
	    case DURACION_CARRITO:
	    	Sistema.getInstancia().setTiempoCaducaCarrito(duracion);
	    	break;
	    case DURACION_OFERTA:
	    	Sistema.getInstancia().setTiempoCaducaCarrito(duracion);
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
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public boolean aceptarIntercambio(ClienteRegistrado cliente, Intercambio intercambio) throws InvalidArgumentException {
		cliente.getCartera().aceptarIntercambio(intercambio);
		for(Empleado e : this.getEmpleados()) {
			e.enviarNotificacion("Un nuevo intercambio ha sido aceptado", TipoNotificacion.INTERCAMBIO);
		}
		intercambio.getEmisor().getDueno().enviarNotificacion("Su oferta de intercambio de Id: " + intercambio.getId() + " ha sido aceptada", TipoNotificacion.INTERCAMBIO);
		
		Intercambio[] intercambiosInvalidados = intercambio.getReceptor().invalidarIntercambiosConArticulos(intercambio.getSolicitados());
		for (Intercambio i: intercambiosInvalidados) {
			ClienteRegistrado emisorInvalidado = i.getEmisor().getDueno();
			emisorInvalidado.enviarNotificacion("Su oferta de intercambio de Id: " + i.getId() + " ha sido invalidada", TipoNotificacion.INTERCAMBIO);
		}
		
		return true;
	}
	
	/**
	 * Cancela un intercambio pendiente
	 * @param cliente El cliente que ha hecho la oferta y que la cancela
	 * @param intercambio Intercambio que es cancelado
	 * @return boolean, true si el intercambio se ha aceptado correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos 
	 */
	public boolean cancelarIntercambio(ClienteRegistrado cliente, Intercambio intercambio) throws InvalidArgumentException {
		cliente.getCartera().cancelarIntercambio(intercambio);
		intercambio.getReceptor().getDueno().enviarNotificacion("Una de sus ofertas pendientes ha sido cancelada", TipoNotificacion.INTERCAMBIO);
		return true;
	}
	
	/**
	 * Rechaza un intercambio en nombre de un cliente registrado
	 * @param cliente Cliente que acepta el intercambio
	 * @param intercambio Intercambio que es rechazado
	 * @return boolean, true si el intercambio se ha rechazado correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos 
	 */
	public boolean rechazarIntercambio(ClienteRegistrado cliente, Intercambio intercambio) throws InvalidArgumentException {
		cliente.getCartera().rechazarIntercambio(intercambio);
		intercambio.getEmisor().getDueno().enviarNotificacion("Su oferta de intercambio ha sido rechazada", TipoNotificacion.INTERCAMBIO);
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
		
		Intercambio[] intercambiosInvalidados = intercambio.getEmisor().invalidarIntercambiosConArticulos(ofrecidos);
		for (Intercambio i: intercambiosInvalidados) {
			ClienteRegistrado emisorInvalidado = i.getEmisor().getDueno();
			emisorInvalidado.enviarNotificacion("Su oferta de intercambio de Id: " + i.getId() + " ha sido invalidada", TipoNotificacion.INTERCAMBIO);
		}
		
		
		return true;
	}
	
	/**
	 * Solicita una valoración de un artículo en nombre de un cliente 
	 * @param cliente Cliente que solicita la valoración
	 * @param articulo Artículo del que se pide valoración
	 * @param numTarjeta Número de tarjeta de crédito del cliente
	 * @return boolean, true si la solicitud se ha hecho, false en caso contrario
	 * @throws InvalidArgumentException Se lanza en caso de error a la hora de guardar la valoración
	 */
	public boolean solicitarValoracion(ClienteRegistrado cliente, ArticuloSegundaMano articulo, String numTarjeta) throws InvalidArgumentException {
		if(cliente == null || articulo == null || numTarjeta == null) return false;
		if(articulo.getValoracion()!= null) throw new InvalidArgumentException("Este artículo ya ha sido valorado", "solicitar valoración");
		
		try {
			TeleChargeAndPaySystem.charge(numTarjeta, "Pago de valoración de artículo de segunda mano.", Sistema.getInstancia().getPrecioValoracion());
		} catch (OrderRejectedException e) {
			return false;
		} 
		
		Valoracion valoracion = new Valoracion(articulo);
		historial.guardarValoracion(valoracion);
		for(Empleado e : this.getEmpleados()) {
			e.enviarNotificacion("Se ha hecho una nueva solicitud de valoración de un artículo de segunda mano", TipoNotificacion.VALORACION);
		}
		return true;
	}
	
	/**
	 * Método para añadir un nuevo artículo a la tienda
	 * @param nombre Nombre del artículo
	 * @param desc Descripción del artículo
	 * @param cartera Cartera a la que se añade
	 * @param categorias Categorías del artículo
	 * @param interesadoEn Descripción de los intereses de intercambio
	 * @return true si se pudo añadir 
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos 
	 */
	public boolean anadirArticulo(String nombre, String desc, Cartera cartera, Categoria[] categorias, String interesadoEn) throws InvalidArgumentException {
		ArticuloSegundaMano nuevo = new ArticuloSegundaMano(nombre, desc, cartera, interesadoEn, categorias);
		cartera.addArticulo(nuevo);
		return this.getAlmacen().anadirArticuloSegundaMano(nuevo);
	}
	
	/**
	 * Método para añadir un producto a un carrito
	 * @param cliente Cliente al que se le añade un producto
	 * @param producto Producto que se añade al carrito
	 * @throws InvalidArgumentException Se lanza si los argumentos de entrada no son válidos
	 * @throws ProductoNoDisponibleException Se lanza si el producto que se quiere añadir no está disponible
	 */
	public void anadirACarritoDe(ClienteRegistrado cliente, Producto producto) throws InvalidArgumentException, ProductoNoDisponibleException {
		if(cliente == null || producto == null || almacen.getStock(producto) == null) throw new InvalidArgumentException("No se pueden dejar argumentos vacíos");
		Stock st = almacen.getStock(producto);
		if(!st.disponible()) throw new ProductoNoDisponibleException("No queda stock del producto solicitado", producto);
		
		cliente.getCarrito().anadirProducto(producto);
		st.reducirStock();
	}
	
	/**
	 * Método para quitar un producto del carrito de un cliente
	 * @param cliente Cliente propietario del carrito
	 * @param producto Producto que se quiere quitar (una unidad)
	 * @return true si se pudo quitar el producto, false si no
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public boolean quitarDeCarritoDe(ClienteRegistrado cliente, Producto producto) throws InvalidArgumentException {
		if(cliente == null || producto == null || almacen.getStock(producto) == null) throw new InvalidArgumentException("No se pueden dejar argumentos vacíos", "quitar producto de carrito");
		
		cliente.getCarrito().quitarProducto(producto);
		almacen.getStock(producto).incrementarStock();
		return true;
	}
	
	/**
	 * Método para cancelar el carrito de un cliente, devolviendo el stock a la tienda
	 * @param cliente Cliente propietario del carrito
	 * @return true si se pudo cancelar el carrito, false si no existe el cliente
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public boolean cancelarCarritoDe(ClienteRegistrado cliente) throws InvalidArgumentException {
		if(cliente == null) return false;
		
		for(StockExterno st : cliente.getCarrito().getItems()) {
			almacen.getStock(st.getProducto()).incrementarStock(st.getUdsEnStock());
		}
		
		cliente.getCarrito().vaciarCarrito();
		return true;
	}
	
	/**
	 * Método para realizar el pago de un carrito
	 * @param cliente Cliente propietario del carrito
	 * @param numTarjeta Número de tarjeta que se introduce para pagar
	 * @return true si se pudo realizar el pago, false si no
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public boolean pagarCarritoDe(ClienteRegistrado cliente, String numTarjeta) throws InvalidArgumentException {
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
			for(StockExterno st : carrito.getRegalos()) {
				getAlmacen().getStock(st.getProducto()).incrementarStock(st.getUdsEnStock());
			}
			return false;
		} 
		
		Pedido pedido = cliente.carritoAPedido();
		carrito.vaciarCarrito();
		
		getHistorial().guardarPedido(pedido);

		long codigoPedido = pedido.getId();
		
		cliente.enviarNotificacion("Tu pedido con código "+codigoPedido+" ya está pagado! Se te notificará cuando esté listo para recoger.", TipoNotificacion.PEDIDO);
		
		for(Empleado e : this.getEmpleados()) {
			e.enviarNotificacion("Se ha realizado un nuevo pedido", TipoNotificacion.PEDIDO);
		}
		
		return true;
	}
}