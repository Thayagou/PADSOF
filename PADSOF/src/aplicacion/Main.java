package aplicacion;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import exceptions.*;
import sistema.*;
import usuario.*;
import venta.productos.*;

public class Main {
	protected static Tienda tienda = new Tienda();
	protected static String action = "";
	private static Scanner sc = new Scanner(System.in);
	private static String filename = "tienda.dat";
	
	static void cleanScreen() {
		for(int i = 0; i < 50 ; i++) showMessage("\n");
	}
	
	protected static void showMessage(String message) {
		//cleanScreen();
		System.out.println("\n"+message);
	}
	
	protected static char getUserInputChar(String message) {
		showMessage(message);
		char c = sc.next().charAt(0);
		sc.nextLine();
		return c;
	}
	
	protected static String getUserInputString(String message) {
		showMessage(message);
		String s = sc.next().trim();
		sc.nextLine();
		return s;
	}
	
	protected static String getUserInputLine(String message) {
		showMessage(message);
		return sc.nextLine().trim();
	}
	
	protected static int getUserInputInt(String message) {
		showMessage(message);
		int n = sc.nextInt();
		sc.nextLine();
		return n;
	}
	
	protected static double getUserInputDouble(String message) {
		showMessage(message);
		double r = sc.nextDouble();
		sc.nextLine();
		return r;
	}
	
	protected static LocalDateTime getUserInputLocalDateTime(String message) {
		showMessage(message);
		String input = sc.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime fecha = LocalDateTime.parse(input, formatter);
		
		return fecha;
	}
	
	protected static YearMonth getUserInputYearMonth(String message) {
		showMessage(message);
		String input = sc.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
		YearMonth fecha = YearMonth.parse(input, formatter);
		
		return fecha;		
	}
	
	protected static List<Integer> getUserInputIntList(String message) {
		showMessage(message);
		String input = sc.nextLine();
		String[] split = input.trim().split("\\s+");
		List<Integer> list = new ArrayList<>();
		for (String s: split) {
			list.add(Integer.parseInt(s));
		}
		
		return list;
	}
	
	
	protected static void getAction(String message) {
		showMessage(message);
		action = sc.next().trim();
		sc.nextLine();
	}
	
	/**
	 * Carga los datos de la tienda desde un fichero
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
	 */
	static void guardarTienda() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
			oos.writeObject(tienda);
			oos.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Usuario usuario;
		
		cargarTienda();
		
		try {
			while (!action.equals("e")) {

				usuario = menuInicio();
				if (usuario == null) break;
				
				if (usuario instanceof Gestor) {
			        ActionGestor.menuGestor(tienda.getGestor());
			    } else if (usuario instanceof Empleado) {
			        ActionEmpleado.menuEmpleado(tienda.getEmpleado(usuario.getNombre()));
			    } else if (usuario instanceof ClienteRegistrado) {
			        ActionCliente.menuCliente(tienda.getCliente(usuario.getNombre()));
			    }
			}
			
		} catch (RuntimeException e) {
			showMessage("Error no manejado: " + e.getMessage());
			e.printStackTrace();
		}
		
		guardarTienda();
		
		return;
	}
	
	static Usuario menuInicio() {
		while(!action.equals("e")) {
			
			getAction("r: registrarse | i: iniciar sesión | b: buscar | e: exit ");
			try {

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
					actionBuscarPorFiltros();
				}
			} catch (NotValidUserException | InvalidArgumentException e) {
				showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			}
		}
		return null;
	}
	
	static Producto[] actionBuscarPorFiltros() throws InvalidArgumentException {
		List<Categoria> categorias = new ArrayList<Categoria>();
		for(Categoria cat : tienda.getAlmacen().getCategorias()) {
			getAction("Incluir categoria " + cat.getNombre() + "? s/n");
			if(action.equals("s")) {
				categorias.add(cat);
			}
		}
		double precioMin = getUserInputDouble("Precio minimo: ");
		double precioMax = getUserInputDouble("Precio maximo: ");
		double estrellasMin = getUserInputDouble("Estrellas minimas: ");
		
		Producto[] productos = tienda.getAlmacen().getProductosPorFiltros(categorias.toArray(new Categoria[0]), precioMin, precioMax, estrellasMin);
		int i = 1;
		showMessage(productos.length + "");
		for(Producto p : productos) {
			showMessage(i++ + ") " + p);
		}
		return productos;
	}
}