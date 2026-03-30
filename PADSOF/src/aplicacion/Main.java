package aplicacion;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
	
	static LocalDateTime getUserInputLocalDateTime(String message) {
		System.out.println("\n"+message);
		String input = sc.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime fecha = LocalDateTime.parse(input, formatter);
		
		return fecha;
	}
	
	
	static void getAction(String message) {
		System.out.println("\n"+message);
		action = sc.next();
	}

	public static void main(String[] args) {
		Usuario usuario;
		
		//cargarTienda();
		
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
			}
		}
	}
		
	static void menuGestor(Gestor gestor) throws InvalidArgumentException  {
		while(!action.equals("e")) {
			if(gestor == null) return;
			
			getAction("ad: añadir descuentos | cs: configurar sistema | ce: consultar estadísticas | gp: gestionar productos y categorias | ge: gestionar empleados | e: exit");
			switch(action) {
			case "ad":
				double valorMin;
				String cond = getUserInputString("Tipo de condición (c: cantidad | v: volumen | sc: sin condiciones): ");
				switch (cond) {
					case "c":
					case "v":
						valorMin = getUserInputDouble("Valor mínimo para la compensación (número no negativo): ");
						sc.nextLine();
						break;
					default:
						valorMin = 0;
				}
				
				try {
					LocalDateTime fechaInicio = getUserInputLocalDateTime("Fecha de inicio (formato DD/MM/YYYY hh:mm) : ");
					LocalDateTime fechaFin = getUserInputLocalDateTime("Fecha de inicio (formato DD/MM/YYYY hh:mm) : ");
				} catch(DateTimeParseException e) {
					System.out.println( "\u001B[31m" + "Fecha introducida con formato incorrecto\n" + "\u001B[0m");
				}
				
				String tipo = getUserInputString("Tipo de compensación (d: dinero | p: porcentaje | r: regalo): ");
				switch (tipo) {
				case "d": 
					
				case "p":
				case "r":
					String regalo = getUserInputLine("Enter para elegir entre todo el inventario o : ");
					Producto[] productos = tienda.getAlmacen().getProductosCoincidentes(regalo);
					
					for (int i = 1; i <= productos.length; i++) {
						System.out.println(i + ")" + productos[i]);
					}
					
					int index = getUserInputInt("Número de producto para elegir como regalo: ");
					if (index <= 0 || index > productos.length) break;
					// Mirar esto arriba!!!!!!!
					Producto p = productos[index-1];
				}
				
			case "cs":
				
			case "ce":
				
			case "gp":
				
			case "ge":

			}
		}
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
	
	/**
	 * Realiza la acción de valorar un artículo de segunda mano
	 * 
	 * @param empleado
	 * @throws InvalidArgumentException
	 */
	static void actionValorarArticulo(Empleado empleado) throws InvalidArgumentException {
		int i = 1;
		Valoracion[] valoraciones = tienda.getHistorial().getValoracionesPendientes();
		if (valoraciones.length < 1) throw new InvalidArgumentException("No existen valoraciones pendientes en este momento", "valorar articulo");
		for(Valoracion v : valoraciones) {
			System.out.println(i + ") " + v);
		}
		int num = getUserInputInt("Escriba el número del artículo que desea valorar: ");
		double precio = getUserInputDouble("Precio estimado: ");
		EstadoFisicoArticulo est = EstadoFisicoArticulo.valueOf(getUserInputString("Estado del artículo: "));
		
		tienda.getHistorial().valorarArticulo(empleado, tienda.getHistorial().getValoracionesPendientes()[num-1].getArticulo(), precio, est);
	}
	
	/**
	 * Realiza la acción de confirmar un intercambio
	 * 
	 * @param empleado
	 * @throws InvalidArgumentException
	 */
	static void actionConfirmarIntercambio(Empleado empleado) throws InvalidArgumentException {
		int i = 1;
		Intercambio[] intercambios = tienda.getHistorial().getIntercambiosPendientes();
		if(intercambios.length < 1) throw new InvalidArgumentException("No existen intercambios pendientes en este momento", "confirmar intercambio");
		for (Intercambio t : intercambios) {
			System.out.println(i + ") " + t);
		}
		int num = getUserInputInt("Escriba el número del intercambio que desea confirmar: ");
		tienda.getHistorial().validarIntercambio(empleado, intercambios[num-1]);
	}
	
	/**
	 * Realiza la acción de gestionar pedidos pendientes 
	 * 
	 * @param empleado
	 * @throws InvalidArgumentException
	 */
	static void actionGestionarPedidos(Empleado empleado) throws InvalidArgumentException {
		int i = 1;
		Pedido[] pedidos= tienda.getHistorial().getPedidosPendientes();
		if(pedidos.length < 1) throw new InvalidArgumentException("No existen pedidos pendientes en este momento", "gestionar pedidos");
		for(Pedido p : pedidos) {
			System.out.println(i + ") " + p);
		}
		int num = getUserInputInt("Escriba el número del pedido que desea avanzar: ");
		tienda.getHistorial().avanzarEstadoPedido(empleado, pedidos[num-1]);
	}
	
	/**
	 * Realiza la acción de gestionar productos 
	 * 
	 * @param empleado
	 * @throws InvalidArgumentException
	 * @throws DoubleDiscountException
	 */
	static void actionGestionarProductos(Empleado empleado) throws InvalidArgumentException, DoubleDiscountException {
		getAction("a: añadir producto | c: cargar fichero de productos | mp: modificar producto | bp: borrar producto | mc: modificar categorias | p: crear packs | e: exit");
		
		switch(action) {
		case "a":
			char tipo = getUserInputChar("Tipo de producto (c: comic | j: juego | f: figura): ");
			String nombre = getUserInputLine("Nombre: ");
			String desc = getUserInputLine("Descripción: ");
			double precio = getUserInputDouble("Precio: ");
			int uds = getUserInputInt("Unidades: ");
			
			List<Categoria> categorias = new ArrayList<Categoria>();
			for(Categoria cat : tienda.getAlmacen().getCategorias()) {
				getAction("Incluir categoria" + cat.getNombre() + "? s/n");
				if(action == "s") {
					categorias.add(cat);
				}
			}
			switch(tipo) {
			case 'C':
				int numPags = getUserInputInt("Número de páginas: ");
				String autor = getUserInputLine("Autor: ");
				String editorial = getUserInputLine("Editorial: ");
				String fecha[] = getUserInputString("Fecha(DD/MM/YYYY): ").split("/");
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
			
		case "c":
			String fichero = getUserInputString("Nombre del archivo: ");
			tienda.getAlmacen().anadirProductosDeFichero(fichero);
			
		case "mp":
		case "b":
		case "mc":
		case "p":
			
		}
	}
}
