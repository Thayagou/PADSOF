package aplicacion;

import java.util.*;
import exceptions.InvalidArgumentException;
import usuario.*;
import venta.pedidos.Pedido;
import venta.productos.*;
import wallapop.*;

public class ActionCliente {
	
	static void menuCliente(ClienteRegistrado cliente) {
		if(cliente == null) return;
		while(!Main.action.equals("e")) {
			
			Main.getAction("b: buscar | r: recomendaciones | s: buscar segunda mano | w: cartera | c: carrito | p: Ver pedidos anteriores y valorar | a: cuenta | n: notificaciones | e: exit");
			try {
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
					
				}
			} catch (InvalidArgumentException e) {
				Main.showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			} catch (IllegalArgumentException e) {
				Main.showMessage("Error: el valor introducido no pudo ser parseado correctamente");
			}
		}
	}
	
	static void actionBuscarPorFiltros(ClienteRegistrado cliente) throws InvalidArgumentException {
		Producto[] productos = Main.actionBuscarPorFiltros();
		char dec = Main.getUserInputChar("¿Desea añadir al carrito alguno de estos productos? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		
		int num = Main.getUserInputInt("Introduzca el número del producto que desea añadir: ");
		
		Main.tienda.anadirACarritoDe(cliente, productos[num-1]);	
	}
	
	static void actionRecomendaciones(ClienteRegistrado cliente) throws InvalidArgumentException {
		Producto[] productos = Main.tienda.getAlmacen().getListaRecomendacion(cliente);
		int i = 1;
		for(Producto p : productos) {
			Main.showMessage(i++ + ") " + p);
		}
		
		char dec = Main.getUserInputChar("¿Desea añadir al carrito alguno de estos productos? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		
		int num = Main.getUserInputInt("Introduzca el número del producto que desea añadir: ");
		cliente.getCarrito().anadirProducto(productos[num-1]);
	}
	
	static void actionVerSegundaMano(ClienteRegistrado cliente) throws InvalidArgumentException {
		ArticuloSegundaMano[] articulos = Main.tienda.getAlmacen().getArticulosParaCliente(cliente);
		if(articulos.length < 1) throw new InvalidArgumentException("No hay artículos disponibles para intercambiar", "ver artículos de segunda mano");
		int i = 1;
		for (ArticuloSegundaMano a : articulos) {
			Main.showMessage(i++ + ") " + a);
		}
		char dec = Main.getUserInputChar("¿Desea ver la cartera del propietario de alguno de estos artículos? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		
		int num = Main.getUserInputInt("Introduzca el número del artículo cuyo propietario quiere ver: ");
		Main.showMessage("Cartera de " + articulos[num-1].getPropietario().getNombre() + ": ");
		i = 1;
		for(ArticuloSegundaMano a : articulos[num-1].getDueno().getArticulosDisponibles()) {
			Main.showMessage(i++ + ") " + a);
		}
		dec = Main.getUserInputChar("¿Desea realizar una oferta de intercambio a este usuario? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		List<Integer> pide = Main.getUserInputIntList("Indique los números de los artículos que desea pedir: (Números separados por espacios)");
		ArticuloSegundaMano[] pideArts = getArticulosSeleccionados(articulos[num-1].getDueno().getArticulosDisponibles(), pide).toArray(new ArticuloSegundaMano[0]);
		
		Main.showMessage("Su cartera: ");
		i = 1;
		for(ArticuloSegundaMano a : cliente.getCartera().getArticulosDisponibles()) {
			Main.showMessage(i++ + ") " + a);
		}
		List<Integer> ofrece = Main.getUserInputIntList("Indique los números de los artículos que desea ofrecer: (Números separados por espacios)");
		ArticuloSegundaMano[] ofreceArts = getArticulosSeleccionados(cliente.getCartera().getArticulosDisponibles(), ofrece).toArray(new ArticuloSegundaMano[0]);
		
		Main.tienda.hacerOfertaIntercambio(cliente, pideArts, ofreceArts);
	}
	
	static void actionVerCartera(ClienteRegistrado cliente) throws InvalidArgumentException {
		Main.showMessage("Su cartera: ");
		ArticuloSegundaMano[] articulos = cliente.getCartera().getArticulos();
		int i = 1, num;
		for(ArticuloSegundaMano a : articulos) {
			Main.showMessage(i++ + ") " + a);
		}
		
		Main.getAction("p: pedir valoración de un artículo | i: ver intercambios pendientes | a: añadir artículo de segunda mano | v: volver");
		switch(Main.action) {
		case "p":
			num = Main.getUserInputInt("Introduzca el número del artículo que desea pedir una valoración: ");
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
				Main.tienda.aceptarIntercambio(cliente, intercambios[num-1]);
				break;
			case "r":
				num = Main.getUserInputInt("Introduzca el número del intercambio que desea rechazar: ");
				Main.tienda.rechazarIntercambio(cliente, intercambios[num-1]);
				break;
			case "c":
				num = Main.getUserInputInt("Introduzca el número del intercambio que desea cancelar: ");
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
	 */
	static void actionVerCarrito(ClienteRegistrado cliente) throws InvalidArgumentException {
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
			Main.tienda.quitarDeCarritoDe(cliente, productos[num-1].getProducto());
			break;
			
		}
	}
	
	/**
	 * Permite ver los pedidos que ha realizado un cliente
	 * @param cliente Cliente que desea ver sus pedidos
	 * @throws InvalidArgumentException
	 */
	private static void actionVerPedidos(ClienteRegistrado cliente) throws InvalidArgumentException {
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
	 * @throws InvalidArgumentException En caso de que algún ínidce introducido no sea válido se lanza una excepción
	 */
	private static void actionValorarProducto(ClienteRegistrado cliente, Pedido p) throws InvalidArgumentException {
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
	 * @throws InvalidArgumentException En caso de que haya problemas al cambiar la contraseña se lanza esta excepción
	 */
	static void actionVerCuenta(ClienteRegistrado cliente) throws InvalidArgumentException {
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
	 * @throws InvalidArgumentException Se lanza en caso de que se introduzcan datos inválidos
	 */
	private static void actionGestionarNotificaciones(ClienteRegistrado cliente) throws InvalidArgumentException {
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
	 * @throws InvalidArgumentException Se lanza en caso de que se introduzcan datos inválidos
	 */
	static void actionGestionarInteresesNotificaciones(ClienteRegistrado cliente) throws InvalidArgumentException {
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
	 * @throws InvalidArgumentException Se lanza en caso de que se introduzcan datos inválidos
	 */
	static private Set<TipoNotificacion> getInteresesDeNotificaciones() throws InvalidArgumentException {
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
	 * @throws InvalidArgumentException Se lanza en caso de que se introduzcan datos inválidos
	 */
	static void actionVerNotificaciones(ClienteRegistrado cliente) throws InvalidArgumentException {
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
	 * Obtiene un set de articulos a partir de un array de permisos y una lista de números
	 * @return set de articulos obtenidos
	 * @throws InvalidArgumentException Se lanza en caso de que se introduzcan datos inválidos
	 */
	private static Set<ArticuloSegundaMano> getArticulosSeleccionados(ArticuloSegundaMano[] listaArts, List<Integer> nums) throws InvalidArgumentException {
		Set<ArticuloSegundaMano> articulos = new HashSet<>();
		for (Integer i : nums) {
			if (i < 1 || i > listaArts.length) throw new InvalidArgumentException("Índice de permiso inválido", "hacer oferta de intercambio");
			articulos.add(listaArts[i-1]);
		}
		return articulos;		
	}
}
