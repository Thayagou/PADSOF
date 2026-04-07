/**
 * Este paquete contiene la interfaz que permite interactuar con la tienda
 */
package aplicacion;

import java.util.*;

import exceptions.*;
import usuario.*;
import venta.pedidos.Pedido;
import venta.productos.*;
import wallapop.*;

/**
 * Clase ActionCliente, contiene todos los métodos que permiten que un cliente navegue por la tienda
 * @author Tiago Oselka, Claudia Saiz
 */
public class ActionCliente {
	
	/**
     * Constructor privado para evitar instanciación.
     */
    private ActionCliente() {
    }
	
	/**
	 * Muestra el menú principal de un cliente desde el que puede realizar las diferentes funcionalidades
	 * @param cliente Cliente que ha iniciado sesión
	 */
	static void menuCliente(ClienteRegistrado cliente) {
		if(cliente == null) return;
		while(!Main.action.equals("e")) {
			
			try {
				Main.getAction("b: buscar | r: recomendaciones | s: buscar segunda mano | w: cartera | c: carrito | p: ver pedidos anteriores y valorar | a: cuenta | n: notificaciones | cs: cerrar sesión | e: exit)");
				switch(Main.action) {
				case "b":
					actionBuscarPorFiltros(cliente);
					break;
					
				case "r":
					actionRecomendaciones(cliente);
					break;
					
				case "s":
					actionVerSegundaMano(cliente);
					break;
					
				case "w":
					actionVerCartera(cliente);
					break;
					
				case "c":
					actionVerCarrito(cliente);
					break;
					
				case "p":
					actionVerPedidos(cliente);
					
				case "a":
					actionVerCuenta(cliente);
					break;
					
				case "n":
					actionGestionarNotificaciones(cliente);
					break;
				case "cs":
					return;
					
				}
			} catch (CustomException e) {
				Main.showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Realiza una búsqueda por filtros de productos de la tienda
	 * @param cliente Cliente que desea realizar la búsqueda
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException
	 */
	static void actionBuscarPorFiltros(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		Producto[] productos = Main.actionBuscarPorFiltros();
		if(productos.length < 1) throw new InvalidArgumentException("No hay productos en la tienda en este momento", "buscar por filtros");
		char dec = Main.getUserInputChar("¿Desea añadir al carrito alguno de estos productos? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		int num = Main.getUserInputInt("Introduzca el número del producto que desea añadir: ");
		
		try{
			Main.tienda.anadirACarritoDe(cliente, productos[num-1]);	
		} catch(ProductoNoDisponibleException e){
			Main.showMessage(String.format("El producto '%s' no tiene stock disponible", e.getProducto().getNombre()));
		}
	}
	
	/**
	 * Muestra la lista de productos recomendados a un cliente específico
	 * @param cliente Cliente del que se muestra la lista de recomendaciones
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException
	 */
	static void actionRecomendaciones(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		Producto[] productos = Main.tienda.getAlmacen().getListaRecomendacion(cliente);
		int i = 1;
		if(productos.length < 1) throw new InvalidArgumentException("No hay productos en la tienda en este momento", "ver recomendaciones");
		for(Producto p : productos) {
			Main.showMessage(i++ + ") " + p);
		}
		char dec = Main.getUserInputChar("¿Desea añadir al carrito alguno de estos productos? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		
		int num = Main.getUserInputInt("Introduzca el número del producto que desea añadir: ");
		try {
			Main.tienda.anadirACarritoDe(cliente, productos[num-1]);
		} catch(ProductoNoDisponibleException e) {
			Main.showMessage(String.format("El producto '%s' no tiene stock disponible", e.getProducto().getNombre()));
		}
		
	}
	
	/**
	 * Muestra la lista de artículos disponibles para un cliente y permite realizar una oferta de intercambio
	 * @param cliente Cliente que desea ver los artículos disponibles
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException
	 */
	static void actionVerSegundaMano(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		ArticuloSegundaMano[] articulos = Main.tienda.getAlmacen().getArticulosParaCliente(cliente);
		if(articulos.length < 1) throw new InvalidArgumentException("No hay artículos disponibles para intercambiar", "ver artículos de segunda mano");
		int i = 1;
		for (ArticuloSegundaMano a : articulos) {
			Main.showMessage(i++ + ") " + a);
		}
		char dec = Main.getUserInputChar("¿Desea ver la cartera del propietario de alguno de estos artículos? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		
		// Elegir productos que se piden
		int num = Main.getUserInputInt("Introduzca el número del artículo cuyo propietario quiere ver: ");
		if (num < 1 || num > articulos.length) throw new InvalidArgumentException("Número de artículo inválido", "hacer oferta de intercambio");
		
		Main.showMessage("Cartera de " + articulos[num-1].getPropietario().getNombre() + ": ");
		i = 1;
		ArticuloSegundaMano[] pidesArticulos = articulos[num-1].getDueno().getArticulosDisponibles();
		if(pidesArticulos.length < 1) throw new InvalidArgumentException("Este usuario no tiene artículos disponibles para intercambiar", "hacer oferta de intercambio");
		for(ArticuloSegundaMano a : pidesArticulos) {
			Main.showMessage(i++ + ") " + a);
		}
		dec = Main.getUserInputChar("¿Desea realizar una oferta de intercambio a este usuario? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		List<Integer> pide = Main.getUserInputIntList("Indique los números de los artículos que desea pedir: (Números separados por espacios)");
		ArticuloSegundaMano[] pideArts = getArticulosSeleccionados(pidesArticulos, pide).toArray(new ArticuloSegundaMano[0]);
		
		// Elegir productos que se ofrecen
		Main.showMessage("Su cartera: ");
		i = 1;
		ArticuloSegundaMano[] tusArticulos = cliente.getCartera().getArticulosDisponibles();
		if(tusArticulos.length < 1) throw new InvalidArgumentException("No tienes artículos disponibles para intercambiar", "hacer oferta de intercambio");
		for(ArticuloSegundaMano a : tusArticulos) {
			Main.showMessage(i++ + ") " + a);
		}
		List<Integer> ofrece = Main.getUserInputIntList("Indique los números de los artículos que desea ofrecer: (Números separados por espacios)");
		ArticuloSegundaMano[] ofreceArts = getArticulosSeleccionados(tusArticulos, ofrece).toArray(new ArticuloSegundaMano[0]);
		
		Main.tienda.hacerOfertaIntercambio(cliente, pideArts, ofreceArts);
	}
	
	/**
	 * Permite ver la cartera del cliente y realizar varias tareas relacionadas
	 * @param cliente Cliente que desea ver su cartera
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException
	 */
	static void actionVerCartera(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		Main.showMessage("Su cartera: ");
		ArticuloSegundaMano[] articulos = cliente.getCartera().getArticulos();
		if(articulos.length < 1) throw new InvalidArgumentException("No tienes artículos ahora mismo en la cartera", "ver cartera");
		int i = 1, num;
		for(ArticuloSegundaMano a : articulos) {
			Main.showMessage(i++ + ") " + a);
		}
		
		Main.getAction("p: pedir valoración de un artículo | i: ver intercambios pendientes | a: añadir artículo de segunda mano | v: volver");
		switch(Main.action) {
		case "p":
			num = Main.getUserInputInt("Introduzca el número del artículo que desea pedir una valoración: ");
			if (num < 1 || num > articulos.length) throw new InvalidArgumentException("Número de artículo inválido", "pedir valoración");
			String numTarjeta = Main.getUserInputString("Introduzca su tarjeta de crédito para realizar el pago de la valoración(16 dígitos): ");
			boolean st = Main.tienda.solicitarValoracion(cliente, articulos[num-1], numTarjeta);
			if (st) {
				Main.showMessage("El pago de su valoración se ha efectuado correctamente");
			} else {
				Main.showMessage("Ha ocurrido un error al intentar realizar el pago");
			}
			break;
			
		case "i":
			Intercambio[] intercambios = cliente.getCartera().getIntercambiosPendientes();
			i = 1;
			for(Intercambio it : intercambios) {
				Main.showMessage(i++ + ") " + it);
			}
			
			Main.getAction("a: aceptar intercambio | r: rechazar intercambio | c: cancelar intercambio | v: volver");
			switch(Main.action) {
			case "a":
				num = Main.getUserInputInt("Introduzca el número del intercambio que desea aceptar: ");
				if (num < 1 || num > intercambios.length) throw new InvalidArgumentException("Número de intercambio inválido", "aceptar intercambio");
				Main.tienda.aceptarIntercambio(cliente, intercambios[num-1]);
				break;
			case "r":
				num = Main.getUserInputInt("Introduzca el número del intercambio que desea rechazar: ");
				if (num < 1 || num > intercambios.length) throw new InvalidArgumentException("Número de intercambio inválido", "rechazar intercambio");
				Main.tienda.rechazarIntercambio(cliente, intercambios[num-1]);
				break;
			case "c":
				num = Main.getUserInputInt("Introduzca el número del intercambio que desea cancelar: ");
				if (num < 1 || num > intercambios.length) throw new InvalidArgumentException("Número de intercambio inválido", "cancelar intercambio");
				Main.tienda.cancelarIntercambio(cliente, intercambios[num-1]);
				break;
			}
			break;
			
		case "a":
			String nombre = Main.getUserInputLine("Nombre: ");
			String desc = Main.getUserInputLine("Descripción: ");
			List<Categoria> categorias = new ArrayList<Categoria>();
			for(Categoria cat : Main.tienda.getAlmacen().getCategorias()) {
				Main.getAction("Incluir categoria " + cat.getNombre() + "? s/n");
				if(Main.action.equals("s")) {
					categorias.add(cat);
				}
			}
			String mensaje = Main.getUserInputLine("Breve mensaje informando sobre lo que estás interesado en recibir: ");
			
			Main.tienda.anadirArticulo(nombre, desc, cliente.getCartera(), categorias.toArray(new Categoria[0]), mensaje);
			break;
		}		
	}
	
	/**
	 * Permite ver el carrito y efectuar diferentes acciones sobre él
	 * @param cliente Cliente que desea ver su carrito
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException 
	 */
	static void actionVerCarrito(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		Stock[] productos= cliente.getCarrito().getContenido();
		int i = 1;
		for(Stock p : productos) {
			Main.showMessage(i++ + ") " + p);
		}
		
		Main.getAction("¿Qué desea hacer? (p: pagar | c: cancelar carrito | q: quitar producto | v: volver)");
		switch(Main.action) {
		case "p":
			String numTarjeta = Main.getUserInputString("Introduzca su número de tarjeta(16 dígitos): ");
			boolean st = Main.tienda.pagarCarritoDe(cliente, numTarjeta);
			if (st) {
				Main.showMessage("Su compra se ha efectuado correctamente");
			} else {
				Main.showMessage("Ha ocurrido un error al intentar realizar la compra");
			}
			break;
			
		case "c":
			Main.tienda.cancelarCarritoDe(cliente);
			break;
			
		case "q":
			int num = Main.getUserInputInt("Introduzca el número deproducto que desea quitar: ");
			if (num < 1 || num > productos.length) throw new InvalidArgumentException("Número de producto inválido", "quitar producto de carrito");
			Main.tienda.quitarDeCarritoDe(cliente, productos[num-1].getProducto());
			break;
			
		}
	}
	
	/**
	 * Permite ver los pedidos que ha realizado un cliente
	 * @param cliente Cliente que desea ver sus pedidos
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException 
	 */
	private static void actionVerPedidos(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		Pedido[] pedidos = cliente.getPedidos();
		int i = 1;
		for (Pedido p: pedidos) {
			Main.showMessage("  " + i++ + ") Id: " + p.getId() + " Fecha realización: " + p.getFechaPago() + " Estado: " + p.getEstado().name());
		}
		
		int index = Main.getUserInputInt("Introducir número de pedido a consultar (1 - " + pedidos.length + "): ");
		if (index < 1 || index > pedidos.length) throw new InvalidArgumentException("Número de pedido inválido", "ver pedidos");
		
		Main.showMessage(pedidos[index-1].toString());
		
		Main.getAction("¿Desea valorar algún producto de este pedido? (Pulsar 's' si lo desea): ");
		switch(Main.action) {
		case "s":
			actionValorarProducto(cliente, pedidos[index-1]);
			break;
		}
		
	}

	/**
	 * A partir de un pedido realizado por el cliente permite valorar un producto comprado
	 * @param cliente Cliente que realiza la valoración
	 * @param p Pedido del cual se obtiene el producto
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException 
	 */
	private static void actionValorarProducto(ClienteRegistrado cliente, Pedido p) throws InvalidArgumentException, InvalidUserInputException {
		int i;
		StockExterno[] productos = p.getItemsPedido();
		i = 1;
		Main.showMessage("Productos entre los que elegir: ");
		for (StockExterno st: productos) {
			Main.showMessage("  " + i++ + ") " + st.getProducto().getNombre());
		}
		
		int index = Main.getUserInputInt("Introducir número de producto a valorar (1 - " + productos.length + "): ");
		if (index < 1 || index > productos.length) throw new InvalidArgumentException("Número de producto inválido", "valorar producto");
		
		double punt = Main.getUserInputDouble("Introducir puntuación (0 - 5 estrellas): ");
		if (punt < 0 || punt > 5) throw new InvalidArgumentException("El rango de la puntuación es inválido", "valorar producto");
		
		String comentario = Main.getUserInputLine("Añade un comentario del producto (sin dar saltos de línea): ");
		
		Resena resena = new Resena(punt, comentario, cliente);
		productos[index-1].getProducto().anadirResena(resena);
	}
	
	/**
	 * Muestra la información de cuenta de un usuario
	 * @param cliente Cliente de la tienda
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException 
	 */
	static void actionVerCuenta(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		Main.showMessage("Nombre de usuario: " + cliente.getNombre());
		
		String tipo = Main.getUserInputString("Desea cambiar su contraseña? (s: si | n: no): ");
		if (tipo.equals("s")) {
			String antigua = Main.getUserInputString("Introducir contraseña antigua: ");
			String nueva1 = Main.getUserInputString("Introducir nueva contraseña: ");
			String nueva2 = Main.getUserInputString("Repetir contraseña nueva: ");
			
			cliente.cambiarContrasena(antigua, nueva1, nueva2);
			Main.showMessage("Se ha cambiado correctamente la contraseña");
			
		}
		
	}
	
	/**
	 * Permite al cliente gestionar todo lo referente a sus notificaciones
	 * @param cliente Cliente actual de la tienda
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException 
	 */
	private static void actionGestionarNotificaciones(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		Main.getAction("¿Qué desea hacer? (g: gestionar intereses de notificaciones | v: ver notificaciones): ");
		
		switch (Main.action) {
		case "g":
			actionGestionarInteresesNotificaciones(cliente);
			break;
		case "v":
			actionVerNotificaciones(cliente);
			break;
		}
		
	}
	
	/**
	 * Permite al cliente gestionar sus intereses de notificaciones dentro de la tienda
	 * @param cliente Cliente actual
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException 
	 */
	static void actionGestionarInteresesNotificaciones(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		Set<TipoNotificacion> intesesesActuales = new HashSet<>(Arrays.asList(cliente.getIntereses()));
		Main.showMessage("Tus intereses de notificaciones: " + intesesesActuales);
		
		Main.getAction("¿Desea quitar o añadir intereses? (a: añadir | el: eliminar): ");
		Set<TipoNotificacion> intereses = getInteresesDeNotificaciones();
		
		switch(Main.action) {
		case "a":
			for (TipoNotificacion tn: intereses) {
				cliente.anadirInteres(tn);
			}
			break;
		case "el":
			for (TipoNotificacion tn: intereses) {
				cliente.quitarInteres(tn);
			}
			break;
		}
	}
	
	/**
	 * Obtiene un Set de tipos de notificación a partir de los seleccionados por el usuario
	 * @return Dicho set de tipos
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException 
	 */
	static private Set<TipoNotificacion> getInteresesDeNotificaciones() throws InvalidArgumentException, InvalidUserInputException {
		TipoNotificacion[] interesesDisponibles = TipoNotificacion.values();
		int i = 1;
		for (TipoNotificacion tn : interesesDisponibles) {
			Main.showMessage("  " + i++ + ") " + tn.name());
		}
		List<Integer> listaIntereses = Main.getUserInputIntList("Introducir la lista de números de permisos (1 - " + interesesDisponibles.length + "): ");
		
		Set<TipoNotificacion> interesesSeleccionados= new HashSet<>();
		for (Integer j: listaIntereses) {
			if (j < 1 || j > interesesDisponibles.length) throw new InvalidArgumentException("Número de interés inválido", "gestionar empleados");
			interesesSeleccionados.add(interesesDisponibles[j-1]);
		}
		
		return interesesSeleccionados;
	}
	
	/**
	 * Muestra las notificaciones no eliminadas del cliente y da la opción de marcarlas como leído o de eliminarlas
	 * @param cliente Cliente actual
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException 
	 */
	static void actionVerNotificaciones(ClienteRegistrado cliente) throws InvalidArgumentException, InvalidUserInputException {
		Notificacion[] notificaciones = cliente.getNotificaciones();
		int i = 1;
		for (Notificacion n: notificaciones) {
			Main.showMessage("  " + i++ + " " + (n.isLeida() ? "leída" : "no leída") + ") " + n);
		}
		
		int index = Main.getUserInputInt("Introducir número de la notificación (1 - " + notificaciones.length + "): ");
		if (index < 1 || index > notificaciones.length) throw new InvalidArgumentException("Número de notificación inválido", "gestionar notificaciones");
		
		Main.getAction("¿Qué desea hacer? (m: marcar como leída | en: eliminar notificación | v: volver)");
		
		switch (Main.action) {
		case "m": 
			notificaciones[index-1].marcarLeida();
			break;
		case "en":
			notificaciones[index-1].borrar();
			break;
		case "v":
			return;
		}		
	}
	
	/**
	 * Obtiene un set de articulos a partir de un array de articulos y una lista de números
	 * @param listaArts Lista de artículos a la que se refieren los índices
	 * @param nums Lista de índices
	 * @return set de articulos obtenidos
	 * @throws InvalidArgumentException
	 */
	private static Set<ArticuloSegundaMano> getArticulosSeleccionados(ArticuloSegundaMano[] listaArts, List<Integer> nums) throws InvalidArgumentException {
		Set<ArticuloSegundaMano> articulos = new HashSet<>();
		for (Integer i : nums) {
			if (i < 1 || i > listaArts.length) throw new InvalidArgumentException("Número de artículo inválido", "hacer oferta de intercambio");
			articulos.add(listaArts[i-1]);
		}
		return articulos;		
	}
}
