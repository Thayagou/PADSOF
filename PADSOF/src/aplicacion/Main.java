package aplicacion;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import exceptions.*;
import sistema.*;
import usuario.*;
import venta.pedidos.Pedido;
import venta.productos.*;
import wallapop.*;

public class Main {
	private static Tienda tienda = new Tienda();
	private static String action = "";
	private static Scanner sc = new Scanner(System.in);
	private static String filename = "tienda.dat";
	
	static void cleanScreen() {
		for(int i = 0; i < 50 ; i++) System.out.println("\n");
	}
	
	static void showMessage(String message) {
		//cleanScreen();
		System.out.println("\n"+message);
	}
	
	static char getUserInputChar(String message) {
		System.out.println("\n"+message);
		return sc.next().charAt(0);
	}
	
	static String getUserInputString(String message) {
		System.out.println("\n"+message);
		return sc.next().trim();
	}
	
	static String getUserInputLine(String message) {
		System.out.println("\n"+message);
		return sc.nextLine().trim();
	}
	
	static int getUserInputInt(String message) {
		System.out.println("\n"+message);
		return sc.nextInt();
	}
	
	static double getUserInputDouble(String message) {
		System.out.println("\n"+message);
		return sc.nextDouble();
	}
	
	static void getAction(String message) {
		System.out.println("\n"+message);
		action = sc.next();
	}
	
	/**
	 * Carga los datos de la tienda desde un fichero
	 * 
	 */
	static void cargarTienda() {
		try {
	        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
	        tienda = (Tienda) ois.readObject();
	        ois.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Guarda los datos de la tienda en un fichero
	 * 
	 */
	static void guardarTienda() {
		try {
	        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
	        oos.writeObject(tienda);
	        oos.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static void main(String[] args) {
		Usuario usuario;
		
		cargarTienda();
		
		try {
			while (!action.equals("e")) {
				
				try {
					usuario = menuInicio();
					if (usuario == null) break;

				} catch (NotValidUserException e){
					System.out.println("Error al " + e.getMetodo() + " con el nombre de usuario '" + e.getNombre() + "': " + e.getMessage());
					continue;
				}
				
				if (usuario instanceof Gestor) {
			        menuGestor(tienda.getGestor());
			    } else if (usuario instanceof Empleado) {
			        menuEmpleado(tienda.getEmpleado(usuario.getNombre()));
			    } else if (usuario instanceof ClienteRegistrado) {
			        menuCliente(tienda.getCliente(usuario.getNombre()));
			    }
			}
			
		} catch (InvalidArgumentException | DoubleDiscountException e) {
			System.out.println(e.getMessage());
		}
		
		guardarTienda();
		
		return;
	}
	
	static Usuario menuInicio() throws InvalidArgumentException, NotValidUserException {
		while(!action.equals("e")) {
			getAction("r: registrarse | i: iniciar sesión | b: buscar | e: exit ");
				
			switch(action) {
			case "r":
				String nombreR = getUserInputString("Introducir nombre: ");
				String contrasenaR = getUserInputString("Introducir contraseña: ");					
				String confirmacionR = getUserInputString("Repetir contraseña: ");
				
				Usuario usuario = tienda.registrarse(nombreR, contrasenaR, confirmacionR);
				
				return usuario;
				
			case "i":
				String nombreI = getUserInputString("Introducir nombre: ");
				String contrasenaI = getUserInputString("Introducir contraseña: ");
				
				usuario = tienda.iniciarSesion(nombreI, contrasenaI);
				
				return usuario;
				
			case "b":
				List<Categoria> categorias = new ArrayList<Categoria>();
				for(Categoria cat : tienda.getAlmacen().getCategorias()) {
					getAction("Incluir categoria" + cat.getNombre() + "? s/n");
					if(action == "s") {
						categorias.add(cat);
					}
				}
				double precioMin = getUserInputDouble("Precio minimo: ");
				double precioMax = getUserInputDouble("Precio maximo: ");
				double estrellasMin = getUserInputDouble("Estrellas minimas: ");
				
				try{
					tienda.getAlmacen().getProductosPorFiltros(categorias.toArray(new Categoria[0]), precioMin, precioMax, estrellasMin);
				}catch(IllegalArgumentException e){
					
				}
				// Ver mas informacion de los productos
			}	
		}
		return null;
	}
	
	static void menuCliente(ClienteRegistrado cliente) throws InvalidArgumentException  {
		while(!action.equals("e")) {
			if(cliente == null) return;
			
			getAction("b: buscar | r: recomendaciones | s: buscar segunda mano | w: cartera | c: carrito | a: cuenta | n: notificaciones | e: exit");
			switch(action) {
			case "b":
				List<Categoria> categorias = new ArrayList<Categoria>();
				for(Categoria cat : tienda.getAlmacen().getCategorias()) {
					getAction("Incluir categoria" + cat.getNombre() + "? s/n");
					if(action == "s") {
						categorias.add(cat);
					}
				}
				double precioMin = getUserInputDouble("Precio minimo: ");
				double precioMax = getUserInputDouble("Precio maximo: ");
				double estrellasMin = getUserInputDouble("Estrellas minimas: ");
				
				try{
					tienda.getAlmacen().getProductosPorFiltros(categorias.toArray(new Categoria[0]), precioMin, precioMax, estrellasMin);
				}catch(IllegalArgumentException e){
					
				}
				
			case "r":
				
			case "s":
			case "w":
			case "c":
			case "a":
			case "n":
			}
		}
	}

	static void menuEmpleado(Empleado empleado) throws DoubleDiscountException {
		if(empleado == null) return;
		
		while(!action.equals("e")) {
			getAction("v: valorar articulo | c: confirmar intercambio | pd: gestionar pedidos | pr: gestionar productos y categorias| e: exit");
			
			try {
				switch(action) {
				case "v":
					actionValorarArticulo(empleado);
					
				case "c":
					actionConfirmarIntercambio(empleado);
					
				case "pd":
					actionGestionarPedidos(empleado);
					
				case "pr":
					actionGestionarProductos(empleado);
				}
			} catch (InvalidArgumentException e) {
				System.out.println("\n\u001B[31mError al " + e.getMetodo() + ": " + e.getMessage() + "\u001B[0m");
			} catch (InvalidPermit e) {
				System.out.println("\n\u001B[31mError al " + e.getMetodo() + ": " + e.getMessage() + "\nNecesitas el permiso: " + e.getPermiso() + "\u001B[0m");
			}
		}
	}
		
	static void menuGestor(Gestor gestor) throws InvalidArgumentException  {
		
	}
	
	/**
	 * Realiza la acción de valorar un artículo de segunda mano
	 * 
	 * @param empleado Empleado que desea valorar un artículo
	 * @throws InvalidArgumentException
	 * @throws InvalidPermit 
	 */
	static void actionValorarArticulo(Empleado empleado) throws InvalidArgumentException, InvalidPermit {
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) throw new InvalidPermit("No tienes el permiso para hacer esta acción", "valorar artículo", "Intercambios");
		int i = 1;
		Valoracion[] valoraciones = tienda.getHistorial().getValoracionesPendientes();
		if (valoraciones.length < 1) throw new InvalidArgumentException("No existen valoraciones pendientes en este momento", "valorar articulo");
		for(Valoracion v : valoraciones) {
			System.out.println(i + ") " + v + "\n");
			i++;
		}
		int num = getUserInputInt("Escriba el número del artículo que desea valorar: ");
		double precio = getUserInputDouble("Precio estimado: ");
		EstadoFisicoArticulo est = EstadoFisicoArticulo.valueOf(getUserInputString("Estado del artículo: "));
		
		tienda.getHistorial().valorarArticulo(empleado, valoraciones[num-1].getArticulo(), precio, est);
	}
	
	/**
	 * Realiza la acción de confirmar un intercambio
	 * 
	 * @param empleado Empleado que desea confirmar un intercambio
	 * @throws InvalidArgumentException
	 * @throws InvalidPermit 
	 */
	static void actionConfirmarIntercambio(Empleado empleado) throws InvalidArgumentException, InvalidPermit {
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) throw new InvalidPermit("No tienes el permiso para hacer esta acción", "validar intercambio", "Intercambios");
		int i = 1;
		Intercambio[] intercambios = tienda.getHistorial().getIntercambiosPendientes();
		if(intercambios.length < 1) throw new InvalidArgumentException("No existen intercambios pendientes en este momento", "confirmar intercambio");
		for (Intercambio t : intercambios) {
			System.out.println(i + ") " + t + "\n");
			i++;
		}
		int num = getUserInputInt("Escriba el número del intercambio que desea confirmar: ");
		tienda.getHistorial().validarIntercambio(empleado, intercambios[num-1]);
	}
	
	/**
	 * Realiza la acción de gestionar pedidos pendientes 
	 * 
	 * @param empleado Empleado que desea gestionar pedidos
	 * @throws InvalidArgumentException
	 * @throws InvalidPermit 
	 */
	static void actionGestionarPedidos(Empleado empleado) throws InvalidArgumentException, InvalidPermit {
		if(!empleado.tienePermiso(Permiso.PEDIDOS)) throw new InvalidPermit("No tienes el permiso para hacer esta acción", "gestionar pedidos", "Pedidos");
		int i = 1;
		Pedido[] pedidos= tienda.getHistorial().getPedidosPendientes();
		if(pedidos.length < 1) throw new InvalidArgumentException("No existen pedidos pendientes en este momento", "gestionar pedidos");
		for(Pedido p : pedidos) {
			System.out.println(i + ") " + p + "\n");
			i++;
		}
		int num = getUserInputInt("Escriba el número del pedido que desea avanzar: ");
		tienda.getHistorial().avanzarEstadoPedido(empleado, pedidos[num-1]);
	}
	
	/**
	 * Realiza la acción de gestionar productos 
	 * 
	 * @param empleado Empleado que desea gestionar productos
	 * @throws InvalidArgumentException
	 * @throws DoubleDiscountException
	 * @throws InvalidPermit 
	 */
	static void actionGestionarProductos(Usuario usuario) throws InvalidArgumentException, DoubleDiscountException, InvalidPermit {
		if(!usuario.tienePermiso(Permiso.PRODUCTOS)) throw new InvalidPermit("No tienes el permiso para hacer esta acción", "gestionar productos", "Productos");
		
		getAction("a: añadir producto | c: cargar fichero de productos | mp: modificar producto | bp: borrar producto | mc: modificar categorias | p: crear packs | e: exit");
		
		switch(action) {
		case "a":
			actionAnadirProducto();
			
		case "c":
			actionCargarFicheroProductos();
			
		case "mp":
			actionModificarProducto();
			
		case "b":
			actionBorrarProducto();
			
		case "mc":
			actionModificarCategoria();
			
		case "p":
			actionCrearPack();
			
		}
	}
	
	 /**
	  * Añade un nuevo producto por la interfaz
	  * 
	  * @throws InvalidArgumentException
	  * @throws DoubleDiscountException
	  */
	static void actionAnadirProducto() throws InvalidArgumentException, DoubleDiscountException {
		char tipo = getUserInputChar("Tipo de producto (c: comic | j: juego | f: figura): ");
		String nombre = getUserInputLine("Nombre: ");
		String desc = getUserInputLine("Descripción: ");
		double precio = getUserInputDouble("Precio: ");
		int uds = getUserInputInt("Unidades: ");
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		for(Categoria cat : tienda.getAlmacen().getCategorias()) {
			getAction("Incluir categoria " + cat.getNombre() + "? s/n");
			if(action == "s") {
				categorias.add(cat);
			}
		}
		switch(tipo) {
		case 'C':
			int numPags = getUserInputInt("Número de páginas: ");
			String autor = getUserInputLine("Autor: ");
			String editorial = getUserInputLine("Editorial: ");
			String fecha[] = getUserInputString("Fecha(YYYY/MM/DD): ").split("/");
			LocalDate fechaPublicacion = LocalDate.of(Integer.parseInt(fecha[0]), Month.of(Integer.parseInt(fecha[1])), Integer.parseInt(fecha[2]));
			
			tienda.getAlmacen().anadirComic(uds, nombre, desc, precio, null, fechaPublicacion, autor, numPags, editorial, categorias.toArray(new Categoria[0]));
		case 'J':
			int numJugs = getUserInputInt("Número de jugadores: ");
			String rangoEdad = getUserInputString("Rango de edad: ");
			TipoJuego tipoJuego = TipoJuego.valueOf(getUserInputString("Tipo de juego: "));
			
			tienda.getAlmacen().anadirJuego(uds, nombre, desc, precio, null, numJugs, rangoEdad, tipoJuego, categorias.toArray(new Categoria[0]));
		case 'F':
			String marca = getUserInputString("Marca: ");
			String material = getUserInputString("Material: ");
			String dimensiones = getUserInputString("Dimensiones: ");
			
			tienda.getAlmacen().anadirFigura(uds, nombre, desc, precio, null, dimensiones, marca, material, categorias.toArray(new Categoria[0]));
		}
	}
	
	/**
	 * Añade varios nuevos productos por un fichero
	 * 
	 * @throws DoubleDiscountException
	 * @throws InvalidArgumentException
	 */
	static void actionCargarFicheroProductos() throws DoubleDiscountException, InvalidArgumentException {
		String fichero = getUserInputString("Nombre del archivo: ");
		tienda.getAlmacen().anadirProductosDeFichero(fichero);
	}
	
	static void actionModificarProducto() {
		String nombre = getUserInputString("Introduzca el nombre del producto que quiere modificar");
	}
	
	static void actionBorrarProducto() {
		
	}
	
	static void actionModificarCategoria() {
		
	}
	
	static void actionCrearPack() {
		
	}
}
